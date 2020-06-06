package top.fols.box.util.xfe.executer.variablepoint.abstractlist;

import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.util.XFEStackThrowMessageTool;

public class XFEAbstractVariablePointTool {
	public static Object getVariable(XFEAbstractVariablePoint point,
							  XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name) {
		try {
			return point.getVariableProcess(execStatus, xfeexecute, name);
		} catch (Throwable e) {
			xfeexecute.getStack().setJavaThrow(XFEStackThrowMessageTool.getJavaStackString(e), e);
			return null;
		}
	}

	public static Object setVariable(XFEAbstractVariablePoint point,
							  XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name, Object value) {
		try {
			return point.setVariableProcess(execStatus, xfeexecute, name, value);
		} catch (Throwable e) {
			xfeexecute.getStack().setJavaThrow(XFEStackThrowMessageTool.getJavaStackString(e), e);
			return null;
		}
	}

	public static Object executeMethod(XFEAbstractVariablePoint point,
								XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name, Object[] args) {
		try {
			return point.executeMethodProcess(execStatus, xfeexecute, name, args);
		} catch (Throwable e) {
			xfeexecute.getStack().setJavaThrow(XFEStackThrowMessageTool.getJavaStackString(e), e);
			return null;
		}
	}

}
