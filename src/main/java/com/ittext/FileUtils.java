package com.ittext;

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
                bufferedWriter.write(service.toString() + "\n");
            }

            bufferedWriter.write("\n");

            for (Service service : grottyServices) {
                bufferedWriter.write(service.toString() + "\n");
            }
        }
        return path;
    }
}
