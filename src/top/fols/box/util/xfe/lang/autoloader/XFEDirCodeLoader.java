package top.fols.box.util.xfe.lang.autoloader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.lang.XFEClassLoader;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;
import top.fols.box.util.xfe.util.XFECodeContent;

/*
 * 自动加载 
 * 编译代码优先
 * 其次加载未编译代码
 */
public class XFEDirCodeLoader extends XFEAutoCodeLoaderAbstract {
	@Override
	public void reLoad() {
		// TODO: Implement this method
		return;
	}
	@Override
	public String[] listClsName() {
		// TODO: Implement this method
		String[] fileNames = this.getFile().list();
		String[] newFileList = matchExtensionName(fileNames, XFEClass.getClassFileExtensionName());
		String[] clsNames = deleteExtensionName(newFileList);
		return clsNames;
	}
	@Override
	public XFEClass loadCode(XFEClassLoader clsLoader, String clsName0) throws IOException, OutOfMemoryError, RuntimeException {
		// TODO: Implement this method
		String noCompilerFileName = XFEClass.getClassFileName(clsName0);
		if (this.exists(noCompilerFileName)) {
			XFECodeContent content = XFECodeContent.wrapFile(new File(this.getFile(), noCompilerFileName), XFEKeyWords.CODE_DEFAULT_CHARSET_UTF_8);
			XFEClass xfeclass = clsLoader.loadCode(content);
			content.releaseBuffer();
			return xfeclass;
		}
		return null;
	}



	private boolean exists(String name) {
		File file = new File(this.getFile(), name);
		return file.exists() && file.isFile();
	}
	private void checkFile() {
		if (!this.isDirectory(this.getFile())) {
			throw new RuntimeException("this is not is a dir: " + this.getFile());
		}
	}




	public static boolean isDirectory(File file) {
		return file == null ?false: file.isDirectory();
	}

	public XFEDirCodeLoader(String file) {
		super(file);
		this.checkFile();
	}
	public XFEDirCodeLoader(File file) {
		super(file);
		this.checkFile();
	}
}
