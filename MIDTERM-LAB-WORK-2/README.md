# MidtermLab2 – Cavite Network Node Map & Shortest Path Finder

## Overview
This program visualizes a network of locations in Cavite and finds the
shortest path between any two nodes based on **Distance**, **Time**, or
**Fuel** consumption using **Dijkstra's Algorithm**.

---

## Files
| File | Description |
|---|---|
| `MidtermLab2-Mangubat.py` | CLI-based Python program |
| `MidtermLab2-Mangubat.html` | Interactive browser-based visualization |
| `README.md` | This file |

---

## Nodes in the Network
IMUS, BACOOR, DASMA, KAWIT, INDANG, SILANG, GENTRI, NOVELETA
![Node Map](image.png)

---

## Algorithm Used – Dijkstra's Algorithm

Dijkstra's algorithm finds the shortest path from a source node to a
destination node in a weighted graph by:

1. Starting at the source with cost 0.
2. Using a **min-heap (priority queue)** to always expand the lowest-cost
   unvisited node.
3. Relaxing all neighbors – if a cheaper route to a neighbor is found,
   it replaces the old route.
4. Stopping once the destination node is popped from the heap.

**Time Complexity:** O((V + E) log V) where V = nodes, E = edges.

The graph is treated as **undirected** – every edge can be traversed in
both directions with the same cost.

---

## How to Run (Python CLI)

```bash
python MidtermLab2-Student.py
```

Follow the prompts:
1. Enter a **start** node (e.g., `IMUS`)
2. Enter an **end** node (e.g., `SILANG`)
3. Choose criterion: `1` Distance · `2` Time · `3` Fuel

### Sample Output
```
Shortest Path (distance) from IMUS to SILANG:
  Path   : IMUS → BACOOR → SILANG
  Distance: 20 km
  Time    : 40 mins
  Fuel    : 2.5 Liters
```

---

## How to Run (Browser / HTML)

Open `MidtermLab2-Student.html` in any modern browser.  
- The interactive **node map** is drawn with Canvas/SVG.  
- Select source, destination, and criterion, then click **Find Path**.  
- The shortest path is highlighted on the map with full stats.

---

## Challenges Faced

1. **Graph directionality** – The original table lists directed edges, but
   treating the graph as undirected gives more realistic routing. Both modes
   are supported in the HTML version via a toggle.

2. **Multi-criteria optimization** – Dijkstra natively handles one weight.
   Switching criteria rebuilds the priority key dynamically so all three
   metrics can be optimized independently.

3. **Tie-breaking** – When two paths have equal primary cost, the algorithm
   picks whichever is expanded first from the heap. Secondary sorting by
   node name ensures deterministic, reproducible results.

---

## References
- Dijkstra, E.W. (1959). *A note on two problems in connexion with graphs.*
- Cormen et al., *Introduction to Algorithms*, Chapter 24.