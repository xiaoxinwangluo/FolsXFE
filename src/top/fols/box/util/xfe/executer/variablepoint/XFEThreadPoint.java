package top.fols.box.util.xfe.executer.variablepoint;

import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.XFEThread;
import top.fols.box.util.xfe.executer.variablepoint.abstractlist.XFEAbstractVariablePoint;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.lang.XFEClassInstance;
import top.fols.box.util.xfe.util.interfacelist.XFEInterfaceGetXFEClass;

public class XFEThreadPoint implements XFEInterfaceGetXFEClass, XFEAbstractVariablePoint {

	private XFEClassInstance xfeclassinstance;
	private boolean executed = false;
	private XFEStack newstack = null;

	public XFEThreadPoint(XFEClassInstance classinstance) {
		this.xfeclassinstance = classinstance;
	}

	@Override
	public XFEClass getXFEClass() {
		// TODO: Implement this method
		return this.xfeclassinstance;
	}

	public XFEStack getStack() {
		return this.newstack;
	}

	@Override
	public Object getVariableProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name) throws Throwable {
		// TODO: Implement this method
		return this.xfeclassinstance.getVariable(name);
	}

	@Override
	public Object setVariableProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name, Object value) throws Throwable {
		// TODO: Implement this method
		return this.xfeclassinstance.setVariable(name, value);
	}

	@Override
	public Object executeMethodProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name, Object[] args) throws Throwable {
		// TODO: Implement this method
		XFEStack stack = xfeexecute.getStack();
		
		if (this.executed) {
			stack.setThrow("already executed");
			return null;
		}
		XFEClassInstance instance = this.xfeclassinstance;
		XFEThread th;
		th = XFEThread.newInstance(stack, instance, name, args);
		th.start();

		this.executed = true;
		this.newstack = th.getStack();
		return null;
	}
}
