package top.fols.box.util.xfe.executer.basemethod.lang;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.executer.XFEExecute;

public class THROW extends XFEBaseMethod {
	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		if (args.length == 1) {
			String reason = args[0].toString();
			XFEStack stack = xfeexecute.getStack();
			stack.setThrow(reason);
			return null;
		}
		XFEStack stack = xfeexecute.getStack();
		super.throwNotFoundMethod(stack, args);
		return null;
	}
}
