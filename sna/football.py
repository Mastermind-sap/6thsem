import networkx as nx, matplotlib.pyplot as plt, matplotlib.colors as mcolors
import urllib.request, io, zipfile

url = "http://www-personal.umich.edu/~mejn/netdata/football.zip"
sock = urllib.request.urlopen(url)
s = io.BytesIO(sock.read())
sock.close()

zf = zipfile.ZipFile(s)
gml = zf.read("football.gml").decode().split("\n")[1:]
G = nx.parse_gml(gml)

cent = {
    "Degree": (nx.degree_centrality(G), plt.cm.cool),
    "Betweenness": (nx.betweenness_centrality(G, normalized=True), plt.cm.winter),
    "Eigenvector": (nx.eigenvector_centrality(G), plt.cm.summer)
}

# Print centralities in table format
print(f"{'Node':<30} | {'Degree':<25} | {'Betweenness':<25} | {'Eigenvector':<25}")
print("-" * 110)
for node in G.nodes():
    degree = cent["Degree"][0][node]
    betweenness = cent["Betweenness"][0][node]
    eigenvector = cent["Eigenvector"][0][node]
    print(f"{node:<30} | {degree:<25} | {betweenness:<25} | {eigenvector:<25}")
print()

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
fig.text(0.5, 0.04, "Football Dataset", ha='center', fontsize=16)
plt.show()