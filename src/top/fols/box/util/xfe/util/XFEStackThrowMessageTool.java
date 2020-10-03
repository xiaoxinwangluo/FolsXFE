package top.fols.box.util.xfe.util;

import java.io.PrintWriter;
import top.fols.box.io.base.XCharArrayWriter;
import top.fols.box.lang.XClass;
import top.fols.box.util.XStringJoiner;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;

//*****
public class XFEStackThrowMessageTool {
	private static String joinParamJavaClassName(Object[] objs) {
		XStringJoiner sb = new XStringJoiner(", ", "(", ")");
		if (null != objs) {
			for (Object oi : objs) {
				sb.add(getJavaClassName(oi));
			}
		}
		return sb.toString();
	}
	private static String joinParamJavaClassName(Class<?>[] objs) {
		XStringJoiner sb = new XStringJoiner(", ", "(", ")");
		if (null != objs) {
			for (Class<?> oi : objs) {
				sb.add(getJavaClassName(oi));
			}
		}
		return sb.toString();
	}
	public static String getXFEJavaClassName(XFEClass cls) {
		return null == cls ?null: cls.getName();
	}
	public static String getJavaClassName(Class<?> cls) {
		return null == cls ?null: XClass.toAbsCanonicalName(cls);
	}
	public static String getJavaClassName(Object cls) {
		return null == cls ?null: XClass.toAbsCanonicalName(cls.getClass());
	}
		
	
	
	
	public static String noXFEClassLoader(XFEClass xfeclass){
		StringBuilder sb = new StringBuilder();
		sb.append("this xfeclass no set xfeclassloader: ").append(getXFEJavaClassName(xfeclass));
		return sb.toString();
	}
		
		
		
	public static String notFoundJavaClassStaticField(Class<?> cls, String name) {
		StringBuilder sb = new StringBuilder();
		sb.append("not found javaStaticField: ").append(getJavaClassName(cls)).append(".").append(name);
		return sb.toString();
	}


	public static String notFoundJavaClassStaticMethod(Class<?> cls, String name, Object... args) {
		StringBuilder sb = new StringBuilder();
		sb.append("not found javaStaticMethod: ").append(getJavaClassName(cls)).append(".").append(name).append(joinParamJavaClassName(args));
		return sb.toString();
	}



	public static String notFoundJavaClass(String name) {
		StringBuilder sb = new StringBuilder();
		sb.append("not found javaClass: ").append(name);
		return sb.toString();
	}

	public static String notFoundXfeClass(String name) {
		StringBuilder sb = new StringBuilder();
		sb.append("not found xfeClass: ").append(name);
		return sb.toString();
	}

	public static String autoCodeLoaderNotFoundXfeClass(String name, String path) {
		StringBuilder sb = new StringBuilder();
		sb.append("not found xfeClass: ").append(name).append(" from: ").append(path);
		return sb.toString();
	}


	public static String notFoundObjectFieled(Object object, String name) {
		StringBuilder sb = new StringBuilder();
		sb.append("not found objectField: ").append(getJavaClassName(object)).append('.').append(name);
		return sb.toString();
	}

	public static String notFoundObjectMethod(Object object, String name, Object[] args) {
		StringBuilder sb = new StringBuilder();
		sb.append("not found objectMethod: ").append(getJavaClassName(object)).append('.').append(name).append(joinParamJavaClassName(args));
		return sb.toString();
	}


	public static String cannotFromObjectGetXfeClass(Object object) {
		StringBuilder sb = new StringBuilder();
		sb.append("cannot from object get xfeClass: ").append(object).append(" objectClass: ").append(getJavaClassName(object));
		return sb.toString();
	}

	public static String notFoundXfeClassField(String cls, String name) {
		StringBuilder sb = new StringBuilder();
		sb.append("not found xfeField: ").append(cls).append(XFEKeyWords.CODE_OBJECT_POINT_SYMBOL).append(name);
		return sb.toString();
	}
	public static String notFoundXfeClassMethod(String cls, String name) {
		StringBuilder sb = new StringBuilder();
		sb.append("not found xfeMethod: ").append(cls).append(XFEKeyWords.CODE_OBJECT_POINT_SYMBOL).append(name).append(XFEKeyWords.CODE_PARAM_JOIN_SYMBOL).append(XFEKeyWords.CODE_PARAM_END_SYMBOL);
		return sb.toString();
	}
	public static String notFoundXfeClassMethod(String cls, String name, Object[] args) {
		StringBuilder sb = new StringBuilder();
		sb.append("not found xfeMethod: ").append(cls).append(XFEKeyWords.CODE_OBJECT_POINT_SYMBOL).append(name).append(joinParamJavaClassName(args));
		return sb.toString();
	}
	public static String notFoundXfeClassBaseMethod(String name, Object[] args) {
		StringBuilder sb = new StringBuilder();
		sb.append("not found xfeMethod: ").append(name).append(joinParamJavaClassName(args));
		return sb.toString();
	}
	public static String notFoundXfeClassBaseMethod(String name, int argslen) {
		StringBuilder sb = new StringBuilder();
		sb.append("not found xfeMethod: ").append(name).append(XFEKeyWords.CODE_PARAM_JOIN_SYMBOL).append("paramlen=").append(argslen).append(XFEKeyWords.CODE_PARAM_END_SYMBOL);
		return sb.toString();
	}
	public static String notFoundXfeClassBaseMethodOrMethod(String cls, String name) {
		StringBuilder sb = new StringBuilder();
		sb.append("not found xfeMethod: ")
			.append(name).append(XFEKeyWords.CODE_PARAM_JOIN_SYMBOL).append(XFEKeyWords.CODE_PARAM_END_SYMBOL)
			.append(" or ")
			.append(cls).append(XFEKeyWords.CODE_OBJECT_POINT_SYMBOL).append(name).append(XFEKeyWords.CODE_PARAM_JOIN_SYMBOL).append(XFEKeyWords.CODE_PARAM_END_SYMBOL);
		return sb.toString();
	}
	
	
	
	
	public static String cannotSetVariable(String name) {
		StringBuilder sb = new StringBuilder();
		sb.append("cannot set xfeVariable: ").append(name);
		return sb.toString();
	}
	
	
	public static String getJavaStackString(Throwable e) {
		XCharArrayWriter sw = new XCharArrayWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String str = sw.toString();
		sw.close(); 
		pw.close();
		return str.trim();
	}





}
