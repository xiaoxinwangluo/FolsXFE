package top.fols.box.util.xfe.executer.basemethod.lang;

import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;
import top.fols.box.util.xfe.util.XFEStackTool;

public class GETXFECLASS extends XFEBaseMethod {
	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		XFEStack stack = xfeexecute.getStack();
		if (args.length >= 1) {
			Object object = args[0];
			if (null != object) {
				XFEClass xfec = XFEKeyWords.getXFEClassInterfaceGetClass(object);
				if (null == xfec) {
					stack.setThrow(XFEStackTool.cannotFromObjectGetXfeClass(object));
					return null;
				}
				return xfec;
			}
		}
		return null;
	}
}
