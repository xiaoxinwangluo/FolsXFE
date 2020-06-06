package top.fols.box.util.xfe.executer.basemethod.lang;
import java.util.HashMap;
import java.util.Map;
import top.fols.box.lang.XClass;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.executer.variablepoint.XFEJavaClassStaticPoint;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.util.XFEStackThrowMessageTool;
import top.fols.box.util.xfe.util.XFEUtil;

public class IMPORTJAVA extends XFEBaseMethod {
	private static final Map<ClassLoader, Map<String, Class>> javaClassCache = new HashMap<>();

	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		ClassLoader cl = null;
		String className = null;
		
		/**
		 *
		 * (ClassLoader, className, setVariableName) return null
		 * (ClassLoader, className) return XFEJavaClassStaticPoint
		 * (className) return XFEJavaClassStaticPoint
		 * (Class) return return XFEJavaClassStaticPoint
		 *
		 */
		 
		if (args.length == 3) {
			cl = args[0] instanceof ClassLoader ?(ClassLoader)args[0]: null;
			className = null == args[1] ?null: args[1].toString();
		} else if (args.length == 2) {
			cl = args[0] instanceof ClassLoader ?(ClassLoader)args[0]: null;
			className = null == args[0] ?null: args[0].toString();
		} else if (args.length == 1) {
			if (args[0] instanceof Class) {
				XFEJavaClassStaticPoint jcp = new XFEJavaClassStaticPoint((Class)args[0]);
				return jcp;
			}
			cl = null;
			className = null == args[0] ?null: args[0].toString();
		} 

		if (null != className) {
			Class cls;

			Map<String, Class> m = this.javaClassCache.get(cl);
			if (null == m) {
				this.javaClassCache.put(cl, m = new HashMap<>());
			}
			Class cache = m.get(className);
			if (null != cache) {
				cls = cache;
			} else {
				cls = XFEUtil.forName(cl, className);
				m.put(className, cls);
			}
			
			XFEJavaClassStaticPoint jcp = new XFEJavaClassStaticPoint(cls);
			return jcp;
		}
		
		XFEStack stack = xfeexecute.getStack();
		super.throwNotFoundMethod(stack, args);
		return null;
	}

}
