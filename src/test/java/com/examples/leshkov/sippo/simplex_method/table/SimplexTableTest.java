package com.examples.leshkov.sippo.simplex_method.table;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SimplexTableTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testTransform() throws IOException {
        File f = folder.newFile("tmp");

        try (BufferedWriter out = new BufferedWriter(new FileWriter(f))) {
            out.write(
                "0   -3    1 2 0 0    max\n" +
                "-1   1 2    	 0  0   >= 2\n" +
                "1    0   1 1 0  = 4\n" +
                "1   1   1  0   -1   <=  	 6\n"
            );
        }

        SimplexTable table = SimplexTableParser.parse(f);
        System.out.println(table);

        table.transform(1, 1);
        System.out.println("!!!!!!!!!!\n");

        System.out.println(table);
    }
}