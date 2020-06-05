package top.fols.box.util.xfe.executer.basemethod.lang;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.executer.variablepoint.XFEClassStaticPoint;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.lang.XFEClassInstance;
import top.fols.box.util.xfe.lang.XFEClassLoader;
import top.fols.box.util.xfe.util.XFEStackTool;

public class IMPORT extends XFEBaseMethod {

	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		XFEClassInstance xfeclass = xfeexecute.getXFEClassInstance();
		XFEStack stack = xfeexecute.getStack();

		XFEClassLoader cl = null;
		String className = null;
		String set = null;

		/**
		 *
		 * (XFEClassLoader, className, setVariableName) return null
		 * (XFEClassLoader, className) return XFEJavaClassStaticPoint
		 * (className) return XFEJavaClassStaticPoint
		 * (XFEClass) return XFEJavaClassStaticPoint
		 *
		 */

		if (args.length == 3) {
			cl = args[0] instanceof XFEClassLoader ?(XFEClassLoader)args[0]: null;
			className = null == args[1] ?null: args[1].toString();
			set = null == args[2] ?null: args[2].toString();
		} else if (args.length == 2) {
			cl = args[0] instanceof XFEClassLoader ?(XFEClassLoader)args[0]: null;
			className = null == args[0] ?null: args[0].toString();
			set = null;
		} else if (args.length == 1) {
			if (args[0] instanceof XFEClass) {
				XFEClassStaticPoint jcp = new XFEClassStaticPoint((XFEClass)args[0]);
				return jcp;
			}
			cl = null;
			className = null == args[0] ?null: args[0].toString();
			set = null;
		} 

		if (null != className) {
			XFEClass cls;
			if (null == cl) {
				cls = xfeclass.getClassLoader().forName(className);
			} else {
				cls = cl.forName(className);
			}
			if (stack.isThrow()) {
				return null;
			}
			XFEClassStaticPoint jcp = new XFEClassStaticPoint(cls);
			if (null == set) {
				return jcp;
			} else {
				xfeexecute.setVariableValue(set, jcp);
			}
		}
		return null;
	}
}
