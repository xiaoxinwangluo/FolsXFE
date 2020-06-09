package top.fols.box.util.xfe.executer.basemethod.lang;

import java.util.Map;
import top.fols.box.io.base.XCharArrayWriter;
import top.fols.box.util.xfe.executer.XFEExecute;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.executer.basemethod.XFEBaseMethod;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;
import top.fols.box.util.xfe.util.XFEUtil;

public class STRF extends XFEBaseMethod {
	@Override
	public Object executeProcess(XFEExecute.ExecuteStatus execStatus, XFEExecute xfeexecute, Object[] args) throws Throwable {
		// TODO: Implement this method
		if (args.length == 1) {
			return format(xfeexecute, null == args[0] ?null: args[0].toString());
		}
		XFEStack stack = xfeexecute.getStack();
		super.throwNotFoundMethod(stack, args);
		return null;
	}

	/**
	 * {local_var_name}
	 * any char of the variable name must conform to XFEKeyWords.isStandardVariableNameChar();
	 */
	private static String format(XFEExecute varm, String text) {
		if (null == text) {
			return null;
		}
		XCharArrayWriter writer = new XCharArrayWriter();
		char ANNOTATION_START = XFEKeyWords.XFEBaseMethodResource.STRF.ANNOTATION_START;
		char ANNOTATION_END = XFEKeyWords.XFEBaseMethodResource.STRF.ANNOTATION_END;
		int strlen = text.length();
		int st_index = -1;
		boolean st = false;
		for (int i = 0;i < strlen;i++) {
			char ch = text.charAt(i);
			if (st) {
				if (ch == ANNOTATION_START) {
					writer.write(text, st_index , i - st_index);
					st_index = i;
					st = true;
				} else if (ch == ANNOTATION_END) {
					int len = i - (st_index + 1);
					if (len > 0) {
						String name = text.substring(st_index + 1, i);
						Object value = varm.getVariableValue(name);
//						writer.append("|" + name + "|");
						writer.append(XFEUtil.objectString(value));
						st_index = i + 1;
					}
				} else {
					if (!XFEKeyWords.isStandardVariableNameChar(ch)) {
						writer.write(text, st_index , i - st_index + 1);
						st_index = -1;
						st = false;
					}
				}
			} else {
				if (ch == ANNOTATION_START) {
					st_index = i;
					st = true;
				} else {
					writer.append(ch);
				}
			}
		}
		if (st) {
			writer.write(text, st_index , text.length() - st_index);
		}
		return writer.toString();
	}










}
