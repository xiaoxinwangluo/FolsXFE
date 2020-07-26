package top.fols.box.util.xfe.executer.basemethod.lang;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.lang.XFEClassLoader;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;
import top.fols.box.util.xfe.util.XFECodeContent;

public class LOADCODE extends XFEBaseMethod {
	
	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		XFEStack stack = xfeexecute.getStack();
		if (args.length > 0) {
			String xfeclassname = null;
			String code = null;
			XFEClassLoader xfeclassloader = null;
			if (args.length == 2) {
				Object fileName = args[0];
				Object codeString = args[1];
				xfeclassname = fileNameToString(fileName);
				code = codeToString(codeString);
				xfeclassloader = xfeexecute.getXFEClassInstance().getClassLoader();
			} else if (args.length == 3) {
				Object xcl = args[0];
				Object fileName = args[1];
				Object codeString = args[2];
				if (!XFEKeyWords.isXFEClassLoader(xcl)) {
					stack.setThrow("this is not xfeclassloader: " + xcl);
					return null;
				}
				xfeclassname = fileNameToString(fileName);
				code = codeToString(codeString);
				xfeclassloader = (XFEClassLoader) xcl;
			}
			if (null == xfeclassloader) {
				stack.setThrow("this is not xfeclassloader: " + xfeclassloader);
			} 
            return xfeclassloader.loadCode(XFECodeContent.wrapString(xfeclassname, xfeclassname, code));
		}
		super.throwNotFoundMethod(stack, args);
		return null;
	}
	
	private static String fileNameToString(Object fileName) {
		return null == fileName ?"null": fileName.toString();
	}
	private static String codeToString(Object cs) {
		return null == cs ?"": cs.toString();
	}
}
