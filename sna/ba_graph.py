import networkx as nx
import matplotlib.pyplot as plt
import random

while True:
    # Generate random number of nodes
    num_nodes = random.randint(10, 20)

    # Generate random number of edges to attach from a new node to existing nodes
    m = random.randint(1, 5)

    # Generate the random BA graph
    G = nx.barabasi_albert_graph(num_nodes, m)

    # Output the number of nodes and edges in the terminal
    print(f"Number of nodes: {G.number_of_nodes()}")
    print(f"Number of edges: {G.number_of_edges()}")

    # Calculate centrality measures
    degree_centrality = nx.degree_centrality(G)
    eigenvector_centrality = nx.eigenvector_centrality(G)
    betweenness_centrality = nx.betweenness_centrality(G)

    # Sort and get top 5 nodes for each centrality measure
    top_degree = sorted(degree_centrality.items(), key=lambda x: x[1], reverse=True)[:5]
    top_eigenvector = sorted(eigenvector_centrality.items(), key=lambda x: x[1], reverse=True)[:5]
    top_betweenness = sorted(betweenness_centrality.items(), key=lambda x: x[1], reverse=True)[:5]

    # Print top 5 nodes for each centrality measure
    print("\nTop 5 nodes by degree centrality:")
    for node, centrality in top_degree:
        print(f"Node {node}: {centrality:.4f}")

    print("\nTop 5 nodes by eigenvector centrality:")
    for node, centrality in top_eigenvector:
        print(f"Node {node}: {centrality:.4f}")

    print("\nTop 5 nodes by betweenness centrality:")
    for node, centrality in top_betweenness:
        print(f"Node {node}: {centrality:.4f}")

    # Draw the graph
    nx.draw(G, with_labels=True, node_color='skyblue', node_size=700, edge_color='gray')
    plt.suptitle(f"Barabási-Albert Graph (n={num_nodes}, m={m})")
    plt.show()

    # Check for user input to generate again
    user_input = input("Enter 'r' to generate a new graph, or any other key to exit: ")
    if user_input.lower() != 'r':
        break
