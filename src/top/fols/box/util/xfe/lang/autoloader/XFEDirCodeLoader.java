package top.fols.box.util.xfe.lang.autoloader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import top.fols.box.io.os.XFile;
import top.fols.box.util.XCHashMap;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.lang.XFEClassLoader;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;
import top.fols.box.util.xfe.util.XFECodeContent;
import top.fols.box.util.xfe.util.XFEStackThrowMessageTool;

/*
 * 自动加载 
 * 编译代码优先
 * 其次加载未编译代码
 */
public class XFEDirCodeLoader extends XFEAutoCodeLoaderAbstract {

	/**
	 * 类名对应的文件路径
	 *
	 * com.a => com/a
	 */
	private static final XCHashMap<String, String> classRelativeFilePath = new XCHashMap<>();
	private static String getClassRelativeFilePath(String xfeclassname) {
		String path = classRelativeFilePath.get(xfeclassname);
		return path;
	}



	List<String> listClassName(File filePath) {
		List<String> list;
		list = new ArrayList<>();
		list = listClassName(list, new StringBuilder(), new StringBuilder(), filePath);
		return list;
	}
	List<String> listClassName(List<String> list, StringBuilder basePackage, StringBuilder baseDir, File filePath) {
		File[] files = filePath.listFiles();
		if (null != files) {
			for (File file : files) {
				if (null == file) {
					continue;
				}
				String filename = file.getName();
				if (file.isDirectory()) {
					listClassName(list,
							new StringBuilder(basePackage).append(filename).append(XFEKeyWords.PACKAGE_PATH_SEPARATOR),
							new StringBuilder(baseDir).append(filename).append(File.separator), file);
				} else {
					String fileExtensionName = XFile.getExtensionName(filename, File.separator,
							XFEKeyWords.CODE_FILE_EXTENSION_NAME_SEPARATOR);
					if (XFEKeyWords.CODE_FILE_EXTENSION_NAME.equals(fileExtensionName)) {

						String fileNameNoExtension = XFile.getNameNoExtension(filename, File.separator,
								XFEKeyWords.CODE_FILE_EXTENSION_NAME_SEPARATOR);

						String xfeclassname = new StringBuilder(basePackage).append(fileNameNoExtension).toString();
						String filepath = new StringBuilder(baseDir).append(filename).toString();

						list.add(xfeclassname);
						classRelativeFilePath.put(xfeclassname, filepath);
					}
				}
			}
		}
		return list;
	}

	@Override
	public void reLoad() {
		// TODO: Implement this method
		return;
	}

	@Override
	public String[] listClassName() {
		// TODO: Implement this method
		List<String> relatives = listClassName(this.getFile());
		String[] clsNames = relatives.toArray(new String[relatives.size()]);
		return clsNames;
	}

	@Override
	public XFEClass loadCode(XFEClassLoader clsLoader, String xfeclassname)
			throws IOException, OutOfMemoryError, RuntimeException {
		// TODO: Implement this method
		XFECodeContent content = this.getCode(xfeclassname);
		if (null != content) {
			XFEClass xfeclass = clsLoader.loadCode(content);
			content.releaseBuffer();
			return xfeclass;
		}
		throw new RuntimeException(
				XFEStackThrowMessageTool.autoCodeLoaderNotFoundXfeClass(xfeclassname, this.getFile().getPath()));
	}

	@Override
	public XFECodeContent getCode(String xfeclassname) {
		File file;

		String noCompilerFilePath = getClassRelativeFilePath(xfeclassname);
		if (null == noCompilerFilePath) {
			return null;
		}
		file = new File(this.getFile(), noCompilerFilePath);
		if (file.exists()) {
			XFECodeContent content = XFECodeContent.wrapFile(file, xfeclassname,
					XFEKeyWords.CODE_DEFAULT_CHARSET_UTF_8);
			return content;
		}
		return null;

	}

	private void checkFile() {
		if (!XFEDirCodeLoader.isDirectory(this.getFile())) {
			throw new RuntimeException("this is not is a dir: " + this.getFile());
		}
	}

	public static boolean isDirectory(File file) {
		return file == null ? false : file.isDirectory();
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
