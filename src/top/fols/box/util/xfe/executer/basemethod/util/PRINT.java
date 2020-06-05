package top.fols.box.util.xfe.executer.basemethod.util;

import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.lang.XFEClassInstance;

public class PRINT extends XFEBaseMethod {

	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		XFEClassInstance xfeclass = xfeexecute.getXFEClassInstance();
		for (Object object: args) {
			xfeclass.getClassLoader().getConsole().print(object);
		}
		return null;
	}
}
