package com.flxpoint.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CSVHelper {

    public static void main(String[] args) {
        String inputFile = "src/main/resources/input.csv";
        String outputFile = "src/main/resources/output.csv";

        List<String> lines = readCsvFile(inputFile);
        List<String> uniqueLines = removeDuplicates(lines);

        writeCsvFile(outputFile, uniqueLines);
        System.out.println("Deduplicated CSV saved to " + outputFile);
    }

    private static List<String> readCsvFile(String filePath) {
        try {
            return Files.lines(Path.of(filePath)).map(line -> line.trim()).filter(line -> !line.isEmpty()).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("failed to read the file");
        }

    }

    private static List<String> removeDuplicates(List<String> lines) {
        if (lines.isEmpty()) {
            return List.of();
        }

        String header = lines.get(0).trim();
        Set<String> seen = new LinkedHashSet<>();

        seen.add(header);

        lines.stream().skip(1).map(line -> line.trim())
                .map(line -> {
                    String[] parts = line.split(",");
                    return Arrays.stream(parts)
                            .map(part -> part.trim())
                            .collect(Collectors.joining(","));
                }).filter(line -> !line.isBlank()).forEach(line -> seen.add(line));

        return new ArrayList<>(seen);
    }

    private static void writeCsvFile(String filePath, List<String> lines) {
        // TODO: Implement logic to write file
        try {
            Files.write(Path.of(filePath), lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write CSV file", e);
        }
    }
}
