import networkx as nx
import matplotlib.pyplot as plt
import random

while True:
    
    num_nodes = random.randint(10, 20)

    
    probability = random.uniform(0.1, 0.5)

    
    G = nx.erdos_renyi_graph(num_nodes, probability)

    
    print(f"Number of nodes: {G.number_of_nodes()}")
    print(f"Number of edges: {G.number_of_edges()}")

    
    degree_centrality = nx.degree_centrality(G)
    eigenvector_centrality = nx.eigenvector_centrality(G)
    betweenness_centrality = nx.betweenness_centrality(G)

    
    top_degree = sorted(degree_centrality.items(), key=lambda x: x[1], reverse=True)[:5]
    top_eigenvector = sorted(eigenvector_centrality.items(), key=lambda x: x[1], reverse=True)[:5]
    top_betweenness = sorted(betweenness_centrality.items(), key=lambda x: x[1], reverse=True)[:5]

    
    print("\nTop 5 nodes by degree centrality:")
    for node, centrality in top_degree:
        print(f"Node {node}: {centrality:.4f}")

    print("\nTop 5 nodes by eigenvector centrality:")
    for node, centrality in top_eigenvector:
        print(f"Node {node}: {centrality:.4f}")

    print("\nTop 5 nodes by betweenness centrality:")
    for node, centrality in top_betweenness:
        print(f"Node {node}: {centrality:.4f}")

    
    nx.draw(G, with_labels=True, node_color='skyblue', node_size=700, edge_color='gray')
    plt.suptitle(f"Erdős-Rényi Graph (n={num_nodes}, p={probability:.2f})")
    plt.show()

    
    user_input = input("Enter 'r' to generate a new graph, or any other key to exit: ")
    if user_input.lower() != 'r':
        break
