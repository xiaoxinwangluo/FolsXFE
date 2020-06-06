package top.fols.box.util.xfe.executer.basemethod.lang.array;

import java.lang.reflect.Array;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.util.XFEStackThrowMessageTool;
import top.fols.box.util.xfe.util.XFEUtil;
import top.fols.box.util.xfe.lang.XFEClassInstance;

public class NEWARRAY extends XFEBaseMethod {
	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		final int argslen = args.length;
		if (argslen >= 1) {
			Class cls = null;
			if (args[0] instanceof Class) {
				cls = (Class)args[0];
			} else {
				cls = XFEUtil.forName(null, null == args[0] ?null: args[0].toString());
			}
			int[] lengths = null;
			if (argslen == 2) {
				if (args[1] instanceof int[]) {
					lengths = (int[])args[1];
				} else {
					lengths = new int[]{ XFEUtil.toint(args[1]) };
				}
			} else {
				lengths = XFEUtil.tointArray(args, 1, args.length - 1);
			}
			return Array.newInstance(cls, lengths);
		}
		XFEClassInstance xfeclass = xfeexecute.getXFEClassInstance();
		XFEStack stack = xfeexecute.getStack();
		stack.setThrow(XFEStackThrowMessageTool.notFoundXfeClassMethod(xfeclass.getName(), this.getName(), args));
		return null;
	}
}
