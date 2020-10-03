package top.fols.box.util.xfe.executer;

import java.util.ArrayList;
import java.util.List;

import top.fols.box.lang.XClass;
import top.fols.box.util.XDoubleLinked;
import top.fols.box.util.XStringJoiner;
import top.fols.box.util.xfe.lang.XFEMethodCode;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;
import top.fols.box.util.xfe.util.XFEUtil;
import top.fols.box.util.xfe.util.interfacelist.XFEStackInterface;
import top.fols.box.util.xfe.util.XFEStackThrowMessageTool;

public class XFEStack implements XFEStackInterface {

	public static class StackElement {
		private String fileName;
		private String className;
		private String methodName;
		private int line;

		public StackElement(String fileName, String className, String methodName, int line) {
			this.fileName = fileName;
			this.className = className;
			this.methodName = methodName;
			this.line = line;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getFileName() {
			return fileName;
		}

		public void setClassName(String className) {
			this.className = className;
		}
		public String getClassName() {
			return className;
		}

		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}
		public String getMethodName() {
			return methodName;
		}

		public void setLine(int line) {
			this.line = line;
		}
		public int getLine() {
			return line;
		}

		@Override
		public StackElement clone() {
			// TODO: Implement this method
			return new StackElement(this.fileName, this.className, this.methodName, this.line);
		}

		@Override
		public String toString() {
			// TODO: Implement this method
			return XFEMethodCode.lineAddresString(this).toString();
		}

	}



	


	private XDoubleLinked<StackElement> stacks = new XDoubleLinked<>(null);

	private XFEStack(StackElement threadStartStackElement) {
		if (null != threadStartStackElement) {
			this.stacks.addNext(new XDoubleLinked<StackElement>(threadStartStackElement.clone()));
		}
	}

	private XFEStack(XFEStack beConleStack) {
		XDoubleLinked<StackElement> now = beConleStack.stacks;
		XDoubleLinked<StackElement> top = this.stacks;
		while (null != (now = (XDoubleLinked<XFEStack.StackElement>) now.getNext())) {
			StackElement current = now.content();
			if (null != current) {
				top.addNext(top = new XDoubleLinked<StackElement>(current.clone()));
			}
		}
	}

	public static XFEStack newStack() {
		return new XFEStack((StackElement) null);
	}

	public static XFEStack newXFEThreadStack(StackElement threadStartStackElement) {
		return new XFEStack(threadStartStackElement);
	}

	public static XFEStack cloneStack(XFEStack originStack) {
		return new XFEStack(originStack);
	}

	private int maxsize = 8 * 1024 * 1024;// 8M
	private int nowsize = 0;

	private boolean isThrow = false;
	private String throwReason = null;
	private Throwable throwJavaThrowableInstance = null;

	public void setThrow(String reason) {
		this.isThrow = true;
		this.setThrowReason(reason);
		this.setThrowJavaThrowableInstance(null);
	}

	public void setJavaThrow(String reason, Throwable javaThrowableInstance) {
		this.isThrow = true;
		this.setThrowReason(reason);
		this.setThrowJavaThrowableInstance(javaThrowableInstance);
	}

	public void setThrowReason(String reason) {
		this.throwReason = reason;
	}
	public String getThrowReason() {
		return this.throwReason;
	}

	public void setThrowJavaThrowableInstance(Throwable throwable) {
		this.throwJavaThrowableInstance = throwable;
	}
	public Throwable getThrowJavaThrowableInstance() {
		return this.throwJavaThrowableInstance;
	}

	public boolean isThrow() {
		return this.isThrow;
	}
	public boolean isNoThrow() {
		return !this.isThrow;
	}

	public XFEStack clearThrow() {
		this.isThrow = false;
		this.throwReason = null;
		this.throwJavaThrowableInstance = null;
		return this;
	}

	protected XDoubleLinked<StackElement> addStackElement(StackElement current) {
		XDoubleLinked<StackElement> c = new XDoubleLinked<StackElement>(current);
		this.stacks.addNext(c);
		this.nowsize++;
		if (this.nowsize >= this.maxsize) {
			this.setThrow("stack overflow, max=" + this.maxsize);
		}
		return c;
	}

	protected void removeStackElement(XDoubleLinked<StackElement> current) {
		current.remove();
		this.nowsize--;
	}

	@Override
	public StackElement[] stacks() {
		List<StackElement> cs = new ArrayList<>();
		XDoubleLinked<StackElement> bottom = this.stacks;
		XDoubleLinked<StackElement> now = bottom;
		while (null != (now = (XDoubleLinked<XFEStack.StackElement>) now.getNext())) {
			StackElement current = now.content();
			if (null != current) {
				cs.add(current.clone());
			}
		}
		return cs.toArray(new StackElement[cs.size()]);
	}

	@Override
	public StackElement now() {
		XDoubleLinked<StackElement> bottom = this.stacks;
		XDoubleLinked<StackElement> now = (XDoubleLinked<XFEStack.StackElement>) bottom.getNext();
		StackElement n = null == now ? null : now.content();
		return null == n ? null : n.clone();
	}

	@Override
	public XFEStack clone() {
		// TODO: Implement this method
		return new XFEStack(this);
	}

	@Override
	public String string() {
		XDoubleLinked<StackElement> bottom = this.stacks;
		XDoubleLinked<StackElement> now = bottom;
		XStringJoiner sb = new XStringJoiner(String.valueOf(XFEKeyWords.CODE_LINE_SEPARATOR_CHAR));
		while (null != (now = (XDoubleLinked<XFEStack.StackElement>) now.getNext())) {
			StackElement current = now.content();
			if (null != current) {
				sb.add(new StringBuilder("at: ").append(XFEMethodCode.lineAddresString(current.fileName,
							current.className, current.methodName, current.line)));
			}
		}
		return sb.toString();
	}

	@Override
	public String stringall() {
		if (!this.isThrow()) {
			return this.string();
		}

		StringBuilder sb = new StringBuilder();

		Throwable tT = this.getThrowJavaThrowableInstance();
		if (null != tT) {
			sb.append("java_throwable: ").append(XClass.toAbsCanonicalName(tT.getClass()))
				.append(XFEKeyWords.CODE_LINE_SEPARATOR_CHAR);
		}

		String tR = this.getThrowReason();
		if (true) {
			sb.append("reason: ").append(XFEUtil.trim(tR)).append(XFEKeyWords.CODE_LINE_SEPARATOR_CHAR);
		}

		sb.append(this.toString());
		return sb.toString();
	}

	@Override
	public String toString() {
		return this.string();
	}

	public static String getJavaStackTraceString(Throwable e) {
		return XFEStackThrowMessageTool.getJavaStackString(e);
	}
}
