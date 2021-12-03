package com.itext;

import com.ittext.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileUtilsTest {

    ClassLoader classLoader = getClass().getClassLoader();
    @Test
    public void testReadFile() throws IOException {
        List<String> resultServices = new ArrayList<>();
        resultServices.add("Posh 10:15 11:10");
        resultServices.add("Posh 10:10 11:00");
        resultServices.add("Grotty 10:10 11:00");
        resultServices.add("Grotty 16:30 18:45");
        resultServices.add("Posh 12:05 12:30");
        resultServices.add("Grotty 12:30 13:25");
        resultServices.add("Grotty 12:45 13:25");
        resultServices.add("Posh 17:25 18:01");


        URL resource = classLoader.getResource("timeTable.txt");
        List<String> stringServices = FileUtils.readFile(resource.getPath());

        assertTrue(stringServices.size() == resultServices.size());
        for(int i = 0 ; i< stringServices.size() ; i++){
            assertEquals(resultServices.get(i),stringServices.get(i));
        }
    }

    public static long compareToFile(Path path1, Path path2) throws IOException {
        try (BufferedReader bf1 = Files.newBufferedReader(path1);
             BufferedReader bf2 = Files.newBufferedReader(path2)) {

            long lineNumber = 1;
            String line1 = "", line2 = "";
            while ((line1 = bf1.readLine()) != null) {
                line2 = bf2.readLine();
                if (line2 == null || !line1.equals(line2)) {
                    return lineNumber;
                }
                lineNumber++;
            }
            if (bf2.readLine() == null) {
                return -1;
            }
            else {
                return lineNumber;
            }
        }
    }
}
