package com.examples.leshkov.sippo.math;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Point2DReader {
	private BufferedReader in;

	public Point2DReader(String file) throws FileNotFoundException {
		this.in = new BufferedReader(new FileReader(file));
	}

	public Point2D next() throws IOException {
		String line = in.readLine();

		if (line == null) {
			return null;
		} else {
			String[] words = line.split(" ");

			return new Point2D(
			Double.parseDouble(words[0]),
			Double.parseDouble(words[1])
			);
		}
	}

	public void reset() throws IOException {
		in.reset();
	}

	public long skip(long bytes) throws IOException {
		return in.skip(bytes);
	}

	public void close() throws IOException {
		in.close();
	}
}
