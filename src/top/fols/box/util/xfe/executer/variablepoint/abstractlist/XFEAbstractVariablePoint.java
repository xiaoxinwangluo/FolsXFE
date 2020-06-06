package top.fols.box.util.xfe.executer.variablepoint.abstractlist;

import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.util.XFEStackThrowMessageTool;

public abstract class XFEAbstractVariablePoint {

	public abstract Object getVariableProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name)
			throws Throwable;

	public abstract Object setVariableProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name,
			Object value) throws Throwable;

	public abstract Object executeMethodProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name,
			Object... value) throws Throwable;

	public Object getVariable(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name) {
		try {
			return this.getVariableProcess(execStatus, xfeexecute, name);
		} catch (Throwable e) {
			xfeexecute.getStack().setJavaThrow(XFEStackThrowMessageTool.getJavaStackString(e), e);
			return null;
		}
	}

	public Object setVariable(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name, Object value) {
		try {
			return this.setVariableProcess(execStatus, xfeexecute, name, value);
		} catch (Throwable e) {
			xfeexecute.getStack().setJavaThrow(XFEStackThrowMessageTool.getJavaStackString(e), e);
			return null;
		}
	}

	public Object executeMethod(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name, Object[] args) {
		try {
			return this.executeMethodProcess(execStatus, xfeexecute, name, args);
		} catch (Throwable e) {
			xfeexecute.getStack().setJavaThrow(XFEStackThrowMessageTool.getJavaStackString(e), e);
			return null;
		}
	}

}
