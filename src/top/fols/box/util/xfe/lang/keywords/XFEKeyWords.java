package top.fols.box.util.xfe.lang.keywords;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethodManager;
import top.fols.box.util.xfe.executer.basemethod.lang.BREAK;
import top.fols.box.util.xfe.executer.basemethod.lang.CALC;
import top.fols.box.util.xfe.executer.basemethod.lang.CONTINUE;
import top.fols.box.util.xfe.executer.basemethod.lang.EQUAL;
import top.fols.box.util.xfe.executer.basemethod.lang.EQUALS;
import top.fols.box.util.xfe.executer.basemethod.lang.GETCLASS;
import top.fols.box.util.xfe.executer.basemethod.lang.GETJAVACLASS;
import top.fols.box.util.xfe.executer.basemethod.lang.IMPORT;
import top.fols.box.util.xfe.executer.basemethod.lang.IMPORTJAVA;
import top.fols.box.util.xfe.executer.basemethod.lang.LOADCODE;
import top.fols.box.util.xfe.executer.basemethod.lang.NEW;
import top.fols.box.util.xfe.executer.basemethod.lang.NOTEQUAL;
import top.fols.box.util.xfe.executer.basemethod.lang.NOTEQUALS;
import top.fols.box.util.xfe.executer.basemethod.lang.RETURN;
import top.fols.box.util.xfe.executer.basemethod.lang.SLEEP;
import top.fols.box.util.xfe.executer.basemethod.lang.STACK;
import top.fols.box.util.xfe.executer.basemethod.lang.STRF;
import top.fols.box.util.xfe.executer.basemethod.lang.THREAD;
import top.fols.box.util.xfe.executer.basemethod.lang.THROW;
import top.fols.box.util.xfe.executer.basemethod.lang.array.ARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.array.ARRAYCAST;
import top.fols.box.util.xfe.executer.basemethod.lang.array.NEWARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.array.base_array.BOOLEANARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.array.base_array.BYTEARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.array.base_array.CHARARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.array.base_array.DOUBLEARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.array.base_array.FLOATARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.array.base_array.INTARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.array.base_array.LONGARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.array.base_array.NEWBOOLEANARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.array.base_array.NEWBYTEARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.array.base_array.NEWCHARARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.array.base_array.NEWDOUBLEARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.array.base_array.NEWFLOATARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.array.base_array.NEWINTARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.array.base_array.NEWLONGARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.array.base_array.NEWOBJECTARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.array.base_array.NEWSHORTARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.array.base_array.NEWSTRINGARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.array.base_array.OBJECTARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.array.base_array.SHORTARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.array.base_array.STRINGARRAY;
import top.fols.box.util.xfe.executer.basemethod.lang.base.BOOLEAN;
import top.fols.box.util.xfe.executer.basemethod.lang.base.BYTE;
import top.fols.box.util.xfe.executer.basemethod.lang.base.CHAR;
import top.fols.box.util.xfe.executer.basemethod.lang.base.DOUBLE;
import top.fols.box.util.xfe.executer.basemethod.lang.base.FLOAT;
import top.fols.box.util.xfe.executer.basemethod.lang.base.INT;
import top.fols.box.util.xfe.executer.basemethod.lang.base.LONG;
import top.fols.box.util.xfe.executer.basemethod.lang.base.SHORT;
import top.fols.box.util.xfe.executer.basemethod.lang.base.STRING;
import top.fols.box.util.xfe.executer.basemethod.util.MAP;
import top.fols.box.util.xfe.executer.basemethod.util.PRINT;
import top.fols.box.util.xfe.executer.basemethod.util.PRINTLN;
import top.fols.box.util.xfe.executer.basemethod.util.RANDOM;
import top.fols.box.util.xfe.executer.basemethod.util.TIME;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.lang.XFEClassInstance;
import top.fols.box.util.xfe.lang.XFEClassLoader;
import top.fols.box.util.xfe.util.interfacelist.XFEInterfaceGetJavaClass;
import top.fols.box.util.xfe.util.interfacelist.XFEInterfaceGetXFEClass;


public class XFEKeyWords {

	/* ---------- */
	public static final String 		CODE_FILE_EXTENSION_NAME_SEPARATOR = ".";
	public static final String 		CODE_FILE_EXTENSION_NAME = "xfe";

	public static final Charset 	CODE_DEFAULT_CHARSET_UTF_8 = Charset.forName("UTF-8");
    public static final String      CODE_METHOD_MAIN_NAME = "main";


    public static final String      CODE_NOTE_LINE_START = "//";
	public static final char 		CODE_LINE_SEPARATOR_CHAR = '\n';
	public static final String 		CODE_LINE_MULTI_CODE_SEPARATOR = ";";


	public static final String		CODE_OBJECT_POINT_SYMBOL = ".";

	public static final String		CODE_VARIABLE_ASSIGNMENT_SYMBOL = "=";
    public static final String      CODE_PARAM_JOIN_SYMBOL = "(";
    public static final String      CODE_PARAM_SEPARATOR = ",";
    public static final String      CODE_PARAM_END_SYMBOL = ")";


	//*****
	/**
	 * XFE KeyWord
	 */
	public static final String 
	RETURN = "return",  	FUN = "fun", 				END 	= "end",
	ENDL = "endl", 			FINAL = "final",			THIS 	= "this",
	INIT = "init", 			TRUE = "true", 				FALSE 	= "false",
	NULL = "null",			IF = "if",					WHILE 	= "while",
	BREAK = "break",		CONTINUE = "continue",		STATIC 	= "static",
    TRY = "try",

	NEW = "new"//XFEClassStaticPoint / XFEJavaClassStaticPoint, create instance method name extension		
	;
	private static Map<String, String> KEYWORDS = putKeyWords(new String[] {
			RETURN, 	FUN, 		END,
			ENDL, 		FINAL, 		THIS,
			INIT,		TRUE,		FALSE,
			NULL,		IF,			WHILE,
			BREAK, 		CONTINUE,	STATIC,
			TRY,

            NEW
		});
	private static Map<String, String> putKeyWords(String[]... str) {
		if (null == KEYWORDS) {
			KEYWORDS = new HashMap<>();
		}
		for (String[] strElement: str) {
			for (String s: strElement) {
				KEYWORDS.put(s, s);
			}
		}
		return KEYWORDS;
	}
	/**
	 * 唯一内存关键词
	 */
	public static String searchRootKeyWords(String keywords) {
		if (null == keywords) {
			return null;
		} else {
			String cache = KEYWORDS.get(keywords);
			return null == cache ?keywords: cache;
		}
	}







	/**
	 * XFEAbstractVariablePoint.java
	 */
	private static final String CODE_XFEVARIABLEPOINT_METHOD_NAME_NEW = XFEKeyWords.NEW;
	public static boolean isNewMethodName(String name) {
		return CODE_XFEVARIABLEPOINT_METHOD_NAME_NEW == name;
	}





	/**
	 * @XFECodeLoader dealStringVar/dealCharVar/dealBaseVar > XFEFinalVariableManager.java
	 *
	 * all name list
	 * 基础数据 固定不可变
	 *
	 * 声明方法
	 *
     * type:value
     * type     字母
     * value    大写字母 小写字母 数字 . + -
     *
     * 
	 * byte:xx >> final.bytexx
	 * int:xx >> final.intxx
	 * long:xx >> final.longxx
	 * double:xx >> final.doublexx_xx
	 * float:xx >> final.floatxx_xx
	 * short:xx >> final.shortxx
	 * 'x' >> final.charxx
	 * "x" >> final.stringxx
	 *
	 * final.true / final.false
	 * final.null
	 *
	 */
	public static final String BASE_VARIABLE_TYPE_BYTE = "byte";
	public static final String BASE_VARIABLE_TYPE_INT = "int";
	public static final String BASE_VARIABLE_TYPE_LONG = "long";
	public static final String BASE_VARIABLE_TYPE_DOUBLE = "double";
	public static final String BASE_VARIABLE_TYPE_FLOAT = "float";
	public static final String BASE_VARIABLE_TYPE_SHORT = "short";
	public static final char BASE_VARIABLE_TYPE_STATEMENT = ':';

	public static final String BASE_VARIABLE_TYPE_CHAR = "char";
	public static final String BASE_VARIABLE_TYPE_STRING = "string";
	//public static final String BASE_VARIABLE_TYPE_BOOLEAN = "boolean";
	//public static final String BASE_VARIABLE_TYPE_OBJECT = "object";




	public static final Map<String, Object> getFinalBaseFieldValues() {
		HashMap<String, Object> kf = new HashMap<>();
		kf.put(XFEKeyWords.TRUE, 	true);
		kf.put(XFEKeyWords.FALSE, 	false);
		kf.put(XFEKeyWords.NULL, 	null);
		return kf;
	}


	//*****
	public static final XFEBaseMethodManager DEFAULT_BASEMETHOD_MANAGER = new XFEBaseMethodManager(
		//----lang
		new RETURN(),			new CONTINUE(),			new BREAK(),

		new IMPORT(),			new IMPORTJAVA(), 

		new EQUALS(),			new EQUAL(),			new NOTEQUALS(),
		new NOTEQUAL(),

		new SLEEP(),

		new NEW(), 			

		new CALC(),			

		new GETJAVACLASS(),			new GETCLASS(),	

		new LOADCODE(),

		new THROW(),		

		new THREAD(),		


		new STRF(),

		new BYTE(),				new INT(),				new LONG(),				new DOUBLE(),
		new FLOAT(),			new SHORT(),			new CHAR(),				new BOOLEAN(),
		new STRING(),			//new OBJECT(),

		new ARRAYCAST(),

		new ARRAY(),
		new BYTEARRAY(),		new INTARRAY(),			new LONGARRAY(),		new DOUBLEARRAY(),
		new FLOATARRAY(),		new SHORTARRAY(),		new CHARARRAY(),		new BOOLEANARRAY(),
		new STRINGARRAY(),		new OBJECTARRAY(),	

		new NEWARRAY(),
		new NEWBYTEARRAY(),		new NEWINTARRAY(),		new NEWLONGARRAY(),		new NEWDOUBLEARRAY(),
		new NEWFLOATARRAY(),	new NEWSHORTARRAY(),	new NEWCHARARRAY(),		new NEWBOOLEANARRAY(),
		new NEWSTRINGARRAY(),	new NEWOBJECTARRAY(),

		new STACK(),



		//----util
		new PRINT(), 			new PRINTLN(), 		

		new MAP(),				

		new RANDOM(),

		new TIME()
	);


	public static boolean isCodeBlockHeader(String option) {
		return 
			option == XFEKeyWords.IF || 
			option == XFEKeyWords.WHILE || 
			option == XFEKeyWords.TRY;
	}
	public static boolean isCodeBlockTail(String option) {
		return 
			option == XFEKeyWords.ENDL;
	}



	//prohibit set variable
	public static boolean isFinalVariable(String name) {
		return 
			XFEKeyWords.FINAL == name || 
			XFEKeyWords.THIS == name || 
			XFEKeyWords.STATIC == name;
	}

	public static void initExecuterParam(XFEStack stack, Map<String, Object> map, XFEExecute execute) {
		XFEClassInstance xfeclassinstance = execute.getXFEClassInstance();
		//setBaseData ***** 
		map.put(XFEKeyWords.FINAL, 			xfeclassinstance.getFinalVariablePoint());
		map.put(XFEKeyWords.THIS, 			xfeclassinstance);
		map.put(XFEKeyWords.STATIC, 		xfeclassinstance.getStaticInstance(stack));
	}


    public static boolean isStandardVariableName(String str) {
		int strlen = str.length();
		if (strlen == 0) {
			return false;
		}
		int i = 0;
		char first = str.charAt(i++);
		if (Character.isDigit(first)) {
			return false;
		} else {
			for (;i < strlen;i++) {
				if (!isStandardVariableNameChar(str.charAt(i))) {
					return false;
				}
			}
			return true;
		}
	}
	public static boolean isStandardVariableNameChar(char ch) {
		return 
			Character.isDigit(ch) || 
			Character.isLowerCase(ch) ||
			Character.isUpperCase(ch) ||
			ch == '_';
	}







	public static class XFEBaseMethodResource {
		public static class STRF {
			public static final char ANNOTATION_START = '{';
			public static final char ANNOTATION_END = '}';
		}
	}
	public static class XFEUTILRESOURCE {
	    public static class ArrayOption {
			public static final String METHOD_GET = "get";
			public static final String METHOD_SET = "set";
			public static final String METHOD_LENGTH = "length";
		}
	}
















	public static Class getJavaClassInterfaceGetClass(Object object) {
		Class cls = null;
		if (object instanceof XFEInterfaceGetJavaClass) {
			cls = ((XFEInterfaceGetJavaClass)object).getJavaClass();
		} else {
			cls = null;
		}
		return cls;
	}
	public static XFEClass getXFEClassInterfaceGetClass(Object object) {
		XFEClass cls = null;
		if (object instanceof XFEInterfaceGetXFEClass) {
			cls = ((XFEInterfaceGetXFEClass)object).getXFEClass();
		} else {
			cls = null;
		}
		return cls;
	}

	public static boolean isXFEClassLoader(Object object) {
		return object instanceof XFEClassLoader;
	}



	public static final long XFE_VERSION = 202006011627L; //XFE版本号

	public static final long XFE_CODE_LOADER_VERSION = 3L; //代码加载器版本号
	public static final long XFE_EXECUTER_VERSION = 3L; //代码执行器版本号
}
