import networkx as nx
import matplotlib.pyplot as plt
import random

# G = nx.read_gml("./dataset/football.gml", label="id")
G=nx.karate_club_graph()

def plot_results(results, title):
    plt.figure(figsize=(10, 6))
    for key in results:
        plt.plot(results[key], label=key)
    plt.title(title)
    plt.xlabel('Time Steps')
    plt.ylabel('Number of Nodes')
    plt.legend()
    plt.grid(True)
    plt.show()

def run_sir_model(G, beta=0.3, gamma=0.1, initial_infected=[0], time_steps=30):
    status = {node: 0 for node in G.nodes()}  
    for node in initial_infected:
        status[node] = 1

    sir_counts = {'Susceptible': [], 'Infected': [], 'Recovered': []}
    
    for _ in range(time_steps):
        new_status = status.copy()
        for node in G.nodes():
            if status[node] == 1:
                for neighbor in G.neighbors(node):
                    if status[neighbor] == 0 and random.random() < beta:
                        new_status[neighbor] = 1
                if random.random() < gamma:
                    new_status[node] = 2
        status = new_status
        sir_counts['Susceptible'].append(list(status.values()).count(0))
        sir_counts['Infected'].append(list(status.values()).count(1))
        sir_counts['Recovered'].append(list(status.values()).count(2))

    return sir_counts


def run_si_model(G, beta=0.4, initial_infected=[0], time_steps=30):
    status = {node: 0 for node in G.nodes()} 
    for node in initial_infected:
        status[node] = 1

    si_counts = {'Susceptible': [], 'Infected': []}

    for _ in range(time_steps):
        new_status = status.copy()
        for node in G.nodes():
            if status[node] == 1:
                for neighbor in G.neighbors(node):
                    if status[neighbor] == 0 and random.random() < beta:
                        new_status[neighbor] = 1
        status = new_status
        si_counts['Susceptible'].append(list(status.values()).count(0))
        si_counts['Infected'].append(list(status.values()).count(1))

    return si_counts

def run_linear_cascade_model(G, threshold=0.3, initial_active=[0], time_steps=30):
    active = set(initial_active)
    lc_counts = {'Active': []}

    for _ in range(time_steps):
        new_active = set(active)
        for node in G.nodes():
            if node not in active:
                neighbors = list(G.neighbors(node))
                if neighbors:
                    active_neighbors = [n for n in neighbors if n in active]
                    if len(active_neighbors) / len(neighbors) >= threshold:
                        new_active.add(node)
        if new_active == active:
            break
        active = new_active
        lc_counts['Active'].append(len(active))

    return lc_counts


sir = run_sir_model(G)
plot_results(sir, 'SIR Model on Karate Club Network')

si = run_si_model(G)
plot_results(si, 'SI Model on Karate Club Network')

lc = run_linear_cascade_model(G)
plot_results(lc, 'Linear Cascade Model on Karate Club Network')