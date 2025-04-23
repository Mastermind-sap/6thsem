import networkx as nx
import matplotlib.pyplot as plt

G=nx.karate_club_graph()

communities=nx.community.louvain_communities(G)

print("Detected communities:",communities)
communityM={}
for i,comm in enumerate(communities):
    for node in comm:
        communityM[node]=i
colors=[communityM[node] for node in G.nodes()]

nx.draw(
    G,
    node_color=colors,
    with_labels=True,
)
plt.show()