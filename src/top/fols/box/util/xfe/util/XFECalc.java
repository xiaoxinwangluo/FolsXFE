package top.fols.box.util.xfe.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import top.fols.box.lang.reflect.safety.XReflect;
import top.fols.box.statics.XStaticFixedValue;

public class XFECalc {
	
	/** 
	 * calc:
	 * 
	 * byte
	 * long
	 * double
	 * char
	 * int
	 * boolean
	 * float
	 * short
	 */
	 
	 //*****
	public static final int hash1_ = "~".hashCode();
	public static final int hash2_ = "&".hashCode();
	public static final int hash3_ = "|".hashCode();
	public static final int hash4_ = "^".hashCode();
	public static final int hash5_ = ">>".hashCode();
	public static final int hash6_ = "<<".hashCode();
	public static final int hash7_ = ">>>".hashCode();

	public static final int hash8_ = "!=".hashCode();
	public static final int hash9_ = "==".hashCode();

	public static final int hash10_ = "+".hashCode();
	public static final int hash11_ = "-".hashCode();
	public static final int hash12_ = "*".hashCode();
	public static final int hash13_ = "/".hashCode();
	public static final int hash14_ = "%".hashCode();

	public static final int hash15_ = "<".hashCode();
	public static final int hash16_ = ">".hashCode();
	public static final int hash17_ = "<=".hashCode();
	public static final int hash18_ = ">=".hashCode();

	/*
	 * It is never possible to have an exception
	 * 永远不会出现异常
	 */
	static {
		//System.out.println("xfe calc exec hash......check start");
		try {
			Field[] fs = XReflect.getFields(XFECalc.class);
			List<Integer> list = new ArrayList<>();
			for (Field f:fs) {
				//System.out.println("check " + f.getName());
				if (f.getName().endsWith("_") && Modifier.isStatic(f.getModifiers())) {
					Object obj = f.get(null);
					if (obj instanceof Integer == false)
						continue;
					//System.out.println("result " + obj);
					if (list.contains(obj))
						throw new RuntimeException("xfecalc method Hash Exception");
					list.add((Integer)obj);
					//System.out.println("add " + obj);
				}	
				//System.out.println();
			}	
		} catch (Throwable e) {
			e.printStackTrace();
		}
		//System.out.println("xfe calc exec hash......check end");
	}
	
	
	
	
	
	public static Object calc(Object x0 , Object key, Object x1) {
		return calc(x0, null == key?null:key.toString(), x1);
	}
	public static Object calc(Object x0 , String key, Object x1) {
		int keyHashCode = key.hashCode();
		
		if (hash1_ == keyHashCode) return hash1(key, x1);//~
		if (hash2_ == keyHashCode) return hash2(x0, key, x1);//&
		if (hash3_ == keyHashCode) return hash3(x0, key, x1);//|
		if (hash4_ == keyHashCode) return hash4(x0, key, x1);//^
		if (hash5_ == keyHashCode) return hash5(x0, key, x1);//>>
		if (hash6_ == keyHashCode) return hash6(x0, key, x1);//<<
		if (hash7_ == keyHashCode) return hash7(x0, key, x1);//>>>
		if (hash8_ == keyHashCode) return hash8(x0, key, x1) ?XStaticFixedValue.Boolean_TRUE: XStaticFixedValue.Boolean_FALSE;//!=
		if (hash9_ == keyHashCode) return hash9(x0, key, x1) ?XStaticFixedValue.Boolean_TRUE: XStaticFixedValue.Boolean_FALSE;//==
		if (hash10_ == keyHashCode) return hash10(x0, key, x1);//+
		if (hash11_ == keyHashCode) return hash11(x0, key, x1);//+
		if (hash12_ == keyHashCode) return hash12(x0, key, x1);//*
		if (hash13_ == keyHashCode) return hash13(x0, key, x1);///
		if (hash14_ == keyHashCode) return hash14(x0, key, x1);//%
		if (hash15_ == keyHashCode) return hash15(x0, key, x1) ?XStaticFixedValue.Boolean_TRUE: XStaticFixedValue.Boolean_FALSE;//<
		if (hash16_ == keyHashCode) return hash16(x0, key, x1) ?XStaticFixedValue.Boolean_TRUE: XStaticFixedValue.Boolean_FALSE;//>
		if (hash17_ == keyHashCode) return hash17(x0, key, x1) ?XStaticFixedValue.Boolean_TRUE: XStaticFixedValue.Boolean_FALSE;//<=
		if (hash18_ == keyHashCode) return hash18(x0, key, x1) ?XStaticFixedValue.Boolean_TRUE: XStaticFixedValue.Boolean_FALSE;//>=
		
		throw new IllegalArgumentException("not found operating " + key);
	}

	//~
	static Object hash1(String key, Object x1) {
		if (x1 instanceof Byte)
			return ~((Byte)x1).byteValue();
		else if (x1 instanceof Long)
			return ~((Long)x1).longValue();
		//else if (x instanceof Double)
		//	return ~((Double)x).doubleValue();
		else if (x1 instanceof Character)
			return ~((Character)x1).charValue();
		else if (x1 instanceof Integer)
			return ~((Integer)x1).intValue();
		//else if (x instanceof Boolean)
		//	return ~((Boolean)x).booleanValue();
		//else if (x instanceof Float)
		//	return ~((Float)x).floatValue();
		else if (x1 instanceof Short)
			return ~((Short)x1).shortValue();
		throw new IllegalArgumentException("unknown operating: " + key + " " + (x1 == null ?null: x1.getClass().getCanonicalName()));
	}
	//&
	static Object hash2(Object x0 , String key, Object x1) {
		if (x0 instanceof Byte) {
			if (x1 instanceof Byte)
				return ((Byte)x0).byteValue() & ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Byte)x0).byteValue() & ((Long)x1).longValue();
			//else if (x1 instanceof Double)
			//	return ((Byte)x0).byteValue() & ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Byte)x0).byteValue() & ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Byte)x0).byteValue() & ((Integer)x1).intValue();
			//else if (x1 instanceof Boolean)
			//	return ((Byte)x0).byteValue() & ((Boolean)x1).booleanValue();
			//else if (x1 instanceof Float)
			//	return ((Byte)x0).byteValue() & ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Byte)x0).byteValue() & ((Short)x1).shortValue();
		} else if (x0 instanceof Long) {
			if (x1 instanceof Byte)
				return ((Long)x0).longValue() & ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Long)x0).longValue() & ((Long)x1).longValue();
			//else if (x1 instanceof Double)
			//	return ((Long)x0).longValue() & ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Long)x0).longValue() & ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Long)x0).longValue() & ((Integer)x1).intValue();
			//else if (x1 instanceof Boolean)
			//	return ((Long)x0).longValue() & ((Boolean)x1).booleanValue();
			//else if (x1 instanceof Float)
			//	return ((Long)x0).longValue() & ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Long)x0).longValue() & ((Short)x1).shortValue();
		} else if (x0 instanceof Character) {
			if (x1 instanceof Byte)
				return ((Character)x0).charValue() & ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Character)x0).charValue() & ((Long)x1).longValue();
			//else if (x1 instanceof Double)
			//	return ((Character)x0).charValue() & ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Character)x0).charValue() & ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Character)x0).charValue() & ((Integer)x1).intValue();
			//else if (x1 instanceof Boolean)
			//	return ((Character)x0).charValue() & ((Boolean)x1).booleanValue();
			//else if (x1 instanceof Float)
			//	return ((Character)x0).charValue() & ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Character)x0).charValue() & ((Short)x1).shortValue();
		} else if (x0 instanceof Integer) {
			if (x1 instanceof Byte)
				return ((Integer)x0).intValue() & ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Integer)x0).intValue() & ((Long)x1).longValue();
			//else if (x1 instanceof Double)
			//	return ((Integer)x0).intValue() & ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Integer)x0).intValue() & ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Integer)x0).intValue() & ((Integer)x1).intValue();
			//else if (x1 instanceof Boolean)
			//	return ((Integer)x0).intValue() & ((Boolean)x1).booleanValue();
			//else if (x1 instanceof Float)
			//	return ((Integer)x0).intValue() & ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Integer)x0).intValue() & ((Short)x1).shortValue();
		} else if (x0 instanceof Boolean) {
//				if (x1 instanceof Byte)
//					return ((Boolean)x0).booleanValue() & ((Byte)x1).byteValue();
//				else if (x1 instanceof Long)
//					return ((Boolean)x0).booleanValue() & ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Boolean)x0).booleanValue() & ((Double)x1).doubleValue();
//				else if (x1 instanceof Character)
//					return ((Boolean)x0).booleanValue() & ((Character)x1).charValue();
//				else if (x1 instanceof Integer)
//					return ((Boolean)x0).booleanValue() & ((Integer)x1).intValue();
//				else
			if (x1 instanceof Boolean)
				return ((Boolean)x0).booleanValue() & ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Boolean)x0).booleanValue() & ((Float)x1).floatValue();
//				else if (x1 instanceof Short)
//					return ((Boolean)x0).booleanValue() & ((Short)x1).shortValue();
		} else if (x0 instanceof Short) {
			if (x1 instanceof Byte)
				return ((Short)x0).shortValue() & ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Short)x0).shortValue() & ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Short)x0).shortValue() & ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Short)x0).shortValue() & ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Short)x0).shortValue() & ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Short)x0).shortValue() & ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Short)x0).shortValue() & ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Short)x0).shortValue() & ((Short)x1).shortValue();
		}
		throw new IllegalArgumentException("unknown operating: " + (x0 == null ?null: x0.getClass().getCanonicalName()) + " " + key + " " + (x1 == null ?null: x1.getClass().getCanonicalName()));
	}

	//|
	static Object hash3(Object x0 , String key, Object x1) {
		if (x0 instanceof Byte) {
			if (x1 instanceof Byte)
				return ((Byte)x0).byteValue() | ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Byte)x0).byteValue() | ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Byte)x0).byteValue() | ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Byte)x0).byteValue() | ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Byte)x0).byteValue() | ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Byte)x0).byteValue() | ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Byte)x0).byteValue() | ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Byte)x0).byteValue() | ((Short)x1).shortValue();
		} else if (x0 instanceof Long) {
			if (x1 instanceof Byte)
				return ((Long)x0).longValue() | ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Long)x0).longValue() | ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Long)x0).longValue() | ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Long)x0).longValue() | ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Long)x0).longValue() | ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Long)x0).longValue() | ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Long)x0).longValue() | ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Long)x0).longValue() | ((Short)x1).shortValue();
		} else if (x0 instanceof Character) {
			if (x1 instanceof Byte)
				return ((Character)x0).charValue() | ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Character)x0).charValue() | ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Character)x0).charValue() | ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Character)x0).charValue() | ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Character)x0).charValue() | ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Character)x0).charValue() | ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Character)x0).charValue() | ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Character)x0).charValue() | ((Short)x1).shortValue();
		} else if (x0 instanceof Integer) {
			if (x1 instanceof Byte)
				return ((Integer)x0).intValue() | ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Integer)x0).intValue() | ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Integer)x0).intValue() | ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Integer)x0).intValue() | ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Integer)x0).intValue() | ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Integer)x0).intValue() | ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Integer)x0).intValue() | ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Integer)x0).intValue() | ((Short)x1).shortValue();
		} else if (x0 instanceof Boolean) {
//				if (x1 instanceof Byte)
//					return ((Boolean)x0).booleanValue() | ((Byte)x1).byteValue();
//				else if (x1 instanceof Long)
//					return ((Boolean)x0).booleanValue() | ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Boolean)x0).booleanValue() | ((Double)x1).doubleValue();
//				else if (x1 instanceof Character)
//					return ((Boolean)x0).booleanValue() | ((Character)x1).charValue();
//				else if (x1 instanceof Integer)
//					return ((Boolean)x0).booleanValue() | ((Integer)x1).intValue();
//				else
			if (x1 instanceof Boolean)
				return ((Boolean)x0).booleanValue() | ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Boolean)x0).booleanValue() | ((Float)x1).floatValue();
//				else if (x1 instanceof Short)
//					return ((Boolean)x0).booleanValue() | ((Short)x1).shortValue();
		} else if (x0 instanceof Short) {
			if (x1 instanceof Byte)
				return ((Short)x0).shortValue() | ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Short)x0).shortValue() | ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Short)x0).shortValue() | ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Short)x0).shortValue() | ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Short)x0).shortValue() | ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Short)x0).shortValue() | ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Short)x0).shortValue() | ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Short)x0).shortValue() | ((Short)x1).shortValue();
		}
		throw new IllegalArgumentException("unknown operating: " + (x0 == null ?null: x0.getClass().getCanonicalName()) + " " + key + " " + (x1 == null ?null: x1.getClass().getCanonicalName()));

	}

	//^
	static Object hash4(Object x0 , String key, Object x1) {
		if (x0 instanceof Byte) {
			if (x1 instanceof Byte)
				return ((Byte)x0).byteValue() ^ ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Byte)x0).byteValue() ^ ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Byte)x0).byteValue() ^ ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Byte)x0).byteValue() ^ ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Byte)x0).byteValue() ^ ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Byte)x0).byteValue() ^ ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Byte)x0).byteValue() ^ ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Byte)x0).byteValue() ^ ((Short)x1).shortValue();
		} else if (x0 instanceof Long) {
			if (x1 instanceof Byte)
				return ((Long)x0).longValue() ^ ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Long)x0).longValue() ^ ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Long)x0).longValue() ^ ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Long)x0).longValue() ^ ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Long)x0).longValue() ^ ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Long)x0).longValue() ^ ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Long)x0).longValue() ^ ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Long)x0).longValue() ^ ((Short)x1).shortValue();
		} else if (x0 instanceof Character) {
			if (x1 instanceof Byte)
				return ((Character)x0).charValue() ^ ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Character)x0).charValue() ^ ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Character)x0).charValue() ^ ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Character)x0).charValue() ^ ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Character)x0).charValue() ^ ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Character)x0).charValue() ^ ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Character)x0).charValue() ^ ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Character)x0).charValue() ^ ((Short)x1).shortValue();
		} else if (x0 instanceof Integer) {
			if (x1 instanceof Byte)
				return ((Integer)x0).intValue() ^ ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Integer)x0).intValue() ^ ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Integer)x0).intValue() ^ ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Integer)x0).intValue() ^ ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Integer)x0).intValue() ^ ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Integer)x0).intValue() ^ ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Integer)x0).intValue() ^ ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Integer)x0).intValue() ^ ((Short)x1).shortValue();
		} else if (x0 instanceof Boolean) {
//				if (x1 instanceof Byte)
//					return ((Boolean)x0).booleanValue() ^ ((Byte)x1).byteValue();
//				else if (x1 instanceof Long)
//					return ((Boolean)x0).booleanValue() ^ ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Boolean)x0).booleanValue() ^ ((Double)x1).doubleValue();
//				else if (x1 instanceof Character)
//					return ((Boolean)x0).booleanValue() ^ ((Character)x1).charValue();
//				else if (x1 instanceof Integer)
//					return ((Boolean)x0).booleanValue() ^ ((Integer)x1).intValue();
//				else
			if (x1 instanceof Boolean)
				return ((Boolean)x0).booleanValue() ^ ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Boolean)x0).booleanValue() ^ ((Float)x1).floatValue();
//				else if (x1 instanceof Short)
//					return ((Boolean)x0).booleanValue() ^ ((Short)x1).shortValue();
		} else if (x0 instanceof Short) {
			if (x1 instanceof Byte)
				return ((Short)x0).shortValue() ^ ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Short)x0).shortValue() ^ ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Short)x0).shortValue() ^ ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Short)x0).shortValue() ^ ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Short)x0).shortValue() ^ ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Short)x0).shortValue() ^ ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Short)x0).shortValue() ^ ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Short)x0).shortValue() ^ ((Short)x1).shortValue();
		}
		throw new IllegalArgumentException("unknown operating: " + (x0 == null ?null: x0.getClass().getCanonicalName()) + " " + key + " " + (x1 == null ?null: x1.getClass().getCanonicalName()));

	}


	//>>
	static Object hash5(Object x0 , String key, Object x1) {
		if (x0 instanceof Byte) {
			if (x1 instanceof Byte)
				return ((Byte)x0).byteValue() >> ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Byte)x0).byteValue() >> ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Byte)x0).byteValue() >> ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Byte)x0).byteValue() >> ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Byte)x0).byteValue() >> ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Byte)x0).byteValue() >> ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Byte)x0).byteValue() >> ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Byte)x0).byteValue() >> ((Short)x1).shortValue();
		} else if (x0 instanceof Long) {
			if (x1 instanceof Byte)
				return ((Long)x0).longValue() >> ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Long)x0).longValue() >> ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Long)x0).longValue() >> ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Long)x0).longValue() >> ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Long)x0).longValue() >> ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Long)x0).longValue() >> ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Long)x0).longValue() >> ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Long)x0).longValue() >> ((Short)x1).shortValue();
		} else if (x0 instanceof Character) {
			if (x1 instanceof Byte)
				return ((Character)x0).charValue() >> ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Character)x0).charValue() >> ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Character)x0).charValue() >> ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Character)x0).charValue() >> ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Character)x0).charValue() >> ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Character)x0).charValue() >> ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Character)x0).charValue() >> ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Character)x0).charValue() >> ((Short)x1).shortValue();
		} else if (x0 instanceof Integer) {
			if (x1 instanceof Byte)
				return ((Integer)x0).intValue() >> ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Integer)x0).intValue() >> ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Integer)x0).intValue() >> ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Integer)x0).intValue() >> ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Integer)x0).intValue() >> ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Integer)x0).intValue() >> ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Integer)x0).intValue() >> ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Integer)x0).intValue() >> ((Short)x1).shortValue();
		} else if (x0 instanceof Short) {
			if (x1 instanceof Byte)
				return ((Short)x0).shortValue() >> ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Short)x0).shortValue() >> ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Short)x0).shortValue() >> ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Short)x0).shortValue() >> ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Short)x0).shortValue() >> ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Short)x0).shortValue() >> ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Short)x0).shortValue() >> ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Short)x0).shortValue() >> ((Short)x1).shortValue();
		}
		throw new IllegalArgumentException("unknown operating: " + (x0 == null ?null: x0.getClass().getCanonicalName()) + " " + key + " " + (x1 == null ?null: x1.getClass().getCanonicalName()));

	}

	//<<
	static Object hash6(Object x0 , String key, Object x1) {
		if (x0 instanceof Byte) {
			if (x1 instanceof Byte)
				return ((Byte)x0).byteValue() << ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Byte)x0).byteValue() << ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Byte)x0).byteValue() << ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Byte)x0).byteValue() << ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Byte)x0).byteValue() << ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Byte)x0).byteValue() << ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Byte)x0).byteValue() << ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Byte)x0).byteValue() << ((Short)x1).shortValue();
		} else if (x0 instanceof Long) {
			if (x1 instanceof Byte)
				return ((Long)x0).longValue() << ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Long)x0).longValue() << ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Long)x0).longValue() << ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Long)x0).longValue() << ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Long)x0).longValue() << ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Long)x0).longValue() << ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Long)x0).longValue() << ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Long)x0).longValue() << ((Short)x1).shortValue();
		} else if (x0 instanceof Character) {
			if (x1 instanceof Byte)
				return ((Character)x0).charValue() << ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Character)x0).charValue() << ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Character)x0).charValue() << ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Character)x0).charValue() << ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Character)x0).charValue() << ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Character)x0).charValue() << ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Character)x0).charValue() << ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Character)x0).charValue() << ((Short)x1).shortValue();
		} else if (x0 instanceof Integer) {
			if (x1 instanceof Byte)
				return ((Integer)x0).intValue() << ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Integer)x0).intValue() << ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Integer)x0).intValue() << ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Integer)x0).intValue() << ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Integer)x0).intValue() << ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Integer)x0).intValue() << ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Integer)x0).intValue() << ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Integer)x0).intValue() << ((Short)x1).shortValue();
		} else if (x0 instanceof Short) {
			if (x1 instanceof Byte)
				return ((Short)x0).shortValue() << ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Short)x0).shortValue() << ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Short)x0).shortValue() << ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Short)x0).shortValue() << ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Short)x0).shortValue() << ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Short)x0).shortValue() << ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Short)x0).shortValue() << ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Short)x0).shortValue() << ((Short)x1).shortValue();
		}
		throw new IllegalArgumentException("unknown operating: " + (x0 == null ?null: x0.getClass().getCanonicalName()) + " " + key + " " + (x1 == null ?null: x1.getClass().getCanonicalName()));

	}


	//>>>
	static Object hash7(Object x0 , String key, Object x1) {
		if (x0 instanceof Byte) {
			if (x1 instanceof Byte)
				return ((Byte)x0).byteValue() >>> ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Byte)x0).byteValue() >>> ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Byte)x0).byteValue() >>> ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Byte)x0).byteValue() >>> ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Byte)x0).byteValue() >>> ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Byte)x0).byteValue() >>> ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Byte)x0).byteValue() >>> ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Byte)x0).byteValue() >>> ((Short)x1).shortValue();
		} else if (x0 instanceof Long) {
			if (x1 instanceof Byte)
				return ((Long)x0).longValue() >>> ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Long)x0).longValue() >>> ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Long)x0).longValue() >>> ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Long)x0).longValue() >>> ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Long)x0).longValue() >>> ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Long)x0).longValue() >>> ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Long)x0).longValue() >>> ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Long)x0).longValue() >>> ((Short)x1).shortValue();
		} else if (x0 instanceof Character) {
			if (x1 instanceof Byte)
				return ((Character)x0).charValue() >>> ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Character)x0).charValue() >>> ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Character)x0).charValue() >>> ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Character)x0).charValue() >>> ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Character)x0).charValue() >>> ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Character)x0).charValue() >>> ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Character)x0).charValue() >>> ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Character)x0).charValue() >>> ((Short)x1).shortValue();
		} else if (x0 instanceof Integer) {
			if (x1 instanceof Byte)
				return ((Integer)x0).intValue() >>> ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Integer)x0).intValue() >>> ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Integer)x0).intValue() >>> ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Integer)x0).intValue() >>> ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Integer)x0).intValue() >>> ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Integer)x0).intValue() >>> ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Integer)x0).intValue() >>> ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Integer)x0).intValue() >>> ((Short)x1).shortValue();
		} else if (x0 instanceof Short) {
			if (x1 instanceof Byte)
				return ((Short)x0).shortValue() >>> ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Short)x0).shortValue() >>> ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Short)x0).shortValue() >>> ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Short)x0).shortValue() >>> ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Short)x0).shortValue() >>> ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Short)x0).shortValue() >>> ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Short)x0).shortValue() >>> ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Short)x0).shortValue() >>> ((Short)x1).shortValue();
		}
		throw new IllegalArgumentException("unknown operating: " + (x0 == null ?null: x0.getClass().getCanonicalName()) + " " + key + " " + (x1 == null ?null: x1.getClass().getCanonicalName()));

	}


	// !=
	static boolean hash8(Object x0 , String key, Object x1) {
		if (x0 instanceof Byte) {
			if (x1 instanceof Byte)
				return ((Byte)x0).byteValue() != ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Byte)x0).byteValue() != ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Byte)x0).byteValue() != ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Byte)x0).byteValue() != ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Byte)x0).byteValue() != ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Byte)x0).byteValue() != ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Byte)x0).byteValue() != ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Byte)x0).byteValue() != ((Short)x1).shortValue();
		} else if (x0 instanceof Long) {
			if (x1 instanceof Byte)
				return ((Long)x0).longValue() != ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Long)x0).longValue() != ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Long)x0).longValue() != ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Long)x0).longValue() != ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Long)x0).longValue() != ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Long)x0).longValue() != ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Long)x0).longValue() != ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Long)x0).longValue() != ((Short)x1).shortValue();
		} else if (x0 instanceof Double) {
			if (x1 instanceof Byte)
				return ((Double)x0).doubleValue() != ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Double)x0).doubleValue() != ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Double)x0).doubleValue() != ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Double)x0).doubleValue() != ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Double)x0).doubleValue() != ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Double)x0).doubleValue() != ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Double)x0).doubleValue() != ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Double)x0).doubleValue() != ((Short)x1).shortValue();
		} else if (x0 instanceof Character) {
			if (x1 instanceof Byte)
				return ((Character)x0).charValue() != ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Character)x0).charValue() != ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Character)x0).charValue() != ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Character)x0).charValue() != ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Character)x0).charValue() != ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Character)x0).charValue() != ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Character)x0).charValue() != ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Character)x0).charValue() != ((Short)x1).shortValue();
		} else if (x0 instanceof Integer) {
			if (x1 instanceof Byte)
				return ((Integer)x0).intValue() != ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Integer)x0).intValue() != ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Integer)x0).intValue() != ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Integer)x0).intValue() != ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Integer)x0).intValue() != ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Integer)x0).intValue() != ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Integer)x0).intValue() != ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Integer)x0).intValue() != ((Short)x1).shortValue();
		} else if (x0 instanceof Boolean) {
//				if (x1 instanceof Byte)
//					return ((Boolean)x0).booleanValue() != ((Byte)x1).byteValue();
//				else if (x1 instanceof Long)
//					return ((Boolean)x0).booleanValue() != ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Boolean)x0).booleanValue() != ((Double)x1).doubleValue();
//				else if (x1 instanceof Character)
//					return ((Boolean)x0).booleanValue() != ((Character)x1).charValue();
//				else if (x1 instanceof Integer)
//					return ((Boolean)x0).booleanValue() != ((Integer)x1).intValue();
//				else
			if (x1 instanceof Boolean)
				return ((Boolean)x0).booleanValue() != ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Boolean)x0).booleanValue() != ((Float)x1).floatValue();
//				else if (x1 instanceof Short)
//					return ((Boolean)x0).booleanValue() != ((Short)x1).shortValue();
		} else if (x0 instanceof Float) {
			if (x1 instanceof Byte)
				return ((Float)x0).floatValue() != ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Float)x0).floatValue() != ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Float)x0).floatValue() != ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Float)x0).floatValue() != ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Float)x0).floatValue() != ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Float)x0).floatValue() != ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Float)x0).floatValue() != ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Float)x0).floatValue() != ((Short)x1).shortValue();
		} else if (x0 instanceof Short) {
			if (x1 instanceof Byte)
				return ((Short)x0).shortValue() != ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Short)x0).shortValue() != ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Short)x0).shortValue() != ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Short)x0).shortValue() != ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Short)x0).shortValue() != ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Short)x0).shortValue() != ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Short)x0).shortValue() != ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Short)x0).shortValue() != ((Short)x1).shortValue();
		}
		throw new IllegalArgumentException("unknown operating: " + (x0 == null ?null: x0.getClass().getCanonicalName()) + " " + key + " " + (x1 == null ?null: x1.getClass().getCanonicalName()));

	}



	//==
	static boolean hash9(Object x0 , String key, Object x1) {
		if (x0 instanceof Byte) {
			if (x1 instanceof Byte)
				return ((Byte)x0).byteValue() == ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Byte)x0).byteValue() == ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Byte)x0).byteValue() == ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Byte)x0).byteValue() == ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Byte)x0).byteValue() == ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Byte)x0).byteValue() == ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Byte)x0).byteValue() == ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Byte)x0).byteValue() == ((Short)x1).shortValue();
		} else if (x0 instanceof Long) {
			if (x1 instanceof Byte)
				return ((Long)x0).longValue() == ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Long)x0).longValue() == ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Long)x0).longValue() == ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Long)x0).longValue() == ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Long)x0).longValue() == ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Long)x0).longValue() == ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Long)x0).longValue() == ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Long)x0).longValue() == ((Short)x1).shortValue();
		} else if (x0 instanceof Double) {
			if (x1 instanceof Byte)
				return ((Double)x0).doubleValue() == ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Double)x0).doubleValue() == ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Double)x0).doubleValue() == ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Double)x0).doubleValue() == ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Double)x0).doubleValue() == ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Double)x0).doubleValue() == ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Double)x0).doubleValue() == ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Double)x0).doubleValue() == ((Short)x1).shortValue();
		} else if (x0 instanceof Character) {
			if (x1 instanceof Byte)
				return ((Character)x0).charValue() == ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Character)x0).charValue() == ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Character)x0).charValue() == ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Character)x0).charValue() == ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Character)x0).charValue() == ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Character)x0).charValue() == ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Character)x0).charValue() == ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Character)x0).charValue() == ((Short)x1).shortValue();
		} else if (x0 instanceof Integer) {
			if (x1 instanceof Byte)
				return ((Integer)x0).intValue() == ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Integer)x0).intValue() == ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Integer)x0).intValue() == ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Integer)x0).intValue() == ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Integer)x0).intValue() == ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Integer)x0).intValue() == ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Integer)x0).intValue() == ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Integer)x0).intValue() == ((Short)x1).shortValue();
		} else if (x0 instanceof Boolean) {
//				if (x1 instanceof Byte)
//					return ((Boolean)x0).booleanValue() == ((Byte)x1).byteValue();
//				else if (x1 instanceof Long)
//					return ((Boolean)x0).booleanValue() == ((Long)x1).longValue();
//				else if (x1 instanceof Double)
//					return ((Boolean)x0).booleanValue() == ((Double)x1).doubleValue();
//				else if (x1 instanceof Character)
//					return ((Boolean)x0).booleanValue() == ((Character)x1).charValue();
//				else if (x1 instanceof Integer)
//					return ((Boolean)x0).booleanValue() == ((Integer)x1).intValue();
//				else 
			if (x1 instanceof Boolean)
				return ((Boolean)x0).booleanValue() == ((Boolean)x1).booleanValue();
//				else if (x1 instanceof Float)
//					return ((Boolean)x0).booleanValue() == ((Float)x1).floatValue();
//				else if (x1 instanceof Short)
//					return ((Boolean)x0).booleanValue() == ((Short)x1).shortValue();
		} else if (x0 instanceof Float) {
			if (x1 instanceof Byte)
				return ((Float)x0).floatValue() == ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Float)x0).floatValue() == ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Float)x0).floatValue() == ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Float)x0).floatValue() == ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Float)x0).floatValue() == ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Float)x0).floatValue() == ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Float)x0).floatValue() == ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Float)x0).floatValue() == ((Short)x1).shortValue();
		} else if (x0 instanceof Short) {
			if (x1 instanceof Byte)
				return ((Short)x0).shortValue() == ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Short)x0).shortValue() == ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Short)x0).shortValue() == ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Short)x0).shortValue() == ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Short)x0).shortValue() == ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Short)x0).shortValue() == ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Short)x0).shortValue() == ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Short)x0).shortValue() == ((Short)x1).shortValue();
		}
		throw new IllegalArgumentException("unknown operating: " + (x0 == null ?null: x0.getClass().getCanonicalName()) + " " + key + " " + (x1 == null ?null: x1.getClass().getCanonicalName()));

	}


	//+
	static Object hash10(Object x0 , String key, Object x1) {
		if (x0 instanceof Byte) {
			if (x1 instanceof Byte)
				return ((Byte)x0).byteValue() + ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Byte)x0).byteValue() + ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Byte)x0).byteValue() + ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Byte)x0).byteValue() + ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Byte)x0).byteValue() + ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Byte)x0).byteValue() + ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Byte)x0).byteValue() + ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Byte)x0).byteValue() + ((Short)x1).shortValue();
		} else if (x0 instanceof Long) {
			if (x1 instanceof Byte)
				return ((Long)x0).longValue() + ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Long)x0).longValue() + ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Long)x0).longValue() + ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Long)x0).longValue() + ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Long)x0).longValue() + ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Long)x0).longValue() + ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Long)x0).longValue() + ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Long)x0).longValue() + ((Short)x1).shortValue();
		} else if (x0 instanceof Double) {
			if (x1 instanceof Byte)
				return ((Double)x0).doubleValue() + ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Double)x0).doubleValue() + ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Double)x0).doubleValue() + ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Double)x0).doubleValue() + ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Double)x0).doubleValue() + ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Double)x0).doubleValue() + ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Double)x0).doubleValue() + ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Double)x0).doubleValue() + ((Short)x1).shortValue();
		} else if (x0 instanceof Character) {
			if (x1 instanceof Byte)
				return ((Character)x0).charValue() + ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Character)x0).charValue() + ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Character)x0).charValue() + ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Character)x0).charValue() + ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Character)x0).charValue() + ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Character)x0).charValue() + ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Character)x0).charValue() + ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Character)x0).charValue() + ((Short)x1).shortValue();
		} else if (x0 instanceof Integer) {
			if (x1 instanceof Byte)
				return ((Integer)x0).intValue() + ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Integer)x0).intValue() + ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Integer)x0).intValue() + ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Integer)x0).intValue() + ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Integer)x0).intValue() + ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Integer)x0).intValue() + ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Integer)x0).intValue() + ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Integer)x0).intValue() + ((Short)x1).shortValue();
		} else if (x0 instanceof Float) {
			if (x1 instanceof Byte)
				return ((Float)x0).floatValue() + ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Float)x0).floatValue() + ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Float)x0).floatValue() + ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Float)x0).floatValue() + ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Float)x0).floatValue() + ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Float)x0).floatValue() + ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Float)x0).floatValue() + ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Float)x0).floatValue() + ((Short)x1).shortValue();
		} else if (x0 instanceof Short) {
			if (x1 instanceof Byte)
				return ((Short)x0).shortValue() + ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Short)x0).shortValue() + ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Short)x0).shortValue() + ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Short)x0).shortValue() + ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Short)x0).shortValue() + ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Short)x0).shortValue() + ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Short)x0).shortValue() + ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Short)x0).shortValue() + ((Short)x1).shortValue();
		}
		throw new IllegalArgumentException("unknown operating: " + (x0 == null ?null: x0.getClass().getCanonicalName()) + " " + key + " " + (x1 == null ?null: x1.getClass().getCanonicalName()));

	}


	//-
	static Object hash11(Object x0 , String key, Object x1) {
		if (x0 instanceof Byte) {
			if (x1 instanceof Byte)
				return ((Byte)x0).byteValue() - ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Byte)x0).byteValue() - ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Byte)x0).byteValue() - ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Byte)x0).byteValue() - ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Byte)x0).byteValue() - ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Byte)x0).byteValue() - ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Byte)x0).byteValue() - ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Byte)x0).byteValue() - ((Short)x1).shortValue();
		} else if (x0 instanceof Long) {
			if (x1 instanceof Byte)
				return ((Long)x0).longValue() - ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Long)x0).longValue() - ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Long)x0).longValue() - ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Long)x0).longValue() - ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Long)x0).longValue() - ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Long)x0).longValue() - ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Long)x0).longValue() - ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Long)x0).longValue() - ((Short)x1).shortValue();
		} else if (x0 instanceof Double) {
			if (x1 instanceof Byte)
				return ((Double)x0).doubleValue() - ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Double)x0).doubleValue() - ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Double)x0).doubleValue() - ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Double)x0).doubleValue() - ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Double)x0).doubleValue() - ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Double)x0).doubleValue() - ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Double)x0).doubleValue() - ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Double)x0).doubleValue() - ((Short)x1).shortValue();
		} else if (x0 instanceof Character) {
			if (x1 instanceof Byte)
				return ((Character)x0).charValue() - ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Character)x0).charValue() - ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Character)x0).charValue() - ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Character)x0).charValue() - ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Character)x0).charValue() - ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Character)x0).charValue() - ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Character)x0).charValue() - ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Character)x0).charValue() - ((Short)x1).shortValue();
		} else if (x0 instanceof Integer) {
			if (x1 instanceof Byte)
				return ((Integer)x0).intValue() - ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Integer)x0).intValue() - ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Integer)x0).intValue() - ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Integer)x0).intValue() - ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Integer)x0).intValue() - ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Integer)x0).intValue() - ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Integer)x0).intValue() - ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Integer)x0).intValue() - ((Short)x1).shortValue();
		} else if (x0 instanceof Float) {
			if (x1 instanceof Byte)
				return ((Float)x0).floatValue() - ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Float)x0).floatValue() - ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Float)x0).floatValue() - ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Float)x0).floatValue() - ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Float)x0).floatValue() - ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Float)x0).floatValue() - ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Float)x0).floatValue() - ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Float)x0).floatValue() - ((Short)x1).shortValue();
		} else if (x0 instanceof Short) {
			if (x1 instanceof Byte)
				return ((Short)x0).shortValue() - ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Short)x0).shortValue() - ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Short)x0).shortValue() - ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Short)x0).shortValue() - ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Short)x0).shortValue() - ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Short)x0).shortValue() - ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Short)x0).shortValue() - ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Short)x0).shortValue() - ((Short)x1).shortValue();
		}
		throw new IllegalArgumentException("unknown operating: " + (x0 == null ?null: x0.getClass().getCanonicalName()) + " " + key + " " + (x1 == null ?null: x1.getClass().getCanonicalName()));

	}


	//*
	static Object hash12(Object x0 , String key, Object x1) {
		if (x0 instanceof Byte) {
			if (x1 instanceof Byte)
				return ((Byte)x0).byteValue() * ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Byte)x0).byteValue() * ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Byte)x0).byteValue() * ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Byte)x0).byteValue() * ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Byte)x0).byteValue() * ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Byte)x0).byteValue() * ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Byte)x0).byteValue() * ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Byte)x0).byteValue() * ((Short)x1).shortValue();
		} else if (x0 instanceof Long) {
			if (x1 instanceof Byte)
				return ((Long)x0).longValue() * ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Long)x0).longValue() * ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Long)x0).longValue() * ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Long)x0).longValue() * ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Long)x0).longValue() * ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Long)x0).longValue() * ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Long)x0).longValue() * ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Long)x0).longValue() * ((Short)x1).shortValue();
		} else if (x0 instanceof Double) {
			if (x1 instanceof Byte)
				return ((Double)x0).doubleValue() * ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Double)x0).doubleValue() * ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Double)x0).doubleValue() * ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Double)x0).doubleValue() * ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Double)x0).doubleValue() * ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Double)x0).doubleValue() * ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Double)x0).doubleValue() * ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Double)x0).doubleValue() * ((Short)x1).shortValue();
		} else if (x0 instanceof Character) {
			if (x1 instanceof Byte)
				return ((Character)x0).charValue() * ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Character)x0).charValue() * ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Character)x0).charValue() * ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Character)x0).charValue() * ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Character)x0).charValue() * ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Character)x0).charValue() * ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Character)x0).charValue() * ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Character)x0).charValue() * ((Short)x1).shortValue();
		} else if (x0 instanceof Integer) {
			if (x1 instanceof Byte)
				return ((Integer)x0).intValue() * ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Integer)x0).intValue() * ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Integer)x0).intValue() * ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Integer)x0).intValue() * ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Integer)x0).intValue() * ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Integer)x0).intValue() * ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Integer)x0).intValue() * ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Integer)x0).intValue() * ((Short)x1).shortValue();
		} else if (x0 instanceof Float) {
			if (x1 instanceof Byte)
				return ((Float)x0).floatValue() * ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Float)x0).floatValue() * ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Float)x0).floatValue() * ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Float)x0).floatValue() * ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Float)x0).floatValue() * ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Float)x0).floatValue() * ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Float)x0).floatValue() * ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Float)x0).floatValue() * ((Short)x1).shortValue();
		} else if (x0 instanceof Short) {
			if (x1 instanceof Byte)
				return ((Short)x0).shortValue() * ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Short)x0).shortValue() * ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Short)x0).shortValue() * ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Short)x0).shortValue() * ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Short)x0).shortValue() * ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Short)x0).shortValue() * ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Short)x0).shortValue() * ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Short)x0).shortValue() * ((Short)x1).shortValue();
		}
		throw new IllegalArgumentException("unknown operating: " + (x0 == null ?null: x0.getClass().getCanonicalName()) + " " + key + " " + (x1 == null ?null: x1.getClass().getCanonicalName()));

	}



	// /
	static Object hash13(Object x0 , String key, Object x1) {
		if (x0 instanceof Byte) {
			if (x1 instanceof Byte)
				return ((Byte)x0).byteValue() / ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Byte)x0).byteValue() / ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Byte)x0).byteValue() / ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Byte)x0).byteValue() / ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Byte)x0).byteValue() / ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Byte)x0).byteValue() / ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Byte)x0).byteValue() / ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Byte)x0).byteValue() / ((Short)x1).shortValue();
		} else if (x0 instanceof Long) {
			if (x1 instanceof Byte)
				return ((Long)x0).longValue() / ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Long)x0).longValue() / ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Long)x0).longValue() / ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Long)x0).longValue() / ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Long)x0).longValue() / ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Long)x0).longValue() / ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Long)x0).longValue() / ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Long)x0).longValue() / ((Short)x1).shortValue();
		} else if (x0 instanceof Double) {
			if (x1 instanceof Byte)
				return ((Double)x0).doubleValue() / ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Double)x0).doubleValue() / ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Double)x0).doubleValue() / ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Double)x0).doubleValue() / ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Double)x0).doubleValue() / ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Double)x0).doubleValue() / ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Double)x0).doubleValue() / ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Double)x0).doubleValue() / ((Short)x1).shortValue();
		} else if (x0 instanceof Character) {
			if (x1 instanceof Byte)
				return ((Character)x0).charValue() / ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Character)x0).charValue() / ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Character)x0).charValue() / ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Character)x0).charValue() / ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Character)x0).charValue() / ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Character)x0).charValue() / ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Character)x0).charValue() / ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Character)x0).charValue() / ((Short)x1).shortValue();
		} else if (x0 instanceof Integer) {
			if (x1 instanceof Byte)
				return ((Integer)x0).intValue() / ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Integer)x0).intValue() / ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Integer)x0).intValue() / ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Integer)x0).intValue() / ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Integer)x0).intValue() / ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Integer)x0).intValue() / ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Integer)x0).intValue() / ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Integer)x0).intValue() / ((Short)x1).shortValue();
		} else if (x0 instanceof Float) {
			if (x1 instanceof Byte)
				return ((Float)x0).floatValue() / ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Float)x0).floatValue() / ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Float)x0).floatValue() / ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Float)x0).floatValue() / ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Float)x0).floatValue() / ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Float)x0).floatValue() / ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Float)x0).floatValue() / ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Float)x0).floatValue() / ((Short)x1).shortValue();
		} else if (x0 instanceof Short) {
			if (x1 instanceof Byte)
				return ((Short)x0).shortValue() / ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Short)x0).shortValue() / ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Short)x0).shortValue() / ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Short)x0).shortValue() / ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Short)x0).shortValue() / ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Short)x0).shortValue() / ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Short)x0).shortValue() / ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Short)x0).shortValue() / ((Short)x1).shortValue();
		}
		throw new IllegalArgumentException("unknown operating: " + (x0 == null ?null: x0.getClass().getCanonicalName()) + " " + key + " " + (x1 == null ?null: x1.getClass().getCanonicalName()));

	}


	//%
	static Object hash14(Object x0 , String key, Object x1) {
		if (x0 instanceof Byte) {
			if (x1 instanceof Byte)
				return ((Byte)x0).byteValue() % ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Byte)x0).byteValue() % ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Byte)x0).byteValue() % ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Byte)x0).byteValue() % ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Byte)x0).byteValue() % ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Byte)x0).byteValue() % ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Byte)x0).byteValue() % ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Byte)x0).byteValue() % ((Short)x1).shortValue();
		} else if (x0 instanceof Long) {
			if (x1 instanceof Byte)
				return ((Long)x0).longValue() % ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Long)x0).longValue() % ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Long)x0).longValue() % ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Long)x0).longValue() % ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Long)x0).longValue() % ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Long)x0).longValue() % ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Long)x0).longValue() % ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Long)x0).longValue() % ((Short)x1).shortValue();
		} else if (x0 instanceof Double) {
			if (x1 instanceof Byte)
				return ((Double)x0).doubleValue() % ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Double)x0).doubleValue() % ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Double)x0).doubleValue() % ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Double)x0).doubleValue() % ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Double)x0).doubleValue() % ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Double)x0).doubleValue() % ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Double)x0).doubleValue() % ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Double)x0).doubleValue() % ((Short)x1).shortValue();
		} else if (x0 instanceof Character) {
			if (x1 instanceof Byte)
				return ((Character)x0).charValue() % ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Character)x0).charValue() % ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Character)x0).charValue() % ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Character)x0).charValue() % ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Character)x0).charValue() % ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Character)x0).charValue() % ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Character)x0).charValue() % ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Character)x0).charValue() % ((Short)x1).shortValue();
		} else if (x0 instanceof Integer) {
			if (x1 instanceof Byte)
				return ((Integer)x0).intValue() % ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Integer)x0).intValue() % ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Integer)x0).intValue() % ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Integer)x0).intValue() % ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Integer)x0).intValue() % ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Integer)x0).intValue() % ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Integer)x0).intValue() % ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Integer)x0).intValue() % ((Short)x1).shortValue();
		} else if (x0 instanceof Float) {
			if (x1 instanceof Byte)
				return ((Float)x0).floatValue() % ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Float)x0).floatValue() % ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Float)x0).floatValue() % ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Float)x0).floatValue() % ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Float)x0).floatValue() % ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Float)x0).floatValue() % ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Float)x0).floatValue() % ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Float)x0).floatValue() % ((Short)x1).shortValue();
		} else if (x0 instanceof Short) {
			if (x1 instanceof Byte)
				return ((Short)x0).shortValue() % ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Short)x0).shortValue() % ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Short)x0).shortValue() % ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Short)x0).shortValue() % ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Short)x0).shortValue() % ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Short)x0).shortValue() % ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Short)x0).shortValue() % ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Short)x0).shortValue() % ((Short)x1).shortValue();
		}
		throw new IllegalArgumentException("unknown operating: " + (x0 == null ?null: x0.getClass().getCanonicalName()) + " " + key + " " + (x1 == null ?null: x1.getClass().getCanonicalName()));

	}


	//<
	static boolean hash15(Object x0 , String key, Object x1) {
		if (x0 instanceof Byte) {
			if (x1 instanceof Byte)
				return ((Byte)x0).byteValue() < ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Byte)x0).byteValue() < ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Byte)x0).byteValue() < ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Byte)x0).byteValue() < ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Byte)x0).byteValue() < ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Byte)x0).byteValue() < ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Byte)x0).byteValue() < ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Byte)x0).byteValue() < ((Short)x1).shortValue();
		} else if (x0 instanceof Long) {
			if (x1 instanceof Byte)
				return ((Long)x0).longValue() < ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Long)x0).longValue() < ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Long)x0).longValue() < ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Long)x0).longValue() < ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Long)x0).longValue() < ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Long)x0).longValue() < ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Long)x0).longValue() < ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Long)x0).longValue() < ((Short)x1).shortValue();
		} else if (x0 instanceof Double) {
			if (x1 instanceof Byte)
				return ((Double)x0).doubleValue() < ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Double)x0).doubleValue() < ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Double)x0).doubleValue() < ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Double)x0).doubleValue() < ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Double)x0).doubleValue() < ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Double)x0).doubleValue() < ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Double)x0).doubleValue() < ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Double)x0).doubleValue() < ((Short)x1).shortValue();
		} else if (x0 instanceof Character) {
			if (x1 instanceof Byte)
				return ((Character)x0).charValue() < ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Character)x0).charValue() < ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Character)x0).charValue() < ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Character)x0).charValue() < ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Character)x0).charValue() < ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Character)x0).charValue() < ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Character)x0).charValue() < ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Character)x0).charValue() < ((Short)x1).shortValue();
		} else if (x0 instanceof Integer) {
			if (x1 instanceof Byte)
				return ((Integer)x0).intValue() < ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Integer)x0).intValue() < ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Integer)x0).intValue() < ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Integer)x0).intValue() < ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Integer)x0).intValue() < ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Integer)x0).intValue() < ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Integer)x0).intValue() < ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Integer)x0).intValue() < ((Short)x1).shortValue();
		} else if (x0 instanceof Float) {
			if (x1 instanceof Byte)
				return ((Float)x0).floatValue() < ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Float)x0).floatValue() < ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Float)x0).floatValue() < ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Float)x0).floatValue() < ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Float)x0).floatValue() < ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Float)x0).floatValue() < ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Float)x0).floatValue() < ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Float)x0).floatValue() < ((Short)x1).shortValue();
		} else if (x0 instanceof Short) {
			if (x1 instanceof Byte)
				return ((Short)x0).shortValue() < ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Short)x0).shortValue() < ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Short)x0).shortValue() < ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Short)x0).shortValue() < ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Short)x0).shortValue() < ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Short)x0).shortValue() < ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Short)x0).shortValue() < ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Short)x0).shortValue() < ((Short)x1).shortValue();
		}
		throw new IllegalArgumentException("unknown operating: " + (x0 == null ?null: x0.getClass().getCanonicalName()) + " " + key + " " + (x1 == null ?null: x1.getClass().getCanonicalName()));

	}


	//>
	static boolean hash16(Object x0 , String key, Object x1) {
		if (x0 instanceof Byte) {
			if (x1 instanceof Byte)
				return ((Byte)x0).byteValue() > ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Byte)x0).byteValue() > ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Byte)x0).byteValue() > ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Byte)x0).byteValue() > ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Byte)x0).byteValue() > ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Byte)x0).byteValue() > ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Byte)x0).byteValue() > ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Byte)x0).byteValue() > ((Short)x1).shortValue();
		} else if (x0 instanceof Long) {
			if (x1 instanceof Byte)
				return ((Long)x0).longValue() > ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Long)x0).longValue() > ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Long)x0).longValue() > ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Long)x0).longValue() > ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Long)x0).longValue() > ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Long)x0).longValue() > ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Long)x0).longValue() > ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Long)x0).longValue() > ((Short)x1).shortValue();
		} else if (x0 instanceof Double) {
			if (x1 instanceof Byte)
				return ((Double)x0).doubleValue() > ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Double)x0).doubleValue() > ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Double)x0).doubleValue() > ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Double)x0).doubleValue() > ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Double)x0).doubleValue() > ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Double)x0).doubleValue() > ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Double)x0).doubleValue() > ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Double)x0).doubleValue() > ((Short)x1).shortValue();
		} else if (x0 instanceof Character) {
			if (x1 instanceof Byte)
				return ((Character)x0).charValue() > ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Character)x0).charValue() > ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Character)x0).charValue() > ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Character)x0).charValue() > ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Character)x0).charValue() > ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Character)x0).charValue() > ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Character)x0).charValue() > ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Character)x0).charValue() > ((Short)x1).shortValue();
		} else if (x0 instanceof Integer) {
			if (x1 instanceof Byte)
				return ((Integer)x0).intValue() > ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Integer)x0).intValue() > ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Integer)x0).intValue() > ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Integer)x0).intValue() > ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Integer)x0).intValue() > ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Integer)x0).intValue() > ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Integer)x0).intValue() > ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Integer)x0).intValue() > ((Short)x1).shortValue();
		} else if (x0 instanceof Float) {
			if (x1 instanceof Byte)
				return ((Float)x0).floatValue() > ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Float)x0).floatValue() > ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Float)x0).floatValue() > ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Float)x0).floatValue() > ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Float)x0).floatValue() > ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Float)x0).floatValue() > ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Float)x0).floatValue() > ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Float)x0).floatValue() > ((Short)x1).shortValue();
		} else if (x0 instanceof Short) {
			if (x1 instanceof Byte)
				return ((Short)x0).shortValue() > ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Short)x0).shortValue() > ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Short)x0).shortValue() > ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Short)x0).shortValue() > ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Short)x0).shortValue() > ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Short)x0).shortValue() > ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Short)x0).shortValue() > ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Short)x0).shortValue() > ((Short)x1).shortValue();
		}
		throw new IllegalArgumentException("unknown operating: " + (x0 == null ?null: x0.getClass().getCanonicalName()) + " " + key + " " + (x1 == null ?null: x1.getClass().getCanonicalName()));

	}


	//<=
	static boolean hash17(Object x0 , String key, Object x1) {
		if (x0 instanceof Byte) {
			if (x1 instanceof Byte)
				return ((Byte)x0).byteValue() <= ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Byte)x0).byteValue() <= ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Byte)x0).byteValue() <= ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Byte)x0).byteValue() <= ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Byte)x0).byteValue() <= ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Byte)x0).byteValue() <= ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Byte)x0).byteValue() <= ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Byte)x0).byteValue() <= ((Short)x1).shortValue();
		} else if (x0 instanceof Long) {
			if (x1 instanceof Byte)
				return ((Long)x0).longValue() <= ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Long)x0).longValue() <= ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Long)x0).longValue() <= ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Long)x0).longValue() <= ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Long)x0).longValue() <= ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Long)x0).longValue() <= ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Long)x0).longValue() <= ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Long)x0).longValue() <= ((Short)x1).shortValue();
		} else if (x0 instanceof Double) {
			if (x1 instanceof Byte)
				return ((Double)x0).doubleValue() <= ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Double)x0).doubleValue() <= ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Double)x0).doubleValue() <= ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Double)x0).doubleValue() <= ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Double)x0).doubleValue() <= ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Double)x0).doubleValue() <= ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Double)x0).doubleValue() <= ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Double)x0).doubleValue() <= ((Short)x1).shortValue();
		} else if (x0 instanceof Character) {
			if (x1 instanceof Byte)
				return ((Character)x0).charValue() <= ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Character)x0).charValue() <= ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Character)x0).charValue() <= ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Character)x0).charValue() <= ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Character)x0).charValue() <= ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Character)x0).charValue() <= ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Character)x0).charValue() <= ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Character)x0).charValue() <= ((Short)x1).shortValue();
		} else if (x0 instanceof Integer) {
			if (x1 instanceof Byte)
				return ((Integer)x0).intValue() <= ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Integer)x0).intValue() <= ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Integer)x0).intValue() <= ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Integer)x0).intValue() <= ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Integer)x0).intValue() <= ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Integer)x0).intValue() <= ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Integer)x0).intValue() <= ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Integer)x0).intValue() <= ((Short)x1).shortValue();
		} else if (x0 instanceof Float) {
			if (x1 instanceof Byte)
				return ((Float)x0).floatValue() <= ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Float)x0).floatValue() <= ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Float)x0).floatValue() <= ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Float)x0).floatValue() <= ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Float)x0).floatValue() <= ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Float)x0).floatValue() <= ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Float)x0).floatValue() <= ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Float)x0).floatValue() <= ((Short)x1).shortValue();
		} else if (x0 instanceof Short) {
			if (x1 instanceof Byte)
				return ((Short)x0).shortValue() <= ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Short)x0).shortValue() <= ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Short)x0).shortValue() <= ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Short)x0).shortValue() <= ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Short)x0).shortValue() <= ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Short)x0).shortValue() <= ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Short)x0).shortValue() <= ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Short)x0).shortValue() <= ((Short)x1).shortValue();
		}
		throw new IllegalArgumentException("unknown operating: " + (x0 == null ?null: x0.getClass().getCanonicalName()) + " " + key + " " + (x1 == null ?null: x1.getClass().getCanonicalName()));

	}

	//>=
	static boolean hash18(Object x0 , String key, Object x1) {
		if (x0 instanceof Byte) {
			if (x1 instanceof Byte)
				return ((Byte)x0).byteValue() >= ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Byte)x0).byteValue() >= ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Byte)x0).byteValue() >= ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Byte)x0).byteValue() >= ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Byte)x0).byteValue() >= ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Byte)x0).byteValue() >= ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Byte)x0).byteValue() >= ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Byte)x0).byteValue() >= ((Short)x1).shortValue();
		} else if (x0 instanceof Long) {
			if (x1 instanceof Byte)
				return ((Long)x0).longValue() >= ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Long)x0).longValue() >= ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Long)x0).longValue() >= ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Long)x0).longValue() >= ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Long)x0).longValue() >= ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Long)x0).longValue() >= ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Long)x0).longValue() >= ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Long)x0).longValue() >= ((Short)x1).shortValue();
		} else if (x0 instanceof Double) {
			if (x1 instanceof Byte)
				return ((Double)x0).doubleValue() >= ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Double)x0).doubleValue() >= ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Double)x0).doubleValue() >= ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Double)x0).doubleValue() >= ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Double)x0).doubleValue() >= ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Double)x0).doubleValue() >= ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Double)x0).doubleValue() >= ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Double)x0).doubleValue() >= ((Short)x1).shortValue();
		} else if (x0 instanceof Character) {
			if (x1 instanceof Byte)
				return ((Character)x0).charValue() >= ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Character)x0).charValue() >= ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Character)x0).charValue() >= ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Character)x0).charValue() >= ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Character)x0).charValue() >= ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Character)x0).charValue() >= ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Character)x0).charValue() >= ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Character)x0).charValue() >= ((Short)x1).shortValue();
		} else if (x0 instanceof Integer) {
			if (x1 instanceof Byte)
				return ((Integer)x0).intValue() >= ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Integer)x0).intValue() >= ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Integer)x0).intValue() >= ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Integer)x0).intValue() >= ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Integer)x0).intValue() >= ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Integer)x0).intValue() >= ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Integer)x0).intValue() >= ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Integer)x0).intValue() >= ((Short)x1).shortValue();
		} else if (x0 instanceof Float) {
			if (x1 instanceof Byte)
				return ((Float)x0).floatValue() >= ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Float)x0).floatValue() >= ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Float)x0).floatValue() >= ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Float)x0).floatValue() >= ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Float)x0).floatValue() >= ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Float)x0).floatValue() >= ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Float)x0).floatValue() >= ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Float)x0).floatValue() >= ((Short)x1).shortValue();
		} else if (x0 instanceof Short) {
			if (x1 instanceof Byte)
				return ((Short)x0).shortValue() >= ((Byte)x1).byteValue();
			else if (x1 instanceof Long)
				return ((Short)x0).shortValue() >= ((Long)x1).longValue();
			else if (x1 instanceof Double)
				return ((Short)x0).shortValue() >= ((Double)x1).doubleValue();
			else if (x1 instanceof Character)
				return ((Short)x0).shortValue() >= ((Character)x1).charValue();
			else if (x1 instanceof Integer)
				return ((Short)x0).shortValue() >= ((Integer)x1).intValue();
//				else if (x1 instanceof Boolean)
//					return ((Short)x0).shortValue() >= ((Boolean)x1).booleanValue();
			else if (x1 instanceof Float)
				return ((Short)x0).shortValue() >= ((Float)x1).floatValue();
			else if (x1 instanceof Short)
				return ((Short)x0).shortValue() >= ((Short)x1).shortValue();
		}
		throw new IllegalArgumentException("unknown operating: " + (x0 == null ?null: x0.getClass().getCanonicalName()) + " " + key + " " + (x1 == null ?null: x1.getClass().getCanonicalName()));
	}
}

