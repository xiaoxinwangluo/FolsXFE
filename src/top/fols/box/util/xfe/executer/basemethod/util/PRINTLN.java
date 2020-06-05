package top.fols.box.util.xfe.executer.basemethod.util;

import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.lang.XFEClassInstance;
import top.fols.box.util.xfe.util.XFEConsole;

public class PRINTLN extends XFEBaseMethod {

	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		XFEClassInstance xfeclass = xfeexecute.getXFEClassInstance();
		XFEConsole console = xfeclass.getClassLoader().getConsole();
		if (args.length > 0) {
			for (Object object: args) {
				console.println(object);
			}
		} else {
			console.println();
		}
		return null;
	}

}
