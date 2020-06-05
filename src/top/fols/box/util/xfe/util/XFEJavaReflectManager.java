package top.fols.box.util.xfe.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import top.fols.box.lang.reflect.XReflectMatcher;

public class XFEJavaReflectManager {
	private XReflectMatcher matcher;

	public XReflectMatcher getMatcher() {
		if (null == this.matcher) {
			this.matcher = new XReflectMatcher();
		}
		return this.matcher;
	}

	public XFEJavaReflectManager setMatcher(XReflectMatcher matcher) {
		this.matcher = matcher;
		return this;
	}

	public static XFEJavaReflectManager newInstance() {
		return new XFEJavaReflectManager();
	}

	public void releaseCache() {
		this.getMatcher().getCacher().clear();
	}

	public Field getField(Class<?> cls, String name) throws java.lang.RuntimeException {
		return this.getMatcher().getField(cls, name);
	}

	public Method getMethod(Class<?> cls, String name, Object[] args) throws java.lang.RuntimeException {
		return this.getMatcher().getMethod(cls, name, args);
	}

	public Constructor<?> getConstructor(Class<?> cls, Object[] args) throws java.lang.RuntimeException {
		return this.getMatcher().getConstructor(cls, args);
	}

	private XFEJavaReflectManager() {
	}
}
