package top.fols.box.util.xfe.executer.basemethod;

import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.util.XFEStackTool;

public abstract class XFEBaseMethod {
	private String name;

	public XFEBaseMethod() {
		String name = this.getClass().getSimpleName().toLowerCase();
		this.setName(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Object execute(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object... args) {
		XFEStack stack = xfeexecute.getStack();
		try {
			return this.executeProcess(execStatus, xfeexecute, args);
		} catch (Throwable e) {
			stack.setJavaThrow(XFEStackTool.getJavaStackString(e), e);
			return null;
		}
	}
	protected abstract Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object... args)
			throws Throwable;

			
	public void throwNotFoundMethod(XFEStack stack, Object[] args) {
		stack.setThrow(XFEStackTool.notFoundXfeClassBaseMethod(this.getName(), args));
	}
}
