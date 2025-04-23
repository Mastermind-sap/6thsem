import networkx as nx
import matplotlib.pyplot as plt
import random

def gen(gtype):
    if gtype=="ER":
        n,p=random.randint(10,20),random.uniform(0.1,0.5)
        G=nx.erdos_renyi_graph(n,p)
        return G, f"ER graph (n={n},p={p:.2f})"
    elif gtype=="BA":
        n, m = random.randint(50, 100), random.randint(1, 5)
        G = nx.barabasi_albert_graph(n, m)
        return G, f"Barabási-Albert Graph (n={n}, m={m})"
    elif gtype=="LFR":
        G=nx.LFR_benchmark_graph(250,3,1.5,0.1,average_degree=5,min_community=20,seed=10)
        return G,"LFR (n=250,tau1=3,tau2=1.5,mu=0.1)"


def calcCent(G):
    return (nx.degree_centrality(G),nx.betweenness_centrality(G),nx.eigenvector_centrality(G));

def getTopCent(centDict):
    return sorted(centDict.items(),key=lambda x:x[1],reverse=True)[:3]

def plotGraphs():
    fig,ax=plt.subplots(1,3)
    graphT=["ER","BA","LFR"]
    for ax,gT in zip(ax,graphT):
        g,title=gen(gT)
        nx.draw(g,with_labels=True,ax=ax)
        ax.set_title(title)
        degree,between,eigen=calcCent(g)
        topD=getTopCent(degree)
        topB=getTopCent(between)
        topE=getTopCent(eigen)
        print(topB,topD,topE)

    plt.suptitle("Sap")
    plt.show()

plotGraphs()