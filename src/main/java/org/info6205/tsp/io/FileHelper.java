package org.info6205.tsp.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

	/**
	 * Helper function to read lines from a file
	 * @param fileName Name of file to be read
	 * @return A list of strings containing the lines in the file
	 * @throws IOException
	 */
	public List<String> read(String fileName) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		List<String> lines = new ArrayList<>();
		while((line = bufferedReader.readLine()) != null) {
			lines.add(line);
		}

		fileReader.close();
		bufferedReader.close();
		return lines;
	}

	/**
	 * Helper function to write to a file
	 * @param fileName Name of file to be written to
	 * @param content Content to write to the file
	 * @throws IOException
	 */
    public void write(String fileName, List<String> content) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for(String line: content) {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        }

        bufferedWriter.flush();
        bufferedWriter.close();
        fileWriter.close();
    }
}
