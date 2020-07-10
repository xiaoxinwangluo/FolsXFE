package top.fols.box.util.xfe.lang;

public final class XFEMethod {
	private String fileName;
	private String className;
	private String name;
	private String[] paramName;
	private XFEMethodCode[] codes;
	

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

	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public void setParamName(String[] paramName) {
		this.paramName = paramName;
	}
	public String[] getParamName() {
		return this.paramName;
	}

	public void setCode(XFEMethodCode[] code) {
		this.codes = code;
	}
	public XFEMethodCode[] getCodes() {
		return this.codes;
	}

	@Override
	public String toString() {
		// TODO: Implement this method
		return new StringBuilder("xfemethod").append(' ')
			.append(this.className).append('.').append(this.name)
			.toString();
	}

}
