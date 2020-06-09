package top.fols.box.util.xfe.util;
import java.io.InputStream;
import java.io.PrintStream;

public class XFEConsole {
	public static final XFEConsole DEFAULT_JAVA_SYSTEM_OUT = new XFEConsole()
	.setInputStream(System.in)
	.setOutStream(System.out);


	private InputStream in;
	private PrintStream out = null;
	public XFEConsole setInputStream(InputStream ps) {
		this.in = ps;
		return this;
	}
	public XFEConsole setOutStream(PrintStream ps) {
		this.out = ps;
		return this;
	}

	public InputStream getInputStream() {
		return this.in;
	}
	public PrintStream getOutputStream() {
		return this.out;
	}

	public void print(Object content) {
		this.out.print(XFEUtil.objectString(content));
	}
	public void println() {
		this.out.println();
	}
	public void println(Object content) {
		this.out.println(XFEUtil.objectString(content));
	}
}
