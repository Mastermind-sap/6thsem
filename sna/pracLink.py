import networkx as nx
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import random

def cosSim(G,u,v):
    nu,nv=set(G.neighbors(u)),set(G.neighbors(v))
    comm=nu&nv
    if not nu or not nv:
        return 0
    return float(len(comm)/np.sqrt(len(nu)*len(nv)))

def jaccIdx(G,u,v):
    nu,nv=set(G.neighbors(u)),set(G.neighbors(v))
    comm=nu&nv
    tot=nu|nv
    if not tot:
        return 0
    return len(comm)/len(tot)

def predLinks(G,metric,thresh=0.1):
    preds=[]
    for u in G.nodes():
        for v in G.nodes():
            if u==v or G.has_edge(u,v):
                continue
            score=metric(G,u,v)
            if score>thresh:
                preds.append((u,v,score))
    return preds

def visualize(G,preds):
    plt.figure()
    pos=nx.spring_layout(G,seed=42)
    nx.draw_networkx_nodes(G,pos)
    nx.draw_networkx_edges(G, pos,label="Existing")
    if preds:
        pred_edges = [(u, v) for u, v, _ in preds]
        nx.draw_networkx_edges(G, pos, edgelist=pred_edges,label="Predicted",style="dashed",edge_color="blue");
    nx.draw_networkx_labels(G,pos)
    plt.title("Link pred")
    plt.legend()
    plt.show()

def split(G,ratio=0.2,seed=42):
    random.seed(seed)
    train=G.copy()
    edges=list(G.edges())
    test=random.sample(edges,int(len(edges)*ratio))
    train.remove_edges_from(test)
    return train,test

def precision(preds,test):
    predSet=set([(u,v) for u,v,_ in preds])
    testSet=set([(u,v) for u,v in test])
    tp=0
    for u,v in predSet:
        for x,y in testSet:
            if(u==x and v==y)or(u==y and v==x):
                tp+=1
    if predSet:
        return tp/len(predSet)
    return 0

def main():
    df = pd.read_csv("../../../Downloads/graph2.csv")
    G = nx.from_pandas_edgelist(df, "Source", "Target", create_using=nx.Graph())
    train, test  = split(G, 0.2)
    print(train)
    pred = predLinks(train, cosSim, 0.3)
    prec = precision(pred, test)
    print(f"Precision = {prec:.4f}")
    visualize(train, pred)
    pred=predLinks(train,jaccIdx,0.3)
    prec=precision(pred,test)
    print(f"Precision = {prec:.4f}")
    visualize(train, pred)
    
if __name__ == "__main__":
    main()