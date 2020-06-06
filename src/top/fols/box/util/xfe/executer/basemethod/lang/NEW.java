package top.fols.box.util.xfe.executer.basemethod.lang;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.lang.XFEClassInstance;
import top.fols.box.util.xfe.lang.XFEClassLoader;

public class NEW extends XFEBaseMethod {

	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		XFEClassInstance xfeclass = xfeexecute.getXFEClassInstance();
		XFEClassLoader cl = xfeclass.getClassLoader();
		XFEStack stack = xfeexecute.getStack();
		String className = null;
		XFEClass cls;

		int argsOff = 0;
		if (args.length >= 1) {
			if (args[0] instanceof XFEClassLoader) {
				cl = (XFEClassLoader) args[0];
				argsOff++;
				if (args.length >= 2) {
					if (args[1] instanceof String) {
						className = (String) args[1];
						argsOff++;
					}
				}
			} else if (args[0] instanceof String) {
				className = (String) args[0];
				argsOff++;
			}
		} else {
			className = xfeexecute.getXFEClassInstance().getName();
		}
		cls = cl.forName(className);
		XFEClassInstance instance = cls.newInstance(stack, args, argsOff, args.length - argsOff);
		return instance;
	}
}
