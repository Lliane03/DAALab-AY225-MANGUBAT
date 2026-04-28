# Faded Without Notice — Customer Churn Dashboard (Final Project)

**Collaborative Data Analysis Project**

---

## Course Information

| Field | Details |
|-------|---------|
| **Course** | BSCS 2206: Design, Analysis & Algorithm Technologies |
| **Sections** | 9406 (Lec) & 9407 (Lab) |
| **Instructor** | Mr. Val Patrick Fabregas |
| **Year Level & Program** | 2nd Year Bachelor of Science in Computer Science |
| **Semester & Academic Year** |  Second Semester A.Y. 2025-2026 |

---

## Contributors

| Role | Name |
|------|------|
| **Repository Owner** | Angel Julliane I. Mangubat |
| **Collaborator** | Louise Jeanne T. de Dios |

---

## Project Overview

### 🗞️ Faded Without Notice — Customer Churn Dashboard

*A Chronicle of Customer Departures & Retention*

**Faded Without Notice** is an interactive, browser-based dashboard for analyzing **customer churn behavior**.

It processes a dataset (`customer_churn.csv`) and generates:

* 📊 Dynamic visualizations
* 📉 Statistical analysis
* 🧠 Automated insights
* 🔮 A churn prediction model

**Data Source:** [Kaggle Customer Churn Dataset](https://www.kaggle.com/datasets/somiel20/customer-churn-data)

---

## Project Objective

This repository documents a **collaborative final project** focused on **analyzing large datasets through the application of design, analysis, and algorithmic techniques**.

**Goal:** Develop practical skills in data analysis, algorithm design, and implementation through hands-on collaborative activities.

---

## Project Structure

```
DAALab-AY225-MANGUBAT/
├── README.md                           # Project documentation (this file)
├── customer_churn.csv                  # Dataset for dashboard analysis
├── index.html                          # Customer Churn Dashboard (main deliverable)
│
└── MANGUBAT-PRELIM-MIDTERM-FILES       # Previos Lab Work of Angel
```

---

## Files Description

### Core Deliverable
- **`index.html`** — Interactive customer churn analysis dashboard with visualizations, statistical analysis, and predictive modeling
- **`customer_churn.csv`** — Dataset containing customer information for churn analysis

---

## ✦ Requirements

| What         | Details                                            |
| ------------ | -------------------------------------------------- |
| **Browser**  | Chrome, Firefox, Edge, Safari (modern versions)    |
| **Internet** | Required initially *(CDN: Chart.js, Google Fonts)* |
| **Runtime**  | None — runs fully in the browser                   |
| **Dataset**  | `customer_churn.csv` *(required)*                  |

---

## ✦ How to Run

### Setup — Clone the Repository

1. Clone the repository with git:
```bash
git clone https://github.com/Lliane03/DAALab-AY225-MANGUBAT.git
```
2. Navigate into the project directory
3. Choose any of the 3 options to run the this project
---

### Option 1 — Open Directly *(with manual CSV upload)*

1. Save the file as **`index.html`**
2. Open it in your browser
3. If prompted, **upload `customer_churn.csv`**

---

### Option 2 — VS Code Live Server

1. Open the project folder in VS Code
2. Install the Live Server extension
3. Right-click index.html
4. Click “Open with Live Server”

The dashboard will launch in your browser with automatic reloading

---

### Option 3 — GitHub Pages *(Online Access)*

1. Go to the repository:
    https://github.com/Lliane03/DAALab-AY225-MANGUBAT
2. Enable GitHub Pages:
    * Go to Settings → Pages
    * Under Source, select:
        * Branch: main
        * Folder: /root
3. After saving, access your live site at:

https://lliane03.github.io/DAALab-AY225-MANGUBAT/

The dashboard will run directly in the browser without installation. 

---

## ✦ Key Features

### 📂 CSV-Based Data Loading

* Automatically loads `customer_churn.csv`
* Falls back to **manual upload** if file is missing
* Uses **PapaParse** for parsing

---

### 📋 The Registry (Main Table)

* Search, filter, and sort customers
* KPI summary cards (churn rate, spend, tenure)
* Dynamic row limits and ordering

---

### 📊 Illustrations (Charts)

* **Bar Chart** — Top spenders
* **Doughnut Chart** — Churn by subscription
* **Scatter Plot** — Tenure vs Spend with trend line

> Charts update based on visible (filtered) data

---

### 📈 The Ledger (Analysis)

* Descriptive statistics (mean, median, variance, etc.)
* Pearson correlation analysis
* Heatmap-style correlation visualization

---

### 🔮 Churn Predictor

Located under **Linear Regression Model**

* Inputs:

  * Tenure (months)
  * Support Calls
* Output:

  * **Churn Risk (%)**

> 🔴 Above 50% → High risk
> 🟢 Below 50% → Lower risk

---

### 🧠 Dispatches (Insights)

* Auto-generated narrative insights
* Based on **full dataset (not filtered view)**
* Covers:

  * Tenure behavior
  * Support trends
  * Contract impact
  * Payment delays
  * Spending patterns

---

## ✦ Dataset Format

The dashboard expects a CSV with these columns:

```text
CustomerID, Age, Gender, Tenure, Usage Frequency,
Support Calls, Payment Delay, Subscription Type,
Contract Length, Total Spend, Last Interaction, Churn
```

---

## ✦ Modifying the Dataset

Replace or edit the file:

```
customer_churn.csv
```

* Ensure column names remain consistent
* The dashboard updates automatically after reload

---

## ✦ External Dependencies *(CDN)*

| Library      | Version | Purpose             |
| ------------ | ------- | ------------------- |
| Chart.js     | 4.4.0   | Data visualizations |
| PapaParse    | 5.4.1   | CSV parsing         |
| Google Fonts | —       | Typography          |

---

## ✦ Notes

* Works entirely on the **client-side**
* No backend required
* Handles large datasets efficiently using streaming + sampling

---

## Acknowledgments

**Project Supervision:**
- Mr. Val Patrick Fabregas, Instructor, BSCS 2206

**Data Source:**
- [Kaggle Customer Churn Dataset](https://www.kaggle.com/datasets/somiel20/customer-churn-data)

**Libraries & Tools:**
- Chart.js for data visualization
- PapaParse for CSV processing
- Google Fonts for typography

---

## License

This project is submitted as coursework for BSCS 2206: Design, Analysis & Algorithm Technologies at University of Perpetual Help System DALTA - Molino Campus.

---

**Last Updated:** April 28, 2025  
**Repository:** Lliane03/DAALab-AY225-MANGUBAT



