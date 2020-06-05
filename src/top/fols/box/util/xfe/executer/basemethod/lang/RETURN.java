package top.fols.box.util.xfe.executer.basemethod.lang;
import java.util.HashMap;
import java.util.Map;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.lang.XFEClass;
import java.util.Arrays;

public class RETURN extends XFEBaseMethod {
	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		if (args.length == 1) {
			execStatus.setReturn(true);
			execStatus.setResult(args[0]);
		} else {
			XFEStack stack = xfeexecute.getStack();
			super.throwNotFoundMethod(stack, args);
		}
		return null;
	}
}

