package com.itext;

import com.ittext.Company;
import com.ittext.FileUtils;
import com.ittext.Service;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileUtilsTest {

    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
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

    @Test
    public void testWriteFile() throws ParseException, IOException {

        List<Service> initTimeTable = new ArrayList<>();
        initTimeTable.add(new Service(Company.Posh, formatter.parse("10:15"), formatter.parse("11:10")));
        initTimeTable.add(new Service(Company.Posh, formatter.parse("10:10"), formatter.parse("11:00")));
        initTimeTable.add(new Service(Company.Grotty, formatter.parse("10:10"), formatter.parse("11:00")));
        initTimeTable.add(new Service(Company.Grotty, formatter.parse("16:30"), formatter.parse("18:45")));
        initTimeTable.add(new Service(Company.Posh, formatter.parse("12:05"), formatter.parse("12:30")));
        initTimeTable.add(new Service(Company.Grotty, formatter.parse("12:30"), formatter.parse("13:25")));
        initTimeTable.add(new Service(Company.Grotty, formatter.parse("12:45"), formatter.parse("13:25")));
        initTimeTable.add(new Service(Company.Posh, formatter.parse("17:25"), formatter.parse("18:01")));

        Path createdPath = FileUtils.writeFile(initTimeTable, "/home/saeid/Downloads/itext/resultTimeTable.txt");
        Path existedPath = Path.of(classLoader.getResource("timeTable.txt").getPath());

        assertEquals(compareToFile(createdPath,existedPath),-1);
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
