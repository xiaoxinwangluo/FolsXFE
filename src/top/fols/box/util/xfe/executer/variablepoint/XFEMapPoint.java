package top.fols.box.util.xfe.executer.variablepoint;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.variablepoint.abstractlist.XFEAbstractVariablePoint;
import java.util.function.Function;
import java.util.function.BiFunction;

public class XFEMapPoint implements Map, XFEAbstractVariablePoint {


	private Class mapClass = Map.class;
	private Map map = null;

	public XFEMapPoint(Map map) {
		this.map = (null == map ? map = new LinkedHashMap<>() : map);
        this.mapClass = this.map.getClass();
	}

	@Override
	public Object getVariableProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name) {
		// TODO: Implement this method
		return this.map.get(name);
	}

	@Override
	public Object setVariableProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name,
                                     Object value) {
		// TODO: Implement this method
		this.map.put(name, value);
		return value;
	}

	@Override
	public Object executeMethodProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name,
                                       Object[] value) throws Throwable {
		// TODO: Implement this method
		// System.out.println(XString.join(Thread.currentThread().getStackTrace(),
		// "\n"));
		Method method = xfeexecute.getJavaReflectManager().getMethod(this.mapClass, name, value);
		return method.invoke(this.map, value);
	}

	//
	@Override
	public int size() {
		// TODO: Implement this method
		return this.map.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO: Implement this method
		return this.map.isEmpty();
	}

	@Override
	public boolean containsKey(Object p1) {
		// TODO: Implement this method
		return this.map.containsKey(p1);
	}

	@Override
	public boolean containsValue(Object p1) {
		// TODO: Implement this method
		return this.map.containsValue(p1);
	}

	@Override
	public Object get(Object p1) {
		// TODO: Implement this method
		return this.map.get(p1);
	}

	@Override
	public Object put(Object p1, Object p2) {
		// TODO: Implement this method
		return this.map.put(p1, p2);
	}

	@Override
	public Object remove(Object p1) {
		// TODO: Implement this method
		return this.map.remove(p1);
	}

	@Override
	public void putAll(Map p1) {
		// TODO: Implement this method
		this.map.putAll(p1);
	}

	@Override
	public void clear() {
		// TODO: Implement this method
		this.map.clear();
	}

	@Override
	public Set keySet() {
		// TODO: Implement this method
		return this.map.keySet();
	}

	@Override
	public Collection values() {
		// TODO: Implement this method
		return this.map.values();
	}

	@Override
	public Set entrySet() {
		// TODO: Implement this method
		return this.map.entrySet();
	}

    @Override
    public Object getOrDefault(Object key, Object defaultValue) {
        return this.map.getOrDefault(key, defaultValue);
    }

    @Override
    public Object putIfAbsent(Object key, Object value) {
        return this.map.putIfAbsent(key, value);
    }

    @Override
    public Object replace(Object key, Object value) {
        return this.map.replace(key, value);
    }

    @Override
    public Object computeIfAbsent(Object key, Function mappingFunction) {
        return this.map.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public Object computeIfPresent(Object key, BiFunction remappingFunction) {
        return this.map.computeIfPresent(key, remappingFunction);
    }

    @Override
    public Object compute(Object key, BiFunction remappingFunction) {
        return this.map.compute(key, remappingFunction);
    }

    @Override
    public Object merge(Object key, Object value, BiFunction remappingFunction) {
        return this.map.merge(key, value, remappingFunction);
    }



	// -----
	@Override
	public boolean equals(Object obj) {
		// TODO: Implement this method
		return this.map.equals(obj);
	}

	@Override
	public int hashCode() {
		// TODO: Implement this method
		return this.map.hashCode();
	}

	@Override
	public String toString() {
		// TODO: Implement this method
		return this.map.toString();
	}
}
