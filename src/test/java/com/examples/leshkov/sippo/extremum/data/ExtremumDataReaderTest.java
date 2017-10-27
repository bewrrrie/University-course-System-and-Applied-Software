package com.examples.leshkov.sippo.extremum.data;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class ExtremumDataReaderTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@After
	public void after() {
		folder.delete();
		folder = null;
	}

	@Test
	public void testReading() throws IOException {
		File tmp = folder.newFile("tmp");

		FileWriter writer = new FileWriter(tmp);
		writer.write("0 1 0.00000001 1000");
		writer.close();

		ExtremumDataReader reader = new ExtremumDataReader(tmp);
		DataUnit unit = reader.next();

		assertTrue(new DataUnit(0, 1, 0.00000001, 1000).equals(unit));
	}

	@Test
	public void testReadingSeveral() throws IOException {
		DataUnit sample = new DataUnit(0, 1, 10e-7, 1000);

		File tmp = folder.newFile("tmp");
		ExtremumDataReader reader = new ExtremumDataReader(tmp);

		int N = 10;

		FileWriter writer = new FileWriter(tmp);
		for (int k = 0; k < N; k++) {
			writer.write(sample.toString() + '\n');
		}
		writer.close();

		DataUnit[] actual = new DataUnit[N];
		DataUnit current = reader.next();

		int i = -1;
		while (current != null) {
			actual[++i] = current;
			current = reader.next();
		}

		DataUnit[] expected = new DataUnit[N];
		for (int k = 0; k < N; k++) {
			expected[k] = new DataUnit(sample);
		}

		assertArrayEquals(expected, actual);
	}
}