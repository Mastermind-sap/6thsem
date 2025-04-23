import networkx as nx
import matplotlib.pyplot as plt

G=nx.read_gml("./dataset/football.gml")

cent={
    "Degree":(nx.degree_centrality(G),plt.cm.cool),
    "Betweenness":(nx.betweenness_centrality(G),plt.cm.summer),
    "Eigenvector":(nx.eigenvector_centrality(G),plt.cm.winter)
}

print(f"{'Node':<10}|{'Degree':<25}|{'Betweenness':<25}|{'Eigenvector':<25}");

for node in G.nodes():
    degree=cent["Degree"][0][node]
    between=cent["Betweenness"][0][node]
    eigenvector=cent["Eigenvector"][0][node]
    print(f"{node:<10} | {degree:<25} | {between:<25} | {eigenvector:<25}")

print()


def draw(ax,title,cent,cmap):
    pos=nx.spring_layout(G,seed=42)
    nColor=[cent[i] for i in G.nodes()]
    nSize=[cent[i]*5000 for i in G.nodes()]
    nx.draw_networkx_nodes(G,pos,node_color=nColor,node_size=nSize,cmap=cmap,ax=ax)
    nx.draw_networkx_edges(G,pos,ax=ax)
    # nx.draw_networkx_labels(G,ax=ax,pos=pos)
    ax.set_title(title)

fig,ax=plt.subplots(1,3)

for ax,(title,(cent,cmap)) in zip(ax,cent.items()):
    draw(ax,title,cent,cmap)

fig.suptitle("Sap")
plt.show()