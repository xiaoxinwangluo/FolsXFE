package top.fols.box.util.xfe.executer.basemethod.util;

import java.util.LinkedHashMap;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.executer.variablepoint.XFEMapPoint;
import top.fols.box.util.xfe.lang.XFEClass;

public class MAP extends XFEBaseMethod {
	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		return new XFEMapPoint(new LinkedHashMap<>());
	}
}
