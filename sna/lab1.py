# import matplotlib.pyplot as plt
# import networkx as nx

# G = nx.karate_club_graph()
# print("Node Degree")
# for v in G:
#     print(f"{v:4} {G.degree(v):6}")

# nx.draw_circular(G, with_labels=True)
# plt.show()
import matplotlib.pyplot as plt
import networkx as nx

# Load the Karate Club Network
G = nx.karate_club_graph()

# Calculate Centrality Measures
degree_centrality = nx.degree_centrality(G)
betweenness_centrality = nx.betweenness_centrality(G)
eigenvector_centrality = nx.eigenvector_centrality(G)

# Print Centrality Measures
print("Node Centrality Measures")
print(f"{'Node':<6}{'Degree':<10}{'Betweenness':<15}{'Eigenvector':<15}")
for v in G.nodes():
    print(f"{v:<6}{degree_centrality[v]:<10.4f}{betweenness_centrality[v]:<15.4f}{eigenvector_centrality[v]:<15.4f}")

# Visualization - Circular Layout
plt.figure(figsize=(12, 8))

# Node sizes based on Degree Centrality
node_size = [degree_centrality[node] * 2000 for node in G.nodes()]
node_color = list(betweenness_centrality.values())  # Node color based on Betweenness Centrality

# Draw the graph with a circular layout
pos = nx.circular_layout(G)  # Get the positions for circular layout
nodes = nx.draw_networkx_nodes(
    G, pos, node_size=node_size, node_color=node_color, cmap=plt.cm.Blues
)
nx.draw_networkx_edges(G, pos)
nx.draw_networkx_labels(G, pos, font_color="white", font_weight="bold")

# Add colorbar linked to the node colors
ax = plt.gca()  # Get the current axis
sm = plt.cm.ScalarMappable(cmap=plt.cm.Blues, norm=plt.Normalize(vmin=min(node_color), vmax=max(node_color)))
sm.set_array([])
cbar = plt.colorbar(sm, ax=ax, orientation="vertical", pad=0.1)
cbar.set_label("Betweenness Centrality")

# Add title
plt.title("Karate Club Network - Centrality Measures Visualization", fontsize=16)
plt.show()
