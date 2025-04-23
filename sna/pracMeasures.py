import networkx as nx, matplotlib.pyplot as plt
from sklearn.metrics import normalized_mutual_info_score

G=nx.karate_club_graph()

communities=nx.community.louvain_communities(G)

comMap={}

for i,community in enumerate(communities):
    for node in community:
        comMap[node]=i

modularity=nx.community.modularity(G,communities)

ground_truth=[G.nodes[node]['club']=='Mr. Hi' for node in G.nodes()]
predicted=[comMap[node] for node in G.nodes()]

nmiScore=normalized_mutual_info_score(ground_truth,predicted)

print(modularity)
print(nmiScore)