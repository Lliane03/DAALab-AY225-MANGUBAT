# Sorting GUI - PRELIM-LAB-WORK-2

A Java GUI application that implements and compares various sorting algorithms (Bubble Sort, Insertion Sort, and Merge Sort).

## Requirements

- Java 8 or higher (JDK installed)
- A `.txt` file containing numeric data for sorting

## How to Run

### 1. Compile the Code

```bash
javac SortingGUI.java
```

### 2. Run the Application

```bash
java SortingGUI
```

### 3. Using the Application

1. **Upload Dataset**: Click the "Upload Dataset (.txt)" button to select a text file containing numbers (one number per line or space-separated)
2. **Select Sort Algorithm**: Choose from:
   - Bubble Sort
   - Insertion Sort
   - Merge Sort
3. **Choose Order**: Select either "Ascending" or "Descending" sort order
4. **View Results**: The sorted data will be displayed in the text area along with performance metrics
5. **Refresh**: Click the "Refresh" button to clear and start over

## Example Dataset Format

Create a `dataset.txt` file with numbers like:

```
5 2 8 1 9 3 7 4 6
```

or one number per line:

```
5
2
8
1
9
```

## Features

- Visual GUI interface built with Swing
- Support for multiple sorting algorithms
- Ascending/Descending sort order selection
- Performance metrics display
- File upload functionality for custom datasets
