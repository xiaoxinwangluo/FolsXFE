package top.fols.box.util.xfe.executer.basemethod.lang;

import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;
import top.fols.box.util.xfe.util.XFEStackThrowMessageTool;

public class GETCLASS extends XFEBaseMethod {
	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		XFEStack stack = xfeexecute.getStack();
		if (args.length == 1) {
			Object object = args[0];
			if (null != object) {
				XFEClass xfec = XFEKeyWords.getXFEClassInterfaceGetClass(object);
				if (null == xfec) {
					stack.setThrow(XFEStackThrowMessageTool.cannotFromObjectGetXfeClass(object));
					return null;
				}
				return xfec;
			}
		}
		super.throwNotFoundMethod(stack, args);
		return null;
	}
}
