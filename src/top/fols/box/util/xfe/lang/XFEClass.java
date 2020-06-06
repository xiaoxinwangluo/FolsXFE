package top.fols.box.util.xfe.lang;
import java.util.HashMap;
import java.util.Map;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;
import top.fols.box.util.xfe.util.interfacelist.XFEInterfaceGetXFEClass;
import top.fols.box.util.xfe.executer.variablepoint.abstractlist.XFEAbstractVariablePoint;
import top.fols.box.util.xfe.executer.XFEExecute;

public class XFEClass implements XFEInterfaceGetXFEClass {
	protected String fileName;//class filename

	protected Map<String, XFEMethod> methods;//methods
	protected XFEClassLoader classLoader;//class loader
	protected String name;//class name
	protected boolean isInstance;//is instance
	protected boolean isStaticInstance;//is static instance
	protected Map<String, Object> variable;//variable
	protected XFEClassInstance staticInstance;//static instance
	protected XFEFinalVariableManager finalVariable;//String char and base type data

	//instance
	protected XFEClass(XFEClass cls) {
		this.fileName = cls.fileName;
		this.classLoader = cls.classLoader;
		this.name = cls.name;
		this.methods = cls.methods;
		this.variable = new HashMap<>();
		this.isInstance = true;
		this.isStaticInstance = cls.isStaticInstance;
		this.staticInstance = cls.staticInstance;
		this.finalVariable = cls.finalVariable;
	}





	public XFEClassInstance getStaticInstance(XFEStack stack) {
		if (null == this.staticInstance) {
			XFEClassInstance instance = new XFEClassInstance(this); 
			this.staticInstance = instance;
			instance.staticInstance = instance;
			instance.isStaticInstance = true;
			instance.executeStaticMethod(stack);
		}
		return this.staticInstance;
	}



	private XFEClass() {
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



	private Map<String, XFEMethod> getMethodMap0() {
		return null == this.methods ?this.methods = new HashMap<>(): this.methods;
	}
	public void putMethod(String name, XFEMethod m) {
		this.getMethodMap0().put(name, m);
	}
	public void putMethod(XFEMethod m) {
		this.putMethod(null == m ?null: m.getName(), m);
	}
	public XFEMethod getMethod(String name) {
		return this.getMethodMap0().get(name);
	}

	public String[] listMethodName() {
		return this.getMethodMap0().keySet().toArray(new String[this.getMethodMap0().size()]);
	}


	XFEFinalVariableManager getFinalVariableManager0() {
		return null == this.finalVariable ?this.finalVariable = XFEFinalVariableManager.newInstance(): this.finalVariable;
	}
	void releaseFinalVariableManagerCache0() {
		if (null != this.finalVariable) {
			this.finalVariable.releaseCache();
		}
	}
	public XFEAbstractVariablePoint getFinalVariablePoint() {
		return this.getFinalVariableManager0().getXFEFinalVariablePoint();
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
		XFEMethod method = this.getMethodMap0().get(XFEKeyWords.INIT);
		return method;
	}
	protected XFEMethod getStaticMethod() {
		XFEMethod method = this.getMethodMap0().get(XFEKeyWords.STATIC);
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
	public static String getStandardFormatFileName(String className) {
		return new StringBuilder(null == className ?"null": className).append(XFEKeyWords.CODE_FILE_EXTENSION_NAME_SEPARATOR).append(XFEClass.getClassFileExtensionName()).toString();
	}
}
