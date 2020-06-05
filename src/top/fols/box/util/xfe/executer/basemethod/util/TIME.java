package top.fols.box.util.xfe.executer.basemethod.util;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.executer.XFEExecute.ExecuteStatus;
import top.fols.box.util.xfe.executer.XFEExecute;

public class TIME extends XFEBaseMethod {

	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		return System.currentTimeMillis();
	}
	
}
