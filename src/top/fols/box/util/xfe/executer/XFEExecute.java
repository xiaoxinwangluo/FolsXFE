package top.fols.box.util.xfe.executer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import top.fols.box.statics.XStaticBaseType;
import top.fols.box.util.XDoubleLinked;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethodManager;
import top.fols.box.util.xfe.executer.variablepoint.abstractlist.XFEAbstractVariablePoint;
import top.fols.box.util.xfe.executer.variablepoint.abstractlist.XFEAbstractVariablePointTool;
import top.fols.box.util.xfe.lang.XFEClassInstance;
import top.fols.box.util.xfe.lang.XFEClassLoader;
import top.fols.box.util.xfe.lang.XFECodeLoader;
import top.fols.box.util.xfe.lang.XFECodeLoader.Var;
import top.fols.box.util.xfe.lang.XFEMethod;
import top.fols.box.util.xfe.lang.XFEMethodCode;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;
import top.fols.box.util.xfe.util.XFEArrayOption;
import top.fols.box.util.xfe.util.XFEJavaReflectManager;
import top.fols.box.util.xfe.util.XFEStackThrowMessageTool;
import top.fols.box.util.xfe.util.XFEUtil;

import static top.fols.box.util.xfe.lang.XFECodeLoader.*;

public class XFEExecute {
    private XFEStack stack;

	private XFEClassInstance clsInstance;
	private XFEMethod method;
	private Map<String, Object> variable;

	private XDoubleLinked.VarLinked<XFEStack.StackElement> current;

	private XFEBaseMethodManager baseMethodManager;
	private XFEJavaReflectManager javaReflectMatcher;

	public XFEStack getStack() {
		return this.stack;
	}

    /**
     * exit current
     */
	private static void removeCurrentTry(XFEStack stack, XFEExecute execute) {
		if (!stack.isThrow()) {
			stack.removeStackElement(execute.current);
			execute.current = null;
		}
	}
	private XFEExecute(XFEClassInstance xfeclass, XFEMethod xfemethod, XFEStack stack) {
		this(xfeclass, xfemethod, stack, null);
	}
	private XFEExecute(XFEClassInstance xfeclass, XFEMethod xfemethod, XFEStack stack,
                       XFEExecute inherit) {
		this.stack = (null == stack ? (stack = XFEStack.newXFEThreadStack(null)) : stack);
        if (this.stack.isThrow()) {
            return;
		}
        this.clsInstance = xfeclass;
        this.method = xfemethod;

        this.variable = (null == inherit ?new HashMap<>(): inherit.variable);


        XFEStack.StackElement currentMessage = new XFEStack.StackElement(xfeclass.getFileName(), xfeclass.getName(), xfemethod.getName(), -1);
        XDoubleLinked.VarLinked<XFEStack.StackElement> current = this.stack.addStackElement(currentMessage);
		this.current = current;

		XFEClassLoader xfeclassloader = xfeclass.getClassLoader();
		if (null == xfeclassloader) {
			this.stack.setThrow(XFEStackThrowMessageTool.noXFEClassLoader(xfeclass));
			return;
		}
        this.baseMethodManager = xfeclassloader.getBaseMethodManager();
        this.javaReflectMatcher = xfeclassloader.getJavaReflectManager();
		// System.out.println("{" + this.stack + "}");
	}

	public XFEJavaReflectManager getJavaReflectManager() {
		return this.javaReflectMatcher;
	}

	public XFEClassInstance getXFEClassInstance() {
		return this.clsInstance;
	}

	/**
	 * must setParam before executing execute
	 */
	public XFEExecute setParam(Object[] args) {
		return this.setParam0(args, 0, args.length);
	}
	private XFEExecute setParam0(Object[] args, int off, int len) {
        // setParam
		String[] pn = this.method.getParamName();
		int length = Math.min(pn.length, len);
		for (int i = 0; i < length; i++) {
            this.variable.put(pn[i], args[off++]);
		}
        // setBaseData
		this.initExecuterParam();
		return this;
	}
	private void initExecuterParam() {
		XFEKeyWords.initExecuterFinalVariable(this.stack, this.variable, this);
	}







	private Object getPointVariableValue0(XFEExecute.ExecuteStatus execStatus, Object object, String name) {
		// System.out.println(object + ":" + name);
		Object result = null;
		if (null == object) {
			// from object can found name
			this.stack.setThrow(XFEStackThrowMessageTool.notFoundObjectFieled(object, name));
			return null;
		}
		if (object instanceof XFEAbstractVariablePoint) {
			XFEAbstractVariablePoint clsInstance = (XFEAbstractVariablePoint) object;
			result = XFEAbstractVariablePointTool.getVariable(clsInstance, execStatus, this, name);
		} else {
			try {
				Object javaInstance = object;
				Class cls = javaInstance.getClass();
				Field field = this.javaReflectMatcher.getField(cls, name);
				result = field.get(javaInstance);
			} catch (Throwable e) {
				this.stack.setJavaThrow(XFEStackThrowMessageTool.getJavaStackString(e), e);
				return null;
			}
		}
		return result;
	}
	public Object getVariableValue(String name) {
		return this.variable.get(name);
	}

	private Object setPointVariableValue0(XFEExecute.ExecuteStatus execStatus, Object instance, String name, Object value) {
		if (this.stack.isThrow()) {
            return null;
        }
        if (instance instanceof XFEAbstractVariablePoint) {
            XFEAbstractVariablePoint clsInstance = (XFEAbstractVariablePoint) instance;
            XFEAbstractVariablePointTool.setVariable(clsInstance, execStatus, this, name, value);
        } else {
            try {
                if (null == instance) {
                    stack.setThrow(XFEStackThrowMessageTool.notFoundObjectFieled(instance, name));
                    return null;
                }
                Class cls = instance.getClass();
                Field field = this.javaReflectMatcher.getField(cls, name);
                field.set(instance, value);
            } catch (Throwable e) {
                this.stack.setJavaThrow(XFEStackThrowMessageTool.getJavaStackString(e), e);
                return null;
            }
        }
        return value;
	}
	public Object setVariableValue(String name, Object value) {
		if (XFEKeyWords.isExecuterFinalVariable(name)) {
			this.stack.setThrow(XFEStackThrowMessageTool.cannotSetVariable(name));
			return null;
		}
		this.variable.put(name, value);
        return value;
	}




    private Object execObjectPointMethod(XFEExecute.ExecuteStatus execStatus, Object instance, String name, Object[] param) {
        if (stack.isThrow()) {
            return null;
        }
        if (instance == null) {
            stack.setThrow(XFEStackThrowMessageTool.notFoundObjectMethod(instance, name, param));
            return null;
        } if (instance instanceof XFEAbstractVariablePoint) {
            XFEAbstractVariablePoint vp = ((XFEAbstractVariablePoint) instance);
            Object value = XFEAbstractVariablePointTool.executeMethod(vp, execStatus, this, name, param);
            return value;
        } else {
            try {
                Class javaClass = instance.getClass();
                if (javaClass.isArray()) {
                    return XFEArrayOption.option(this.stack, instance, name, param);
                }
                Method method = this.javaReflectMatcher.getMethod(javaClass, name, param);
                return method.invoke(instance, param);
            } catch (Throwable e) {
                this.stack.setJavaThrow(XFEStackThrowMessageTool.getJavaStackString(e), e);
                return null;
            }
        }
    }
    private Object execBaseOrThisXFEClassMethod(XFEExecute.ExecuteStatus execStatus, String name, Object[] param) {
        if (stack.isThrow()) {
            return null;
        }
        XFEBaseMethod xfeBaseMethod = this.baseMethodManager.get(name);
        if (null != xfeBaseMethod) {
            return xfeBaseMethod.execute(execStatus, this, param);
        } else {
            XFEClassInstance cls = this.clsInstance;
            XFEMethod method = cls.getMethod(name);
            if (null == method) {
                stack.setThrow(XFEStackThrowMessageTool.notFoundXfeClassBaseMethodOrMethod(this.clsInstance.getName(), name));
                return null;
            }
            XFEExecute execute = new XFEExecute(cls, method, this.stack).setParam(param);
            Object result = execute.execute();
            return result;
        }
	}






	// *****
	private void tryParamSet(ExecuteStatus execStatus, XFECodeLoader.ContentLinked<Var> rootVars, boolean isthrow) {
        XFECodeLoader.Fun fun = (XFECodeLoader.Fun) rootVars.getNext().content();
		int len = fun.getParamCount();
		if (len == 0) {
			return;
		} else if (len == 1) {
            XFECodeLoader.ContentLinked<Code> now = fun.getParamRoot();
            String local1 = XFECodeLoader.getLocal_FunParam_Name(now = now.getNext());
            this.setVariableValue(local1, this.stack.clone());
        } else if (len == 3) {
            XFEStack.StackElement stackElement = null;
            String reason = null;
            Throwable e = null;
            if (isthrow) {
                stackElement = this.stack.now();
                reason = this.stack.getThrowReason();
                e = this.stack.getThrowJavaThrowableInstance();
            }
            XFECodeLoader.ContentLinked<Code> now = fun.getParamRoot();
			String local1 = XFECodeLoader.getLocal_FunParam_Name((now = now.getNext()));
            String local2 = XFECodeLoader.getLocal_FunParam_Name((now = now.getNext()));
            String local3 = XFECodeLoader.getLocal_FunParam_Name((now = now.getNext()));
            this.setVariableValue(local1, stackElement);
            this.setVariableValue(local2, reason);
            this.setVariableValue(local3, e);
			return;
		} else {
			this.stack.setThrow(XFEStackThrowMessageTool.notFoundXfeClassBaseMethod(XFEKeyWords.TRY, len));
			return;
		}
	}
	private boolean ifParamResult(ExecuteStatus execStatus, XFECodeLoader.ContentLinked<Var> rootVars) {
        XFECodeLoader.Fun fun = (XFECodeLoader.Fun) rootVars.getNext().content();
		int len = fun.getParamCount();
		if (len == 1) {
			Object obj = this.getParamValue(execStatus, fun.getParamRoot().getNext());
			return XFEUtil.equals(obj, XStaticBaseType.Boolean_TRUE);
		} else if (len == 2) {
            Object obj = this.getParamValue(execStatus, fun.getParamRoot().getNext());
			Object obj2 = this.getParamValue(execStatus, fun.getParamRoot().getNext().getNext());
			return XFEUtil.equals(obj, obj2);
		}
		this.stack.setThrow(XFEStackThrowMessageTool.notFoundXfeClassBaseMethod(XFEKeyWords.IF, fun.getParamCount()));
		return false;
	}



//	private VarLinkedReader paramVarLinkedReader = new VarLinkedReader();
    private Object[] getParamValues(ExecuteStatus execStatus, XFECodeLoader.Fun funv) {
        int paramIndex = 0;
        Object[] paramList = new Object[funv.getParamCount()];
        XFECodeLoader.ContentLinked<Code> nowParam = funv.getParamRoot();
        while (null != (nowParam = nowParam.getNext())) {
            paramList[paramIndex++] = this.executeVars0(execStatus, new VarLinkedReader().setRoot(nowParam.content().getCodeRoot()));
        }
        return paramList;
    }
//	private VarLinkedReader paramVarLinkedReader2 = new VarLinkedReader();
    private Object getParamValue(ExecuteStatus execStatus, XFECodeLoader.ContentLinked<Code> nowParam) {
        return this.executeVars0(execStatus, new VarLinkedReader().setRoot(nowParam.content().getCodeRoot()));
    }





    private static class VarLinkedReader {
        private XFECodeLoader.ContentLinked<Var> now;

        public VarLinkedReader() {
        }
        public XFECodeLoader.ContentLinked<Var> next() {
            XFECodeLoader.ContentLinked<Var> next = null == this.now ? null : this.now.getNext();
            this.now = next;
            return next;
        }
        public XFECodeLoader.ContentLinked<Var> getNext() {
            XFECodeLoader.ContentLinked<Var> next = null == this.now ? null : this.now.getNext();
            return next;
        }
        public VarLinkedReader setRoot(XFECodeLoader.ContentLinked<Var> now) {
            this.now = now;
            return this;
        }
        public static XFECodeLoader.ContentLinked<Var> next(XFECodeLoader.ContentLinked<Var> now) {
            return null == now ? null : now.getNext();
        }
    }

    private Object executeVars0(ExecuteStatus execStatus, VarLinkedReader rootVars) {
        Object result = null;
        boolean setResult = false;
        boolean joinPoint = false;
        Var nowVar;
        VarLinkedReader linkedReader = rootVars;
        XFECodeLoader.ContentLinked<Var> now = null;
        while (null != (now = linkedReader.next())) {
            nowVar = now.content();
            if (isPoint(nowVar)) {
                joinPoint = true;
                continue;
            }
            if (stack.isThrow()) {
                return null;
            }

            // System.out.println(System.currentTimeMillis() + " * " +
            // XFEMethodCode.formatCodeFromFirst(now, false) + "/" + result);
            if (isVar(nowVar)) {
                // 栈底为空值
                if (null == result) {
                    if (setResult) {
                        // 无法从空结果获取字段
                        stack.setThrow(XFEStackThrowMessageTool.notFoundObjectFieled(null, nowVar.getName()));
                        return null;
                    }
                    XFECodeLoader.ContentLinked<Var> next = VarLinkedReader.next(now);
                    if (null != next && isAssignment(next.content())) {
                        // x = ...
                        result = this.setVariableValue(nowVar.getName(),
							this.executeVars0(execStatus, linkedReader.setRoot(next)));
                        setResult = true;
                        return result;
                    } else {
                        // 以外的情况
                        result = this.getVariableValue(nowVar.getName());
                        setResult = true;
                    }
                } else if (joinPoint) {
                    // 上一个元素为点
                    XFECodeLoader.ContentLinked<Var> next = VarLinkedReader.next(now);
                    if (null == next) {
                        // 数据被读完 x.x
                        result = this.getPointVariableValue0(execStatus, result, nowVar.getName());
                        setResult = true;
                        return result;
                    } else {
                        if (isAssignment(next.content())) {
                            // x.x = ...
                            result = this.setPointVariableValue0(execStatus, result, nowVar.getName(),
								this.executeVars0(execStatus, linkedReader.setRoot(next)));
                            setResult = true;
                        } else if (isPoint(next.content())) {
                            // x.x.?
                            result = this.getPointVariableValue0(execStatus, result, nowVar.getName());
                            setResult = true;
                        } else {
                            stack.setThrow("grammatical errors");
                        }
                    }
                } else {
                    stack.setThrow("grammatical errors");
                }
            } else if (isFun(nowVar)) {
                // 栈底为空值
                if (null == result) {
                    if (setResult) {
                        // 无法从空结果获取方法
                        stack.setThrow(XFEStackThrowMessageTool.notFoundObjectMethod(null, nowVar.getName(), null));
                        return null;
                    }
                    // x();
                    XFECodeLoader.Fun fun = (XFECodeLoader.Fun) nowVar;
                    Object[] param = this.getParamValues(execStatus, fun);
                    result = this.execBaseOrThisXFEClassMethod(execStatus, nowVar.getName(), param);
                    setResult = true;
                } else if (joinPoint) {
                    // ?.x()
                    XFECodeLoader.Fun fun = (XFECodeLoader.Fun) nowVar;
                    Object[] param = this.getParamValues(execStatus, fun);
                    result = this.execObjectPointMethod(execStatus, result, nowVar.getName(), param);
                    setResult = true;
                } else {
                    stack.setThrow("grammatical errors");
                }
            } else {
                stack.setThrow("grammatical errors");
                return null;
            }
            joinPoint = false;
        }
        return result;
    }

    private VarLinkedReader varLinkedReader = new VarLinkedReader();
    private Object executeVars(ExecuteStatus execStatus, XFECodeLoader.ContentLinked<Var> rootVars) {
        this.varLinkedReader.setRoot(rootVars);
        Object result = this.executeVars0(execStatus, this.varLinkedReader);
        return result;
    }







	public static class ExecuteStatus {
        private boolean isReturn;
        private Object result;

        private static ExecuteStatus newInstance() {
            ExecuteStatus flag = new ExecuteStatus();
            flag.isReturn = false;
            return flag;
        }

        public void setReturn(boolean isReturn) {
            this.isReturn = isReturn;
        }

        public boolean isReturn() {
            return isReturn;
        }

        public void setResult(Object result) {
            this.result = result;
        }

        public Object getResult() {
            return this.result;
        }

        protected void clear() {
            this.isReturn = false;
            this.result = null;
        }
    }






	public static Object execute(XFEClassInstance xfeclassinstance, XFEStack stack, XFEMethod method, String methodName,
								 Object[] args, int off, int len) {
        return XFEExecute.execute(xfeclassinstance, stack, method, methodName, args, off, len, null);
    }
    public static Object execute(XFEClassInstance xfeclassinstance, XFEStack stack, XFEMethod method,
								 String methodName, Object[] args, int off, int len,
								 XFEExecute inherit) {
        if (stack.isThrow()) {
            return null;
        }
        if (null != method) {
            XFEExecute executer;
            executer = new XFEExecute(xfeclassinstance, method, stack, inherit);
            executer.setParam0(args, off, len);
            Object result = executer.execute();
            return result;
        }
        stack.setThrow(XFEStackThrowMessageTool.notFoundXfeClassMethod(xfeclassinstance.getName(), methodName));
        return null;
    }




	public Object execute() {
		if (this.stack.isThrow()) {
			return null;
		} else {
			XFEMethodCode[] codes = this.method.getCodes();
			XFEExecute.ExecuteStatus flag = ExecuteStatus.newInstance();
			this.executeProcess(flag, codes, 0, codes.length - 1);
			this.variable = null; // clear variable
			this.removeCurrentTry(this.stack, this); // remove nowStack
			Object result = flag.getResult();
			flag.clear();// clear result cache
			return result;
		}
	}

    private void executeProcess(ExecuteStatus status, XFEMethodCode[] codes, int startIndex, int endIndex) {
        while (true) {
            if (this.stack.isThrow()) {
                status.clear();
                return;
            }
            if (startIndex > endIndex) {
                return;
            }
            XFEMethodCode code = codes[startIndex];
            this.current.content().setLine(code.lineNumber);
            XFECodeLoader.ContentLinked<Var> rootVars = code.rootCode;
            String cbo = code.codeBlocOptionName;
            if (cbo == XFEKeyWords.IF) {
				while (true) {
					boolean ifResult = this.ifParamResult(status, rootVars);
					int absIndex, absTail; 
					if (code.crashIndex > -1) {//存在else
						if (ifResult) {
							absIndex = startIndex + 1;
							absTail = code.crashIndex - 1;
						} else {
							absIndex = code.crashIndex + 1;
							absTail = code.gotoIndex - 1;
						}
					} else {
						if (ifResult) {
							absIndex = startIndex + 1;
							absTail = code.gotoIndex - 1;
						} else {
							break;
						}
					}
					this.executeProcess(status, codes, absIndex, absTail);// CodeBlock header index
					break;
				}
                startIndex = code.gotoIndex + 1;// CodeBlock tail + 1
                continue;
            } else if (cbo == XFEKeyWords.WHILE) {
                while (true) {
                    this.current.content().setLine(code.lineNumber);
                    Object result = this.ifParamResult(status, rootVars);
                    if (!(result instanceof Boolean) || !((Boolean) result).booleanValue()) {
                        break;
                    }
                    this.executeProcess(status, codes, startIndex + 1, code.gotoIndex - 1);// CodeBlock header index
                    if (this.stack.isThrow()) {
                        return;
                    } else if (status.isReturn) {
                        if (status.result == XFEKeyWords.BREAK) {
                            status.clear();
                            break;
                        } else if (status.result == XFEKeyWords.CONTINUE) {
                            status.clear();
                            continue;
                        }
                        return;
                    }
                }
                startIndex = code.gotoIndex + 1;// CodeBlock tail + 1
                continue;
            } else if (cbo == XFEKeyWords.TRY) {
				int absIndex, absTail;
				if (code.crashIndex > -1) {
					absIndex = startIndex + 1;
					absTail = code.crashIndex - 1;
				} else {
					absIndex = startIndex + 1;
					absTail = code.gotoIndex - 1;
				}
				this.executeProcess(status, codes, absIndex, absTail);// CodeBlock header index +1,
				boolean isthrow = false;
				if (this.stack.isThrow()) {
					// run exception
					status.clear();
					this.stack.clearThrow();
					isthrow = true;
				} else if (status.isReturn) {
					// return
					return;
				}
				this.tryParamSet(status, rootVars, isthrow);// set try method param
				if (isthrow) {
					if (code.crashIndex > -1) {
						absIndex = code.crashIndex + 1;
						absTail = code.gotoIndex - 1;
						this.executeProcess(status, codes, absIndex, absTail);// CodeBlock header index +1,
					}
				}
                startIndex = code.gotoIndex + 1;// CodeBlock tail + 1
                continue;
            } else {
                // normal execute
                this.executeVars(status, rootVars);
            }

            // System.out.println();
            // System.out.println(XFEMethodCode.lineAddresString(code)
            // + "\n"
            // + "local: " + XString.join(this.cloneParam(), "{" , "=", "\n", "}")
            // + "\n"
            // + "this: " + XString.join(this.getXFEClassInstance().cloneParam(), "{" , "=",
            // "\n", "}")
            // );
            if (status.isReturn) {
                return;
            }
            startIndex++;// next line
        }
    }





}
