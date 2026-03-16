"""
MidtermLab2 - Network Node Map & Shortest Path Finder
Uses Dijkstra's Algorithm for shortest path calculation.
"""

import heapq

# ── Graph Data ──────────────────────────────────────────────────────────────
edges = [
    ("IMUS",     "BACOOR",   10, 15, 1.2),
    ("BACOOR",   "DASMA",    12, 25, 1.5),
    ("DASMA",    "KAWIT",    12, 25, 1.5),
    ("KAWIT",    "INDANG",   12, 25, 1.2),
    ("INDANG",   "SILANG",   14, 25, 1.5),
    ("SILANG",   "GENTRI",   10, 25, 1.3),
    ("GENTRI",   "NOVELETA", 10, 25, 1.5),
    ("NOVELETA", "IMUS",     10, 15, 1.2),
    ("BACOOR",   "SILANG",   10, 25, 1.3),
    ("DASMA",    "SILANG",   12, 25, 1.5),
    ("SILANG",   "BACOOR",   10, 25, 1.3),
    ("NOVELETA", "BACOOR",   10, 15, 1.2),
    ("SILANG",   "KAWIT",    14, 25, 1.2),
    ("IMUS",     "NOVELETA", 10, 15, 1.2),
]

# Build adjacency list: graph[node] = list of (neighbor, dist, time, fuel)
graph = {}
nodes = set()
for frm, to, d, t, f in edges:
    nodes.add(frm); nodes.add(to)
    graph.setdefault(frm, []).append((to, d, t, f))
    graph.setdefault(to,  []).append((frm, d, t, f))   # undirected


# ── Dijkstra ────────────────────────────────────────────────────────────────
CRITERIA = {"distance": 0, "time": 1, "fuel": 2}

def dijkstra(start: str, end: str, criterion: str = "distance"):
    """Return (cost, path, totals) where totals = (dist, time, fuel)."""
    idx = CRITERIA[criterion]
    weights = [0, 0, 0]           # running totals [dist, time, fuel]

    # heap: (primary_cost, node, path_so_far, [dist, time, fuel])
    heap = [(0, start, [start], [0, 0, 0])]
    visited = {}

    while heap:
        cost, node, path, totals = heapq.heappop(heap)
        if node in visited:
            continue
        visited[node] = cost
        if node == end:
            return cost, path, totals
        for neighbor, d, t, f in graph.get(node, []):
            if neighbor not in visited:
                vals = [d, t, f]
                new_cost   = cost + vals[idx]
                new_totals = [totals[0]+d, totals[1]+t, totals[2]+f]
                heapq.heappush(heap, (new_cost, neighbor, path+[neighbor], new_totals))

    return float("inf"), [], [0, 0, 0]   # no path found


# ── CLI ─────────────────────────────────────────────────────────────────────
def print_nodes():
    print("\nAvailable nodes:", ", ".join(sorted(nodes)))

def run():
    print("=" * 55)
    print("   CAVITE NETWORK – Shortest Path Finder")
    print("=" * 55)
    print_nodes()

    start = input("\nEnter START node: ").strip().upper()
    end   = input("Enter END node  : ").strip().upper()

    if start not in nodes or end not in nodes:
        print("❌  One or both nodes not found. Please check spelling.")
        return

    print("\nCriteria: (1) Distance  (2) Time  (3) Fuel")
    choice = input("Choose criterion [1/2/3]: ").strip()
    criterion = {"1": "distance", "2": "time", "3": "fuel"}.get(choice, "distance")

    cost, path, totals = dijkstra(start, end, criterion)

    print("\n" + "─" * 55)
    if not path:
        print(f"No path found from {start} to {end}.")
    else:
        print(f"Shortest Path ({criterion}) from {start} to {end}:")
        print("  Path   :", " → ".join(path))
        print(f"  Distance: {totals[0]} km")
        print(f"  Time    : {totals[1]} mins")
        print(f"  Fuel    : {totals[2]:.1f} Liters")
    print("─" * 55)

if __name__ == "__main__":
    run()
