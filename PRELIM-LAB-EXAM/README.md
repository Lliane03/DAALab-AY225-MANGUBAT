# The Sorting Algorithm Stress Test - Prelim Exam

A comprehensive benchmarking tool for analyzing sorting algorithm performance on large datasets with structured CSV data.

## 📋 Project Overview

This project implements and compares three fundamental sorting algorithms (Bubble Sort, Insertion Sort, and Merge Sort) using a dataset of 100,000 person records. The program demonstrates the dramatic performance differences between O(n²) and O(n log n) algorithms.

## 🎯 Features

- **Three Sorting Algorithms** (implemented from scratch):
  - Bubble Sort (O(n²))
  - Insertion Sort (O(n²))
  - Merge Sort (O(n log n))

- **Multi-Column Sorting**:
  - Sort by ID (Integer comparison)
  - Sort by First Name (String comparison)
  - Sort by Last Name (String comparison)

- **Scalable Testing**:
  - Test with 1,000, 10,000, 50,000, or 100,000 records
  - Performance tracking for both file loading and sorting
  - Warning system for large O(n²) operations

- **Modern GUI Interface**:
  - Split-view design with data preview table
  - Real-time performance metrics
  - Progress indicators for long operations

## 📊 Benchmark Results

### Performance Comparison Table (Sorting by ID)

| Algorithm          | 1,000 Records | 10,000 Records | 100,000 Records | Time Complexity |
|--------------------|---------------|----------------|-----------------|-----------------|
| **Bubble Sort**    |               |                |                 | O(n²)           |
| **Insertion Sort** |               |                |                 | O(n²)           |
| **Merge Sort**     |               |                |                 | O(n log n)      |

### Performance Comparison Table (Sorting by FirstName)

| Algorithm          | 1,000 Records | 10,000 Records | 100,000 Records | Time Complexity |
|--------------------|---------------|----------------|-----------------|-----------------|
| **Bubble Sort**    |               |                |                 | O(n²)           |
| **Insertion Sort** |               |                |                 | O(n²)           |
| **Merge Sort**     |               |                |                 | O(n log n)      |

### Performance Comparison Table (Sorting by LastName)

| Algorithm          | 1,000 Records | 10,000 Records | 100,000 Records | Time Complexity |
|--------------------|---------------|----------------|-----------------|-----------------|
| **Bubble Sort**    |               |                |                 | O(n²)           |
| **Insertion Sort** |               |                |                 | O(n²)           |
| **Merge Sort**     |               |                |                 | O(n log n)      |

### Key Observations

1. **Merge Sort Dominance**: For 100,000 records, Merge Sort is approximately:
   - **___× faster** than Bubble Sort
   - **___× faster** than Insertion Sort

2. **Quadratic Growth**: O(n²) algorithms show exponential time increase:
   - From 1,000 to 10,000 records (10× data): Time increased by approximately **___×**
   - From 10,000 to 100,000 records (10× data): Time increased by approximately **___×**

3. **Linear-Logarithmic Efficiency**: Merge Sort maintains near-linear scaling:
   - From 1,000 to 10,000 records (10× data): Time increased by approximately **___×**
   - From 10,000 to 100,000 records (10× data): Time increased by approximately **___×**

4. **String vs Integer Comparison**: 
   - Sorting by FirstName/LastName is *[faster/slower/similar]* compared to sorting by ID
   - This is because *[add explanation]*

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 17 or higher
- Any Java IDE or command line

### Repository Structure

```
DAALab-AY225-MANGUBAT/PRELIM-LAB-EXAM/
├── src/
│   └── SortingStressTest.java     # Main application file
├── data/
│   └── generated_data.csv         # Dataset (100,000 records)
└── README.md                      # This file
```

### Installation & Setup

1. **Clone the Repository**

2. **Compile the Program**

3. **Run the Application**

## 📖 How to Use

### Step 1: Load Data
1. Click the **"Load CSV File"** button
2. Navigate to the `data/` folder and select `generated_data.csv`
3. Wait for the loading confirmation message

### Step 2: Configure Sorting Parameters
1. **Select Column**: Choose ID, FirstName, or LastName from the dropdown menu
2. **Select Row Count**: Choose 1,000 / 10,000 / 50,000 / 100,000 from the dropdown

### Step 3: Execute Sorting Algorithm
1. Click one of the algorithm buttons:
   - **Bubble Sort** (Blue) - O(n²) exchange sort
   - **Insertion Sort** (Green) - O(n²) insertion sort  
   - **Merge Sort** (Pink) - O(n log n) divide-and-conquer

2. For large datasets with O(n²) algorithms, you may receive a performance warning

### Step 4: View Results
- **Left Panel**: Preview table shows the first 10 sorted records
- **Right Panel**: Detailed performance metrics including:
  - Load time
  - Sort time
  - Total execution time
  - Algorithm complexity
- **Status Bar**: Quick summary of the operation

## 🔬 Algorithm Details

### Bubble Sort
- **Time Complexity**: O(n²)
- **Space Complexity**: O(1)
- **Method**: Repeatedly swaps adjacent elements if they are in the wrong order
- **Best Use Case**: Educational purposes, very small datasets (<100 records)
- **Performance**: Slowest of the three algorithms

### Insertion Sort
- **Time Complexity**: O(n²)
- **Space Complexity**: O(1)
- **Method**: Builds the sorted array one element at a time by inserting elements into their correct position
- **Best Use Case**: Small datasets (<1,000 records), nearly-sorted data
- **Performance**: Faster than Bubble Sort in practice

### Merge Sort
- **Time Complexity**: O(n log n)
- **Space Complexity**: O(n)
- **Method**: Divide-and-conquer approach that recursively splits the array and merges sorted subarrays
- **Best Use Case**: Large datasets (>10,000 records), when consistent performance is required
- **Performance**: Consistently the fastest, industry standard for general-purpose sorting

## 📊 Dataset Information

- **Filename**: `generated_data.csv`
- **Total Records**: 100,000
- **Format**: CSV (Comma-Separated Values)
- **Columns**:
  - `ID`: 7-digit integer (range: 1000000-9999999)
  - `FirstName`: String representing first name
  - `LastName`: String representing last name

### Sample Data
```csv
ID,FirstName,LastName
5930868,Bruce,Shiro
5402847,John,Reeves
6697032,Austin,Dickerson
6420665,Peggy,Payne
```

## ⚠️ Performance Notes

### Expected Execution Times (Reference)

*These are approximate guidelines based on typical modern hardware:*

| Algorithm      | 1,000 Records | 10,000 Records  | 100,000 Records |
|----------------|---------------|-----------------|-----------------|
| Bubble Sort    | ~5-15 ms      | ~400-800 ms     | ~40-80 seconds  |
| Insertion Sort | ~2-5 ms       | ~50-100 ms      | ~5-10 seconds   |
| Merge Sort     | ~0.5-1.5 ms   | ~3-6 ms         | ~30-60 ms       |

*Note: Actual times will vary based on your computer's CPU, RAM, and current system load.*

### Warnings

- **Bubble Sort on 100,000 records**: May take 1-2 minutes or longer. Consider using smaller datasets for testing.
- **Insertion Sort on 100,000 records**: Will take several seconds. More practical than Bubble Sort but still slow.
- **Merge Sort**: Handles all dataset sizes efficiently.

## 🎓 Educational Value

This project demonstrates:

1. **Big-O Notation in Practice**: See the real-world impact of algorithmic complexity
2. **Algorithm Selection**: Understand why choosing the right algorithm matters
3. **Data Structure Manipulation**: Working with structured CSV data in Java
4. **Performance Analysis**: Measuring and comparing execution times
5. **Software Engineering**: Clean code organization, GUI design, and user experience

## 🛠️ Technical Implementation

### Technologies Used
- **Language**: Java 17+
- **GUI Framework**: Java Swing
- **Data Structure**: ArrayList<Person>
- **File I/O**: BufferedReader for efficient CSV parsing
- **Concurrency**: SwingWorker for non-blocking background tasks

### Key Design Decisions

1. **No Built-in Sorting**: All algorithms implemented from scratch as required
2. **Generic Comparison**: Single comparison method handles all three column types
3. **User-Friendly Interface**: Modern GUI with progress indicators and warnings
4. **Memory Efficiency**: Only loads the required subset of data for testing
5. **Error Handling**: Graceful handling of file errors and user mistakes

## 📝 Implementation Notes

### Sorting Algorithm Implementation

All three sorting algorithms are implemented without using Java's built-in sorting methods (e.g., `Collections.sort()`, `Arrays.sort()`). Each algorithm:

- Operates on a `List<Person>` data structure
- Uses a custom `compare()` method for multi-column support
- Maintains the original dataset (sorts a copy)
- Provides accurate timing measurements

### Multi-Column Sorting Logic

The program uses a single comparison method that switches based on the selected column:

```java
- ID sorting: Integer.compare(p1.id, p2.id)
- FirstName sorting: p1.firstName.compareToIgnoreCase(p2.firstName)
- LastName sorting: p1.lastName.compareToIgnoreCase(p2.lastName)
```

This approach ensures:
- Consistent sorting behavior across algorithms
- Case-insensitive string comparisons
- Easy maintenance and testing

## 🎯 Project Requirements Checklist

- [x] Implemented Bubble Sort from scratch
- [x] Implemented Insertion Sort from scratch
- [x] Implemented Merge Sort from scratch
- [x] CSV file parsing (100,000 records)
- [x] Column selection (ID, FirstName, LastName)
- [x] Scalability testing (1,000 to 100,000 records)
- [x] Separate timing for file loading vs. sorting
- [x] Performance warning system
- [x] Display first 10 sorted records
- [x] Clean repository structure
- [x] Comprehensive documentation

## 👤 Author

**Name**: Angel Julliane I. Mangubat 

**Student ID**: 24-2246-776

**Degree Program**: Bachelor of Science in Computer Science with Specialization in Data Science 

**Course & Section**: BSCS 2206L Design, Analysis, and Algorithm Technologies - Lab (9407-AY225)

**Instructor**: Engr. Val Patrick F. Fabregas, MTA

**Date**: February 4, 2026

## 🙏 Acknowledgments

- Dataset generated specifically for this educational project
- Sorting algorithms based on classical computer science implementations
- GUI design inspired by modern software development best practices
- Special thanks to Sir Val for guidance and project requirements

## 📞 Contact

For questions or feedback regarding this project:
- **Email**: angeljullianemangubat@gmail.com
- **GitHub**: Lliane03

---

## 📄 License

This project is submitted as academic work for educational purposes only.

---

## 🔗 Repository

**GitHub Repository**: (https://github.com/Lliane03/DAALab-AY225-MANGUBAT.git)

---

*This project demonstrates the fundamental importance of algorithm selection in computer science and software engineering. The dramatic performance differences between O(n²) and O(n log n) algorithms illustrate why understanding algorithmic complexity is crucial for building efficient software systems.*

---

**Last Updated**: February 4, 2026

**Version**: 1.0