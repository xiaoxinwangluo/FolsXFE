package top.fols.box.util.xfe.executer.basemethod;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import top.fols.box.util.xfe.executer.XFEExecute;

/**
 * cannot remove method
 */
public class XFEBaseMethodManager {
	private HashMap<String, XFEBaseMethod> map = new HashMap<>();
	public XFEBaseMethodManager(XFEBaseMethod... methods) {
		this.putAll(methods);
	}
	public XFEBaseMethodManager put(String name, XFEBaseMethod method) throws RuntimeException {
		if (null == name) {
			throw new RuntimeException("null method name");
		}

		XFEBaseMethod origin = this.map.get(name);
		if (null != origin) {
			throw new RuntimeException("already exists method: " + name);
		}

		this.map.put(name, method);
		return this;
	}



	public XFEBaseMethodManager put(XFEBaseMethod method) {
		this.put(method.getName(), method);
		return this;
	}
	public XFEBaseMethodManager putAll(XFEBaseMethod... methods) {
		for (XFEBaseMethod bm: methods) {
			this.put(bm);
		}
		return this;
	}
	public XFEBaseMethodManager putAll(XFEBaseMethodManager methods) {
		for (String name: methods.map.keySet()) {
			this.put(name, methods.get(name));
		}
		return this;
	}


	public XFEBaseMethod get(String name) {
		return this.map.get(name);
	}
	
	public String[] listMethodName() {
		return this.map.keySet().toArray(new String[this.map.size()]);
	}
	
	
	public XFEBaseMethodManager putJavaClassPublicStaticMethod(Class<?> cls) {
		Method[] methods = cls.getMethods();
		for (final Method method: methods) {
			int modifier = method.getModifiers();
			if (!Modifier.isStatic(modifier)) {
				continue;
			}
			final String name = method.getName();
			final XFEBaseMethod baseMethod = new XFEBaseMethod() {
				@Override
				public String getName() {
					return name;
				}
				@Override
				protected Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
					// TODO: Implement this method
					return method.invoke(null, args);
				}
			};
			this.put(baseMethod);
		}
		return this;
	}
	
}
