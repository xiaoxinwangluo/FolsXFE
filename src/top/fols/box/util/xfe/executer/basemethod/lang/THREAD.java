package top.fols.box.util.xfe.executer.basemethod.lang;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.executer.variablepoint.XFEThreadPoint;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.lang.XFEClassInstance;
import top.fols.box.util.xfe.util.XFEStackThrowMessageTool;

public class THREAD extends XFEBaseMethod {
	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		if (args.length == 1) {
			if (args[0] instanceof XFEClassInstance) {
				XFEClassInstance classInstance = (XFEClassInstance) args[0];
				XFEThreadPoint tp = new XFEThreadPoint(classInstance);
				return tp;
			}
		}
		XFEStack stack = xfeexecute.getStack();
		this.throwNotFoundMethod(stack, args);
		return null;
	}

}
