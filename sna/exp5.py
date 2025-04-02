import networkx as nx
from sklearn.metrics import normalized_mutual_info_score
import matplotlib.pyplot as plt

# Load dataset: Karate Club graph
G = nx.karate_club_graph()

# Detect communities using Louvain
communities = nx.community.louvain_communities(G, seed=123)

# Map nodes to community IDs
partition = {}
for i, community in enumerate(communities):
    for node in community:
        partition[node] = i

# Quality measure 1: Modularity
modularity = nx.community.modularity(G, communities)

# Accuracy: NMI (Normalized Mutual Information)
# Ground truth: club membership
ground_truth = [G.nodes[node]['club'] == 'Mr. Hi' for node in G.nodes()]
predicted = [partition[node] for node in G.nodes()]
nmi_score = normalized_mutual_info_score(ground_truth, predicted)

# Quality measure 2: ANUI (Average Normalized Utility Index)
total_edges = G.number_of_edges()
internal_edges = sum(G.subgraph(c).number_of_edges() for c in communities)
coverage = internal_edges / total_edges
normalized_coverage = (coverage - 0.5) * 2
anui = (modularity + nmi_score + normalized_coverage) / 3

# Print results
print("\nCOMMUNITY DETECTION ANALYSIS")
print("----------------------------")
print(f"Dataset: Zachary's Karate Club")
print(f"Algorithm: Louvain")
print(f"Communities found: {len(communities)}")
print(f"Community sizes: {[len(c) for c in communities]}")
print("\nQUALITY MEASURES:")
print(f"- Modularity: {modularity:.4f}")
print(f"- ANUI: {anui:.4f}")
print("\nACCURACY:")
print(f"- NMI: {nmi_score:.4f}")

# Plot the graph with community colors
plt.figure(figsize=(8, 6))
pos = nx.spring_layout(G, seed=123)  # Layout for consistent visualization
colors = [partition[node] for node in G.nodes()]
nx.draw(G, pos, node_color=colors, with_labels=True, cmap=plt.cm.rainbow)
plt.title(f"Saptarshi Adhikari\nMod: {modularity:.3f}, ANUI: {anui:.3f}, NMI: {nmi_score:.3f}")
plt.tight_layout()
plt.show()
