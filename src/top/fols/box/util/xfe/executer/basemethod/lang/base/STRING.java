package top.fols.box.util.xfe.executer.basemethod.lang.base;

import top.fols.box.util.XObjects;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.lang.XFEClass;

public class STRING extends XFEBaseMethod {
	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		if (args.length == 0) {
			return "";
		} else if (args.length == 1) {
			return null == args[0] ?"null": args[0].toString();
		} else {
			StringBuilder sb = new StringBuilder();
			for (Object content: args) {
				sb.append(content);
			} 
			return sb.toString();
		}
	}
}
