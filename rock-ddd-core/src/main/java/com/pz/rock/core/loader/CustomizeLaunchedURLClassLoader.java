package com.pz.rock.core.loader;

import java.net.URL;

import org.springframework.boot.loader.LaunchedURLClassLoader;
import org.springframework.boot.loader.archive.Archive;

public class CustomizeLaunchedURLClassLoader extends LaunchedURLClassLoader {

	public CustomizeLaunchedURLClassLoader(boolean exploded, Archive rootArchive, URL[] urls, ClassLoader parent) {
		super(exploded, rootArchive, urls, parent);
	}

//	@Override
//	public Class<?> loadClass(String name) throws ClassNotFoundException {
//		return this.findClass(name);
//	}

	
}
