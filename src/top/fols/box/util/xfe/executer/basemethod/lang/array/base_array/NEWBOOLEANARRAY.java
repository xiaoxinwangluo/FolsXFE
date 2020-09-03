package top.fols.box.util.xfe.executer.basemethod.lang.array.base_array;

import java.lang.reflect.Array;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.util.XFEStackThrowMessageTool;
import top.fols.box.util.xfe.util.XFEUtil;
import top.fols.box.statics.XStaticFixedValue;

public class NEWBOOLEANARRAY extends XFEBaseMethod {
	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		Class cls = XStaticFixedValue.boolean_class;
		return Array.newInstance(cls, XFEUtil.tointArray(args));
	}
}
