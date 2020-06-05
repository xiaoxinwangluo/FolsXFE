package top.fols.box.util.xfe.executer.variablepoint;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.variablepoint.abstractlist.XFEAbstractVariablePoint;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;
import top.fols.box.util.xfe.util.XFEStackTool;
import top.fols.box.util.xfe.util.interfacelist.XFEInterfaceGetJavaClass;

public class XFEJavaClassStaticPoint extends XFEAbstractVariablePoint implements XFEInterfaceGetJavaClass {
	private Class<?> cls;

	public XFEJavaClassStaticPoint(Class<?> cls) {
		this.cls = cls;
	}

	@Override
	public Class getJavaClass() {
		// TODO: Implement this method
		return this.cls;
	}

	@Override
	public Object getVariableProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name) {
		// TODO: Implement this method
		XFEStack stack = xfeexecute.getStack();
		try {
			Field field = xfeexecute.getJavaReflectManager().getField(this.cls, name);
			if (null == field || !Modifier.isStatic(field.getModifiers())) {
				stack.setThrow(XFEStackTool.notFoundJavaClassStaticField(this.cls, name));
				return null;
			}
			return field.get(null);
		} catch (Throwable e) {
			stack.setJavaThrow(XFEStackTool.getJavaStackString(e), e);
			return null;
		}
	}

	@Override
	public Object setVariableProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name,
			Object value) {
		// TODO: Implement this method
		XFEStack stack = xfeexecute.getStack();
		try {
			Field field = xfeexecute.getJavaReflectManager().getField(this.cls, name);
			if (null == field || !Modifier.isStatic(field.getModifiers())) {
				stack.setThrow(XFEStackTool.notFoundJavaClassStaticField(this.cls, name));
				return null;
			}
			field.set(null, value);
		} catch (Throwable e) {
			stack.setJavaThrow(XFEStackTool.getJavaStackString(e), e);
			return null;
		}
		return value;
	}

	@Override
	public Object executeMethodProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name,
			Object[] value) throws Throwable {
		// TODO: Implement this method
		XFEStack stack = xfeexecute.getStack();
		if (XFEKeyWords.isNewMethodName(name)) {
			Constructor constructor = xfeexecute.getJavaReflectManager().getConstructor(this.cls, value);
			return constructor.newInstance(value);
		} else {
			Method method = xfeexecute.getJavaReflectManager().getMethod(this.cls, name, value);
			if (!Modifier.isStatic(method.getModifiers())) {
				stack.setThrow(XFEStackTool.notFoundJavaClassStaticMethod(this.cls, name, value));
				return null;
			}
			return method.invoke(null, value);
		}
	}
}
