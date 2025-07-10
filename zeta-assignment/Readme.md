# 🧾 CSV Validation Framework (Java)

A robust, maintainable, and customizable framework for validating CSV files using a schema-driven rule engine. Designed with clean architecture and extensibility in mind.

---

## 🚀 Problem Statement

In many real-world data pipelines, CSV files are used to transfer or ingest structured data. These files often:
- Vary in structure (column names/types)
- Require specific validation rules (e.g., unique IDs, numeric zip codes)
- Fail silently when malformed, causing downstream issues

✅ We need a **generic and configurable framework** that can:
- Parse any CSV file
- Validate each column based on user-defined schema
- Report clear and useful validation errors

---

## 🎯 Generic Use Cases

This framework is useful for:
- ✅ Test automation (e.g., validating test data files)
- ✅ ETL pipelines (pre-validation of incoming files)
- ✅ Data quality gates (before ingestion into DB)
- ✅ QA/Dev teams consuming files from clients or partners

---

## 💡 Solution Idea

Let users:
- Upload a **CSV file**
- Define a **schema JSON** specifying rules for each column

The framework will:
1. Parse the schema
2. Read and structure the CSV
3. Run all validation rules
4. Print all violations clearly (row number, column, reason)

---

## 🏗️ Architectural Design

### ➤ Components

The project is modularized for easy extensibility and clarity:

- `CsvValidatorApp.java` – Main class and entry point
- `engine/RuleEngine.java` – Core rule orchestrator
- `parser/`
    - `CsvReader.java` – Reads CSV into column-wise map
    - `SchemaLoader.java` – Loads validation schema from JSON
- `model/ColumnRule.java` – POJO for schema rules
- `rules/` – Contains all validation strategies:
    - `ValidationRule.java` – Interface
    - `NonNullRule.java`
    - `UniqueRule.java`
    - `DataTypeRule.java`
    - `PatternRule.java`
    - `MaxLengthRule.java`

---

## 🧠 Thought Process

1. **Schema-Driven Design**  
   Column-level rules are defined externally in JSON — no hardcoded validations.

2. **Strategy Pattern for Rules**  
   Each rule (e.g., `NonNullRule`, `UniqueRule`) implements a shared interface `ValidationRule`, making it extensible and decoupled.

3. **Single Responsibility Principle**  
   Each class has a single responsibility — schema loading, CSV parsing, or validation.

4. **Open-Closed Principle (OCP)**  
   Easily add new validation rules without modifying the core engine.

5. **Reusability & Maintainability**  
   Clean, generic, and testable code — ready for scale and long-term use.

---

## 📦 Supported Rules

| Rule Type      | Schema Field | Description                                               |
|----------------|--------------|-----------------------------------------------------------|
| Non-Null       | `nullable`   | Ensures value is not null or empty                        |
| Unique         | `unique`     | Checks that all values in the column are unique           |
| Data Type      | `dataType`   | Validates value type: `Integer`, `String`, etc.           |
| Regex Pattern  | `pattern`    | Ensures value matches given regex                         |
| Max Length     | `maxLength`  | Validates that the character count does not exceed limit  |

---

## 📜 Sample Schema

```json
{
  "columns": {
    "ID": {
      "dataType": "Integer",
      "nullable": false,
      "unique": true
    },
    "FirstName": {
      "dataType": "String",
      "nullable": false
    },
    "Zipcode": {
      "dataType": "String",
      "nullable": false,
      "pattern": "^[0-9]{6}$"
    },
    "City": {
      "dataType": "String",
      "nullable": true
    },
    "Address": {
      "dataType": "String",
      "maxLength": 255
    }
  }
}
```

---

## 📂 Sample CSV

```csv
ID,FirstName,Zipcode,City,Address
1,Abhishek,400001,Mumbai,123 Street
2,Ravi,400002,,Apt 567 Residency
3,Rahul,ABCDE,Delhi,This is a very long address that exceeds the character limit intentionally...
1,Ram,400003,Delhi,Flat 101
```

---

## 🔄 Code Flow

```text
CsvValidatorApp
│
├── loads schema.json → Map<String, ColumnRule>
├── reads CSV file → Map<String, List<String>>
├── passes both to RuleEngine
│   ├── iterates through columns
│   ├── applies all registered ValidationRule classes
│   └── collects validation errors
│
└── prints all errors in console
```

---

## ✅ How to Run

### 1. Project Directory Layout

```text
project-root/
├── src/
│   └── main/
│       ├── java/...
│       └── resources/
│           ├── config/schema.json
│           └── csv/sample.csv
```

### 2. Maven Dependencies

```xml
<!-- Add this in your pom.xml -->
<dependencies>
  <dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.19.1</version>
  </dependency>
</dependencies>
```

### 3. Run from Terminal

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="CsvValidatorApp"
```

Or from IntelliJ: Right-click on `CsvValidatorApp` and choose **Run**.

---

## ✅ Output Example

```text
✅ Schema loaded successfully.
✅ CSV data loaded successfully.

❌ CSV Validation Failed. Found 3 Errors:

Row 4: 'ABCDE' in column 'Zipcode' does not match required pattern: ^[0-9]{6}$
Row 5: Duplicate value '1' found in column 'ID'.
Row 4: Value in column 'Address' exceeds max length of 255 characters.
```
## 👨‍💻 Author

**Abhishek Singh**  

