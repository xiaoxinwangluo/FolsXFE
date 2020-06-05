package top.fols.box.util.xfe;

/*
 *ProjectName: XFEExecuter
 *Time: 2019.07.04
 *Author: Author
 *ProjectAddres: https://github.com/xiaoxinwangluo/FolsXFE
 
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import top.fols.box.statics.XStaticFixedValue;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.lang.XFEClassInstance;
import top.fols.box.util.xfe.lang.XFEClassLoader;
import top.fols.box.util.xfe.lang.autoloader.XFEAutoCodeLoaderManager;
import top.fols.box.util.xfe.lang.autoloader.XFEDirCodeLoader;
import top.fols.box.util.xfe.lang.autoloader.XFEZipCodeLoader;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;

public class XFEMain {

	/**
	 * 
	 * java -cp ..\libs\top.fols.box.jar; XFEMain -d
	 * "C:\Users\784920843\Desktop\folsTool\FolsXFE2\example" -c test -p 1
	 * 
	 * -d 动态文件夹代码加载器
	 * </p>
	 * -xar 动态zip压缩包代码加载器
	 * </p>
	 * -c 运行类
	 * </p>
	 * -p 参数
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws Throwable
	 */

	public static void main(String[] args) throws Throwable {
		final String dirKey = "d";
		final String clsNameKey = "c";
		final String xarKey = "xar";
		final String paramKey = "p";

		Map<String, List<String>> argsMap = new HashMap<>();
		argsMap.put(dirKey, null);
		argsMap.put(clsNameKey, null);
		argsMap.put(xarKey, null);
		argsMap.put(paramKey, null);

		if (args == null) {
			args = XStaticFixedValue.nullStringArray;
		}
		for (int i = 0; i < args.length; i++) {
			if (args[i].length() >= 2 && args[i].charAt(0) == '-' && args[i].charAt(1) != '-') {
				String key = args[i].substring(1, args[i].length());
				if (!argsMap.containsKey(key)) {
					throw new RuntimeException("unknown param: " + args[i].toString());
				}
				String value = (i + 1 >= args.length) ? null : args[i + 1];
				List<String> valueList = argsMap.get(key);
				if (valueList == null) {
					argsMap.put(key, valueList = new ArrayList<>());
				}
				valueList.add(value);
				i += 1;
			} else {
				throw new RuntimeException("unknown param: " + args[i].toString());
			}
		}
		List<String> values;

		// d 运行目录 可空
		values = argsMap.get(dirKey);
		if (values != null) {
			for (int i = 0; i < values.size(); i++) {
				String newPath = getAbsPath(values.get(i)).getAbsolutePath();
				values.set(i, newPath);
				if (!new File(newPath).exists()) {
					throw new RuntimeException("cannot found dir: " + newPath);
				}
			}
		}
		// System.out.println("-d = " + values);

		// xar 代码文件压缩包 可使用多个声明 可空
		values = argsMap.get(xarKey);
		if (values != null) {
			for (int i = 0; i < values.size(); i++) {
				String newPath = getAbsPath(values.get(i)).getAbsolutePath();
				values.set(i, newPath);
				if (!new File(newPath).exists()) {
					throw new RuntimeException("cannot found xar: " + newPath);
				}
			}
		}
		// System.out.println("-xar = " + values);

		// c 运行类名 不可空
		values = argsMap.get(clsNameKey);
		if (values == null || values.size() == 0) {
			throw new RuntimeException("need to specify the run class name");
		} else {
			if (values.size() > 1) {
				throw new RuntimeException("cannot specify multiple run class name");
			}
		}
		// System.out.println("-c = " + values);

		// p 参数
		values = argsMap.get(paramKey);
		// System.out.println("-p = " + values);

		XFEClassLoader clsLoader = XFEClassLoader.newInstance();
		XFEAutoCodeLoaderManager aclm = clsLoader.getAutoLoaderCodeManager();

		List<String> runDirList = argsMap.get(dirKey);
		String[] runDir = runDirList == null ? XStaticFixedValue.nullStringArray
				: runDirList.toArray(new String[runDirList.size()]);

		List<String> xarFileList = argsMap.get(xarKey);
		String[] xarFile = xarFileList == null ? XStaticFixedValue.nullStringArray
				: xarFileList.toArray(new String[xarFileList.size()]);

		String runCls = argsMap.get(clsNameKey).get(0);

		List<String> paramList = argsMap.get(paramKey);
		String[] param = paramList == null ? XStaticFixedValue.nullStringArray
				: paramList.toArray(new String[paramList.size()]);

		for (String filei : runDir) {
			XFEDirCodeLoader dcc = new XFEDirCodeLoader(filei);
			aclm.addLoader(dcc);
		}
		for (String filei : xarFile) {
			XFEZipCodeLoader dcc = new XFEZipCodeLoader(filei);
			aclm.addLoader(dcc);
		}

		XFEStack stack = XFEStack.newStack();
		XFEClass xfeclass = clsLoader.forName(runCls);
		XFEClassInstance xfeclassInstance = xfeclass.getStaticInstance(stack);
		Object result = xfeclassInstance.executeMethod(stack, XFEKeyWords.CODE_METHOD_MAIN_NAME, param);

		System.out.println();
		System.out.println("----stack:----");
		System.out.println("classloader=" + clsLoader.toString());
		System.out.println("return: " + result);
		System.out.println("exception=" + stack.isThrow());
		System.out.println();
		System.out.println(stack.stringall());
		System.out.println("----");
		System.out.println();
	}

	public static File getAbsPath(String path) throws IOException {
		return new File(path == null ? "." : path).getCanonicalFile();
	}

	public static File getRunDir() throws IOException {
		return new File(".").getCanonicalFile();
	}
}
