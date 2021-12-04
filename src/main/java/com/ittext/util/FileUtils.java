package com.ittext.util;

import com.ittext.Company;
import com.ittext.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileUtils {
    public static List<String> readFile(String fileName) throws IOException {
        Path path = Path.of(fileName);
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            return bufferedReader.lines().collect(Collectors.toList());
        }
    }

    public static Path writeFile(Map<String, List<Service>> services, String fileName) throws IOException {
        List<Service> poshServices = services.get(Company.Posh.toString());
        List<Service> grottyServices = services.get(Company.Grotty.toString());
        Path path = Path.of(fileName);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            for (Service service : poshServices) {
                bufferedWriter.write(service.toString());
                bufferedWriter.newLine();
            }

            if(!grottyServices.isEmpty() && !poshServices.isEmpty()){
                bufferedWriter.newLine();
            }

            for (Service service : grottyServices) {
                bufferedWriter.write(service.toString());
                bufferedWriter.newLine();
            }
        }
        return path;
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
            } else {
                return lineNumber;
            }
        }
    }
}
