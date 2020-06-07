package top.fols.box.util.xfe.lang.autoloader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.lang.XFEClassLoader;
import top.fols.box.util.xfe.util.XFEStackThrowMessageTool;
import top.fols.box.util.xfe.util.XFECodeContent;

/*
 * 自动加载 
 * 加载未编译代码
 */
public class XFEAutoCodeLoaderManager {
	private Map<String, XFEAutoCodeLoaderAbstract> classNameCorrespondsToLoaderMap = new HashMap<>(); // 每个类名对应一个代码动态加载器
	private Map<XFEAutoCodeLoaderAbstract, ?> loaderList = new HashMap<>();
	public XFEAutoCodeLoaderManager() {
	}
    
    
    
	public void clearLoader(){
		synchronized (this.loaderList) {
			this.classNameCorrespondsToLoaderMap.clear();
			this.classNameCorrespondsToLoaderMap = new HashMap<>();
			this.loaderList.clear();
			this.loaderList = new HashMap<>();
		}
	}
	public XFEAutoCodeLoaderAbstract[] listLoader() {
		synchronized (this.loaderList) {
			return this.loaderList.keySet().toArray(new XFEAutoCodeLoaderAbstract[this.loaderList.size()]);
		}
	}
	public boolean containsLoader(XFEAutoCodeLoaderAbstract al) {
		synchronized (this.loaderList) {
			return this.loaderList.containsKey(al);
		}
	}
	public void addLoader(XFEAutoCodeLoaderAbstract loader) {
		synchronized (this.loaderList) {
			String[] clss = loader.listClsName();
			for (String name:clss) {
				if (name == null) {
					continue;
				}
				this.classNameCorrespondsToLoaderMap.put(name, loader);
			}
			this.loaderList.put(loader, null);
		}
	}
	public void removeLoader(XFEAutoCodeLoaderAbstract loader) {
		synchronized (this.loaderList) {
			List<String> rmName = new ArrayList<>();
			for (String clsName: this.classNameCorrespondsToLoaderMap.keySet()) {
				XFEAutoCodeLoaderAbstract i = this.classNameCorrespondsToLoaderMap.get(clsName);
				if (i == null) {
					continue;
				}
				if (i.equals(loader)) {
					rmName.add(clsName);
				}
			}
			for (String rm: rmName) {
				this.classNameCorrespondsToLoaderMap.remove(rm);
			}
			this.loaderList.remove(loader);
		}
	}



	//寻找自动自动加载器加载代码并添加到ClassLoader
	public XFEClass loadCodeAndAddXClass(XFEClassLoader clsLoader,
										 String clsName) throws IOException, RuntimeException {
		synchronized (loaderList) {
			XFEAutoCodeLoaderAbstract loader = this.classNameCorrespondsToLoaderMap.get(clsName);
			if (loader == null) {
				throw new RuntimeException(XFEStackThrowMessageTool.notFoundXfeClass(clsName));
			}
			XFEClass bs = loader.loadCode(clsLoader, clsName);
			if (bs == null) {
				throw new RuntimeException(XFEStackThrowMessageTool.notFoundXfeClass(clsName));
			}
			return bs;
		}
	}
	public XFECodeContent getCodeContent(String clsName) throws IOException, RuntimeException {
		synchronized (loaderList) {
			XFEAutoCodeLoaderAbstract loader = this.classNameCorrespondsToLoaderMap.get(clsName);
			if (loader == null) {
				throw new RuntimeException(XFEStackThrowMessageTool.notFoundXfeClass(clsName));
			}
			XFECodeContent bs = loader.getCode(clsName);
			if (bs == null) {
				throw new RuntimeException(XFEStackThrowMessageTool.notFoundXfeClass(clsName));
			}
			return bs;
		}
	}
	
	//加载所有自动代码加载器的类文件到ClassLoader
	public static XFEClass[] loadCodeAndAddAllXClass(XFEClassLoader clsLoader,
													 XFEAutoCodeLoaderAbstract... loaders) throws IOException {
		List<XFEClass> clss = new ArrayList<>();
		for (XFEAutoCodeLoaderAbstract loader: loaders) {
			if (loader == null) {
				continue;
			}
			String[] clsNames = loader.listClsName();
			for (String namei: clsNames) {
				XFEClass bs = loader.loadCode(clsLoader, namei);
				clss.add(bs);
			}
		}
		return clss.toArray(new XFEClass[clss.size()]);
	}
	
	
	
	
	
	
	
	
	// 命令所有AutoLoader重新载入文件列表
	public void reLoadXClassNameList() {
		synchronized (this.loaderList) {
			Map<String, XFEAutoCodeLoaderAbstract> oriMap = this.classNameCorrespondsToLoaderMap;
			Map<String, XFEAutoCodeLoaderAbstract> newMap = new HashMap<>();
			Collection<XFEAutoCodeLoaderAbstract> loaders = oriMap.values();
			for (XFEAutoCodeLoaderAbstract lai: loaders) {
				lai.reLoad();
				String[] clss = lai.listClsName();
				for (String name:clss) {
					if (name == null) {
						continue;
					}
					newMap.put(name, lai);
				}
			}
			this.classNameCorrespondsToLoaderMap = newMap;
			oriMap.clear();
		}
	}

//	private XFEAutoCodeLoaderAbstract forFileSearchLoader(File file) {
//		String path = XFile.getCanonicalPath(file.getAbsolutePath());
//		synchronized (list) {
//			for (XFEAutoCodeLoaderAbstract ai: list.map().keySet()) {
//				if (ai == null) {
//					continue;
//				} else if  (XFile.getCanonicalPath(ai.getFile().getAbsolutePath()).equals(path)) {
//					return ai;
//				}
//			}
//			return null;
//		}
//	}


	
	public static XFEAutoCodeLoaderAbstract forFile(File file) throws RuntimeException {
		if (XFEDirCodeLoader.isDirectory(file)) {
			return new XFEDirCodeLoader(file);
		} else if (XFEZipCodeLoader.isZipFile(file)) {
			return new XFEZipCodeLoader(file);
		}
		throw new RuntimeException("cannot find the corresponding loader: " + file.getAbsolutePath());
	}
}
