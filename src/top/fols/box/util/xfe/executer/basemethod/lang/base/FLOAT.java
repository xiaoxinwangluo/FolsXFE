package top.fols.box.util.xfe.executer.basemethod.lang.base;

import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.util.XFEUtil;

public class FLOAT extends XFEBaseMethod {
	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		return XFEUtil.tofloat(args[0]);
	}
}
