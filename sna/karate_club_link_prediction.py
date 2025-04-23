# import networkx as nx
# import numpy as np
# import matplotlib.pyplot as plt
# import random
# from sklearn.metrics import precision_score

# def splitEdges(g, testRatio=0.2, seed=42):
#     random.seed(seed)
#     edges = list(g.edges())
#     random.shuffle(edges)
#     testSize = int(len(edges) * testRatio)
#     testEdges = edges[:testSize]
#     trainEdges = edges[testSize:]
#     gTrain = g.copy()
#     gTrain.remove_edges_from(testEdges)
#     return gTrain, testEdges

# def computeCommonNeighbors(g, nodePairs):
#     commonNeighbors = {}
#     for u, v in nodePairs:
#         commonNeighbors[(u, v)] = list(nx.common_neighbors(g, u, v))
#     return commonNeighbors

# def cosineSimilarity(g, nodePairs):
#     simScores = {}
#     commonNeighborsDict = computeCommonNeighbors(g, nodePairs)
#     for u, v in nodePairs:
#         commonNeighbors = commonNeighborsDict[(u, v)]
#         sim = len(commonNeighbors) / np.sqrt(g.degree(u) * g.degree(v)) if g.degree(u) * g.degree(v) > 0 else 0
#         simScores[(u, v)] = sim
#     return simScores

# def jaccardIndex(g, nodePairs):
#     simScores = {}
#     commonNeighborsDict = computeCommonNeighbors(g, nodePairs)
#     for u, v in nodePairs:
#         commonNeighbors = commonNeighborsDict[(u, v)]
#         neighborsU = set(g.neighbors(u))
#         neighborsV = set(g.neighbors(v))
#         unionSize = len(neighborsU.union(neighborsV))
#         sim = len(commonNeighbors) / unionSize if unionSize > 0 else 0
#         simScores[(u, v)] = sim
#     return simScores

# def predictLinks(simScores, threshold):
#     return [(u, v) for (u, v), score in simScores.items() if score > threshold]

# def evalPredictions(predLinks, actualLinks):
#     predSet = set(predLinks)
#     actualSet = set(actualLinks)
#     truePos = len(predSet.intersection(actualSet))
#     falsePos = len(predSet - actualSet)
#     prec = truePos / len(predSet) if predSet else 0
#     return {'truePos': truePos, 'falsePos': falsePos, 'predLinks': len(predSet), 'prec': prec}

# def main():
#     print("Link Prediction Analysis for Zachary's Karate Club Dataset")
#     g = nx.karate_club_graph()
#     gTrain, testEdges = splitEdges(g)
#     nonEdges = list(nx.non_edges(gTrain))
#     candidateEdges = testEdges + nonEdges
    
#     cosScores = cosineSimilarity(gTrain, candidateEdges)
#     jacScores = jaccardIndex(gTrain, candidateEdges)
    
#     topCos = sorted(cosScores.items(), key=lambda x: x[1], reverse=True)[:5]
#     topJac = sorted(jacScores.items(), key=lambda x: x[1], reverse=True)[:5]
    
#     print("\nTop 5 Node Pairs by Cosine Similarity:")
#     for (u, v), score in topCos:
#         print(f"Nodes {u}-{v}: {score:.4f}")
    
#     print("\nTop 5 Node Pairs by Jaccard Index:")
#     for (u, v), score in topJac:
#         print(f"Nodes {u}-{v}: {score:.4f}")
    
#     thresholds = np.linspace(0, 0.5, 20)
#     cosResults = []
#     jacResults = []
    
#     for t in thresholds:
#         cosLinks = predictLinks(cosScores, t)
#         if cosLinks:
#             cosEval = evalPredictions(cosLinks, testEdges)
#             cosResults.append({
#                 'threshold': t,
#                 'prec': cosEval['prec'],
#                 'predLinks': cosEval['predLinks'],
#                 'truePos': cosEval['truePos']
#             })
        
#         jacLinks = predictLinks(jacScores, t)
#         if jacLinks:
#             jacEval = evalPredictions(jacLinks, testEdges)
#             jacResults.append({
#                 'threshold': t,
#                 'prec': jacEval['prec'],
#                 'predLinks': jacEval['predLinks'],
#                 'truePos': jacEval['truePos']
#             })
    
#     bestCos = max(cosResults, key=lambda x: x['prec']) if cosResults else {'threshold': 0, 'prec': 0, 'predLinks': 0}
#     bestJac = max(jacResults, key=lambda x: x['prec']) if jacResults else {'threshold': 0, 'prec': 0, 'predLinks': 0}
    
#     # print(f"\nTotal test links: {len(testEdges)}")
#     # print(f"\nCosine: threshold={bestCos['threshold']:.4f}, links={bestCos['predLinks']}, truePos={bestCos['truePos']}, prec={bestCos['prec']:.4f}")
#     # print(f"Jaccard: threshold={bestJac['threshold']:.4f}, links={bestJac['predLinks']}, truePos={bestJac['truePos']}, prec={bestJac['prec']:.4f}")
    
#     fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(12, 5))
#     fig.suptitle("Saptarshi Adhikari 2212072", fontsize=14)

#     methods = ['Cosine', 'Jaccard']
#     linkCounts = [bestCos['predLinks'], bestJac['predLinks']]
#     precisions = [bestCos['prec'], bestJac['prec']]
    
#     ax1.bar(methods, linkCounts, color=['blue', 'orange'])
#     ax1.set_title('Predicted Links')
    
#     for i, v in enumerate(linkCounts):
#         ax1.text(i, v + 0.5, f"Prec: {precisions[i]:.4f}", ha='center', fontweight='bold')
    
#     cosT = [r['threshold'] for r in cosResults]
#     cosP = [r['prec'] for r in cosResults]
#     jacT = [r['threshold'] for r in jacResults]
#     jacP = [r['prec'] for r in jacResults]
    
#     ax2.plot(cosT, cosP, 'b-', label='Cosine')
#     ax2.plot(jacT, jacP, 'r-', label='Jaccard')
#     ax2.set_xlabel('Threshold')
#     ax2.set_ylabel('Precision')
#     ax2.legend()
#     ax2.grid(True)
    
#     plt.tight_layout(rect=[0, 0, 1, 0.95])
#     plt.savefig('karate_club_link_prediction.png')
#     plt.show()

# if __name__ == "__main__":
#     main()


import networkx as nx
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import random

def cos_sim(G, u, v):
    nu, nv = set(G.neighbors(u)), set(G.neighbors(v))
    comm = nu&nv
    if not nu or not nv:    
        return 0
    return float(len(comm)/np.sqrt(len(nu)*len(nv)))


def jac_idx(G, u, v):
    nu, nv = set(G.neighbors(u)), set(G.neighbors(v))
    comm = nu&nv
    tot = nu|nv
    if not tot:
        return 0
    return len(comm)/len(tot)


def pred_links(G, metric, thresh=0.1):
    preds = []
    for u in G.nodes():
        for v in G.nodes():
            if u == v or G.has_edge(u, v):
                continue
            score = metric(G, u, v)
            if score > thresh:
                preds.append((u, v, score))
    return preds


def visualize(G, preds):
    plt.figure(figsize=(10, 8))
    pos = nx.spring_layout(G, seed=42)
    nx.draw_networkx_nodes(G, pos, node_size=500, node_color="blue")
    nx.draw_networkx_edges(G, pos, edge_color="blue", width=2.0, label="Existing")
    if preds:
        pred_edges = [(u, v) for u, v, _ in preds]
        nx.draw_networkx_edges(G, pos, edgelist=pred_edges, edge_color="red", width=2.0, style="dashed", label="Predicted")
    nx.draw_networkx_labels(G, pos, font_color="black", font_size=10)
    plt.title("Link Prediction")
    plt.legend()
    plt.axis("off")
    plt.tight_layout()
    plt.show()
    

def split(G, ratio = 0.2, seed=42):
    random.seed(seed)
    train = G.copy()
    edges = list(G.edges())
    test = random.sample(edges, int(len(edges) * ratio))
    train.remove_edges_from(test)
    return train, test

def precision(preds, test):
    pred_set = set([(u, v) for u, v, _ in preds])
    test_set = set([(u, v) for u, v in test])
    tp = 0
    for u, v in pred_set:
        for x, y in test_set:
            if (u == x and v == y) or (u == y or v == x):
                tp = tp + 1
    if pred_set:
        return tp/len(pred_set)
    return 0

def main():
    df = pd.read_csv("../../../Downloads/graph2.csv")
    G = nx.from_pandas_edgelist(df, "Source", "Target", create_using=nx.Graph())
    train, test  = split(G, 0.2)
    print(train)
    pred = pred_links(train, cos_sim, 0.3)
    prec = precision(pred, test)
    print(f"Precision = {prec:.4f}")
    visualize(train, pred)
    
if __name__ == "__main__":
    main()