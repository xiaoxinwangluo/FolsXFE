package top.fols.box.util.xfe.executer.basemethod.lang;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.executer.variablepoint.XFEMapPoint;

public class VALUES extends XFEBaseMethod {

	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		if (args.length == 0) {
			return new XFEMapPoint(xfeexecute.cloneParam());
		} 
		XFEStack stack = xfeexecute.getStack();
		super.throwNotFoundMethod(stack, args);
		return null;
	}

}
