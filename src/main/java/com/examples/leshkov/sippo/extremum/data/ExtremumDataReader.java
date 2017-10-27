package com.examples.leshkov.sippo.extremum.data;

import java.io.*;

public class ExtremumDataReader {
	private BufferedReader in;

	public ExtremumDataReader(File file) throws FileNotFoundException {
		this.in = new BufferedReader(new FileReader(file));
	}

	public ExtremumDataReader(String file) throws FileNotFoundException {
		this.in = new BufferedReader(new FileReader(file));
	}

	public DataUnit next() throws IOException {
		String line = in.readLine();

		System.out.println(line);

		if (line == null) {
			return null;
		} else {
			String[] words = line.split(" ");

			int i = 0;
			while (isEmpty(words[i])) {
				i++;
			}
			double a = Double.parseDouble(words[i++]);

			while (isEmpty(words[i])) {
				i++;
			}
			double b = Double.parseDouble(words[i++]);

			while (isEmpty(words[i])) {
				i++;
			}
			double epsilon = Double.parseDouble(words[i++]);

			while (isEmpty(words[i])) {
				i++;
			}
			int iter = Integer.parseInt(words[i]);

			return new DataUnit(a, b, epsilon, iter);
		}
	}

	private boolean isEmpty(String s) {
		return	s.length() == 0 ||
				s.contains("\t") ||
				s.contains("\n");
	}


	public void reset() throws IOException {
		in.reset();
	}

	public void close() throws IOException {
		in.close();
	}
}
