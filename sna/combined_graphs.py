import networkx as nx
import matplotlib.pyplot as plt
import random
from tabulate import tabulate

def genGraph(graphType):
    if graphType == "ER":
        n, p = random.randint(10, 20), random.uniform(0.1, 0.5)
        G = nx.erdos_renyi_graph(n, p)
        return G, f"Erdős-Rényi Graph (n={n}, p={p:.2f})"
    elif graphType == "BA":
        n, m = random.randint(50, 100), random.randint(1, 5)
        G = nx.barabasi_albert_graph(n, m)
        return G, f"Barabási-Albert Graph (n={n}, m={m})"
    elif graphType == "LFR":
        G = nx.LFR_benchmark_graph(250, 3, 1.5, 0.1, average_degree=5, min_community=20, seed=10)
        return G, "LFR Graph (n=250, tau1=3, tau2=1.5, mu=0.1)"

def calcCentrality(G):
    return (nx.degree_centrality(G), nx.eigenvector_centrality(G), nx.betweenness_centrality(G))

def getTopCentralities(centralityDict):
    return sorted(centralityDict.items(), key=lambda x: x[1], reverse=True)[:3]

def plotGraphs():
    fig, axs = plt.subplots(1, 3, figsize=(18, 6))
    graphTypes = ["ER", "BA", "LFR"]
    
    for ax, graphType in zip(axs, graphTypes):
        G, title = genGraph(graphType)
        nx.draw(G, ax=ax, with_labels=True, node_color='skyblue', node_size=700, edge_color='gray')
        ax.set_title(title)
        
        degree, eigenvector, betweenness = calcCentrality(G)
        topDegree = getTopCentralities(degree)
        topEigenvector = getTopCentralities(eigenvector)
        topBetweenness = getTopCentralities(betweenness)
        
        table = [["Degree Centrality", "Eigenvector Centrality", "Betweenness Centrality"]]
        for i in range(3):
            table.append([
                f"Node {topDegree[i][0]}: {topDegree[i][1]:.4f}",
                f"Node {topEigenvector[i][0]}: {topEigenvector[i][1]:.4f}",
                f"Node {topBetweenness[i][0]}: {topBetweenness[i][1]:.4f}"
            ])
        print(f"\n{title}\n{tabulate(table, headers='firstrow', tablefmt='grid')}")
    
    plt.suptitle("Saptarshi Adhikari 2212072")
    plt.show()

if __name__ == "__main__":
    plotGraphs()
