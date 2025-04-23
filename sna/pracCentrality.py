import networkx as nx
import matplotlib.pyplot as plt
# import matplotlib.colors as mcolors

G=nx.karate_club_graph()

centrality={
    "Degree": (nx.degree_centrality(G), plt.cm.cool),
    "Betweenness": (nx.betweenness_centrality(G, normalized=True), plt.cm.winter),
    "Eigenvector": (nx.eigenvector_centrality(G), plt.cm.summer)
}

print(f"{"Node":<10} | {'Degree':<25} | {'Betweenness':<25} | {'Eigenvector':<25}")
print("-" * 85)
for node in G.nodes():
    degree=centrality['Degree'][0][node]
    between=centrality["Betweenness"][0][node]
    eigenvector=centrality["Eigenvector"][0][node]
    print(f"{node:<10} | {degree:<25} | {between:<25} | {eigenvector:<25}")

print()

def draw(axs,cent,title,cmap):
    nColor=[cent[n] for n in G.nodes()]
    nSize=[5000*cent[n] for n in G.nodes()]

    pos=nx.spring_layout(G,seed=42)
    nx.draw_networkx_nodes(G,pos,node_color=nColor,node_size=nSize,cmap=cmap,ax=axs)
    nx.draw_networkx_edges(G,pos,ax=axs)
    nx.draw_networkx_labels(G,pos,font_size=5,font_color="black",ax=axs)

    # norm=mcolors.Normalize(vmin=min(nColor),vmax=max(nColor))
    # sm=plt.cm.ScalarMappable(cmap=cmap,norm=norm)
    # sm.set_array([])
    # cbar=plt.colorbar(sm,ax=axs,orientation="vertical")
    # cbar.set_label(title,fontsize=5)

    axs.set_title(title,fontsize=6)
    # axs.axis("off")

fig,axs=plt.subplots(1,3)

# print(list(zip(axs,centrality.items())))
for ax,(title,(cent,cmap)) in zip(axs,centrality.items()):
    draw(ax,cent,title,cmap)

fig.suptitle("Saptarshi Adhikari",fontsize=10)
# fig.text(0.5,0.04,"Centrality",ha='center',fontsize=6)
plt.show()



# # Helper function to sort and return top entries
# def get_top_centralities(centrality, top_n=5):
#     sorted_centrality = sorted(centrality.items(), key=lambda x: x[1], reverse=True)
#     return sorted_centrality[:top_n]

# # Print top centralities for each measure
# top_n = 5  # Number of top entries to display
# for measure, (centrality, _) in cent.items():
#     print(f"Top {top_n} nodes by {measure} Centrality:")
#     top_nodes = get_top_centralities(centrality, top_n)
#     for node, value in top_nodes:
#         print(f"Node {node}: {value}")
#     print("-" * 50)