package top.fols.box.util.xfe.executer.basemethod.lang;

import java.util.Map;
import top.fols.box.io.base.XCharArrayWriter;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;
import top.fols.box.util.xfe.util.XFEUtil;
import top.fols.box.lang.XStringFormat;

public class STRF extends XFEBaseMethod {
	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		if (args.length == 1) { 
			return XStringFormat.strf(null == args[0] ?null: args[0].toString(), xfeexecute);
		}
		XFEStack stack = xfeexecute.getStack();
		super.throwNotFoundMethod(stack, args);
		return null;
	}
}
