package top.fols.box.util.xfe.executer.basemethod.lang;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;

public class CONTINUE extends XFEBaseMethod {
	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		execStatus.setReturn(true);
		execStatus.setResult(XFEKeyWords.CONTINUE);
		return null;
	}
}
