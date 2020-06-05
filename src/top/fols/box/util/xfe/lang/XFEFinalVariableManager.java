package top.fols.box.util.xfe.lang;

import java.util.HashMap;
import java.util.Map;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.variablepoint.abstractlist.XFEAbstractVariablePoint;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;

public final class XFEFinalVariableManager {
	//public static final XFEFinalVariableManager DEFAULT = new XFEFinalVariableManager();

	private Map<String, Object> variable = new HashMap<>();
	private XFEFinalVariableManager() {
		this.variable.putAll(XFEKeyWords.getFinalBaseFieldValues());
	}

	public static XFEFinalVariableManager newInstance() {
		return new XFEFinalVariableManager();
	}

	//*****
	private Map<String, String> stringVariableCacheMap = new HashMap<>();
    //返回变量名
	protected String putStringFinalValue(XFEClass cls, String value) {
		//string cache

        String cacheValue = this.stringVariableCacheMap.get(value);
        if (null == cacheValue) {
            this.stringVariableCacheMap.put(value, value);
            cacheValue = value;
        }
        value = cacheValue; 

        String type = XFEKeyWords.BASE_VARIABLE_TYPE_STRING;
        String id = String.valueOf(this.variable.size());
        String name =  type + id;
       	this.putFinalValue(cls, name, value);
		return name;
    }
	protected String putCharFinalValue(XFEClass cls, char value) {
        String type = XFEKeyWords.BASE_VARIABLE_TYPE_CHAR;
        String id = String.valueOf((int)value);
		String name = type + id;
		this.putFinalValue(cls, name, value);
		return name;
    }
	protected String putBaseDataFinalValue(XFEClass cls, Object value) {
		String type = null;
        if (value instanceof Integer) {
			type = XFEKeyWords.BASE_VARIABLE_TYPE_INT;
		} else if (value instanceof Double) {
			type = XFEKeyWords.BASE_VARIABLE_TYPE_DOUBLE;
		} else if (value instanceof Long) {
			type = XFEKeyWords.BASE_VARIABLE_TYPE_LONG;
		} else if (value instanceof Byte) {
			type = XFEKeyWords.BASE_VARIABLE_TYPE_BYTE;
		} else if (value instanceof Float) {
			type = XFEKeyWords.BASE_VARIABLE_TYPE_FLOAT;
		} else if (value instanceof Short) {
			type = XFEKeyWords.BASE_VARIABLE_TYPE_SHORT;
		} 
        String id = String.valueOf(value).replace(".", "_");//format name
        String name = type + id;
		this.putFinalValue(cls, name, value);
		return name;
    }

	/**
	 * cannot use
	 */
//	private void removeFinalValue(String name) {
//		Object content = this.variable.get(name);
//		this.variable.remove(name);
//		if (content instanceof String) {
//			this.stringVariableCacheMap.remove(content);
//		}
//		content = null;
//	}

	protected void releaseFinalValue() {
		this.variable.clear(); 
		this.stringVariableCacheMap.clear(); 

        this.variable = null;
        this.stringVariableCacheMap = null;
	}

	protected void putFinalValue(XFEClass cls, String name, Object value) {
		this.variable.put(name, value);
		//cls.addSystemVariableName(name);
    }
	protected boolean hasFinalValue(String name) {
		return this.variable.containsKey(name);
    }
	protected Object getFinalValue(String name) {
        return this.variable.get(name);
    }


	private XFEFinalVariablePoint pointCache = null;
	public XFEFinalVariablePoint getXFEFinalVariablePoint() {
		if (null == this.pointCache) {
			this.pointCache = new XFEFinalVariablePoint(this);
		}
		return this.pointCache;
	}

	private final static class XFEFinalVariablePoint extends XFEAbstractVariablePoint {
		private XFEFinalVariableManager fvm;
		public XFEFinalVariablePoint(XFEFinalVariableManager variable) {
			this.fvm = variable;
		}


		@Override
		public Object getVariableProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name) {
			// TODO: Implement this method
            return this.fvm.getFinalValue(name);
		}

		@Override
		public Object setVariableProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name, Object value) {
			// TODO: Implement this method
			XFEStack stack = xfeexecute.getStack();
			stack.setThrow("final variable cannot set");
			return null;
		}

		@Override
		public Object executeMethodProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, String name, Object[] value) {
			// TODO: Implement this method
			XFEStack stack = xfeexecute.getStack();
			stack.setThrow("no method");
			return null;
		}

		@Override
		public String toString() {
			// TODO: Implement this method
			return "final variable";
		}
	}
}
