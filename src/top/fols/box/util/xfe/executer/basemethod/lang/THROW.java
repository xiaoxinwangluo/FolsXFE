package top.fols.box.util.xfe.executer.basemethod.lang;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.executer.XFEExecute;

public class THROW extends XFEBaseMethod {
	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		String reason = (args.length > 0 && null != args[0]) ?args[0].toString(): null;
		XFEStack stack = xfeexecute.getStack();
		stack.setThrow(reason);
		return null;
	}
}
