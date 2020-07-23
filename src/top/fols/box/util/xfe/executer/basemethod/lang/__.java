package top.fols.box.util.xfe.executer.basemethod.lang;

import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;

/**
 * no method name --> "(param)" return param[0]
 */
public class __ extends XFEBaseMethod {
	public __() {
		super.setName("");
	}

	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		if (args.length == 1) {
			return args[0];
		}
		XFEStack stack = xfeexecute.getStack();
		super.throwNotFoundMethod(stack, args);
		return null;
	}
}

