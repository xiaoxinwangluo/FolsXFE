package top.fols.box.util.xfe.util;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.util.XFEStackThrowMessageTool;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;

public class XFEArrayOption {
	//*****
	public static final String GET = XFEKeyWords.XFEUTILRESOURCE.ArrayOption.METHOD_GET;
	public static final String SET = XFEKeyWords.XFEUTILRESOURCE.ArrayOption.METHOD_SET;
	public static final String LENGTH = XFEKeyWords.XFEUTILRESOURCE.ArrayOption.METHOD_LENGTH;
	public static Object option(XFEStack stack, Object instance, String methodName, Object[] param) {
		try {
			int paramlength = param.length;
			if (instance instanceof boolean[]) {
				boolean arr[] = ((boolean[])instance);
				if (methodName.equals(GET)) { switch (paramlength) { case 1: int index = (Integer)param[0]; return arr[index]; }
				} else if (methodName.equals(SET)) { switch (paramlength) { case 2: int index = (Integer)param[0]; arr[index] = (Boolean)param[1]; return param[1]; }
				} else if (methodName.equals(LENGTH)) {switch (paramlength) { case 0:  return arr.length; } }
			} else if (instance instanceof byte[]) {
				byte arr[] = ((byte[])instance);
				if (methodName.equals(GET)) { switch (paramlength) { case 1: int index = (Integer)param[0]; return arr[index]; }
				} else if (methodName.equals(SET)) { switch (paramlength) { case 2: int index = (Integer)param[0]; arr[index] = (Byte)param[1]; return param[1]; }
				} else if (methodName.equals(LENGTH)) {switch (paramlength) { case 0:  return arr.length; } }
			} else if (instance instanceof char[]) {
				char arr[] = ((char[])instance);
				if (methodName.equals(GET)) { switch (paramlength) { case 1: int index = (Integer)param[0]; return arr[index]; }
				} else if (methodName.equals(SET)) { switch (paramlength) { case 2: int index = (Integer)param[0]; arr[index] = (Character)param[1]; return param[1]; }
				} else if (methodName.equals(LENGTH)) {switch (paramlength) { case 0:  return arr.length; } }
			} else if (instance instanceof double[]) {
				double arr[] = ((double[])instance);
				if (methodName.equals(GET)) { switch (paramlength) { case 1: int index = (Integer)param[0]; return arr[index]; }
				} else if (methodName.equals(SET)) { switch (paramlength) { case 2: int index = (Integer)param[0]; arr[index] = (Double)param[1]; return param[1]; }
				} else if (methodName.equals(LENGTH)) {switch (paramlength) { case 0:  return arr.length; } }
			} else if (instance instanceof float[]) {
				float arr[] = ((float[])instance);
				if (methodName.equals(GET)) { switch (paramlength) { case 1: int index = (Integer)param[0]; return arr[index]; }
				} else if (methodName.equals(SET)) { switch (paramlength) { case 2: int index = (Integer)param[0]; arr[index] = (Float)param[1]; return param[1]; }
				} else if (methodName.equals(LENGTH)) {switch (paramlength) { case 0:  return arr.length; } }
			} else if (instance instanceof int[]) {
				int arr[] = ((int[])instance);
				if (methodName.equals(GET)) { switch (paramlength) { case 1: int index = (Integer)param[0]; return arr[index]; }
				} else if (methodName.equals(SET)) { switch (paramlength) { case 2: int index = (Integer)param[0]; arr[index] = (Integer)param[1]; return param[1]; }
				} else if (methodName.equals(LENGTH)) {switch (paramlength) { case 0:  return arr.length; } }
			} else if (instance instanceof long[]) {
				long arr[] = ((long[])instance);
				if (methodName.equals(GET)) { switch (paramlength) { case 1: int index = (Integer)param[0]; return arr[index]; }
				} else if (methodName.equals(SET)) { switch (paramlength) { case 2: int index = (Integer)param[0]; arr[index] = (Long)param[1]; return param[1]; }
				} else if (methodName.equals(LENGTH)) {switch (paramlength) { case 0:  return arr.length; } }
			} else if (instance instanceof short[]) {
				short arr[] = ((short[])instance);
				if (methodName.equals(GET)) { switch (paramlength) { case 1: int index = (Integer)param[0]; return arr[index]; }
				} else if (methodName.equals(SET)) { switch (paramlength) { case 2: int index = (Integer)param[0]; arr[index] = (Short)param[1]; return param[1]; }
				} else if (methodName.equals(LENGTH)) {switch (paramlength) { case 0:  return arr.length; } }
			} else if (instance instanceof Object[]) {
				Object arr[] = ((Object[])instance);
				if (methodName.equals(GET)) { switch (paramlength) { case 1: int index = (Integer)param[0]; return arr[index]; }
				} else if (methodName.equals(SET)) { switch (paramlength) { case 2: int index = (Integer)param[0]; arr[index] = (Object)param[1]; return param[1]; }
				} else if (methodName.equals(LENGTH)) {switch (paramlength) { case 0:  return arr.length; } }
			} 
			stack.setThrow(XFEStackThrowMessageTool.notFoundObjectMethod(instance, methodName, param));
			return null;
		} catch (Throwable e) {
			stack.setJavaThrow(XFEStackThrowMessageTool.getJavaStackString(e),e);
			return null;
		}
	}
}
