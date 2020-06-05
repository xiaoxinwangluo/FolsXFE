package top.fols.box.util.xfe.executer.basemethod.util;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.util.XFEUtil;

public class RANDOM extends XFEBaseMethod {

	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		if (args.length == 2) {
			Object min = args[0];
			Object max = args[1];
			Object random = XFEUtil.random(min, max);
			if (null != random) {
				return random;
			}
		}
		XFEStack stack = xfeexecute.getStack();
		super.throwNotFoundMethod(stack, args);
		return null;
	}

}
