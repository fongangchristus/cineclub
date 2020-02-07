/**
 * 
 */
package com.itgstore.cineclub.service.util;

/**
 * @author : p.djomga
 * @date : 19 oct. 2018
 *
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.io.FileUtils;

public class FileHelper {
	public static void renameFile(String oldName, String newName) throws FileNotFoundException {
		if (oldName == null) {
			throw new FileNotFoundException("Old file name required!");
		}
		if (newName == null) {
			throw new FileNotFoundException("New file name required!");
		}
		new File(oldName).renameTo(new File(newName));
	}

	public static File findXMLFileByProcessingNum(String procNum, String path) throws FileNotFoundException, Exception {
		File f = null;
		if (procNum == null) {
			throw new Exception("Processing number required!");
		}
		if (path == null) {
			throw new FileNotFoundException("Path not found!");
		}
		path = path + "/" + procNum;
		path = path.replace("//", "/");
		File root = new File(path);
		try {
			boolean recursive = true;

			Collection files = FileUtils.listFiles(root, null, recursive);
			for (Iterator iterator = files.iterator(); iterator.hasNext();) {
				File file = (File) iterator.next();
				if (file.getName().equals(procNum)) {
					f = file;
				}
			}
		} catch (Exception e) {
			Iterator iterator;
			e.printStackTrace();
		}
		return f;
	}

	public static void copyFileUsingFileStreams(File source, File dest) throws IOException {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);
			byte[] buf = new byte['Ѐ'];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
		} finally {
			input.close();
			output.close();
		}
	}
}
