# https://www.youtube.com/watch?v=VetBkjcm9Go


import networkx as nx;
import matplotlib.pyplot as plt;
import matplotlib.colors as mcolors
import numpy as np;

# first
# G= nx.Graph();#undirected graph
# G= nx.DiGraph();#directed graph
# G.add_edge(1,2);
# G.add_edge(1,3);
# G.add_edge(2,3, weight=4);
# G.add_node(print);

# edge_list=[(1,2),(2,3),(3,4)];

# G=nx.Graph(edge_list);

# G=nx.from_edgelist(edge_list);
# print(nx.adjacency_matrix(G));

# G=nx.from_numpy_array(np.array([[0,1,0],[1,0,1],[0,1,0]]));

# G=nx.complete_graph(5);

# print(nx.shortest_path(G,1,3));

# print(dict(G.in_degree())[print]);
# print(dict(G.out_degree())[print]);
# print(G.degree());
# print(dict(G.degree()))

# G1=nx.complete_graph(5);
# G2=nx.complete_graph(5);
# G2=nx.relabel_nodes(G2,{0:5,1:6,2:7,3:8,4:9});
# GConnector=nx.from_edgelist([(0,"X"),("X",5)]);

# G=nx.compose_all([G1,G2,GConnector]);

# print(nx.degree_centrality(G));
# print(nx.betweenness_centrality(G,normalized=True));    
# print(nx.eigenvector_centrality(G));
# print(nx.closeness_centrality(G));

# print(nx.density(G));
# print(nx.diameter(G));

# print(list(nx.eulerian_path(G)));
# print(list(nx.find_cliques(G)));
# print(list(nx.k_core(G,3)));
# print(list(nx.bridges(G)));
# print(list(nx.local_bridges(G)));

# print(list(nx.connected_components(G)));
# print(list(nx.weakly_connected_components(G)));
# print(list(nx.strongly_connected_components(G)));


# nx.draw_planar(G, with_labels=True); #prints such that no edge crosses another edge not possible for complete 5 nodes graph
nx.draw_spring(G, with_labels=True); 

plt.show();