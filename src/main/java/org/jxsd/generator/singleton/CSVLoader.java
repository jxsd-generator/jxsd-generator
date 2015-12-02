package org.jxsd.generator.singleton;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.inject.Singleton;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.io.input.BOMInputStream;

/**
 * CSV loader class.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
@Singleton
public class CSVLoader {
	/**
	 * Loading file.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public CSVParser load(File file) throws IOException {
		Reader reader = new InputStreamReader(new BOMInputStream(new FileInputStream(file)));
		return CSVFormat.newFormat(';').withHeader("Name", "Description", "Min", "Max", "Format", "Length", "Nullable", "Values").parse(reader);
	}
}
