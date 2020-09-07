import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import top.fols.box.io.os.XFile;
import top.fols.box.lang.XString;
import top.fols.box.time.XTimeConsum;
import top.fols.box.util.xfe.executer.XFEStack;
import top.fols.box.util.xfe.lang.XFEClass;
import top.fols.box.util.xfe.lang.XFEClassInstance;
import top.fols.box.util.xfe.lang.XFEClassLoader;
import top.fols.box.util.xfe.lang.XFEMethod;
import top.fols.box.util.xfe.lang.XFEMethodCode;
import top.fols.box.util.xfe.lang.autoloader.XFEDirCodeLoader;
import top.fols.box.util.xfe.lang.autoloader.XFEZipCodeLoader;
import top.fols.box.util.xfe.util.XFECodeContent;
import top.fols.box.io.base.XCharArrayWriter;

public class Main {



	// 用来包裹XFEMethod
	public static class XFEClassInstanceInterfaceProxy {
		private ClassLoader cl;
		private Class[] interfaces;

		private XFEStack stack;
		private XFEClassInstance xfeclassinstance;

		public XFEClassInstanceInterfaceProxy(XFEStack.StackElement now, XFEClassInstance obj,
                                              ClassLoader cl,  Class[] interfaces) {
			this.stack = XFEStack.newXFEThreadStack(now);
			this.xfeclassinstance = obj;
			this.cl = cl;
			this.interfaces = interfaces;
		}

		private Object instance = null;

		public Object getInstance() {
			if (null == this.instance) {
				this.instance = Proxy.newProxyInstance(this.cl, this.interfaces, new InvocationHandler() {
                        @Override
                        public Object invoke(Object object, Method p2, Object[] param) throws Throwable {
                            // TODO: Implement this method
                            String methodName = p2.getName();
                            Object result = xfeclassinstance.executeMethod(XFEClassInstanceInterfaceProxy.this.stack,
								methodName, param);
                            if (XFEClassInstanceInterfaceProxy.this.stack.isThrow()) {
                                throw new RuntimeException(XFEClassInstanceInterfaceProxy.this.stack.stringall());
                            }
                            return result;
                        }
                    });
			}
			return this.instance;
		}

		public static Object newInstance(XFEStack.StackElement now, XFEClassInstance obj, ClassLoader cl,
                                         Class[] interfaces) {
			return new XFEClassInstanceInterfaceProxy(now, obj, cl, interfaces).getInstance();
		}
	}

	public static void main(String[] args) throws Throwable {
		// final List arraylist = new ArrayList<>();
		// List list = (List) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
		// ArrayList.class.getInterfaces(), new InvocationHandler() {
		// @Override
		// public Object invoke(Object object, Method p2, Object[] param) throws
		// Throwable {
		// // TODO: Implement this method
		// return p2.invoke(arraylist, param);
		// }
		// });
		// System.out.println(list);
		// Class ArrayListClass = ArrayList.class;
		// XTimeConsum xxxx = XTimeConsum.newAndStart();
		// for (int i = 0;i < 10000000;i++) {
		//// Map map = new HashMap<>();
		//// map.put("true", XStaticFixedValue.Boolean_TRUE);
		//// map.put("false", XStaticFixedValue.Boolean_FALSE);
		//// map.put(null, null);
		// Method method = XReflectMatcher.defaultInstance.getMethod(ArrayListClass,
		// "add", "hhh");
		// method.invoke(arraylist, "hhh");
		// }
		// System.out.println("耗时: " + xxxx.endAndGetEndLessStart());


		// System.out.println(XFEUtil.toAbsClassName("java.lang.String[]"));
		// System.out.println(XFEUtil.toAbsClassName("[Ljava.lang.String;"));
        // System.out.println(Class.forName(XFEUtil.toAbsClassName("[B")));

		
		Compiler.start("C:\\Program Files\\Java\\jdk1.8.0_212\\bin", "src", "libs", "top.fols.box.util.xfe.jar");
		if (true) {
			return;
		}


        testThreadStack();
		
		{
			File rundir;
			rundir = new File(XFile.getRunningDir());
			// rundir = new File("/sdcard/_appprojects/x/FolsXFE3/");
			File exampledir = new File(rundir, "example");
			System.out.println(exampledir);
			XTimeConsum XTimeConsumLoad;
			File codeDir = exampledir;

			XFEDirCodeLoader dircode = new XFEDirCodeLoader(codeDir);
			XFEZipCodeLoader zipcode = new XFEZipCodeLoader(new File(rundir, "example_zip\\io.zip"));

			XFEClassLoader xfeclassloader1 = XFEClassLoader.getDefaultLoader();
			xfeclassloader1.getAutoLoaderCodeManager().addLoader(dircode);
			xfeclassloader1.getAutoLoaderCodeManager().addLoader(zipcode);
			xfeclassloader1.forName("hook");
			xfeclassloader1.forName("io.byte");



			XTimeConsumLoad = XTimeConsum.newAndStart();
			XFEClass xfeclass = xfeclassloader1.forName("xfe3main");
			System.out.println("加载耗时: " + "xfeclass: " + xfeclass + " " + XTimeConsumLoad.endAndGetEndLessStart());

//	    String[] methodNames = xfeclass.listMethodName();
//        for (int i = 0; i < methodNames.length; i++) {
//            XFEMethod xfemethod = xfeclass.getMethod(methodNames[i]);
//			System.out.println();
//            System.out.println("----------------");
//            System.out.println("@method " + methodNames[i]);
//            System.out.println("" + XFEMethodCode.formatCode(xfemethod) + "");
//            System.out.println("----------------");
//			System.out.println();
//        }
//


			XFEStack stack;
			XFEClassInstance xfeclassInstance;
			Object result = null;

			stack = XFEStack.newStack();
			//xfeclass = xfeclassloader1.forName(xfeclass.getName());
			System.out.println("_________________________");
			xfeclassInstance = xfeclass.newInstance(stack, new Object[] { "大傻逼" , "大傻逼2"});
			result = xfeclassInstance.executeMethod(stack, "test", new Object[] { "大傻逼" , "大傻逼2"});

			System.out.println("_________________________");
			System.out.println("结果: " + result);
			System.out.println();
			System.out.println("----stack:----");
			System.out.println("classloader=" + xfeclassloader1.toString());
			System.out.println("exception=" + stack.isThrow());
			System.out.println();
			System.out.println(stack.stringall());
			System.out.println("----");
			System.out.println();
			// xfeclassloader1.releaseClassLoader();

			System.out.println("_________________________");







			XTimeConsum timing = XTimeConsum.newAndStart();
			result = xfeclassInstance.executeMethod(stack, "hscz", new Object[] { "大傻逼" , "大傻逼2"});
			System.out.println("耗时: " + timing.endAndGetEndLessStart());


			System.out.println("_________________________");


			{
				//multi classloader

				XFEClassLoader xfeclassloader2 = XFEClassLoader.newInstance();
				xfeclassloader2.getAutoLoaderCodeManager().addLoader(new XFEDirCodeLoader(codeDir));

				stack = XFEStack.newStack();
				xfeclass = xfeclassloader2.forName("cl2");
				System.out.println("_________________________");
				xfeclassInstance = xfeclass.newInstance(stack, new Object[] {});
				result = xfeclassInstance.executeMethod(stack, "test",
					new Object[] { xfeclassloader1.forName("cl1"), xfeclassloader1 });
				System.out.println("_________________________");
				System.out.println("结果: " + result);
				System.out.println();
				System.out.println("----stack:----");
				System.out.println("classloader=" + xfeclassloader2.toString());
				System.out.println("exception=" + stack.isThrow());
				System.out.println();
				System.out.println(stack.stringall());
				System.out.println("----");
				System.out.println();

				System.out.println("_________________________");
			}

		}



	}



    public static void testThreadStack() {
        new Thread() {
            public void run() {
                try {
                    TryTest.error();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
	public static class TryTest {
		public static void error() {
			try {
				throw new Throwable();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
