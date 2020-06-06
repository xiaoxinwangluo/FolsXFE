package top.fols.box.util.xfe.executer.variablepoint.abstractlist;

import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.util.XFEStackThrowMessageTool;

public interface XFEAbstractVariablePoint {

	public abstract Object getVariableProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name)
			throws Throwable;

	public abstract Object setVariableProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name,
			Object value) throws Throwable;

	public abstract Object executeMethodProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name,
			Object... value) throws Throwable;
}
