package zeta;

import zeta.engine.RuleEngine;
import zeta.model.ColumnRule;
import zeta.parser.CsvReader;
import zeta.parser.SchemaLoader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Entry point of the CSV validation framework.
 * Reads the CSV file, loads the schema, runs validations, and prints results.
 */
public class CsvValidatorApp {

    public static void main(String[] args) {
        String schemaPath = "config/schema.json"; // path to JSON schema
        String csvPath = "csv/sample.csv";        // path to CSV file

        try {
            // Step 1: Load schema from JSON file
            Map<String, ColumnRule> schema = SchemaLoader.loadSchema(schemaPath);
            System.out.println("✅ Schema loaded successfully.");

            // Step 2: Read CSV file and map it column-wise
            Map<String, List<String>> csvData = CsvReader.readCsv(csvPath);
            System.out.println("✅ CSV data loaded successfully.");

            // Step 3: Run validations using RuleEngine
            RuleEngine ruleEngine = new RuleEngine();
            List<String> validationErrors = ruleEngine.validateCsv(csvData, schema);

            // Step 4: Show results
            if (validationErrors.isEmpty()) {
                System.out.println("\n✅ CSV Validation Passed. No Errors Found.");
            } else {
                System.out.println("\n❌ CSV Validation Failed. Found " + validationErrors.size() + " Errors:\n");
                validationErrors.forEach(System.out::println);
            }

        } catch (IOException e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
    }
}
