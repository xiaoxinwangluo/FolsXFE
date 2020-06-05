package top.fols.box.util.xfe.lang.autoloader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import top.fols.box.io.os.XFile;
import top.fols.box.statics.XStaticFixedValue;
import top.fols.box.util.XKeyMap;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.lang.XFEClassLoader;

/*
 * 自动加载 
 * 编译代码优先
 * 其次加载未编译代码(UTF-8编码)
 */
public abstract class XFEAutoCodeLoaderAbstract {
	private File file;
	public XFEAutoCodeLoaderAbstract(String file) {
		this(new File(file));
	}
	public XFEAutoCodeLoaderAbstract(File file) {
		this.file = file;
	}

	public File getFile() {
		return this.file;
	}

	public static String[] matchExtensionName(String[] arr, String... extensionName) {
		if (arr == null) {
			return XStaticFixedValue.nullStringArray;
		}
		List<String> newList = new ArrayList<>();
		for (String file:arr) {
			String exName = XFile.getExtensionName(file);
			if (exName != null) {
				for (String suffix:extensionName) {
					if (suffix != null && suffix.equals(exName)) {
						newList.add(file);
					}
				}
			}
		}
		return newList.toArray(new String[newList.size()]);
	}
	public static String[] deleteExtensionName(String[] fileName) {
		if (fileName == null) {
			return XStaticFixedValue.nullStringArray;
		}
		XKeyMap<String> newList = new XKeyMap<>();
		for (String file:fileName) {
			newList.put(XFile.getNameNoExtension(file));
		}
		return newList.getMap().keySet().toArray(new String[newList.size()]);
	}


	public abstract void reLoad();
	public abstract String[] listClsName();
	public abstract XFEClass loadCode(XFEClassLoader clsLoader, String clsName) throws IOException;
}
