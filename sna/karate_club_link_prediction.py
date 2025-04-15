import networkx as nx
import numpy as np
import matplotlib.pyplot as plt
import random
from sklearn.metrics import precision_score

def splitEdges(g, testRatio=0.2, seed=42):
    random.seed(seed)
    edges = list(g.edges())
    random.shuffle(edges)
    testSize = int(len(edges) * testRatio)
    testEdges = edges[:testSize]
    trainEdges = edges[testSize:]
    gTrain = g.copy()
    gTrain.remove_edges_from(testEdges)
    return gTrain, testEdges

def computeCommonNeighbors(g, nodePairs):
    commonNeighbors = {}
    for u, v in nodePairs:
        commonNeighbors[(u, v)] = list(nx.common_neighbors(g, u, v))
    return commonNeighbors

def cosineSimilarity(g, nodePairs):
    simScores = {}
    commonNeighborsDict = computeCommonNeighbors(g, nodePairs)
    for u, v in nodePairs:
        commonNeighbors = commonNeighborsDict[(u, v)]
        sim = len(commonNeighbors) / np.sqrt(g.degree(u) * g.degree(v)) if g.degree(u) * g.degree(v) > 0 else 0
        simScores[(u, v)] = sim
    return simScores

def jaccardIndex(g, nodePairs):
    simScores = {}
    commonNeighborsDict = computeCommonNeighbors(g, nodePairs)
    for u, v in nodePairs:
        commonNeighbors = commonNeighborsDict[(u, v)]
        neighborsU = set(g.neighbors(u))
        neighborsV = set(g.neighbors(v))
        unionSize = len(neighborsU.union(neighborsV))
        sim = len(commonNeighbors) / unionSize if unionSize > 0 else 0
        simScores[(u, v)] = sim
    return simScores

def predictLinks(simScores, threshold):
    return [(u, v) for (u, v), score in simScores.items() if score > threshold]

def evalPredictions(predLinks, actualLinks):
    predSet = set(predLinks)
    actualSet = set(actualLinks)
    truePos = len(predSet.intersection(actualSet))
    falsePos = len(predSet - actualSet)
    prec = truePos / len(predSet) if predSet else 0
    return {'truePos': truePos, 'falsePos': falsePos, 'predLinks': len(predSet), 'prec': prec}

def main():
    print("Link Prediction Analysis for Zachary's Karate Club Dataset")
    g = nx.karate_club_graph()
    gTrain, testEdges = splitEdges(g)
    nonEdges = list(nx.non_edges(gTrain))
    candidateEdges = testEdges + nonEdges
    
    cosScores = cosineSimilarity(gTrain, candidateEdges)
    jacScores = jaccardIndex(gTrain, candidateEdges)
    
    topCos = sorted(cosScores.items(), key=lambda x: x[1], reverse=True)[:5]
    topJac = sorted(jacScores.items(), key=lambda x: x[1], reverse=True)[:5]
    
    print("\nTop 5 Node Pairs by Cosine Similarity:")
    for (u, v), score in topCos:
        print(f"Nodes {u}-{v}: {score:.4f}")
    
    print("\nTop 5 Node Pairs by Jaccard Index:")
    for (u, v), score in topJac:
        print(f"Nodes {u}-{v}: {score:.4f}")
    
    thresholds = np.linspace(0, 0.5, 20)
    cosResults = []
    jacResults = []
    
    for t in thresholds:
        cosLinks = predictLinks(cosScores, t)
        if cosLinks:
            cosEval = evalPredictions(cosLinks, testEdges)
            cosResults.append({
                'threshold': t,
                'prec': cosEval['prec'],
                'predLinks': cosEval['predLinks'],
                'truePos': cosEval['truePos']
            })
        
        jacLinks = predictLinks(jacScores, t)
        if jacLinks:
            jacEval = evalPredictions(jacLinks, testEdges)
            jacResults.append({
                'threshold': t,
                'prec': jacEval['prec'],
                'predLinks': jacEval['predLinks'],
                'truePos': jacEval['truePos']
            })
    
    bestCos = max(cosResults, key=lambda x: x['prec']) if cosResults else {'threshold': 0, 'prec': 0, 'predLinks': 0}
    bestJac = max(jacResults, key=lambda x: x['prec']) if jacResults else {'threshold': 0, 'prec': 0, 'predLinks': 0}
    
    # print(f"\nTotal test links: {len(testEdges)}")
    # print(f"\nCosine: threshold={bestCos['threshold']:.4f}, links={bestCos['predLinks']}, truePos={bestCos['truePos']}, prec={bestCos['prec']:.4f}")
    # print(f"Jaccard: threshold={bestJac['threshold']:.4f}, links={bestJac['predLinks']}, truePos={bestJac['truePos']}, prec={bestJac['prec']:.4f}")
    
    fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(12, 5))
    fig.suptitle("Saptarshi Adhikari 2212072", fontsize=14)

    methods = ['Cosine', 'Jaccard']
    linkCounts = [bestCos['predLinks'], bestJac['predLinks']]
    precisions = [bestCos['prec'], bestJac['prec']]
    
    ax1.bar(methods, linkCounts, color=['blue', 'orange'])
    ax1.set_title('Predicted Links')
    
    for i, v in enumerate(linkCounts):
        ax1.text(i, v + 0.5, f"Prec: {precisions[i]:.4f}", ha='center', fontweight='bold')
    
    cosT = [r['threshold'] for r in cosResults]
    cosP = [r['prec'] for r in cosResults]
    jacT = [r['threshold'] for r in jacResults]
    jacP = [r['prec'] for r in jacResults]
    
    ax2.plot(cosT, cosP, 'b-', label='Cosine')
    ax2.plot(jacT, jacP, 'r-', label='Jaccard')
    ax2.set_xlabel('Threshold')
    ax2.set_ylabel('Precision')
    ax2.legend()
    ax2.grid(True)
    
    plt.tight_layout(rect=[0, 0, 1, 0.95])
    plt.savefig('karate_club_link_prediction.png')
    plt.show()

if __name__ == "__main__":
    main()
