package com.pz.rock.core.loader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.boot.loader.JarLauncher;
import org.springframework.boot.loader.LaunchedURLClassLoader;
import org.springframework.boot.loader.archive.Archive;
import org.springframework.boot.loader.archive.JarFileArchive;

import com.onezhier.rock.exception.SysException;

public class SpringBootClassLoader extends LaunchedURLClassLoader{

	public SpringBootClassLoader(boolean exploded, Archive rootArchive, URL[] urls, ClassLoader parent) {
		super(exploded, rootArchive, urls, parent);
	}

	
	
	
	
	public static class Launcher extends JarLauncher {
		
		public Launcher(String filePath) throws IOException {
		
			super(new JarFileArchive(new File(filePath)));
		}
		
		public Archive getArchive_() {
			return this.getArchive();
		}
		
		public Iterator<Archive> getClassPathArchivesIterator_() throws Exception{
			
			return this.getClassPathArchivesIterator();
		}
		
		
	}
	
	
	public static SpringBootClassLoader createClassLoader(Launcher l,ClassLoader parentClassLoader) {
		
		
		try {
			Iterator<Archive> archives = l.getClassPathArchivesIterator_();
		
		
			List<URL> urls = new ArrayList<>(50);
			while (archives.hasNext()) {
				urls.add(archives.next().getUrl());
			}
			return  new SpringBootClassLoader(false, l.getArchive_(), urls.toArray(new URL[0]), parentClassLoader);
		} catch (Exception e) {
			throw new SysException(e.getMessage(),e);
		}
	}
	
}
