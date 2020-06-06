package top.fols.box.util.xfe.executer;

import top.fols.box.util.xfe.lang.XFEClassInstance;
import top.fols.box.util.xfe.lang.XFEMethod;
import top.fols.box.util.xfe.util.XFEStackThrowMessageTool;

public class XFEThread extends Thread {
	protected XFEStack stack;
	protected XFEClassInstance instance;
	protected XFEMethod method;
	protected Object[] args;

	public XFEThread(XFEStack stack, XFEClassInstance instance, XFEMethod method, Object[] args) {
		this.stack = stack;
		this.instance = instance;
		this.method = method;
		this.args = args;
	}

	public XFEStack getStack() {
		return this.stack;
	}

	@Override
	public void run() {
		// TODO: Implement this method
		try {
			Object result = instance.executeMethod(this.stack, this.method, this.method.getName(), this.args, 0,
					this.args.length);
		} catch (Throwable e) {
			System.out.println("----XFE THREAD EXECUTE UNKNOWN EXCEPTION----");
			e.printStackTrace();
			System.out.println("----XFE THREAD EXECUTE UNKNOWN EXCEPTION----");
		}
		this.stack = null;
		this.instance = null;
		this.method = null;
		this.args = null;
	}

	public static XFEThread newInstance(XFEStack stack, XFEClassInstance instance, String name, Object[] args) {
		XFEMethod method = instance.getMethod(name);
		if (null == method) {
			stack.setThrow(XFEStackThrowMessageTool.notFoundXfeClassMethod(instance.getName(), name));
			return null;
		}
		XFEStack newStack = XFEStack.newXFEThreadStack(stack.now());
		XFEThread th = new XFEThread(newStack, instance, method, args);
		return th;
	}
}
