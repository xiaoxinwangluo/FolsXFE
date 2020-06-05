'''
File rundir = new File(XFile.getRunningDir());
File exampledir = new File(rundir, "example");
System.out.println(exampledir);

File codeDir = exampledir;

XFEClassLoader xfeclassloader1 = XFEClassLoader.getDefaultLoader();
xfeclassloader1.getAutoLoaderCodeManager().addLoader(new XFEDirCodeLoader(codeDir));

XTiming xtimingLoad = XTiming.newAndStart();
XFEClass xfeclass = xfeclassloader1.forName("xfe3main");
System.out.println("加载耗时: " + xtimingLoad.endAndGetEndLessStart());


String[] methodNames = xfeclass.listMethodName();
for (int i = 0; i < methodNames.length; i++) {
	XFEMethod xfemethod = xfeclass.getMethod(methodNames[i]);
	System.out.println("--------");
	System.out.println("@method " + methodNames[i]);
	System.out.println("" + XFEMethodCode.formatCode(xfemethod) + "");
	System.out.println("--------");
}



XFEStack stack;
XFEClassInstance xfeclassInstance;
Object result = null;

stack = XFEStack.newStack();
xfeclass = xfeclassloader1.forName(xfeclass.getName());
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
'''