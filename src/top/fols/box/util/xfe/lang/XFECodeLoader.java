package top.fols.box.util.xfe.lang;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import top.fols.box.io.base.XCharArrayReader;
import top.fols.box.io.base.XCharArrayWriter;
import top.fols.box.statics.XStaticFixedValue;
import top.fols.box.util.XArrays;
import top.fols.box.util.XExceptionTool;
import top.fols.box.util.XObjects;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;
import top.fols.box.util.xfe.util.XFECodeContent;
import top.fols.box.util.xfe.util.XFEUtil;

public class XFECodeLoader {
    private static final char[] lineSeparatorChars = new char[]{XFEKeyWords.CODE_LINE_SEPARATOR_CHAR};
    private static final char[][] separate = new char[][]{
        " ".toCharArray(),
//        "\t".toCharArray(), 

        XFEKeyWords.CODE_PARAM_JOIN_SYMBOL.toCharArray(),            // (
        XFEKeyWords.CODE_PARAM_SEPARATOR.toCharArray(),              // ,
        XFEKeyWords.CODE_PARAM_END_SYMBOL.toCharArray(),             // )
        XFEKeyWords.CODE_OBJECT_POINT_SYMBOL.toCharArray(),          // .
        XFEKeyWords.CODE_VARIABLE_ASSIGNMENT_SYMBOL.toCharArray(),   // =
        XFEKeyWords.CODE_LINE_MULTI_CODE_SEPARATOR.toCharArray(),    // ;

		XFEKeyWords.CODE_BLOCK_JOIN_SYMBOL.toCharArray(),            //{
		XFEKeyWords.CODE_BLOCK_END_SYMBOL.toCharArray(),             //}

        lineSeparatorChars                                           // \n
    };





    public static boolean isVar(Var object) {
        return object.getClass() == VAR_CLASS;
    }
    public static boolean isNextCode(Var object) {
        return object instanceof NEXT_CODE;
    }
    public static boolean isAssignment(Var object) {
        return object instanceof Assignment;
    }
    public static boolean isFun(Var object) {
        return object instanceof Fun;
    }
    public static boolean isPoint(Var object) {
        return object instanceof Point;
    }
	public static boolean isBlock(Var object) {
        return object instanceof Block;
    }
    public static boolean isCode(Var object) {
        return object instanceof Code;
    }



    private static final Class VAR_CLASS = Var.class;
    public static class Var {
        String name;
        int line;

        public String getName() {
            return this.name;
        }
		Var setName(String name) {
			this.name = name;
			return this;
		}
        public int getLine() {
            return this.line;
        }
	    Var setLine(int line) {
			this.line = line;
			return this;
		}
    }
    private static final NEXT_CODE DEFAULT_NEXT_CODE = new NEXT_CODE();
    public static class NEXT_CODE extends Var {
        public NEXT_CODE() {
            super.name = "/";
            super.line = -1;
        }
    }
    private static final Assignment DEFAULT_ASSIGNMENT = new Assignment();
    public static class Assignment extends Var {
        public Assignment() {
            super.name = XFEKeyWords.CODE_VARIABLE_ASSIGNMENT_SYMBOL;
            super.line = -1;
        }
    }
    public static class Fun extends Var {
        //栈底，真正的元素从第二个开始
        private ContentLinked<Code> param = new ContentLinked<Code>(null);
        private ContentLinked<Code> paramTop = param;
        private int paramCount = 0;

        public int getParamCount() {
            return this.paramCount;
        }

        public ContentLinked<Code> getParamRoot() {
            return this.param;
        }
        void addParamToTop(ContentLinked<Code> newTop) {
            this.paramTop.addNext(newTop);
            this.paramTop = newTop;
            this.paramCount++;
        }
        void addParamToTop(Code newTop) {
            this.addParamToTop(new ContentLinked<Code>(newTop));
        }
    }
    private static final Point DEFAULT_POINT = new Point();
    public static class Point extends Var {
        public Point() {
            super.name = XFEKeyWords.CODE_OBJECT_POINT_SYMBOL;
            super.line = -1;
        }
    }
	public static class Block extends Var {
		//栈底，真正的元素从第二个开始
        private Code code;
		protected Code getCode() { return this.code; }
	}




    public static class Code extends Var {
        //栈底，真正的元素从第二个开始
        private ContentLinked<Var> codeRoot = new ContentLinked<Var>(null);
        private ContentLinked<Var> codeTop = codeRoot;

        public ContentLinked<Var> getCodeRoot() {
            return this.codeRoot;
        }

        void addCodeToTop(ContentLinked<Var> newTop) {
            this.codeTop.addNext(newTop);
            this.codeTop = newTop;
        }
        void addCodeToTop(Var newTop) {
            this.addCodeToTop(new ContentLinked<Var>(newTop));
        }
        ContentLinked<Var> getCodeTop() {
            return this.codeTop;
        }
        void removeCode(ContentLinked<Var> element) {
            if (this.codeRoot == element) {return;}
            if (this.codeTop == element) {
                ContentLinked<Var> last = (ContentLinked<Var>) element.getPrev();
                element.remove();
                this.codeTop = last;
            } else {
                element.remove();
            }
        }

        public Code setLine(int line) {
            super.line = line;
            return this;
        }

    }








    private static char[] nextNullableAndNoMatchKeyWord(CodeReader cr) {
        char[] next;
        while (!cr.isReadEnd()) {
            if (null == (next = cr.next())) {
                continue;
            }
            return next;
        }
        return null;
    }
	private void readBlock0(LineTracker lineTracker, Code c, CodeReader lineCodeReader) throws RuntimeException {
        final char[] lineSeparator = XFECodeLoader.lineSeparatorChars;

        String nextCode;
        char[] cCharArr;  
        while (null != (cCharArr = nextNullableAndNoMatchKeyWord(lineCodeReader))) {
            if (Arrays.equals(cCharArr, lineSeparator)) {
                lineTracker.nextLine();
                continue;
            }
            nextCode = new String(cCharArr);
            if ((nextCode = nextCode.trim()).length() == 0) {
                continue;
            }
            nextCode = XFEKeyWords.keyIntern(nextCode);

            if (nextCode.equals(XFEKeyWords.CODE_VARIABLE_ASSIGNMENT_SYMBOL)) {//=
                c.addCodeToTop(DEFAULT_ASSIGNMENT);
            } else if (nextCode.equals(XFEKeyWords.CODE_OBJECT_POINT_SYMBOL)) {//.
                c.addCodeToTop(DEFAULT_POINT);
            } else if (nextCode.equals(XFEKeyWords.CODE_PARAM_JOIN_SYMBOL)) {//(
                ContentLinked<Var> last = c.getCodeTop();
                Var lastContent = null == last.content() ?null: last.content();
				Fun f = new Fun();
			    if (isVar(lastContent)) {
					f.name = lastContent.name;
					c.removeCode(last);
                } else {
					if (null == lastContent || null == lastContent.name || isAssignment(lastContent)) {//a = (b);
						f.name = "";
					} else {
						throw new RuntimeException("Grammatical errors: " + "[" + lastContent.name + "]" + " " + XFEMethodCode.lineAddresString(this.getFileName(), this.getClassName(), null, lineTracker.getLine()));
					}
				}
                f.setLine(lineTracker.getLine());
//                System.out.println("@" + f.name);
                c.addCodeToTop(f);
                readFunParam0(lineTracker, f, null , lineCodeReader);//add param
            } else if (nextCode.equals(XFEKeyWords.CODE_PARAM_END_SYMBOL)) {//)
				throw new RuntimeException("Grammatical errors: " + "[" + nextCode + "]" + " " + XFEMethodCode.lineAddresString(this.getFileName(), this.getClassName(), null, lineTracker.getLine()));
            } else if (nextCode.equals(XFEKeyWords.CODE_BLOCK_JOIN_SYMBOL)) {//{
                Block b = new Block();
                b.line = lineTracker.getLine();
				Code code = (Code) new Code()
					.setName(null)
					.setLine(lineTracker.getLine());
				b.code = code;
//                System.out.println("@" + f.name);
                c.addCodeToTop(b);
                readBlock0(lineTracker, code , lineCodeReader);//add param
            } else if (nextCode.equals(XFEKeyWords.CODE_BLOCK_END_SYMBOL)) {//}
                break;
            } else if (nextCode.equals(XFEKeyWords.CODE_LINE_MULTI_CODE_SEPARATOR)) {//;
                c.addCodeToTop(DEFAULT_NEXT_CODE);
            } else if (nextCode.equals(XFEKeyWords.CODE_PARAM_SEPARATOR)) {//,
                throw new RuntimeException("Grammatical errors: " + "[" + nextCode + "]" + " " + XFEMethodCode.lineAddresString(this.getFileName(), this.getClassName(), null, lineTracker.getLine()));
            } else {
                Var v = new Var();
                v.name = nextCode;
                v.line = lineTracker.getLine();
                c.addCodeToTop(v);
            }
//          System.out.println(nextCode + "<" + cline.line + ">");
        }
    }
    private void readFunParam0(LineTracker lineTracker, Fun fun, Code c, CodeReader lineCodeReader) throws RuntimeException {
        final char[] lineSeparator = XFECodeLoader.lineSeparatorChars;

        String nextCode;
        char[] cCharArr;  
        while (null != (cCharArr = nextNullableAndNoMatchKeyWord(lineCodeReader))) {
            if (Arrays.equals(cCharArr, lineSeparator)) {
                lineTracker.nextLine();
                continue;
            }
            nextCode = new String(cCharArr);
            if ((nextCode = nextCode.trim()).length() == 0) {
				continue;
            }
            nextCode = XFEKeyWords.keyIntern(nextCode);

            if (nextCode.equals(XFEKeyWords.CODE_VARIABLE_ASSIGNMENT_SYMBOL)) {//=
                if (null == c) { fun.addParamToTop(c = new Code().setLine(lineTracker.getLine())); }
                c.addCodeToTop(DEFAULT_ASSIGNMENT);
            } else if (nextCode.equals(XFEKeyWords.CODE_OBJECT_POINT_SYMBOL)) {//.
                if (null == c) { fun.addParamToTop(c = new Code().setLine(lineTracker.getLine())); }
                c.addCodeToTop(DEFAULT_POINT);
            } else if (nextCode.equals(XFEKeyWords.CODE_PARAM_JOIN_SYMBOL)) {//(
                ContentLinked<Var> last = c.getCodeTop();
                Var lastContent = null == last.content() ?null: last.content();
				Fun f = new Fun();
			    if (isVar(lastContent)) {
					f.name = lastContent.name;
					c.removeCode(last);
                } else {
					if (null == lastContent || null == lastContent.name || isAssignment(lastContent)) {//a = (b);
						f.name = "";
					} else {
						throw new RuntimeException("Grammatical errors: " + "[" + lastContent.name + "]" + " " + XFEMethodCode.lineAddresString(this.getFileName(), this.getClassName(), null, lineTracker.getLine()));
					}
				}
                f.setLine(lineTracker.getLine());
//                System.out.println("@" + f.name);
                if (null == c) { fun.addParamToTop(c = new Code().setLine(lineTracker.getLine())); }
                c.addCodeToTop(f);
                readFunParam0(lineTracker, f, null , lineCodeReader);//add param
            } else if (nextCode.equals(XFEKeyWords.CODE_PARAM_END_SYMBOL)) {//)
                break;
            } else if (nextCode.equals(XFEKeyWords.CODE_BLOCK_JOIN_SYMBOL)) {//{
                Block b = new Block();
                b.line = lineTracker.getLine();
				Code code = (Code) new Code()
					.setName(null)
					.setLine(lineTracker.getLine());
				b.code = code;
//                System.out.println("@" + f.name);
				if (null == c) { fun.addParamToTop(c = new Code().setLine(lineTracker.getLine())); }
                c.addCodeToTop(b);
                readBlock0(lineTracker, code , lineCodeReader);//add param
            } else if (nextCode.equals(XFEKeyWords.CODE_BLOCK_END_SYMBOL)) {//}
				throw new RuntimeException("Grammatical errors: " + "[" + nextCode + "]" + " " + XFEMethodCode.lineAddresString(this.getFileName(), this.getClassName(), null, lineTracker.getLine()));
            } else if (nextCode.equals(XFEKeyWords.CODE_PARAM_SEPARATOR)) {//,
                c = null;
                continue;
            } else {
                if (null == c) { fun.addParamToTop(c = new Code().setLine(lineTracker.getLine())); }
                Var v = new Var();
                v.name = nextCode;
                v.line = lineTracker.getLine();
                c.addCodeToTop(v);
            }
//          System.out.println(nextCode + "<" + cline.line + ">");
        }
    }
    private Code readCode0(LineTracker lineTracker, CodeReader lineCodeReader) throws RuntimeException {
        final char[] lineSeparator = XFECodeLoader.lineSeparatorChars;
        final Code c = new Code();

        String nextCode;
        char[] cCharArr;  
        while (null != (cCharArr = nextNullableAndNoMatchKeyWord(lineCodeReader))) {
            if (Arrays.equals(cCharArr, lineSeparator)) {
                lineTracker.nextLine();
                continue;
            }
            nextCode = new String(cCharArr);
            if ((nextCode = nextCode.trim()).length() == 0) {
                continue;
            }
            nextCode = XFEKeyWords.keyIntern(nextCode);

            if (nextCode.equals(XFEKeyWords.CODE_VARIABLE_ASSIGNMENT_SYMBOL)) {//=
                c.addCodeToTop(DEFAULT_ASSIGNMENT);
            } else if (nextCode.equals(XFEKeyWords.CODE_OBJECT_POINT_SYMBOL)) {//.
                c.addCodeToTop(DEFAULT_POINT);
            } else if (nextCode.equals(XFEKeyWords.CODE_PARAM_JOIN_SYMBOL)) {//(
                ContentLinked<Var> last = c.getCodeTop();
                Var lastContent = null == last.content() ?null: last.content();
				Fun f = new Fun();
			    if (isVar(lastContent)) {
					f.name = lastContent.name;
					c.removeCode(last);
                } else {
					if (null == lastContent || null == lastContent.name || isAssignment(lastContent)) {//a = (b);
						f.name = "";
					} else {
						throw new RuntimeException("Grammatical errors: " + "[" + lastContent.name + "]" + " " + XFEMethodCode.lineAddresString(this.getFileName(), this.getClassName(), null, lineTracker.getLine()));
					}
				}
                f.setLine(lineTracker.getLine());
//                System.out.println("@" + f.name);
                c.addCodeToTop(f);
                readFunParam0(lineTracker, f, null , lineCodeReader);//add param
            } else if (nextCode.equals(XFEKeyWords.CODE_PARAM_END_SYMBOL)) {//)
				throw new RuntimeException("Grammatical errors: " + "[" + nextCode + "]" + " " + XFEMethodCode.lineAddresString(this.getFileName(), this.getClassName(), null, lineTracker.getLine()));
            } else if (nextCode.equals(XFEKeyWords.CODE_BLOCK_JOIN_SYMBOL)) {//{
                Block b = new Block();
                b.line = lineTracker.getLine();
				Code code = (Code) new Code()
					.setName(null)
					.setLine(lineTracker.getLine());
				b.code = code;
//                System.out.println("@" + f.name);
                c.addCodeToTop(b);
                readBlock0(lineTracker, code , lineCodeReader);//add param
            } else if (nextCode.equals(XFEKeyWords.CODE_BLOCK_END_SYMBOL)) {//}
				throw new RuntimeException("Grammatical errors: " + "[" + nextCode + "]" + " " + XFEMethodCode.lineAddresString(this.getFileName(), this.getClassName(), null, lineTracker.getLine()));
            } else if (nextCode.equals(XFEKeyWords.CODE_LINE_MULTI_CODE_SEPARATOR)) {//;
                c.addCodeToTop(DEFAULT_NEXT_CODE);
            } else if (nextCode.equals(XFEKeyWords.CODE_PARAM_SEPARATOR)) {//,
                throw new RuntimeException("Grammatical errors: " + "[" + nextCode + "]" + " " + XFEMethodCode.lineAddresString(this.getFileName(), this.getClassName(), null, lineTracker.getLine()));
            } else {
                Var v = new Var();
                v.name = nextCode;
                v.line = lineTracker.getLine();
                c.addCodeToTop(v);
            }
//          System.out.println(nextCode + "<" + cline.line + ">");
        }
        return c;
    }












    //local param 单纯一个参数名 非点语法
    public static String getLocal_FunParam_Name(ContentLinked<Code> nowParam) {
        return nowParam.content().getCodeRoot().getNext().content().getName();
    }
    //local param 单纯一个参数名 非点语法
    private static boolean isLocal_FunParam(ContentLinked<Code> nowParam) {
        ContentLinked<Var> root = nowParam.content().getCodeRoot();
        ContentLinked<Var> one = (ContentLinked<Var>) root.getNext();
        ContentLinked<Var> two = null == one ?null: (ContentLinked<Var>) one.getNext();
        return isVar(one.content()) && null == two;
    }
    //local param 单纯一个参数名 非点语法
    private String[] required_Local_FunParam(XFEClass xfeclass, XFEMethod xfemethod, int nowLine, Fun f) {
        String[] params = new String[f.getParamCount()];
        int i = 0;
        ContentLinked<Code> nowParam = f.getParamRoot();
        while (null != (nowParam = (ContentLinked<Code>) nowParam.getNext())) {
            if (!isLocal_FunParam(nowParam)) {
                throw new RuntimeException("param statement error: " + XFEMethodCode.lineAddresString(xfeclass.getFileName(), xfeclass.getName(), null == xfemethod ? null: xfemethod.getName(), nowLine));
            }
            params[i++] = this.getLocal_FunParam_Name(nowParam);
        }
        return params;
    }
    //*****
    //local param 单纯一个参数名 非点语法
    private void check_Local_FunParam_BlockOption(XFEClass xfeclass, XFEMethod xfemethod, int nowLine, Fun f) {
        if (f.getName() == XFEKeyWords.TRY) {
            this.required_Local_FunParam(xfeclass, xfemethod, nowLine, f);
        }
    }











    private XFECodeContent content;
    private String className;
    private String fileName;
	private int startLine;
    public XFECodeLoader setCode(XFECodeContent code) {
        return this.setCode(code, code.getClassName(), code.getFileName(), 0);
    }
	public XFECodeLoader setCode(XFECodeContent code, String className, String fileName, int startLine) {
        this.content = code;
        this.className = className;
        this.fileName = fileName;
		this.startLine = startLine;
        return this;
    }
    public String getClassName() {
        return this.className;
    }
    public String getFileName() {
        return this.fileName;
    }
	public int getStartLine() {
		return this.startLine;
	}
    public XFECodeLoader clearCode() {
        this.content = null;
        this.fileName = null;
        this.className = null;
		this.startLine = 0;
        return this;
    }


	//加载整个class代码
    public XFEClass loadToXFEClassLoader(XFEClassLoader loader) throws IOException, RuntimeException {
		XFEClass xfeclass;
        xfeclass = new XFEClass(loader);
        xfeclass.setName(this.className);
        xfeclass.setFileName(this.fileName);

		char[] content;
        content = this.content.getContent();
        content = this.dealStringVariable(xfeclass, content);
        content = this.dealCharVariable(xfeclass, content);
        content = this.dealNote(content);
		content = this.dealBaseVariable(xfeclass, content);

		XFEClass newxfeclass = this.loadCodeToXFEClass(xfeclass, content);
	    //添加到xfeclassloader
        loader.addClass(newxfeclass);
		return newxfeclass;
    }
	/*
	 * param@content dealFinalVariable
	 */
	private XFEClass loadCodeToXFEClass(XFEClass xfeclass, char[] content) throws RuntimeException {

//		System.out.println("处理代码: [" + new String(content) + "]");
//		System.out.println("--------");

		//行跟踪器
        LineTracker lineTracker = new LineTracker();
        //代码读取器
        CodeReader lineCodeReader = new CodeReader();
        lineCodeReader.setBuffer(content);
        lineCodeReader.setSeparator(separate);

        //一次性读取所有Code
        Code code = 
			this.readCode0(lineTracker, lineCodeReader);
        this.loadMethodsToXFEClass(xfeclass, code);

        content = null;
        code = null;
        lineCodeReader.releaseBuffer();
		//释放缓存
		xfeclass.releaseFinalVariableManagerCache0();

        return xfeclass;
	}

	private XFEClass loadBlockCastToXFEClass(XFEClassLoader loader, XFEClass superClass, Block block) {
		XFEClass xfeclass;
        xfeclass = new XFEClass(loader);
        xfeclass.setName(null);
        xfeclass.setFileName(this.fileName);
		xfeclass.setFinalVariableManager(superClass.getFinalVariableManager0());

		Code code = new Code();
		code.setName(block.getName());
		code.setLine(block.getLine());
		code.addCodeToTop(block);

        this.loadMethodsToXFEClass(xfeclass, code);
		return xfeclass;
	}

	private void loadMethodsToXFEClass(XFEClass xfeclass, Code code) {
		if (null == code) {
			return;
		}
		XFEMethod xfemethod = null;
		//是否进入方法
        boolean joinFun = false;
		//是否进入类
		boolean joinClass = false;
		//当前行
        int nowLine = 0;
		ContentLinkedReader nowCodeLinkedReader = new ContentLinkedReader(code.getCodeRoot());
        while (null != nowCodeLinkedReader.next()) {
            Var nowCodeVar = nowCodeLinkedReader.content();
            if (nowCodeVar.line != -1 && nowCodeVar.line != nowLine) {
                nowLine = nowCodeVar.line;
            }
			if (!joinClass) {
				if (isBlock(nowCodeVar)) {
					joinClass = true;

					nowCodeLinkedReader = new ContentLinkedReader(((Block)nowCodeVar).code.getCodeRoot());
					while (null != nowCodeLinkedReader.next()) {
						nowCodeVar = nowCodeLinkedReader.content();
						if (nowCodeVar.line != -1 && nowCodeVar.line != nowLine) {
							nowLine = nowCodeVar.line;
						}
						if (!joinFun) {
							if (isVar(nowCodeVar) && nowCodeVar.name == XFEKeyWords.FUN) {
								ContentLinked<Var> next = nowCodeLinkedReader.getNext();
								if (null == next || !isFun(next.content())) {
									throw new RuntimeException("grammatical errors: " + XFEMethodCode.lineAddresString(xfeclass.getFileName(), xfeclass.getName(), null, nowLine));
								} else {
									Fun funv = (XFECodeLoader.Fun) next.content();
									String funName = ((XFECodeLoader.Fun) next.content()).name;
									nowCodeLinkedReader.next();// to fun() after

									nowCodeLinkedReader.next(); // to block
									next = nowCodeLinkedReader.now();
									if (!isBlock(next.content())) {
										throw new RuntimeException("grammatical errors: " + XFEMethodCode.lineAddresString(xfeclass.getFileName(), xfeclass.getName(), null, nowLine));
									}
									Block codeblock = (XFECodeLoader.Block) next.content();
									joinFun = true;

									
									
									xfemethod = new XFEMethod();
									xfemethod.setClassName(className);
									xfemethod.setFileName(fileName);
									xfemethod.setName(funName);
									String[] paramList = this.required_Local_FunParam(xfeclass, xfemethod, nowLine, funv);
									xfemethod.setParamName(paramList);
//                                  System.out.println("+method: " + funName + XString.join(paramList, "(", ",", ")"));
									xfemethod.setCode(this.readXFEMethodCodes(xfeclass, codeblock));
									
									xfeclass.putMethod(xfemethod);
									
									
									

//									System.out.println("");
//									System.out.println("--------Method----------");
//									System.out.println("method: " + funName);
//									int dex = 0;
//									for (XFEMethodCode c: xfemethod.getCodes()) {
//										System.out.println("code: " + c.formatCode());
//										System.out.println("index: " + dex);
//										System.out.println();
//										dex++;
//									}
//									System.out.println("------------------");

									joinFun = false;
									continue;
								}
							} else {
								throw new RuntimeException("no join fun. " + XFEMethodCode.lineAddresString(xfeclass.getFileName(), xfeclass.getName(), null, nowLine));
							}
						}
					}
				} else {
					throw new RuntimeException("no join Class: " + XFEMethodCode.lineAddresString(xfeclass.getFileName(), xfeclass.getName(), null, nowLine));
				}
			} else {
				throw new RuntimeException("already joi nClass: " + XFEMethodCode.lineAddresString(xfeclass.getFileName(), xfeclass.getName(), null, nowLine));
			}
        }
	}




	private XFEMethodCode[] readXFEMethodCodes(XFEClass xfeclass,
											   Block codeblock) throws RuntimeException {
        //单个XFEMethodCode缓存
        ContentLinked<Var> bufCodeRoot = new ContentLinked<Var>(null);
        ContentLinked<Var> bufCodeTop = bufCodeRoot;
		List<XFEMethodCode> xfemethodcodes = new ArrayList<>(0);
		int nowLine = 0;

		ContentLinkedReader nowCodeLinkedReader = new ContentLinkedReader(codeblock.code.getCodeRoot());
		while (null != nowCodeLinkedReader.next()) {
			Var nowCodeVar = nowCodeLinkedReader.content();
			if (nowCodeVar.line != -1 && nowCodeVar.line != nowLine) {
				nowLine = nowCodeVar.line;
			}
			boolean directlyNewLine = false;//下一行是否新的代码块
			if (isVar(nowCodeVar)) {
				if (nowCodeVar.name ==        XFEKeyWords.FUN) {
					throw new RuntimeException("no exit fun. " + XFEMethodCode.lineAddresString(xfeclass.getFileName(), xfeclass.getName(), null, nowLine));
				}
			} else if (isBlock(nowCodeVar)) {
				//代码块转成变量
				Block block = (Block)nowCodeVar;
				XFEClass newXFEClass = this.loadBlockCastToXFEClass(xfeclass.getClassLoader(), xfeclass, block);
				String name = xfeclass.getFinalVariableManager0().putAnonymousClass(xfeclass, newXFEClass);

				ContentLinked<Var> element;
				element = new ContentLinked<Var>(new Var().setLine(nowLine).setName(XFEKeyWords.FINAL));
				bufCodeTop.addNext(element);
				bufCodeTop = element;

				element = new ContentLinked<Var>(DEFAULT_POINT);
				bufCodeTop.addNext(element);
				bufCodeTop = element;

				element = new ContentLinked<Var>(new Var().setLine(nowLine).setName(name));
				bufCodeTop.addNext(element);
				bufCodeTop = element;

				directlyNewLine = true;
			} else if (isFun(nowCodeVar)) {
				if (XFEKeyWords.isCodeBlockHeader(nowCodeVar.name)) {
					//将 if(){}else{} while(){} try(){}else{} 变成一个XFEMethodCode
					{
						String name;
						int line;

						name = nowCodeVar.getName();
						line = nowLine;

						//检查参数规范 必须为局部变量
						this.check_Local_FunParam_BlockOption(xfeclass, null, nowLine, (Fun)nowCodeVar);
						//***** if() {} while() {} try() {} 必须 单独一行 *****
						if (!bufCodeRoot.isOrphan()) {
							throw new RuntimeException("must be independent " + "[" + name + "]" + ": " + XFEMethodCode.lineAddresString(xfeclass.getFileName(), xfeclass.getName(), null, line));
						}
						ContentLinked<Var> element;
						element = new ContentLinked<Var>(nowCodeVar);
						bufCodeTop.addNext(element);
						bufCodeTop = element;

						//***** if() while() try() after not a block *****
						name = nowCodeVar.getName();
						line = nowLine;

						nowCodeLinkedReader.next();
						Var next = nowCodeLinkedReader.now().content();
						if (!isBlock(next)) {
							throw new RuntimeException("a code block is needed after this: " + "[" + name + "]" + " " + XFEMethodCode.lineAddresString(xfeclass.getFileName(), xfeclass.getName(), null, line));
						}
						element = new ContentLinked<Var>(next);
						bufCodeTop.addNext(element);
						bufCodeTop = element;
					}

					{
						ContentLinked<Var> nextLink = nowCodeLinkedReader.getNext();
						Var next = null == nextLink ?null: nextLink.content();
						String name;
						int line;
						if (null != next && XFEKeyWords.isCodeBlockElse(next.name)) {
							name = next.getName();
							line = next.getLine();

							nowCodeLinkedReader.next();
							ContentLinked<Var> element = new ContentLinked<Var>(next);
							bufCodeTop.addNext(element);
							bufCodeTop = element;

							nowCodeLinkedReader.next();
							next = nowCodeLinkedReader.now().content();
							if (!isBlock(next)) {
								throw new RuntimeException("a code block is needed after this: " + "[" + name + "]" + " " + XFEMethodCode.lineAddresString(xfeclass.getFileName(), xfeclass.getName(), null, line));
							}

							element = new ContentLinked<Var>(next);
							bufCodeTop.addNext(element);
							bufCodeTop = element;
						}
					}
					directlyNewLine = true;
				} 
			} else {
				if (isNextCode(nowCodeVar)) {
					//代码分割
					directlyNewLine = true;
				} else if (null == nowCodeLinkedReader.getNext()) {
					//代码末尾
					directlyNewLine = true;
				}
			}


//          System.out.println("========");
//          System.out.println(nowCodeLinkedReader.content());
//          System.out.println("格式化: " + nextLineIsNewLine + " " + "<" + nowLine + ">" + XFEMethodCode.formatCodeFromRoot(nowCodeLinkedReader.now(), true));
//          System.out.println("========");
//              
			if (directlyNewLine) {
				if (bufCodeRoot != bufCodeTop) {
					XFEMethodCode methodCode = new XFEMethodCode();
					methodCode.lineNumber = nowLine;
					methodCode.rootCode = bufCodeRoot;
					xfemethodcodes.add(methodCode);

					bufCodeRoot = new ContentLinked<Var>(null);
					bufCodeTop = bufCodeRoot;
				}
			} else {
				ContentLinked<Var> element = new ContentLinked<Var>(nowCodeVar);
				bufCodeTop.addNext(element);
				bufCodeTop = element;
			}
		}
		//结束了 方法
		XFEMethodCode[] methodcodes = xfemethodcodes.toArray(new XFEMethodCode[xfemethodcodes.size()]);
		xfemethodcodes.clear();
		//更新方法内while if try处理指针
		this.dealBlackOptionGotoIndex(fileName, className, null, xfeclass, methodcodes);
		return methodcodes;
	}




    private void dealBlackOptionGotoIndex(String fileName, String className, String methodName, XFEClass xfeclass, XFEMethodCode[] codes) {
        for (int i = 0;i < codes.length;i++) {
            XFEMethodCode code = codes[i];

            ContentLinked<Var> firstVar = (ContentLinked<Var>) code.rootCode.getNext();
            //不存在元素或者只存在一个元素
            Var varContent = null != firstVar ?firstVar.content(): null;
			ContentLinked<Var> next;
			Var nextVar;

			String name;
			int line;

			//if()
            if (isFun(varContent) && XFEKeyWords.isCodeBlockHeader(varContent.name)) {
				//CodeBlockHeader Name
				code.codeBlocOptionName = varContent.name;

				name = varContent.getName();
				line = varContent.getLine();

				next = (ContentLinked<Var>) firstVar.getNext();
				if (null == next || null == (nextVar = next.content()) || !isBlock(nextVar)) {
					throw new RuntimeException("a code block is needed after this: " + "[" + name + "]" + " " + XFEMethodCode.lineAddresString(xfeclass.getFileName(), xfeclass.getName(), null, line));
				}
				//{}
				code.block = this.readXFEMethodCodes(xfeclass, (Block)nextVar);

				//else
				next = (ContentLinked<Var>) next.getNext();
				if (null != next && null != (nextVar = next.content()) && isVar(nextVar) && XFEKeyWords.isCodeBlockElse(nextVar.name)) {
					name = nextVar.getName();
					line = nextVar.getLine();

					next = (ContentLinked<Var>) next.getNext();
					if (null == next || null == (nextVar = next.content()) || !isBlock(nextVar)) {
						throw new RuntimeException("a code block is needed after this: " + "[" + name + "]" + " " + XFEMethodCode.lineAddresString(xfeclass.getFileName(), xfeclass.getName(), null, line));
					}
					//{}
					code.elseblock = this.readXFEMethodCodes(xfeclass, (Block)nextVar);
				}
			}
        }
    }










    private static char[] dealStringVariable(XFEClass xfeclass, char[] str) throws IOException {
        final char STRING_ANNOTATION_CHAR = '"';
        final char LINE_SEPARATOR_CHAR = XFEKeyWords.CODE_LINE_SEPARATOR_CHAR;

        XCharArrayWriter s = new XCharArrayWriter(0);
        XCharArrayWriter out = new XCharArrayWriter(0);

        int line = 1;

        int joinStringLine = 1;
        boolean joinString = false;
        int joinStringLineCount = 0;// 字符串中换行的数量 注意是换行 而不是\n

        int sz = str.length;
        XCharArrayWriter unicode = new XCharArrayWriter(4);
        boolean hadSlash = false;
        boolean inUnicode = false;
        for (int i = 0; i < sz; i++) {
            char ch = str[i];
            if (ch == LINE_SEPARATOR_CHAR) {
                line++;
                if (joinString) {
                    joinStringLineCount++;
                }
            }
            if (!joinString) {// "
                if (ch == STRING_ANNOTATION_CHAR) {
                    joinStringLine = line;
                    joinString = true;
                    joinStringLineCount = 0;
                    continue;
                }
                s.write(ch);
            } else {
                if (inUnicode) {
                    // if in unicode, then we're reading unicode
                    // values in somehow
                    unicode.append(ch);
                    if (unicode.size() == 4) {
                        // unicode now contains the four hex digits
                        // which represents our unicode character
                        try {
                            int value = Integer.parseInt(unicode.toString(), 16);
                            out.write((char) value);
                            unicode.setBuffSize(0);
                            inUnicode = false;
                            hadSlash = false;
                        } catch (NumberFormatException nfe) {
                            throw new IOException("Unable to parse unicode value: " + unicode, nfe);
                        }
                    }
                    continue;
                }
                if (hadSlash) {
                    // handle an escaped value
                    hadSlash = false;
                    switch (ch) {
                        case '\\':
                            out.write('\\');
                            break;
                        case '\'':
                            out.write('\'');
                            break;
                        case '\"':
                            out.write('"');
                            break;
                        case 'r':
                            out.write('\r');
                            break;
                        case 'f':
                            out.write('\f');
                            break;
                        case 't':
                            out.write('\t');
                            break;
                        case 'n':
                            out.write('\n');
                            break;
                        case 'b':
                            out.write('\b');
                            break;
                        case 'u': {
								// uh-oh, we're in unicode country....
								inUnicode = true;
								break;
							}
                        default:
                            out.write(ch);
                            break;
                    }
                    continue;
                } else if (ch == '\\') {
                    hadSlash = true;
                    continue;
                }
                if (ch == STRING_ANNOTATION_CHAR) {
                    // String element
                    String element = out.toString();
                    // System.out.println("@" + element + "@" + joinStringLineCount + "@");
                    // System.out.println("------");
                    String name = xfeclass.getFinalVariableManager0().putStringFinalValue(xfeclass,
						element);
                    String replStr = new StringBuilder(XFEKeyWords.FINAL).append(XFEKeyWords.CODE_OBJECT_POINT_SYMBOL)
						.append(name).toString();
                    s.append(replStr);
                    for (int i2 = 0; i2 < joinStringLineCount; i2++) {
                        s.append(LINE_SEPARATOR_CHAR);
                    }
                    out.releaseBuffer();
                    joinString = false;
                    continue;
                } else {
                    out.write(ch);
                }
            }
        }
        if (joinString) {
            throw new IOException("error string declaration. "
				+ XFEMethodCode.lineAddresString(xfeclass.getFileName(), xfeclass.getName(), null, joinStringLine));
        }
        char[] result = s.toCharArray();
        s.close();
        out.close();
        return result;
    }

    private static char[] dealCharVariable(XFEClass xfeclass, char[] str) throws IOException {
        final char STRING_ANNOTATION_CHAR = '\'';
        final char LINE_SEPARATOR_CHAR = XFEKeyWords.CODE_LINE_SEPARATOR_CHAR;

        XCharArrayWriter s = new XCharArrayWriter(0);
        XCharArrayWriter out = new XCharArrayWriter(0);

        int line = 1;

        int joinStringLine = 1;
        boolean joinString = false;
        int joinStringLineCount = 0;// 字符串中换行的数量 注意是换行 而不是\n

        int sz = str.length;
        XCharArrayWriter unicode = new XCharArrayWriter(4);
        boolean hadSlash = false;
        boolean inUnicode = false;
        for (int i = 0; i < sz; i++) {
            char ch = str[i];
            if (ch == LINE_SEPARATOR_CHAR) {
                line++;
                if (joinString) {
                    joinStringLineCount++;
                }
            }
            if (!joinString) {// "
                if (ch == STRING_ANNOTATION_CHAR) {
                    joinStringLine = line;
                    joinString = true;
                    joinStringLineCount = 0;
                    continue;
                }
                s.write(ch);
            } else {
                if (inUnicode) {
                    // if in unicode, then we're reading unicode
                    // values in somehow
                    unicode.append(ch);
                    if (unicode.size() == 4) {
                        // unicode now contains the four hex digits
                        // which represents our unicode character
                        try {
                            int value = Integer.parseInt(unicode.toString(), 16);
                            out.write((char) value);
                            unicode.setBuffSize(0);
                            inUnicode = false;
                            hadSlash = false;
                        } catch (NumberFormatException nfe) {
                            throw new IOException("Unable to parse unicode value: " + unicode, nfe);
                        }
                    }
                    continue;
                }
                if (hadSlash) {
                    // handle an escaped value
                    hadSlash = false;
                    switch (ch) {
                        case '\\':
                            out.write('\\');
                            break;
                        case '\'':
                            out.write('\'');
                            break;
                        case '\"':
                            out.write('"');
                            break;
                        case 'r':
                            out.write('\r');
                            break;
                        case 'f':
                            out.write('\f');
                            break;
                        case 't':
                            out.write('\t');
                            break;
                        case 'n':
                            out.write('\n');
                            break;
                        case 'b':
                            out.write('\b');
                            break;
                        case 'u': {
								// uh-oh, we're in unicode country....
								inUnicode = true;
								break;
							}
                        default:
                            out.write(ch);
                            break;
                    }
                    continue;
                } else if (ch == '\\') {
                    hadSlash = true;
                    continue;
                }
                if (ch == STRING_ANNOTATION_CHAR) {
                    // String element
                    String element = out.toString();
                    if (element.length() != 1) {
                        throw new IOException("error char declaration. " + XFEMethodCode
							.lineAddresString(xfeclass.getFileName(), xfeclass.getName(), null, joinStringLine));
                    }
                    char elementChar = element.charAt(0);
                    // System.out.println("@" + element + "@" + joinStringLineCount + "@");
                    // System.out.println("------");
                    String name = xfeclass.getFinalVariableManager0().putCharFinalValue(xfeclass,
						elementChar);
                    String replStr = new StringBuilder(XFEKeyWords.FINAL).append(XFEKeyWords.CODE_OBJECT_POINT_SYMBOL)
						.append(name).toString();
                    for (int i2 = 0; i2 < joinStringLineCount; i2++) {
                        s.append(LINE_SEPARATOR_CHAR);
                    }
                    s.append(replStr);
                    out.releaseBuffer();
                    joinString = false;
                    continue;
                } else {
                    out.write(ch);
                }
            }
        }
        if (joinString) {
            throw new IOException("error char declaration. "
				+ XFEMethodCode.lineAddresString(xfeclass.getFileName(), xfeclass.getName(), null, joinStringLine));
        }
        char[] result = s.toCharArray();
        s.close();
        out.close();
        return result;
    }
    private static char[] dealNote(char[] str) {
        final String annotation = XFEKeyWords.CODE_NOTE_LINE_START;
        final char annotationChar0 = annotation.charAt(0);

        final char LINE_SEPARATOR_CHAR = XFEKeyWords.CODE_LINE_SEPARATOR_CHAR;
        XCharArrayWriter s = new XCharArrayWriter(0);
        int sz = str.length;
        for (int i = 0; i < sz; i++) {
            char ch = str[i];
            if (ch == annotationChar0) {
				if (XArrays.CharSequenceUtil.equalsRange(str, i, annotation, 0, annotation.length())) {
					i = indexOfChar(str, LINE_SEPARATOR_CHAR, i) - 1;
					continue;
				}
            }
            s.append(ch);
        }
        char[] result = s.toCharArray();
        return result;
    }


    //*****
	//(lowercase|uppercase)*:(\n|\-|\+|lowercase|uppercase)*
    public static char[] dealBaseVariable(XFEClass cls, char[] codeChars) {
        char BASE_VARIABLE_TYPE_STATEMENT = XFEKeyWords.BASE_VARIABLE_TYPE_STATEMENT;

        XCharArrayWriter s = new XCharArrayWriter(0);
        int len = codeChars.length;
        for (int i = 0;i < len;i++) {
            char ch = codeChars[i];
            if (ch == BASE_VARIABLE_TYPE_STATEMENT) {
                //get type
                int type_start = 0;
                int type_end = i;
                //*****
                for (int i2 = type_end - 1;i2 >= 0;i2--) {
                    char ch2 = codeChars[i2];
                    if (!(Character.isLowerCase(ch2) || Character.isUpperCase(ch2))) {
                        type_start = i2 + 1;
                        break;
                    }
                }
                int type_backspace = type_end - type_start;
                s.setBuffSize(s.size() - type_backspace);

                String type = new String(codeChars, type_start, type_end - type_start);
                // System.out.println(i + "; typeStart=" + type_start + ", typeEnd=" + type_end + ", type=" + type);

                int value_start = i + 1;
                int value_end = codeChars.length;
                //*****
                for (int i2 = value_start;i2 < codeChars.length;i2++) {
                    char ch2 = codeChars[i2];
                    if (!(Character.isLowerCase(ch2) || Character.isUpperCase(ch2) ||
                        Character.isDigit(ch2) ||
                        ch2 == '.' ||
                        ch2 == '-' ||
                        ch2 == '+')) {
                        value_end = i2;
                        break;
                    }
                }
                String statementValue = new String(codeChars, value_start, value_end - value_start);
                // System.out.println(i + "; valueStart=" + value_start + ", valueEnd=" + value_end + ", value=" + statementValue);

                Object value = null;
                Throwable e = null;
                try {
                    if (statementValue.length() > 0) {
                        if (XFEKeyWords.BASE_VARIABLE_TYPE_INT.equals(type)) {
                            value = XFEUtil.parseInt(statementValue);
                        } else if (XFEKeyWords.BASE_VARIABLE_TYPE_DOUBLE.equals(type)) {
                            value = XFEUtil.parseDouble(statementValue);
                        } else if (XFEKeyWords.BASE_VARIABLE_TYPE_LONG.equals(type)) {
                            value = XFEUtil.parseLong(statementValue);
                        } else if (XFEKeyWords.BASE_VARIABLE_TYPE_BYTE.equals(type)) {
                            value = XFEUtil.parseByte(statementValue);
                        } else if (XFEKeyWords.BASE_VARIABLE_TYPE_FLOAT.equals(type)) {
                            value = XFEUtil.parseFloat(statementValue);
                        } else if (XFEKeyWords.BASE_VARIABLE_TYPE_SHORT.equals(type)) {
                            value = XFEUtil.parseShort(statementValue);
                        }
                    }
                } catch (Throwable e2) {
                    e = e2;
                }

                if (null == value) {
                    throw new RuntimeException(String.format("cannot deal type=%s, value=%s, index=%s, code={%s}, reason={%s}",
                            type, 
                            value,
                            i, 
                            XFECodeLoader.nowLine(new String(codeChars), i, String.valueOf(XFEKeyWords.CODE_LINE_SEPARATOR_CHAR)), 
                            XExceptionTool.StackTraceToString(e)));
                }

                String name = cls.getFinalVariableManager0().putBaseTypeFinalValue(cls, value);
                String replStr = new StringBuilder(XFEKeyWords.FINAL).append(XFEKeyWords.CODE_OBJECT_POINT_SYMBOL).append(name).toString();

                // System.out.println("* " + name);
                // System.out.println("* " + value);

                s.append(replStr);

                i = value_end - 1;
            } else {
                s.append(ch);
            }
        }
        return s.toCharArray();
    }










    /* tool */
    private static String nowLine(String code, int index, String lineSeparator) {
        int lastLine = code.lastIndexOf(lineSeparator, index);
        int nextLine = code.indexOf(lineSeparator, index);
        if (lastLine == -1) {lastLine = 0;} else { lastLine += lineSeparator.length();}
        if (nextLine == -1) {nextLine = code.length();}
        return code.substring(lastLine, nextLine);
    }
    private static int indexOfChar(char[] code, char search, int off) {
        int count = code.length - off;
        char ch;
        for (int i = 0;i < count;i++) {
            ch = code[off];
            if (ch == search) {
                return off;
            }
            off++;
        }
        return off;
    }

    public static class CodeReader implements Closeable {
        private XCharArrayReader stream = new XCharArrayReader(XStaticFixedValue.nullcharArray);
        private char[][] separate;
        private int separateIndex = -1;
        private boolean isReadEnd = false;


        public CodeReader() {}
        public CodeReader setBuffer(char[] chars) {
            CodeReader.setBuffer(this.stream, chars);
            this.releaseBuffer();
            return this;
        }
        public CodeReader setSeparator(char[][] separates) {
            this.separate = separates;
            return this;
        }
        public CodeReader releaseBuffer() {
            this.separateIndex = -1;
            this.isReadEnd = false;
            return this;
        }
        public boolean isReadEnd() { return this.isReadEnd; }
        public char[] next() {
            this.isReadEnd = false;

            char[] result;
            if (this.separateIndex != -1) {
                char[] chs = this.separate[this.separateIndex];
                this.separateIndex = -1;
                result = chs;
            } else {
                char[] next = this.stream.readLine(this.separate, false);
                if (null != next) {
                    this.separateIndex = this.stream.readLineSeparatorsIndex();
                    result = next;
                } else {
                    this.isReadEnd = true;
                    result = null;
                }
            }
            return (null != result && result.length == 0) ?null: result;
        }
        @Override
        public void close() {
            // TODO: Implement this method
            this.stream.close();
        }
        public static void setBuffer(XCharArrayReader reader, char[] chars) {
            reader.setBuff(chars, chars.length);
            reader.seekIndex(0);
        }
	}


	public static class ContentLinked<T extends Object> extends top.fols.box.util.XDoubleLinked<T> implements Serializable {
        private static final long serialVersionUID = 1L;

        //Seriously not recommended
//        protected void setOrphan() {
//            super.prev = null;
//            super.next = null;
//        }


        public ContentLinked(T content) {
            super(content);
        }
    }


    //读了就会销毁上一个 不能回退
    private static class ContentLinkedReader {
        private ContentLinked<Var> now;
        private ContentLinked<Var> last;
        public ContentLinkedReader(ContentLinked<Var> now) {
            this.now = now;
        }
        public ContentLinked<Var> getNext() {
            return null == this.now ?null: (ContentLinked<Var>) this.now.getNext();
        }


        public ContentLinked<Var> now() {
            return now;
        }
        public ContentLinked<Var> next() {
            if (null != this.last) {
                this.last.remove();
                this.last = null;
            }
            this.last = this.now();

            this.now = null == this.now ?null: (ContentLinked<Var>) this.now.getNext();
            return this.now;
        }
        public Var content() {
            return this.now.content();
        }
    }

	/**
     * line of code tracker
     */
    private static class LineTracker {
        int line = 1;
        public void setLine(int line) {
            this.line = line;
        }
        public int getLine() {
            return line;
        }
        public LineTracker nextLine() {
            this.line++;
            return this;
        }
    }



}
