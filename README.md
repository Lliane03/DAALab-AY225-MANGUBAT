# 🗞️ Faded Without Notice — Customer Churn Dashboard

> *A Chronicle of Departures & Retained Souls*
>
> Single-file · Browser-based · No build tools required

---

## ✦ Requirements

| What | Details |
|------|---------|
| **Browser** | Any modern browser — Chrome, Firefox, Edge, Safari 90+ |
| **Internet** | Required on first load only *(for Google Fonts + Chart.js CDN)* |
| **Runtime** | None — no Node.js, Python, or package manager needed |

---

## ✦ How to Run

### Option 1 — Open Directly *(Simplest)*

1. Save the file as **`index.html`**
2. Double-click to open in your browser

> All data is embedded. It works immediately.

---

### Option 2 — Python Local Server *(Recommended)*

```bash
# Python 3
python -m http.server 8080

# Then open:
# http://localhost:8080
```

---

### Option 3 — Node.js (npx)

```bash
npx serve .
# Open the URL shown in your terminal
```

---

### Option 4 — VS Code Live Server

Install the **Live Server** extension → right-click `index.html` → **Open with Live Server**

---

## ✦ Navigating the Dashboard

| Tab | Description |
|-----|-------------|
| **The Registry** | Filterable & sortable table of all 20 customer records + KPI summary cards |
| **Illustrations** | Bar chart (top spenders), doughnut (churn by subscription), scatter (tenure vs spend) |
| **The Ledger** | Descriptive statistics, Pearson correlations, and the interactive churn predictor |
| **Dispatches** | Six narrative insight cards auto-generated from the dataset |

---

## ✦ Using the Churn Predictor

Located in **The Ledger** tab under *Linear Regression Model*:

1. Enter a **Tenure** value in months — e.g. `24`
2. Enter a **Support Calls** count — e.g. `6`
3. **Churn Risk %** updates instantly

> 🔵 Above 50% = high risk &nbsp;|&nbsp; 🟡 Below 50% = lower risk

---

## ✦ Modifying the Dataset

Open `index.html` in any text editor. Find the `CUSTOMERS` array inside the `<script>` tag and edit or extend it. All charts, stats, and insights update automatically.

```javascript
const CUSTOMERS = [
  {
    id: 'C001',
    age: 43,
    gender: 'Male',           // Male | Female
    tenure: 42,               // months as a customer
    usageFreq: 8,
    supportCalls: 8,
    payDelay: 25,             // days late on payment
    subscription: 'Standard', // Basic | Standard | Premium
    contract: 'Annual',       // Monthly | Quarterly | Annual
    totalSpend: 136.99,
    lastInteract: 23,         // days since last interaction
    churn: 1                  // 1 = Churned, 0 = Retained
  },
  // ... add more records
];
```

---

## ✦ External Dependencies *(CDN — no install needed)*

| Library | Version | Purpose |
|---------|---------|---------|
| Chart.js | 4.4.0 | Bar, doughnut, and scatter charts |
| Google Fonts | — | Playfair Display, Special Elite, IM Fell English, Courier Prime |

---

## ✦ Browser Compatibility

| Browser | Status |
|---------|--------|
| Chrome 90+ | ✅ Full support |
| Firefox 88+ | ✅ Full support |
| Edge 90+ | ✅ Full support |
| Safari 14+ | ✅ Full support |

---

*Faded Without Notice · Customer Intelligence Report · AY 2024–25*