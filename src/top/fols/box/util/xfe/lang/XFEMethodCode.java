package top.fols.box.util.xfe.lang;
import java.util.StringJoiner;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.lang.XFECodeLoader.Assignment;
import top.fols.box.util.xfe.lang.XFECodeLoader.Code;
import top.fols.box.util.xfe.lang.XFECodeLoader.Fun;
import top.fols.box.util.xfe.lang.XFECodeLoader.NEXT_CODE;
import top.fols.box.util.xfe.lang.XFECodeLoader.Point;
import top.fols.box.util.xfe.lang.XFECodeLoader.Var;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;

public final class XFEMethodCode {

	public int lineNumber;
    public XFECodeLoader.ContentLinked<Var> rootCode;//Code;
    public String codeBlocOptionName = null;//CodeBlockHeader(if or try or while)....
    public int crashIndex = -1;// if else Index
    public int gotoIndex = -1;//codeBlockHeader fail so ==> gotoIndex




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








    private static String formatCodeFromRoot(XFECodeLoader.ContentLinked<Var> firstVar, boolean interrupt) {
        StringBuilder sb = new StringBuilder();

        XFECodeLoader.ContentLinked<Var> now = firstVar;//root
        while (null != now) {
            Var content = now.content();
            if (content instanceof Assignment) {
                sb.append(content.name);
            } else if (content instanceof Point) {
                sb.append(content.name);
            } else if (content instanceof Fun) {
                sb.append(content.name);
                StringJoiner sj = new StringJoiner(XFEKeyWords.CODE_PARAM_SEPARATOR, XFEKeyWords.CODE_PARAM_JOIN_SYMBOL, XFEKeyWords.CODE_PARAM_END_SYMBOL);
                Fun contentFun = (Fun) content;
                XFECodeLoader.ContentLinked<Code> nowParam = contentFun.getParamRoot();
                while (null != (nowParam = nowParam.getNext())) {
                    XFECodeLoader.ContentLinked<Var> nowParamCodeFirstVar = nowParam.content().getCodeRoot().getNext();
                    sj.add(formatCodeFromRoot(nowParamCodeFirstVar, interrupt));
                }
                sb.append(sj);
            } else if (content instanceof NEXT_CODE) {
                if (interrupt) {
                    break;
                }
            } else {
                if (null != content) {
                    sb.append(content.name);
                }
            }
            now = now.getNext();
        }
        return sb.toString();
    }




	//hide
	//*****
	public String formatCode() {
		return XFEMethodCode.formatCodeFromRoot(this.rootCode, false).toString();
	}

    public static String formatCode(XFEMethod method) {
        StringBuilder code = new StringBuilder();
        XFEMethodCode[] mc = method.getCodes();
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
