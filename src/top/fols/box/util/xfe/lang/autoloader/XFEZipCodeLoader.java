package top.fols.box.util.xfe.lang.autoloader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import top.fols.box.statics.XStaticFixedValue;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.lang.XFEClassLoader;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;
import top.fols.box.util.xfe.util.XFECodeContent;

/*
 * 自动加载 
 * 编译代码优先
 * 其次加载未编译代码(UTF-8)
 */
public class XFEZipCodeLoader extends XFEAutoCodeLoaderAbstract {
	
	@Override
	public void reLoad() {
		// TODO: Implement this methodublic
		this.zipfile = null;
		return;
	}
	@Override
	public String[] listClsName() {
		// TODO: Implement this method
		String[] fileNames = this.getZipRootDirFileList();
		String[] newFileList = matchExtensionName(fileNames, XFEClass.getClassFileExtensionName());
		String[] clsNames = deleteExtensionName(newFileList);
		return clsNames;
	}




	private String[] getZipRootDirFileList() {
		try {
			List<String> names = new ArrayList<>();
			ZipFile zipfile = new ZipFile(getFile());
			Enumeration<ZipEntry> zes = (Enumeration<ZipEntry>) zipfile.entries();
			while (zes.hasMoreElements()) {
				ZipEntry ze = zes.nextElement();
				String name = ze.getName();
				if (!XFEZipCodeLoader.ifPathExistDirSeparator(name)) {
					names.add(ze.getName());
				}
			}
			return names.toArray(new String[names.size()]);
		} catch (Throwable e) {
//			e.printStackTrace();
			return XStaticFixedValue.nullStringArray;
		}
	}
	
	
	
	public static final char 		UNIX_FILE_SEPARATOR = '/';
	public static final String 		UNIX_FILE_SEPARATOR_STRING = String.valueOf(UNIX_FILE_SEPARATOR);

	public static final char 		WINDOWS_FILE_SEPARATOR = '\\';
	public static final String 		WINDOWS_FILE_SEPARATOR_STRING = String.valueOf(WINDOWS_FILE_SEPARATOR);

	//is path exist dir separator
	public static boolean ifPathExistDirSeparator(String path) {
		if (null == path) {
			return false;
		}
		int count = null == path ?0: path.length();
		for (int i = 0;i < count;i++) {
			char ch = path.charAt(i);
			if (ch == UNIX_FILE_SEPARATOR || ch == WINDOWS_FILE_SEPARATOR) {
				return true;
			}
		}
		return false;
	}

	




	private File file;
	private ZipFile zipfile;
	@Override
	public synchronized XFEClass loadCode(XFEClassLoader clsLoader, String clsName0) throws IOException, OutOfMemoryError, RuntimeException  {
		// TODO: Implement this method
		String noCompilerFileName = XFEClass.getStandardFormatFileName(clsName0);
		if (this.exists(noCompilerFileName)) {
			XFECodeContent content = XFECodeContent.wrapZipFile(this.file, this.getZipFile(), noCompilerFileName, XFEKeyWords.CODE_DEFAULT_CHARSET_UTF_8);
			XFEClass xfeclass = clsLoader.loadCode(content);
			content.releaseBuffer();
			return xfeclass;
		}
		return null;
	}
	
	
	private boolean exists(String name) {
		try {
			return null != XFECodeContent.getZipEntryInputStream(this.getZipFile(), this.getZipFile().getEntry(name));
		} catch (IOException e) {
			return false;
		}
	}
	private ZipFile getZipFile() throws IOException {
		return null == this.zipfile ?this.zipfile = new ZipFile(this.getFile()): this.zipfile;
	}
	

	public static boolean isZipFile(File file) {
		try {
			ZipFile zip;
			zip = new ZipFile(file);
			zip.close();
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	public XFEZipCodeLoader(String file) {
		this(new File(file));
	}
	public XFEZipCodeLoader(File file) {
		super(file);
		this.file = file;
		this.checkFile();
	}
	private void checkFile() {
		if (!this.isZipFile(this.getFile())) {
			throw new RuntimeException("this is not is a zip file: " + getFile());
		}
	}
	
	
}
