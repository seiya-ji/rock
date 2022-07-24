package com.pz.rock.core.osgi;
///**
// * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
// */
//package com.fzd.rock.core.osgi;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.jar.Attributes;
//import java.util.jar.JarOutputStream;
//import java.util.jar.Manifest;
//
//import org.eclipse.equinox.servletbridge.FrameworkLauncher;
//
//
//
///**
// * <p>Title: CustomizedFrameworkLauncher </p>
// * <p>Description: TODO </p>
// *
// * @author stephen.ji
// * @version v0.1
// * @date 2021/8/15 3:51 下午
// */
////@Service("frameworkLauncher")
//public class CustomizedFrameworkLauncher extends FrameworkLauncher {
//	
//	
////	@PostConstruct
////	public void initialize() {
////		//this.config = new CustomizedServletConfig();
////		//this.context = this.config.getServletContext();
////		File servletTemp = (File) this.context.getAttribute("javax.servlet.context.tempdir");
////		File platformDirectory = new File(servletTemp, "eclipse");
////		File plugins = new File(platformDirectory, "plugins");
////		deployBridgeExtensionBundle(plugins);
////		this.deploy();
////		this.start();
////	}
//	
//	@Override
//	protected void initResourceBase() {
//		super.initResourceBase();
//		this.context = new CustomizedServletContext();
//		this.resourceBase = "";
//		System.out.println("initResourceBase:"+ Thread.currentThread().getContextClassLoader());
//	}
//	@Override
//	public void init() {
////		File servletTemp = (File) this.context.getAttribute("javax.servlet.context.tempdir");
////		File platformDirectory = new File(servletTemp, "eclipse");
////		File plugins = new File(platformDirectory, "plugins");
//		//deployBridgeExtensionBundle(plugins);
//	}
//
//	private void deployBridgeExtensionBundle(File plugins) {
//		File extensionBundle = new File(plugins, "com.test.bridge.extensionbundle.jar");
//		File extensionBundleDir = new File(plugins, "com.test.bridge.extensionbundle");
//		if ((extensionBundle.exists()) || ((extensionBundleDir.exists()) && (extensionBundleDir.isDirectory()))) {
//			return;
//		}
//		Manifest mf = new Manifest();
//		Attributes attribs = mf.getMainAttributes();
//		attribs.putValue("Manifest-Version", "1.0");
//		attribs.putValue("Bundle-ManifestVersion", "2");
//		attribs.putValue("Bundle-Name", "bridge Extension Bundle");
//		attribs.putValue("Bundle-SymbolicName", "com.test.bridge.extensionbundle");
//		attribs.putValue("Bundle-Version", "1.0.0");
//		attribs.putValue("Fragment-Host", "system.bundle; extension:=framework");
//		String packageExports = "com.test.bridge";
//		attribs.putValue("Export-Package", packageExports);
//		try {
//			JarOutputStream jos = null;
//			try {
//				jos = new JarOutputStream(new FileOutputStream(extensionBundle), mf);
//				jos.finish();
//			} finally {
//				if (jos != null) {
//					jos.close();
//				}
//			}
//		} catch (IOException e) {
//			System.out.println("Error generating extension bundle" + e);
//		}
//	}
//
//}
