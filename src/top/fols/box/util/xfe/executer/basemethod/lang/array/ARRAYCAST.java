package top.fols.box.util.xfe.executer.basemethod.lang.array;
import top.fols.box.util.XArray;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.util.XFEUtil;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;

public class ARRAYCAST extends XFEBaseMethod {
	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		/**
		 *
		 * (arrayObject, className)
		 * (arrayObject, class)
		 * (arrayObject, ClassLoader, className)
		 *
		 */
		final int length = args.length;
		Object array = null;
		Object cl = null;
		Object toClass = null;
		if (length == 2) {
			array = args[0];
			toClass = args[1];
		} else if (length == 3) {
			array = args[0];
			cl = args[1];
			toClass = args[2];
		}
		if ((null != array && array.getClass().isArray()) &&
			(null == cl || cl instanceof ClassLoader)) {
			Class cls = XFEKeyWords.getJavaClassInterfaceGetClass(toClass);
			if (null == cls) {
				if (toClass instanceof Class) {
					cls = (Class)toClass;
				} else if (toClass instanceof String) {
					String name = (String)toClass;
					cls = XFEUtil.forName((ClassLoader)cl, null == name ?null: name.toString());
				}
			}
			return XArray.copyOfConversion(array, XArray.getElementClass(cls));
		}

		XFEStack stack = xfeexecute.getStack();
		this.throwNotFoundMethod(stack, args);
		return null;
	}
}
