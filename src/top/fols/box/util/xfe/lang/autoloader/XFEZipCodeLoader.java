package top.fols.box.util.xfe.lang.autoloader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import top.fols.box.io.os.XFile;
import top.fols.box.lang.XString;
import top.fols.box.statics.XStaticFixedValue;
import top.fols.box.util.XArray;
import top.fols.box.util.XCHashMap;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.lang.XFEClassLoader;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;
import top.fols.box.util.xfe.util.XFECodeContent;
import top.fols.box.util.xfe.util.XFEStackThrowMessageTool;

/*
 * 自动加载 
 * 编译代码优先
 * 其次加载未编译代码(UTF-8)
 */
public class XFEZipCodeLoader extends XFEAutoCodeLoaderAbstract {

	/**
	 * 类名对应的文件路径
	 *
	 * com.a => com/a
	 */
	private XCHashMap<String, String> classRelativeFilePath = new XCHashMap<>();
	private String getClassRelativeFilePath(String xfeclassname) {
		String path = classRelativeFilePath.get(xfeclassname);
		return path;
	}




	@Override
	public void reLoad() {
		// TODO: Implement this methodublic
		this.zipfile = null;
		return;
	}
	@Override
	public String[] listClassName() {
		// TODO: Implement this method
		String[] clsNames = this.getZipRootDirFileList();
		System.out.println(this.classRelativeFilePath);
		return clsNames;
	}




	private String[] getZipRootDirFileList() {
		try {
			List<String> list = new ArrayList<>();
			ZipFile zipfile = new ZipFile(getFile());
			Enumeration<ZipEntry> zes = (Enumeration<ZipEntry>) zipfile.entries();
			while (zes.hasMoreElements()) {
				ZipEntry ze = zes.nextElement();
				String path = ze.getName();
				String fromatpath = toSystemFileSeparator(path);

				String fileExtensionName = XFile.getExtensionName(fromatpath, File.separator,
						XFEKeyWords.CODE_FILE_EXTENSION_NAME_SEPARATOR);
				if (XFEKeyWords.CODE_FILE_EXTENSION_NAME.equals(fileExtensionName)) {

					String fileNameNoExtension = XFile.getNameNoExtension(fromatpath, File.separator,
							XFEKeyWords.CODE_FILE_EXTENSION_NAME_SEPARATOR);

					String xfeclassname = XFEClass.Tool.relativeFilePathToClassName(fromatpath);
					String filepath = path;

					list.add(xfeclassname);
					classRelativeFilePath.put(xfeclassname, filepath);
				}
			}
			return list.toArray(new String[list.size()]);
		} catch (Throwable e) {
			// e.printStackTrace();
			return XStaticFixedValue.nullStringArray;
		}
	}



	public static final char 		UNIX_FILE_SEPARATOR = '/';
	public static final String 		UNIX_FILE_SEPARATOR_STRING = String.valueOf(UNIX_FILE_SEPARATOR);

	public static final char 		WINDOWS_FILE_SEPARATOR = '\\';
	public static final String 		WINDOWS_FILE_SEPARATOR_STRING = String.valueOf(WINDOWS_FILE_SEPARATOR);

	public static String toSystemFileSeparator(String path){
		path = XString.replace(path, UNIX_FILE_SEPARATOR_STRING, File.separator);
		path = XString.replace(path, WINDOWS_FILE_SEPARATOR_STRING, File.separator);
		return path.toString();
	}







	private File file;
	private ZipFile zipfile;
	@Override
	public synchronized XFEClass loadCode(XFEClassLoader clsLoader, String xfeclassname) throws IOException, OutOfMemoryError, RuntimeException  {
		// // TODO: Implement this method
		XFECodeContent content = this.getCode(xfeclassname);
		if (null != content) {
			XFEClass xfeclass = clsLoader.loadCode(content);
			content.releaseBuffer();
			return xfeclass;
		}
		return null;
	}
	
	@Override
	public XFECodeContent getCode(String xfeclassname) throws IOException {
		String noCompilerFilePath = getClassRelativeFilePath(xfeclassname);
		if (null == noCompilerFilePath) {
			return null;
		}
		if (this.exists(noCompilerFilePath)) {
			XFECodeContent content = XFECodeContent.wrapZipFile(this.file, this.getZipFile(), noCompilerFilePath,
					xfeclassname, XFEKeyWords.CODE_DEFAULT_CHARSET_UTF_8);
			return content;
		}
		throw new RuntimeException(
				XFEStackThrowMessageTool.autoCodeLoaderNotFoundXfeClass(xfeclassname, this.getFile().getPath()));
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
