package top.fols.box.util.xfe.executer.variablepoint;

import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.variablepoint.abstractlist.XFEAbstractVariablePoint;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.lang.XFEClassInstance;
import top.fols.box.util.xfe.lang.XFEMethod;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;
import top.fols.box.util.xfe.util.XFEStackThrowMessageTool;
import top.fols.box.util.xfe.util.interfacelist.XFEInterfaceGetXFEClass;

public class XFEClassStaticPoint implements XFEInterfaceGetXFEClass, XFEAbstractVariablePoint {
	private XFEClass cls;
	private XFEClassInstance getStaticInstance(XFEStack stack) {
		return this.cls.getStaticInstance(stack);
	}

	public XFEClassStaticPoint(XFEClass cls) {
		this.cls = cls;
	}



	@Override
	public XFEClass getXFEClass() {
		// TODO: Implement this method
		return this.cls;
	}

	@Override
	public Object getVariableProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name) {
		// TODO: Implement this method
		XFEStack stack = xfeexecute.getStack();
		return this.getStaticInstance(stack).getVariable(xfeexecute.getStack(),name);
	}

	@Override
	public Object setVariableProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name, Object value) {
		// TODO: Implement this method
		XFEStack stack = xfeexecute.getStack();
		return this.getStaticInstance(stack).setVariable(name, value);
	}

	@Override
	public Object executeMethodProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name, Object[] value) {
		// TODO: Implement this method
		XFEStack stack = xfeexecute.getStack();
		if (XFEKeyWords.isNewMethodName(name)) {
			return this.cls.newInstance(stack, value);
		} else {
			XFEClassInstance instance = this.getStaticInstance(stack);
			XFEMethod method = instance.getMethod(name);
			if (null == method) {
				stack.setThrow(XFEStackThrowMessageTool.notFoundXfeClassMethod(instance.getName(), name));
				return null;
			}
			return instance.executeMethod(stack, method, name, value, 0, value.length);
		}
	}

}
