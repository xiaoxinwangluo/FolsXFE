package top.fols.box.util.xfe.lang;

import java.util.HashMap;
import java.util.Map;
import top.fols.box.statics.XStaticFixedValue;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;

public final class XFEClassInstance extends XFEClass {

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

	public Map<String, Object> cloneParam() {
		return new HashMap<String, Object>(this.variable);
	}
}
