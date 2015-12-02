package org.jxsd.generator.singleton;

import static org.apache.commons.io.FileUtils.writeStringToFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.io.input.BOMInputStream;

/**
 * Utils class for files.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
@Singleton
public class FileUtils {
	@Inject
	private SimpleLogger LOGGER;

	/**
	 * Checking if a file exists.
	 * 
	 * @param path
	 * @return boolean
	 */
	public boolean existFile(String path) {
		File f = new File(path);
		return f.exists();
	}

	/**
	 * Deleting file.
	 * 
	 * @param path
	 * @return boolean
	 */
	public boolean deleteFileQuietly(String path) {
		if (existFile(path)) {
			File f = new File(path);
			boolean rtn = f.delete();

			if (existFile(path) || !rtn) {
				try {
					Path p = FileSystems.getDefault().getPath(path, new String[0]);
					Files.delete(p);
					return true;
				} catch (IOException e) {
					LOGGER.error("FileUtils|deleteFileQuietly", "Reading file error", e);
					return false;
				}
			}

			return rtn;
		}

		return false;
	}

	/**
	 * Getting byte content of a file.
	 * 
	 * @param path
	 * @return byte[]
	 */
	public byte[] fileToByteQuietly(String path) {
		Path p = Paths.get(path);
		try {
			return Files.readAllBytes(p);
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Getting absolute path from relative path.
	 * 
	 * @param relatifPath
	 * @return String
	 */
	public String getAbsolutePath(String relatifPath) {
		File file = new File(relatifPath);
		return file.getAbsolutePath();
	}

	/**
	 * Getting file content as String.
	 * 
	 * @param pathFile
	 * @return String
	 */
	public String file2stringQuietly(InputStream file) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(file)));
			StringBuilder builder = new StringBuilder();
			String line;

			// For every line in the file, append it to the string builder
			while (null != (line = reader.readLine())) {
				builder.append(line);
			}

			reader.close();
			return builder.toString();
		} catch (IOException e) {
			LOGGER.error("FileUtils|file2stringQuietly", "Reading file error", e);
			return null;
		}
	}

	/**
	 * Writing in a file.
	 * 
	 * @param pathFile
	 * @param content
	 * @throws IOException
	 */
	public void string2fileQuietly(String pathFile, String content) {
		try {
			writeStringToFile(new File(pathFile), content);
		} catch (IOException e) {
			LOGGER.error("FileUtils|string2fileQuietly", "Reading file error", e);
		}
	}
}
