import networkx as nx
import matplotlib.pyplot as plt

G = nx.karate_club_graph()
communities = nx.community.louvain_communities(
    G,
    weight='weight',
    resolution=1,
    threshold=1e-07,
    max_level=None,
    seed=123
)
print("Detected communities:", communities)


community_map = {}

for i, community in enumerate(communities):
    for node in community:
        community_map[node] = i

colors = [community_map[node] for node in G.nodes()]
nx.draw(G, node_color=colors, with_labels=True, cmap=plt.cm.rainbow)
plt.show()

nx.write_gexf(G, "petersen_graph_louvain.gexf")