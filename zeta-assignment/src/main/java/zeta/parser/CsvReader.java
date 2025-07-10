package zeta.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Utility class to read a CSV file and convert it into a column-wise map.
 * Each key is a column name, and value is a list of all values under that column.
 */
public class CsvReader {

    /**
     * Reads the CSV file and returns a map of column names to their respective values.
     *
     * @param filePath path to the CSV file
     * @return Map where key = column name, value = list of column values row-wise
     * @throws IOException if reading file fails
     */
    public static Map<String, List<String>> readCsv(String filePath) throws IOException {
        Map<String, List<String>> data = new LinkedHashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine(); // Read header row
            if (headerLine == null) {
                throw new IOException("CSV file is empty.");
            }

            String[] headers = headerLine.split(",", -1); // -1 preserves empty columns
            for (String header : headers) {
                data.put(header.trim(), new ArrayList<>());
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1);
                for (int i = 0; i < headers.length; i++) {
                    String header = headers[i].trim();
                    String value = i < values.length ? values[i].trim() : "";
                    data.get(header).add(value);
                }
            }
        }

        return data;
    }
}
