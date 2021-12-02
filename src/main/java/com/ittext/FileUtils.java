package com.ittext;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {
    public static List<String> readFile(String fileName) throws IOException {
        Path path = Path.of(fileName);
        try(BufferedReader bufferedReader = Files.newBufferedReader(path)){
            return bufferedReader.lines().collect(Collectors.toList());
        }
    }

    public static Path writeFile(List<Service> services , String fileName) throws IOException {
        Path path = Path.of(fileName);
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(path)){
            for(Service service : services){
                bufferedWriter.write(service.toString()+"\n");
            }
        }
        return path;
    }
}
