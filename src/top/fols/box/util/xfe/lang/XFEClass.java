package top.fols.box.util.xfe.lang;
import java.util.HashMap;
import java.util.Map;
import top.fols.box.util.XCHashMap;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.variablepoint.abstractlist.XFEAbstractVariablePoint;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;
import top.fols.box.util.xfe.util.interfacelist.XFEInterfaceGetXFEClass;

public class XFEClass implements XFEInterfaceGetXFEClass {
	protected String fileName;//class filename

	protected Map<String, XFEMethod> methods;//methods
	protected XFEClassLoader classLoader;//class loader
	protected String name;//class name
	protected int RUNNING_MOD = 0;//instance/staticinstance
	protected XCHashMap<String, Object> variable;//variable
	protected XFEClassInstance staticInstance;//static instance
	protected XFEFinalVariableManager finalVariable;//String char and base type data

	//instance
	protected XFEClass(XFEClass cls) {
		this.fileName = cls.fileName;
		this.classLoader = cls.classLoader;
		this.name = cls.name;
		this.methods = cls.methods;
		this.variable = new XCHashMap<>();
		this.RUNNING_MOD = cls.RUNNING_MOD;
		this.staticInstance = cls.staticInstance;
		this.finalVariable = cls.finalVariable;
	}
	protected XFEClass(XFEClassLoader classLoader) {
		this.classLoader = classLoader;
	}
	private XFEClass() {
		super();
	}




	private static class RunningModifier {
		private static final int INSTANCE = 0x00000001;
		private static final int STATIC_INSTANCE = 0x00000002;

		public static boolean is(int mod, int m) {
			return (mod & m) != 0;
		}
		public static int add(int mod, int m) {
			return mod | m;
		}
		public static int less(int mod, int m) {
			return is(mod, m) ? mod - m: mod;
		}
	}
	protected void setStaticInstance(boolean b) {
		RUNNING_MOD = b
			? RunningModifier.add(RUNNING_MOD, RunningModifier.STATIC_INSTANCE)
			: RunningModifier.less(RUNNING_MOD, RunningModifier.STATIC_INSTANCE);
	}
    public boolean isStaticInstance() {
		return RunningModifier.is(RUNNING_MOD, RunningModifier.STATIC_INSTANCE);
	}
	protected void setInstance(boolean b) {
		RUNNING_MOD = b
			? RunningModifier.add(RUNNING_MOD, RunningModifier.INSTANCE)
			: RunningModifier.less(RUNNING_MOD, RunningModifier.INSTANCE);
	}
	public boolean isInstance() {
		return RunningModifier.is(RUNNING_MOD, RunningModifier.INSTANCE);
	}






	public XFEClassInstance getStaticInstance(XFEStack stack) {
		if (null == this.staticInstance) {
			XFEClassInstance instance = new XFEClassInstance(this){
				@Override
				public Object getVariable(XFEStack stack, String name) {
					return XFEKeyWords.getVariable(stack, this.variable, "static", name);
				}
			}; 
			this.staticInstance = instance;
			instance.staticInstance = instance;
			instance.setInstance(true);
			instance.setStaticInstance(true);
			instance.executeStaticMethod(stack);
		}
		return this.staticInstance;
	}






	@Override
	public XFEClass getXFEClass() {
		// TODO: Implement this method
		return this;
	}

	public XFEClassLoader getClassLoader() {
		return this.classLoader;
	}
	

	public Object setVariable(String name, Object newValue) {
		this.variable.put(name, newValue);
		return newValue;
	}

	public Object getVariable(XFEStack stack, String name) {
		return XFEKeyWords.getVariable(stack, this.variable, "global", name);
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
		if (this.isStaticInstance()) {
			sb.append('@').append(XFEKeyWords.STATIC);
		} else if (this.isInstance()) {
			sb.append('@').append(super.hashCode());
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
		instance.setInstance(true);
		instance.setStaticInstance(false);
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
