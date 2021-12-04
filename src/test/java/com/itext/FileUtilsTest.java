package com.itext;

import com.ittext.util.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
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


        URL resource = classLoader.getResource("source/timeTable1.txt");
        List<String> stringServices = FileUtils.readFile(resource.getPath());

        assertTrue(stringServices.size() == resultServices.size());
        for(int i = 0 ; i< stringServices.size() ; i++){
            assertEquals(resultServices.get(i),stringServices.get(i));
        }
    }
}
