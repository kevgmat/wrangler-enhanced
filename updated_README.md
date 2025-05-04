# Wrangler Enhanced

This is a forked and enhanced version of the [CDAP Wrangler](https://github.com/data-integrations/wrangler) project. The enhancement was developed as part of a software engineering assignment and involves implementing a new directive called `aggregate-stats`.

## Note

Due to academic commitments, specifically final examinations during the submission period, I was unable to complete Assignment 1 in its entirety. I made significant progress by implementing the core directive (`aggregate-stats`) and its associated logic and tests, but time constraints limited further testing and polish.

## 📌 Enhancement Summary

### ➕ Directive Added: `aggregate-stats`

This directive allows aggregation of byte size and time duration values across multiple rows, with support for unit conversion and multiple input formats.

**Syntax:**
```
aggregate-stats <byte_column> <time_column> <output_byte_column> <output_time_column> [<byte_unit>] [<time_unit>]
```

**Key Features:**
- Aggregates values from `ByteSize`, `TimeDuration`, strings (e.g., "5MB", "2s"), or raw numbers.
- Supports unit conversion:
  - Byte units: `b`, `kb`, `mb`, `gb`, `tb`
  - Time units: `ms`, `s`, `m`, `h`, `d`
- Returns a single-row result with the total byte and time values in the specified units.
- Validates inputs and throws clear errors for unsupported units or formats.

## 🗂️ Project Layout

```
wrangler-enhanced/
├── wrangler-api/
├── wrangler-core/
├── wrangler-transform/
│   ├── src/main/java/io/cdap/wrangler/steps/transformation/AggregateStats.java
│   └── src/test/java/io/cdap/wrangler/steps/transformation/AggregateStatsTest.java
├── prompts.txt
├── README.md
```

## ⚙️ Build Instructions

### Prerequisites
- Java 8 or Java 11
- Maven 3.x

### Build Project
```bash
git clone https://github.com/<your-username>/wrangler-enhanced.git
cd wrangler-enhanced
git checkout develop
mvn clean install
```

## ✅ Testing Instructions

Run the full test suite:
```bash
mvn test
```

You should see tests including:

- `AggregateStatsTest`: Verifies basic aggregation, unit conversion, mixed input types, and error handling.

Located at:
```
wrangler-transform/src/test/java/io/cdap/wrangler/steps/transformation/AggregateStatsTest.java
```

## 📋 Sample Usages

### Default Units
```
aggregate-stats data_transfer_size response_time total_size_mb total_time_sec
```

### Custom Output Units
```
aggregate-stats data_transfer_size response_time total_size_gb total_time_min gb m
```

## 📤 Submission Checklist

- [x] Forked Repo: `https://github.com/<your-username>/wrangler-enhanced`
- [x] Branch: `develop`
- [x] Directive: `AggregateStats.java`
- [x] Unit Tests: `AggregateStatsTest.java`
- [x] Readme: `README.md`
- [x] Prompt file: `prompts.txt`
- [x] Verified: `mvn clean install` success
- [x] Screenshot of passing tests (optional)

## 📅 Submitted On

May 04, 2025

## 🧑 Author : Kevin George Mathew

**<Your Name>**  
GitHub: [@kevgmat](https://github.com/kevgmat/wrangler-enhanced)

## 📝 License

Licensed under the [Apache 2.0 License](https://www.apache.org/licenses/LICENSE-2.0), same as the original CDAP Wrangler project.
