package top.fols.box.util.xfe.util;

import java.util.Arrays;
import top.fols.box.lang.XClass;
import top.fols.box.statics.XStaticBaseType;
import top.fols.box.util.XArray;
import top.fols.box.util.XArrays;
import top.fols.box.util.XRandom;

/**
 * tool
 */
public class XFEUtil {

	public static Class<?> forName(ClassLoader cl, String name) throws ClassNotFoundException {
		String absClassName = XClass.toAbsClassName(name);
		Class<?> cls = XClass.primitiveClassForName(absClassName);
		if (null != cls) {
			return cls;
		}
		if (null == cl) {
			cls = Class.forName(absClassName);
		} else {
			cls = Class.forName(absClassName, true, cl);
		}
		return cls;
	}


	public static boolean ands(Object... ps) {
		boolean result = ps.length > 0;
		for (Object object : ps) {
			if (object instanceof Boolean && !((Boolean) object).booleanValue()) {
				result = false;
				break;
			}
		}
		return result;
	}

	public static Object random(Object min, Object max) {
		if (min instanceof Byte && max instanceof Byte) {
			return (byte) XRandom.getRandomInt((Byte) min, (Byte) min);
		} else if (min instanceof Integer && max instanceof Integer) {
			return (int) XRandom.getRandomInt((Integer) min, (Integer) max);
		} else if (min instanceof Long && max instanceof Long) {
			return XRandom.getRandomLong((Long) min, (Long) max);
		} else if (min instanceof Short && max instanceof Short) {
			return XRandom.getRandomInt((Short) min, (Short) max);
		} else if (min instanceof Character && max instanceof Character) {
			return (char) XRandom.getRandomInt((Character) min, (Character) max);
		} else if (min instanceof Boolean && max instanceof Boolean) {
			int random = XRandom.getRandomInt(0, 1);
			return (boolean) (random == 0 ? min : max);
		}
		return null;
	}




	
	public static boolean equals(Object obj, Object obj2) {
		if (obj == null) {
			if (obj2 == null) {
				return true;
			}
			return false;
		} else {
			if (obj.equals(obj2)) {
				return true;
			}
			return false;
		}
	}






	/**
	 * will data cast to String toString(char[]); toString(byte[]);
	 * toString(byte[],String coding); toString(Object);
	 * 
	 * @param objArr
	 * @param codeing
	 * @return
	 */
	public static String toString(Object objArr) {
		if (null == objArr) {
			return null;
		}
		if (objArr instanceof String) {
			return (String) objArr;
		} else if (objArr instanceof char[]) {
			return new String((char[]) objArr);
		} else {
			return objArr.toString();
		}
	}

	public static char tochar(Object objArr) {
		if (null == objArr) {
			return 0;
		}
		if (objArr instanceof Character) {
			return ((Character) objArr).charValue();
		}
		return parseChar(objArr.toString().trim());
	}

	public static boolean toboolean(Object objArr) {
		if (null == objArr) {
			return false;
		}
		if (objArr instanceof Boolean) {
			return ((Boolean) objArr).booleanValue();
		}
		return parseBoolean(objArr.toString().trim());
	}

	public static byte tobyte(Object objArr) {
		if (null == objArr) {
			return 0;
		}
		if (objArr instanceof Byte) {
			return ((Byte) objArr).byteValue();
		}
		return parseByte(objArr.toString().trim());
	}

	public static int toint(Object objArr) {
		if (null == objArr) {
			return 0;
		}
		if (objArr instanceof Integer) {
			return ((Integer) objArr).intValue();
		}
		return parseInt(objArr.toString().trim());
	}

	public static long tolong(Object objArr) {
		if (null == objArr) {
			return 0;
		}
		if (objArr instanceof Long) {
			return ((Long) objArr).longValue();
		}
		return parseLong(objArr.toString().trim());
	}

	public static short toshort(Object objArr) {
		if (null == objArr) {
			return 0;
		}
		if (objArr instanceof Short) {
			return ((Short) objArr).shortValue();
		}
		return parseShort(objArr.toString().trim());
	}

	/**
	 * xx.xxx
	 **/
	public static double todouble(Object objArr) {
		if (null == objArr) {
			return 0;
		} else if (objArr instanceof Double) {
			return ((Double) objArr).doubleValue();
		}
		return parseDouble(objArr.toString().trim());
	}

	/**
	 * xx.xxx
	 **/
	public static float tofloat(Object objArr) {
		if (null == objArr) {
			return 0;
		}
		if (objArr instanceof Float) {
			return ((Float) objArr).floatValue();
		}
		return parseFloat(objArr.toString().trim());
	}

	



	public static int[] tointArray(Object array, int off, int len) {
		int[] newArray = new int[len];
		XArrays.arraycopyTraverse(array, off, newArray, 0, len);
		return newArray;
	}

	@SuppressWarnings("unchecked")
	public static byte[] tobyteArray(Object originArray) {
		return (byte[]) XArray.copyOfConversion(originArray, XStaticBaseType.byte_class);
	}

	@SuppressWarnings("unchecked")
	public static long[] tolongArray(Object originArray) {
		return (long[]) XArray.copyOfConversion(originArray, XStaticBaseType.long_class);
	}

	@SuppressWarnings("unchecked")
	public static double[] todoubleArray(Object originArray) {
		return (double[]) XArray.copyOfConversion(originArray, XStaticBaseType.double_class);
	}

	@SuppressWarnings("unchecked")
	public static char[] tocharArray(Object originArray) {
		return (char[]) XArray.copyOfConversion(originArray, XStaticBaseType.char_class);
	}

	@SuppressWarnings("unchecked")
	public static int[] tointArray(Object originArray) {
		return (int[]) XArray.copyOfConversion(originArray, XStaticBaseType.int_class);
	}

	@SuppressWarnings("unchecked")
	public static boolean[] tobooleanArray(Object originArray) {
		return (boolean[]) XArray.copyOfConversion(originArray, XStaticBaseType.boolean_class);
	}

	@SuppressWarnings("unchecked")
	public static float[] tofloatArray(Object originArray) {
		return (float[]) XArray.copyOfConversion(originArray, XStaticBaseType.float_class);
	}

	@SuppressWarnings("unchecked")
	public static short[] toshortArray(Object originArray) {
		return (short[]) XArray.copyOfConversion(originArray, XStaticBaseType.short_class);
	}

	@SuppressWarnings("unchecked")
	public static String[] toStringArray(Object originArray) {
		return (String[]) XArray.copyOfConversion(originArray, XStaticBaseType.String_class);
	}

	@SuppressWarnings("unchecked")
	public static Object[] toObjectArray(Object originArray) {
		return (Object[]) XArray.copyOfConversion(originArray, XStaticBaseType.Object_class);
	}




	/**
	 * parse value Object[] byte[] long[] double[] char[] int[] boolean[] float[]
	 * short[]
	 * 
	 * @param str
	 * @return
	 */
	public static byte parseByte(String str) {
		String newstr = retainNum(str, 0, str.length());
		return newstr.length() == 0 ? 0 : Byte.parseByte(newstr);
	}

	public static long parseLong(String str) {
		String newstr = retainNum(str, 0, str.length());
		return newstr.length() == 0 ? 0L : Long.parseLong(newstr);
	}

	public static double parseDouble(String str) {
		String newstr = retainDouble(str, 0, str.length());
		return newstr.length() == 0 ? 0D : Double.parseDouble(newstr);
	}

	public static char parseChar(String str) {
		String newstr = retainNum(str, 0, str.length());
		return newstr.length() == 0 ? 0 : newstr.charAt(0);
	}

	public static int parseInt(String str) {
		String newstr = retainNum(str, 0, str.length());
		return newstr.length() == 0 ? 0 : Integer.parseInt(newstr);
	}

	/**
	 * Get the first boolean value
	 * 
	 * @param str
	 * @return
	 */
	public static boolean parseBoolean(String str) {
		return null == str ? false : str.equalsIgnoreCase("true");
	}

	public static float parseFloat(String str) {
		String newstr = retainDouble(str, 0, str.length());
		return newstr.length() == 0 ? 0F : Float.parseFloat(newstr);
	}

	public static short parseShort(String str) {
		String newstr = retainNum(str, 0, str.length());
		return newstr.length() == 0 ? 0 : Short.parseShort(newstr);
	}


	public static String trim(String str) {
		return null == str ? null : str.trim();
	}










	/**
	 * 
	 * read String first num
	 */
	public static String retainNum(CharSequence str, int off, int len) {
		char[] buf = new char[20];// long max string len = 20
		int bufindex = 0, bufsize = 0;
		char ch;
		for (int i = off; i < off + len; i++) {
			ch = str.charAt(i);
			if (ch == '+' || ch == '-' || (ch >= '0' && ch <= '9')) {
				int minCapacity = bufindex + 1;
				if (minCapacity - buf.length > 0) {
					int oldCapacity = buf.length;
					int newCapacity = oldCapacity << 1;
					if (newCapacity - minCapacity < 0) {
						newCapacity = minCapacity;
					}
					if (newCapacity < 0) {
						if (minCapacity < 0) {
							// overflow
							throw new OutOfMemoryError();
						}
						newCapacity = Integer.MAX_VALUE;
					}
					buf = Arrays.copyOf(buf, newCapacity);
				}

				buf[bufindex++] = ch;
				bufsize++;
			} else if (bufindex > 0) {// interrupt
				break;
			}
		}
		return new String(buf, 0, bufsize);
	}

	/**
	 * 
	 * read String first num
	 */
	public static String retainDouble(CharSequence str, int off, int len) {
		char[] buf = new char[64];
		int bufindex = 0, bufsize = 0;
		char ch;
		for (int i = off; i < off + len; i++) {
			ch = str.charAt(i);
			if (ch == '+' || ch == '-' || (ch >= '0' && ch <= '9') || ch == 'N' || ch == 'I' || ch == 'x' || ch == 'X'
					|| ch == '.' || ch == 'e' || ch == 'E' || ch == 'f' || ch == 'F' || ch == 'd' || ch == 'D') {

				int minCapacity = bufindex + 1;
				if (minCapacity - buf.length > 0) {
					int oldCapacity = buf.length;
					int newCapacity = oldCapacity << 1;
					if (newCapacity - minCapacity < 0) {
						newCapacity = minCapacity;
					}
					if (newCapacity < 0) {
						if (minCapacity < 0) {
							// overflow
							throw new OutOfMemoryError();
						}
						newCapacity = Integer.MAX_VALUE;
					}
					buf = Arrays.copyOf(buf, newCapacity);
				}

				buf[bufindex++] = ch;
				bufsize++;
			} else if (bufindex > 0) {// interrupt
				break;
			}
		}
		return new String(buf, 0, bufsize);
	}




}
