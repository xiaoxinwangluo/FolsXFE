package top.fols.box.util.xfe.lang;

import top.fols.box.statics.XStaticFixedValue;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.variablepoint.abstractlist.XFEAbstractVariablePoint;
import top.fols.box.util.xfe.util.XFEStackThrowMessageTool;

public final class XFEClassInstance extends XFEClass implements XFEAbstractVariablePoint {



	protected XFEClassInstance(XFEClass cls) {
		//System.out.println("创建: " + cls);
//		super.fileName = cls.fileName;
//		super.classLoader = cls.classLoader;
//		super.name = cls.name;
//		super.methods = cls.methods;
//		super.variable = new HashMap<>();
//		super.isInstance = true;
//		super.isStaticInstance = cls.isStaticInstance;
//		super.staticInstance = cls.staticInstance;
//		super.finalVariable = cls.finalVariable;
		super(cls);
	}



	protected XFEClassInstance executeStaticMethod(XFEStack stack) {
		XFEMethod method = this.getStaticMethod();
		if (null != method) {
			this.executeMethod(stack, method, method.getName(), XStaticFixedValue.nullObjectArray);
		}
		return this;
	}
	protected XFEClassInstance executeInitMethod(XFEStack stack, Object[] args, int off, int len) {
		XFEMethod method = this.getInitMethod();
		if (null != method) {
			this.executeMethod(stack, method, method.getName(), args, off, len);
		}
		return this;
	}





	public Object executeMethod(XFEStack stack, String method, Object[] args) {
		return this.executeMethod(stack, this.getMethod(method), method, args, 0, args.length);
	}
	public Object executeMethod(XFEStack stack, XFEMethod method, String methodName, Object[] args) {
		return this.executeMethod(stack, method, methodName, args, 0, args.length);
	}
	public Object executeMethod(XFEStack stack, XFEMethod method, String methodName, Object[] args, int off, int len) {
		return XFEExecute.execute(this, stack, method, methodName, args, off, len);
	}


	
	
	
	
	

	@Override
	public Object getVariableProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name) throws Throwable {
		return super.getVariable(xfeexecute.getStack(), name);
	}
	@Override
	public Object setVariableProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name, Object value) throws Throwable {
		return super.setVariable(name, value);
	}
	@Override
	public Object executeMethodProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name, Object[] value) throws Throwable {
		XFEStack stack = xfeexecute.getStack();
		XFEClassInstance cls = this;
		XFEMethod method = cls.getMethod(name);
		if (null == method) {
			stack.setThrow(XFEStackThrowMessageTool.notFoundXfeClassMethod(this.getName(), name));
			return null;
		}
		Object result = XFEExecute.execute(this, stack, method, name, value, 0, value.length);
		return result;
	}



}
