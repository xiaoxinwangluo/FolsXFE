package top.fols.box.util.xfe.executer.variablepoint;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.variablepoint.abstractlist.XFEAbstractVariablePoint;

public class XFEMapPoint implements Map, XFEAbstractVariablePoint {


	private Class mapClass = Map.class;
	private Map variables = null;

	public XFEMapPoint(Map map) {
		this.variables = (null == map ? map = new HashMap<>() : map);
        this.mapClass = this.variables.getClass();
	}

	@Override
	public Object getVariableProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name) {
		// TODO: Implement this method
		return this.variables.get(name);
	}

	@Override
	public Object setVariableProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name,
                                     Object value) {
		// TODO: Implement this method
		this.variables.put(name, value);
		return value;
	}

	@Override
	public Object executeMethodProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name,
                                       Object[] value) throws Throwable {
		// TODO: Implement this method
		// System.out.println(XString.join(Thread.currentThread().getStackTrace(),
		// "\n"));
		Method method = xfeexecute.getJavaReflectManager().getMethod(this.mapClass, name, value);
		return method.invoke(this.variables, value);
	}

	//
	@Override
	public int size() {
		// TODO: Implement this method
		return this.variables.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO: Implement this method
		return this.variables.isEmpty();
	}

	@Override
	public boolean containsKey(Object p1) {
		// TODO: Implement this method
		return this.variables.containsKey(p1);
	}

	@Override
	public boolean containsValue(Object p1) {
		// TODO: Implement this method
		return this.variables.containsValue(p1);
	}

	@Override
	public Object get(Object p1) {
		// TODO: Implement this method
		return this.variables.get(p1);
	}

	@Override
	public Object put(Object p1, Object p2) {
		// TODO: Implement this method
		return this.variables.put(p1, p2);
	}

	@Override
	public Object remove(Object p1) {
		// TODO: Implement this method
		return this.variables.remove(p1);
	}

	@Override
	public void putAll(Map p1) {
		// TODO: Implement this method
		this.variables.putAll(p1);
	}

	@Override
	public void clear() {
		// TODO: Implement this method
		this.variables.clear();
	}

	@Override
	public Set keySet() {
		// TODO: Implement this method
		return this.variables.keySet();
	}

	@Override
	public Collection values() {
		// TODO: Implement this method
		return this.variables.values();
	}

	@Override
	public Set entrySet() {
		// TODO: Implement this method
		return this.variables.entrySet();
	}

    @Override
    public Object getOrDefault(Object key, Object defaultValue) {
        return this.variables.getOrDefault(key, defaultValue);
    }

    @Override
    public Object putIfAbsent(Object key, Object value) {
        return this.variables.putIfAbsent(key, value);
    }

    @Override
    public Object replace(Object key, Object value) {
        return this.variables.replace(key, value);
    }

    @Override
    public Object computeIfAbsent(Object key, Function mappingFunction) {
        return this.variables.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public Object computeIfPresent(Object key, BiFunction remappingFunction) {
        return this.variables.computeIfPresent(key, remappingFunction);
    }

    @Override
    public Object compute(Object key, BiFunction remappingFunction) {
        return this.variables.compute(key, remappingFunction);
    }

    @Override
    public Object merge(Object key, Object value, BiFunction remappingFunction) {
        return this.variables.merge(key, value, remappingFunction);
    }



	// -----
	@Override
	public boolean equals(Object obj) {
		// TODO: Implement this method
		return this.variables.equals(obj);
	}

	@Override
	public int hashCode() {
		// TODO: Implement this method
		return this.variables.hashCode();
	}

	@Override
	public String toString() {
		// TODO: Implement this method
		return this.variables.toString();
	}
}
