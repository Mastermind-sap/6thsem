import networkx as nx, matplotlib.pyplot as plt, matplotlib.colors as mcolors

G = nx.karate_club_graph()

cent = {
    "Degree": (nx.degree_centrality(G), plt.cm.cool),
    "Betweenness": (nx.betweenness_centrality(G, normalized=True), plt.cm.winter),
    "Eigenvector": (nx.eigenvector_centrality(G), plt.cm.summer)
}

def draw(ax, cent, title, cmap):
    n_color = [cent[n] for n in G.nodes()]
    n_size = [5000 * cent[n] for n in G.nodes()]  
    norm = mcolors.Normalize(vmin=min(n_color), vmax=max(n_color))
    sm = plt.cm.ScalarMappable(cmap=cmap, norm=norm)

    pos = nx.spring_layout(G, seed=42)  
    nx.draw_networkx_nodes(G, pos, node_color=n_color, node_size=n_size, cmap=cmap, ax=ax)
    nx.draw_networkx_edges(G, pos, alpha=0.6, ax=ax)
    nx.draw_networkx_labels(G, pos, font_size=10, font_color="black", ax=ax)

    sm.set_array([])
    cbar = plt.colorbar(sm, ax=ax, orientation="vertical")
    cbar.set_label(title, fontsize=12)

    ax.set_title(title, fontsize=16)
    ax.axis("off")

fig, axs = plt.subplots(1, 3, figsize=(18, 6))
for ax, (title, (cent, cmap)) in zip(axs, cent.items()):
    draw(ax, cent, title, cmap)

fig.suptitle("Saptarshi Adhikari 2212072", fontsize=20)
fig.text(0.5, 0.04, "Karate Club Dataset", ha='center', fontsize=16)
plt.show()