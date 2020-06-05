package top.fols.box.util.xfe.executer.basemethod.lang;

import java.util.Arrays;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethodManager;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.util.XFECalc;

public class CALC extends XFEBaseMethod {

	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args)
			throws Throwable {
		// TODO: Implement this method
		try {
			return XFECalc.calc(args[0], args[1], args[2]);
		} catch (Throwable e) {
			XFEStack stack = xfeexecute.getStack();
			stack.setJavaThrow("cannot calc " + Arrays.toString(args), e);
			return null;
		}
	}
}
