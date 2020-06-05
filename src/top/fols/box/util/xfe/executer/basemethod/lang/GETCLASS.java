package top.fols.box.util.xfe.executer.basemethod.lang;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;

public class GETCLASS extends XFEBaseMethod {
	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		if (args.length >= 1) {
			Object object = args[0];
			Class cls = XFEKeyWords.getJavaClassInterfaceGetClass(object);
			return null == cls && null != object ?object.getClass(): cls;
		}
		return null;
	}
}
