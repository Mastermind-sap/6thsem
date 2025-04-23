import networkx as nx
import matplotlib.pyplot as plt

def clique_percolation(G, k):
    cliques = [set(c) for c in nx.find_cliques(G) if len(c) >= k]
    clique_graph = nx.Graph()
    for i, c1 in enumerate(cliques):
        for j, c2 in enumerate(cliques):
            if i < j and len(c1 & c2) >= k - 1:
                clique_graph.add_edge(i, j)
    communities = []
    for comp in nx.connected_components(clique_graph):
        nodes = set()
        for idx in comp:
            nodes |= cliques[idx]
        communities.append(nodes)
    return communities

G = nx.karate_club_graph()
pos = nx.spring_layout(G, seed=42)
communities = clique_percolation(G, k=4)

color_map = {}

for i, comm in enumerate(communities):
    for node in comm:
        color_map[node] = i

default_color = -1
for node in G.nodes():
    if node not in color_map:
        color_map[node] = default_color
        
plt.figure(figsize=(8, 6))
node_colors = [color_map[n] for n in G.nodes()]
nx.draw(G, pos, with_labels=True, node_color=node_colors, edge_color="blue")
plt.title("Clique Percolation Communities (k=4)")
plt.show()
