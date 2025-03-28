import networkx as nx
import matplotlib.pyplot as plt
from sklearn.metrics import normalized_mutual_info_score
import numpy as np

# Load the Karate Club graph
G = nx.karate_club_graph()
print(f"Graph loaded: {G.number_of_nodes()} nodes, {G.number_of_edges()} edges")
print(G.nodes(data=True))

# Community Detection using Louvain method
communities = nx.community.louvain_communities(G, seed=123)
print(f"Number of communities detected: {len(communities)}")
print(f"Community sizes: {[len(c) for c in communities]}")

# Create a partition dictionary (node_id -> community_id)
partition = {}
for i, community in enumerate(communities):
    for node in community:
        partition[node] = i

# Quality Measure 1: Modularity
# Higher modularity values indicate better community structures (range: -0.5 to 1.0)
modularity = nx.community.modularity(G, communities)
print("\n----- QUALITY MEASURES -----")
print(f"Modularity: {modularity:.4f}")
print(f"Interpretation: {'Good' if modularity > 0.3 else 'Poor'} community structure")

# Quality Measure 2: Normalized Mutual Information (NMI)
# Ground truth for Zachary's Karate Club is the actual split that happened
ground_truth = [G.nodes[node]['club'] == 'Mr. Hi' for node in G.nodes()]
predicted = [partition[node] for node in G.nodes()]
nmi_score = normalized_mutual_info_score(ground_truth, predicted)
print(f"Normalized Mutual Information (NMI): {nmi_score:.4f}")
print(f"Interpretation: NMI ranges from 0 (no match) to 1 (perfect match) with ground truth")

# Quality Measure 3: Average Normalized Utility Index (ANUI)
# Calculate coverage (fraction of edges within communities)
total_edges = G.number_of_edges()
internal_edges = 0
for comm in communities:
    subgraph = G.subgraph(comm)
    internal_edges += subgraph.number_of_edges()
coverage = internal_edges / total_edges
normalized_coverage = (coverage - 0.5) * 2  # Normalize to similar scale as modularity

# Calculate ANUI as the weighted average of normalized quality measures
anui = (modularity + nmi_score + normalized_coverage) / 3
print(f"Coverage: {coverage:.4f}")
print(f"Average Normalized Utility Index (ANUI): {anui:.4f}")
print(f"Interpretation: ANUI combines modularity, NMI, and coverage into a single metric")

# Compare with another common algorithm for validation
girvan_newman_communities = list(nx.community.girvan_newman(G))[1]  # Get communities at level 1
gn_partition = {}
for i, community in enumerate(girvan_newman_communities):
    for node in community:
        gn_partition[node] = i

gn_predicted = [gn_partition[node] for node in G.nodes()]
gn_nmi = normalized_mutual_info_score(ground_truth, gn_predicted)
gn_modularity = nx.community.modularity(G, girvan_newman_communities)

# Calculate GN coverage and ANUI
gn_internal_edges = 0
for comm in girvan_newman_communities:
    subgraph = G.subgraph(comm)
    gn_internal_edges += subgraph.number_of_edges()
gn_coverage = gn_internal_edges / total_edges
gn_normalized_coverage = (gn_coverage - 0.5) * 2
gn_anui = (gn_modularity + gn_nmi + gn_normalized_coverage) / 3

print("\n----- ALGORITHM COMPARISON -----")
print(f"Louvain - Modularity: {modularity:.4f}, NMI: {nmi_score:.4f}, ANUI: {anui:.4f}")
print(f"Girvan-Newman - Modularity: {gn_modularity:.4f}, NMI: {gn_nmi:.4f}, ANUI: {gn_anui:.4f}")
print(f"Average NMI across algorithms: {(nmi_score + gn_nmi) / 2:.4f}")
print(f"Average ANUI across algorithms: {(anui + gn_anui) / 2:.4f}")

# Visualization
plt.figure(figsize=(10, 8))
plt.subplot(121)
pos = nx.spring_layout(G, seed=123)
colors = [partition[node] for node in G.nodes()]
nx.draw(G, pos, node_color=colors, with_labels=True, cmap=plt.cm.rainbow)
plt.title(f"Louvain (Mod: {modularity:.3f}, ANUI: {anui:.3f})")

plt.subplot(122)
gn_colors = [gn_partition[node] for node in G.nodes()]
nx.draw(G, pos, node_color=gn_colors, with_labels=True, cmap=plt.cm.rainbow)
plt.title(f"Girvan-Newman (Mod: {gn_modularity:.3f}, ANUI: {gn_anui:.3f})")

plt.tight_layout()
plt.show()

# Export to Gephi (GEXF format)
nx.write_gexf(G, "karate_graph.gexf")
print("Graph saved for Gephi visualization.")
