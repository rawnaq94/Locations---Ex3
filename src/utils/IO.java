package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;

public class IO {
	public static String[] getFileNamesInFolder(String folderPath) {
		final File file = new File(folderPath);
		return file.list();
	}

	public static void writeToFile(Path path, String text) {
		try (PrintWriter printer = new PrintWriter(path.toString())) {
			printer.println(text);
		} catch (FileNotFoundException e) {
			System.err.println("Failed to write KML file.");
		}
	}

	public static String getExtensionFromFileName(String fileName) {
		String extension = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			extension = fileName.substring(i + 1);
		}
		return extension;
	}
}
