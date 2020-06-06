package top.fols.box.util.xfe.lang;
import java.util.HashMap;
import java.util.Map;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;
import top.fols.box.util.xfe.util.interfacelist.XFEInterfaceGetXFEClass;

public class XFEClass implements XFEInterfaceGetXFEClass {

	protected String fileName;
	protected Map<String, XFEMethod> methods;
	protected XFEClassLoader classLoader;
	protected String name;
	protected boolean isInstance;
	protected boolean isStaticInstance;
	protected Map<String, Object> variable;
	protected XFEClassInstance staticInstance;

	public XFEClassInstance getStaticInstance(XFEStack stack) {
		if (null == this.staticInstance) {
			XFEClassInstance instance = new XFEClassInstance(this); this.staticInstance = instance;
			instance.staticInstance = instance;
			instance.isStaticInstance = true;
			instance.executeStaticMethod(stack);
		}
		return this.staticInstance;
	}


	protected XFEClass() {
	}

	public XFEClass(XFEClassLoader classLoader) {
		this.classLoader = classLoader;
	}





	@Override
	public XFEClass getXFEClass() {
		// TODO: Implement this method
		return this;
	}

	public XFEClassLoader getClassLoader() {
		return this.classLoader;
	}

	public boolean isInstance() {
		return this.isInstance;
	}

	public boolean isStaticInstance() {
		return this.isStaticInstance;
	}

	public Object setVariable(String name, Object newValue) {
		this.variable.put(name, newValue);
		return newValue;
	}

	public Object getVariable(String name) {
		return this.variable.get(name);
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileName() {
		return fileName;
	}


	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}


	private Map<String, XFEMethod> getMethodMap() {
		if (null == this.methods) {
			this.methods = new HashMap<>();
		}
		return this.methods;
	}
	public void putMethod(String name, XFEMethod m) {
		this.getMethodMap().put(name, m);
	}
	public void putMethod(XFEMethod m) {
		this.putMethod(null == m ?null: m.getName(), m);
	}
	public XFEMethod getMethod(String name) {
		return this.getMethodMap().get(name);
	}

	public String[] listMethodName() {
		return this.getMethodMap().keySet().toArray(new String[this.getMethodMap().size()]);
	}

	@Override
	public String toString() {
		// TODO: Implement this method
		StringBuilder sb = new StringBuilder("xfeclass").append(' ').append(this.name);
		if (this.isStaticInstance) {
			sb.append('@').append(XFEKeyWords.STATIC);
		} else if (this.isInstance) {
			sb.append('@').append(this.hashCode());
		}
		return sb.toString();
	}


	protected XFEMethod getInitMethod() {
		XFEMethod method = this.getMethodMap().get(XFEKeyWords.INIT);
		return method;
	}
	protected XFEMethod getStaticMethod() {
		XFEMethod method = this.getMethodMap().get(XFEKeyWords.STATIC);
		return method;
	}

	public XFEClassInstance newInstance(XFEStack stack, Object[] args) {
		return this.newInstance(stack, args, 0, args.length);
	}
	public XFEClassInstance newInstance(XFEStack stack, Object[] args, int off, int len) {
		// TODO: Implement this method
		XFEClassInstance instance;
		instance = new XFEClassInstance(this);
		instance.staticInstance = this.getStaticInstance(stack);
		instance.isStaticInstance = false;
		instance.executeInitMethod(stack, args, off, len);
		return instance;
	}







	public static String getClassFileExtensionNameSeparator() {
		return XFEKeyWords.CODE_FILE_EXTENSION_NAME_SEPARATOR;
	}
	public static String getClassFileExtensionName() {
		return XFEKeyWords.CODE_FILE_EXTENSION_NAME;
	}

	public static String getClassFileName(String className) {
		return new StringBuilder(null == className ?"null": className).append(XFEKeyWords.CODE_FILE_EXTENSION_NAME_SEPARATOR).append(XFEClass.getClassFileExtensionName()).toString();
	}
}
