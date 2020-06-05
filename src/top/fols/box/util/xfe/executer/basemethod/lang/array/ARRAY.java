package top.fols.box.util.xfe.executer.basemethod.lang.array;

import java.lang.reflect.Array;
import top.fols.box.statics.XStaticFixedValue;
import top.fols.box.util.XArrays;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;
import top.fols.box.util.xfe.util.XFEUtil;

public class ARRAY extends XFEBaseMethod {
	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		if (args.length >= 1) {
			Object toClass = args[0];
			Class cls = XFEKeyWords.getJavaClassInterfaceGetClass(toClass);
			if (null == cls) {
				if (toClass instanceof Class) {
					cls = (Class)toClass;
				} else if (toClass instanceof String) {
					String name = (String)toClass;
					cls = XFEUtil.forName(null, null == name ?null: name.toString());
				}
			}
			int off = 1;
			int len = args.length - off;
			Object array = Array.newInstance(cls, len);
			XArrays.arraycopyTraverse(args, off, array, 0, len);
			return array;
		} else {
			return XStaticFixedValue.nullObjectArray;
		}
	}
}
