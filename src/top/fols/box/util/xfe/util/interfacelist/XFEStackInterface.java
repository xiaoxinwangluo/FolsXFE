package top.fols.box.util.xfe.util.interfacelist;
import top.fols.box.util.xfe.executer.XFEStack;

public abstract interface XFEStackInterface {
	public XFEStack.StackElement[] stacks();
	public XFEStack.StackElement now();

	public String string();
	public String stringall();
}
