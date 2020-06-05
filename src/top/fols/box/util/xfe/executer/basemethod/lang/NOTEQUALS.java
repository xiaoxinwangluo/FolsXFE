package top.fols.box.util.xfe.executer.basemethod.lang;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.util.XFEUtil;

public class NOTEQUALS extends XFEBaseMethod {

	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		if (args.length == 2) {
			return XFEUtil.equals(args[0], args[1]) == false;
		}
		XFEStack stack = xfeexecute.getStack();
		super.throwNotFoundMethod(stack, args);
		return null;
	}

}
