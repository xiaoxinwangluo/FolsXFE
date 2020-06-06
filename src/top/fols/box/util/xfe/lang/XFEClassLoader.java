package top.fols.box.util.xfe.lang;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethodManager;
import top.fols.box.util.xfe.lang.autoloader.XFEAutoCodeLoaderManager;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;
import top.fols.box.util.xfe.util.XFECodeContent;
import top.fols.box.util.xfe.util.XFEConsole;
import top.fols.box.util.xfe.util.XFEJavaReflectManager;
import top.fols.box.util.xfe.util.XFEStackThrowMessageTool;

public class XFEClassLoader {
	private static long instanceMod = 0;
	private static final XFEClassLoader DEFAULT_CLASSLOADER = new XFEClassLoader("CLASSLOADER_DEFAULT");


	public static XFEClassLoader getDefaultLoader() {
		return XFEClassLoader.DEFAULT_CLASSLOADER;
	}
	public static XFEClassLoader newInstance() {
		return new XFEClassLoader("CLASSLOADER_CONSTRUCTOR");
	}


	private XFEClassLoader(String name) {
		this.setId(XFEClassLoader.instanceMod++);
		this.setName(name);

		this.xfeClassMap = new HashMap<>();
		this.xfeAutoCodeLoaderManager = new XFEAutoCodeLoaderManager();

		this.xfeConsole = XFEConsole.DEFAULT_JAVA_SYSTEM_OUT; //not recommended to modify
		this.xfeBaseMethodManager = XFEKeyWords.DEFAULT_BASEMETHOD_MANAGER; //not recommended to modify

		this.javaReflectManager = XFEJavaReflectManager.newInstance();
	}

    /**
     * assume that this ClassLoader can be executed without a Class running, 
     * and release memory
     */
	public void releaseClassLoader() {
		this.xfeClassMap.clear();
		this.xfeAutoCodeLoaderManager.clearLoader();
        //this.xfeConsole = XFEConsole.DEFAULT_JAVA_SYSTEM_OUT; //not recommended to modify
		//this.xfeBaseMethodManager = XFEKeyWords.DEFAULT_BASEMETHOD_MANAGER; //not recommended to modif
        this.javaReflectManager.releaseCache();
	}




	private Map<String, XFEClass> xfeClassMap;
	public boolean addClass(XFEClass cls) throws RuntimeException {
		if (null == cls || this.xfeClassMap.containsKey(cls.getName())) {
			throw new RuntimeException("exist class: " + (null == cls ?null: cls.getName()));
		}
		this.xfeClassMap.put(cls.getName(), cls);
		return true;
	}
	public boolean hasClass(String name) {
		return this.xfeClassMap.containsKey(name);
	}
	public XFEClass forName(String name) throws RuntimeException {
		XFEClass c = this.xfeClassMap.get(name);
		if (null != c) {
			return c;
		}
		XFEClass ac = null;
		try {
			ac = this.fromAutoCodeLoaderLoadCode(name);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return ac;
	}
    private XFEClass fromAutoCodeLoaderLoadCode(String n) throws IOException, RuntimeException {
		XFEClass c = null;
		if (this.xfeAutoCodeLoaderManager != null) {
			c = this.autoCodeLoaderForName(n);
		}
		return c;
	}
	public void removeClass(String name) {
        XFEClass c = this.xfeClassMap.get(name);
        if (null != c) {
			c.classLoader = null;
            this.xfeClassMap.remove(name);
            c = null;
        }
    }
	public int getClassCount() {
        return this.xfeClassMap.size();
    }
	
	
	
	
	
	public static XFEClass getRootClass(XFEClass cls)  throws RuntimeException {
		XFEClassLoader classLoader = cls.getClassLoader();
		if (null == classLoader) {
			throw new RuntimeException(XFEStackThrowMessageTool.noXFEClassLoader(cls));
		}
		return classLoader.forName(cls.getName());
	}





	private XFEAutoCodeLoaderManager xfeAutoCodeLoaderManager;
	private XFEClass autoCodeLoaderForName(String clsName) throws IOException, RuntimeException  {
		if (null != this.xfeAutoCodeLoaderManager && !this.hasClass(clsName)) {
			return xfeAutoCodeLoaderManager.loadCodeAndAddXClass(this, clsName);
		}
		return null;
	}
	public XFEAutoCodeLoaderManager getAutoLoaderCodeManager() {
		return this.xfeAutoCodeLoaderManager;
	}


	private XFEConsole xfeConsole;
	public void setConsole(XFEConsole console) {
		this.xfeConsole = console;
	}
	public XFEConsole getConsole() {
		return xfeConsole;
	}





	private XFEBaseMethodManager xfeBaseMethodManager;
	public XFEBaseMethodManager getBaseMethodManager() {
		return this.xfeBaseMethodManager;
	}
	public XFEClassLoader setBaseMethodManager(XFEBaseMethodManager mm) {
		this.xfeBaseMethodManager = mm;
		return this;
	}


	private String name;
	public XFEClassLoader setName(String name) {
		this.name = name;
		return this;
	}
	public String getName() {
		return this.name;
	}


	private long nowid = 0;
	private long getId() {
		return this.nowid;
	}
	private void setId(long id) {
		this.nowid = id;
	}



	private XFEJavaReflectManager javaReflectManager;
	public void setJavaReflectManager(XFEJavaReflectManager javaReflectMatcherManager) {
		this.javaReflectManager = javaReflectMatcherManager;
	}
	public XFEJavaReflectManager getJavaReflectManager() {
		return javaReflectManager;
	}




	@Override
	public String toString() {
		// TODO: Implement this method
		return new StringBuilder("xfeclassloader").append(' ').append(this.getName()).append('@').append(this.getId()).toString();
	}


	public XFEClass loadCode(XFECodeContent content) throws IOException, RuntimeException {
		XFECodeLoader xfecodeloader = new XFECodeLoader();
		XFEClass cls = xfecodeloader.setCode(content).loadTo(this);
		xfecodeloader.clearCode();
		return cls;
	}
}
