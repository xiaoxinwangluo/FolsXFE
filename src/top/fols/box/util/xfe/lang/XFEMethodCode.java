package top.fols.box.util.xfe.lang;
import java.util.StringJoiner;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.lang.XFECodeLoader.Assignment;
import top.fols.box.util.xfe.lang.XFECodeLoader.Code;
import top.fols.box.util.xfe.lang.XFECodeLoader.Fun;
import top.fols.box.util.xfe.lang.XFECodeLoader.NEXT_CODE;
import top.fols.box.util.xfe.lang.XFECodeLoader.Point;
import top.fols.box.util.xfe.lang.XFECodeLoader.Block;
import top.fols.box.util.xfe.lang.XFECodeLoader.Var;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;

public final class XFEMethodCode {


	static final XFEMethodCode[] NULL_METHOD_CODE = new XFEMethodCode[0];

	public int lineNumber;
    public XFECodeLoader.ContentLinked<Var> rootCode;//Code;
    public String codeBlocOptionName = null;//CodeBlockHeader(if or try or while)....
    public XFEMethodCode[] block = NULL_METHOD_CODE, elseblock = NULL_METHOD_CODE;


	public static CharSequence lineAddresString(String fileName, String className, String methodName, int line) {
		return new StringBuilder()
			.append(className).append(XFEKeyWords.CODE_OBJECT_POINT_SYMBOL)
			.append(methodName)
			.append('(').append(fileName).append(':').append(line).append(')');
	}
	public static CharSequence lineAddresString(XFEStack.StackElement se) {
		if (null == se) {
			return "null";
		}
		return lineAddresString(se.getFileName(), se.getClassName(), se.getMethodName(), se.getLine());
	}
	public static CharSequence lineAddresString(XFEMethod method, XFEMethodCode se) {
		if (null == se) {
			return "null";
		}
		return lineAddresString(method.getFileName(), method.getClassName(), method.getName(), se.lineNumber);
	}
	public static CharSequence lineAddresString(String fileName, String className, String methodName, XFEMethodCode se) {
		return lineAddresString(fileName, className, methodName, se.lineNumber);
	}







    private static String formatCodeFromRoot(XFECodeLoader.ContentLinked<Var> firstVar, boolean interrupt) throws RuntimeException {
        StringBuilder string = new StringBuilder();

        XFECodeLoader.ContentLinked<Var> now = firstVar;//root
        while (null != now) {
            Var linkedVar = now.content();
            if (linkedVar instanceof Assignment) {
                string.append(linkedVar.name);
            } else if (linkedVar instanceof Point) {
                string.append(linkedVar.name);
            } else if (linkedVar instanceof Fun) {
                string.append(linkedVar.name);
                StringJoiner paramBuffer = new StringJoiner(XFEKeyWords.CODE_PARAM_SEPARATOR, XFEKeyWords.CODE_PARAM_JOIN_SYMBOL, XFEKeyWords.CODE_PARAM_END_SYMBOL);
                Fun contentFun = (Fun) linkedVar;
                XFECodeLoader.ContentLinked<Code> nowParam = contentFun.getParamRoot();
                while (null != (nowParam = (XFECodeLoader.ContentLinked<Code>) nowParam.getNext())) {
                    XFECodeLoader.ContentLinked<Var> nowParamCodeFirstVar = (XFECodeLoader.ContentLinked<Var>) nowParam.content().getCodeRoot().getNext();
                    paramBuffer.add(formatCodeFromRoot(nowParamCodeFirstVar, interrupt));
                }
                string.append(paramBuffer);
            } else if (linkedVar instanceof Block) {
                string.append(XFEKeyWords.CODE_BLOCK_JOIN_SYMBOL);
                string.append(formatCodeFromRoot(((Block)linkedVar).getCode().getCodeRoot(), false));
                string.append(XFEKeyWords.CODE_BLOCK_END_SYMBOL);
            } else if (linkedVar instanceof NEXT_CODE) {
				if (interrupt) {
                    break;
                }
            } else {
                if (null != linkedVar) {
                    string.append(linkedVar.name);
                }
            }
            now = (XFECodeLoader.ContentLinked<Var>) now.getNext();
        }
        return string.toString();
    }




	//hide
	//*****
	public String formatCode() {
		return XFEMethodCode.formatCodeFromRoot(this.rootCode, false).toString();
	}

    public static String formatCode(XFEMethod method) throws RuntimeException {
        XFEMethodCode[] mc = method.getCodes();
        return formatCode0(mc);
	}
	private static String formatCode0(XFEMethodCode[] mc) throws RuntimeException {
        StringBuilder code = new StringBuilder();
	    for (int i = 0;i < mc.length;i++) {
			code.append(mc[i].formatCode()).append(XFEKeyWords.CODE_LINE_SEPARATOR_CHAR);
        }
        return code.toString();
	}





    //方便开发
    protected static String strAddPrefixAndSuffix(String str) {
        //return "`" + str + "`";
        return str;
    }




}
