import networkx as nx
import numpy as np
import matplotlib.pyplot as plt
import random
from collections import defaultdict
import pandas as pd
import urllib.request
import io
import zipfile
import os

def load_dataset(name):
    """Load different network datasets"""
    if name.lower() == "karate":
        return nx.karate_club_graph()
    
    elif name.lower() == "dolphins":
        url = "http://networkrepository.com/soc-dolphins.php?file=soc-dolphins.edges"
        G = nx.Graph()
        try:
            df = pd.read_csv(url, sep=' ', header=None, names=['source', 'target'])
            G.add_edges_from(zip(df['source'], df['target']))
        except:
            
            G = nx.davis_southern_women_graph()
            print("Using fallback network for dolphins")
        return G
    
    elif name.lower() == "football":
        try:
            return nx.read_gml("http://networkdata.ics.uci.edu/data/football/football.gml")
        except:
            
            return nx.les_miserables_graph()  
            print("Using fallback network for football")
    
    elif name.lower() == "facebook":
        
        try:
            url = "https://snap.stanford.edu/data/facebook_combined.txt.gz"
            G = nx.Graph()
            df = pd.read_csv(url, sep=' ', header=None, names=['source', 'target'])
            G.add_edges_from(zip(df['source'], df['target']))
        except:
            
            G = nx.barabasi_albert_graph(50, 5)  
            print("Using fallback network for Facebook")
        return G
    
    elif name.lower() == "twitter":
        
        G = nx.barabasi_albert_graph(100, 8)  
        print("Using synthesized Twitter-like network")
        return G
    
    else:
        raise ValueError(f"Dataset '{name}' not recognized")

class EpidemicModel:
    """Base class for epidemic models"""
    def __init__(self, graph, seed_nodes=None, seed_fraction=0.05):
        self.graph = graph
        self.nodes = list(graph.nodes())
        self.n_nodes = len(self.nodes)
        
        
        if seed_nodes is None:
            num_seeds = max(1, int(self.n_nodes * seed_fraction))
            self.seed_nodes = random.sample(self.nodes, num_seeds)
        else:
            self.seed_nodes = seed_nodes
            
        self.time_steps = []
        self.model_name = "Base Epidemic Model"
        
    def reset(self):
        """Reset the simulation"""
        pass
    
    def step(self):
        """Simulate one time step"""
        pass
    
    def run(self, max_steps=100):
        """Run the simulation for a maximum number of steps"""
        self.reset()
        self.time_steps = []
        
        for _ in range(max_steps):
            status = self.step()
            self.time_steps.append(status)
            
            
            if len(self.time_steps) > 1 and self.check_equilibrium():
                break
                
        return self.time_steps
    
    def check_equilibrium(self):
        """Check if simulation has reached equilibrium"""
        
        if len(self.time_steps) < 2:
            return False
        return self.time_steps[-1] == self.time_steps[-2]
    
    def get_stats(self):
        """Return statistics about the simulation"""
        pass

class SIModel(EpidemicModel):
    """Susceptible-Infected model"""
    def __init__(self, graph, infection_rate=0.1, seed_nodes=None, seed_fraction=0.05):
        super().__init__(graph, seed_nodes, seed_fraction)
        self.infection_rate = infection_rate
        self.model_name = "SI Model"
        
    def reset(self):
        """Reset the simulation - everyone is susceptible except seed nodes"""
        self.status = {node: 'S' for node in self.nodes}  
        for node in self.seed_nodes:
            self.status[node] = 'I'  
    
    def step(self):
        """Simulate one time step of the SI model"""
        new_status = self.status.copy()
        
        
        for node in self.nodes:
            if self.status[node] == 'S':
                
                infected_neighbors = [neigh for neigh in self.graph.neighbors(node) 
                                     if self.status[neigh] == 'I']
                
                
                for _ in infected_neighbors:
                    if random.random() < self.infection_rate:
                        new_status[node] = 'I'
                        break
        
        self.status = new_status
        
        counts = {'S': 0, 'I': 0}
        for status in self.status.values():
            counts[status] += 1
        return counts
    
    def get_stats(self):
        """Return statistics about the simulation"""
        if not self.time_steps:
            return None
            
        steps = len(self.time_steps)
        final_infected = self.time_steps[-1]['I']
        infection_rate = final_infected / self.n_nodes
        
        return {
            'steps': steps,
            'final_infected': final_infected,
            'infection_rate': infection_rate,
            'final_susceptible': self.time_steps[-1]['S']
        }

class SIRModel(EpidemicModel):
    """Susceptible-Infected-Recovered model"""
    def __init__(self, graph, infection_rate=0.1, recovery_rate=0.05, 
                 seed_nodes=None, seed_fraction=0.05):
        super().__init__(graph, seed_nodes, seed_fraction)
        self.infection_rate = infection_rate
        self.recovery_rate = recovery_rate
        self.model_name = "SIR Model"
        
    def reset(self):
        """Reset the simulation - everyone is susceptible except seed nodes"""
        self.status = {node: 'S' for node in self.nodes}  
        for node in self.seed_nodes:
            self.status[node] = 'I'  
    
    def step(self):
        """Simulate one time step of the SIR model"""
        new_status = self.status.copy()
        
        
        for node in self.nodes:
            if self.status[node] == 'S':
                
                infected_neighbors = [neigh for neigh in self.graph.neighbors(node) 
                                     if self.status[neigh] == 'I']
                
                
                for _ in infected_neighbors:
                    if random.random() < self.infection_rate:
                        new_status[node] = 'I'
                        break
            
            elif self.status[node] == 'I':
                
                if random.random() < self.recovery_rate:
                    new_status[node] = 'R'
        
        self.status = new_status
        
        counts = {'S': 0, 'I': 0, 'R': 0}
        for status in self.status.values():
            counts[status] += 1
        return counts
    
    def check_equilibrium(self):
        """Check if simulation has reached equilibrium"""
        if len(self.time_steps) < 2:
            return False
        
        return self.time_steps[-1]['I'] == 0
    
    def get_stats(self):
        """Return statistics about the simulation"""
        if not self.time_steps:
            return None
            
        steps = len(self.time_steps)
        final_recovered = self.time_steps[-1]['R']
        recovery_rate = final_recovered / self.n_nodes
        
        return {
            'steps': steps,
            'final_recovered': final_recovered,
            'recovery_rate': recovery_rate,
            'final_susceptible': self.time_steps[-1]['S']
        }

class LinearCascadeModel(EpidemicModel):
    """Independent Cascade model with linear threshold"""
    def __init__(self, graph, threshold=0.2, seed_nodes=None, seed_fraction=0.05):
        super().__init__(graph, seed_nodes, seed_fraction)
        self.threshold = threshold
        self.model_name = "Linear Cascade Model"
        
    def reset(self):
        """Reset the simulation - everyone is inactive except seed nodes"""
        self.status = {node: 'inactive' for node in self.nodes}
        self.thresholds = {node: self.threshold for node in self.nodes}
        
        for node in self.seed_nodes:
            self.status[node] = 'active'
        
        self.just_activated = set(self.seed_nodes)
    
    def step(self):
        """Simulate one time step of the linear cascade model"""
        newly_activated = set()
        
        
        for node in self.nodes:
            if self.status[node] == 'inactive':
                
                active_neighbors = [neigh for neigh in self.graph.neighbors(node) 
                                   if neigh in self.just_activated]
                
                total_neighbors = self.graph.degree(node)
                if total_neighbors > 0:
                    
                    if len(active_neighbors) / total_neighbors >= self.thresholds[node]:
                        self.status[node] = 'active'
                        newly_activated.add(node)
        
        self.just_activated = newly_activated
        
        
        counts = {'active': 0, 'inactive': 0}
        for status in self.status.values():
            counts[status] += 1
        
        return counts
    
    def check_equilibrium(self):
        """Check if simulation has reached equilibrium"""
        
        return len(self.just_activated) == 0
    
    def get_stats(self):
        """Return statistics about the simulation"""
        if not self.time_steps:
            return None
            
        steps = len(self.time_steps)
        final_active = self.time_steps[-1]['active']
        activation_rate = final_active / self.n_nodes
        
        return {
            'steps': steps,
            'final_active': final_active,
            'activation_rate': activation_rate,
            'final_inactive': self.time_steps[-1]['inactive']
        }

def compare_models(graph_name, graph, seed=None):
    """Compare different epidemic models on a given graph"""
    if seed:
        random.seed(seed)
        np.random.seed(seed)
    
    
    max_steps = 50
    if seed is None:
        
        degrees = dict(graph.degree())
        seed_nodes = sorted(degrees, key=degrees.get, reverse=True)[:max(1, int(len(graph.nodes()) * 0.05))]
    else:
        seed_nodes = None
    
    
    si_model = SIModel(graph, infection_rate=0.15, seed_nodes=seed_nodes)
    sir_model = SIRModel(graph, infection_rate=0.15, recovery_rate=0.05, seed_nodes=seed_nodes)
    lc_model = LinearCascadeModel(graph, threshold=0.2, seed_nodes=seed_nodes)
    
    
    si_results = si_model.run(max_steps)
    sir_results = sir_model.run(max_steps)
    lc_results = lc_model.run(max_steps)
    
    
    si_stats = si_model.get_stats()
    sir_stats = sir_model.get_stats()
    lc_stats = lc_model.get_stats()
    
    
    fig, axes = plt.subplots(1, 3, figsize=(18, 6))
    fig.suptitle(f"Information Diffusion Models on {graph_name} Network", fontsize=16)
    
    
    t = range(len(si_results))
    axes[0].plot(t, [step['S'] for step in si_results], 'b-', label='Susceptible')
    axes[0].plot(t, [step['I'] for step in si_results], 'r-', label='Infected')
    axes[0].set_title(f"SI Model (Infection Rate: {si_model.infection_rate})")
    axes[0].set_xlabel("Time Steps")
    axes[0].set_ylabel("Number of Nodes")
    axes[0].legend()
    axes[0].grid(True)
    
    
    t = range(len(sir_results))
    axes[1].plot(t, [step['S'] for step in sir_results], 'b-', label='Susceptible')
    axes[1].plot(t, [step['I'] for step in sir_results], 'r-', label='Infected')
    axes[1].plot(t, [step['R'] for step in sir_results], 'g-', label='Recovered')
    axes[1].set_title(f"SIR Model (Infection: {sir_model.infection_rate}, Recovery: {sir_model.recovery_rate})")
    axes[1].set_xlabel("Time Steps")
    axes[1].set_ylabel("Number of Nodes")
    axes[1].legend()
    axes[1].grid(True)
    
    
    t = range(len(lc_results))
    axes[2].plot(t, [step['inactive'] for step in lc_results], 'b-', label='Inactive')
    axes[2].plot(t, [step['active'] for step in lc_results], 'r-', label='Active')
    axes[2].set_title(f"Linear Cascade (Threshold: {lc_model.threshold})")
    axes[2].set_xlabel("Time Steps")
    axes[2].set_ylabel("Number of Nodes")
    axes[2].legend()
    axes[2].grid(True)
    
    plt.tight_layout(rect=[0, 0, 1, 0.95])
    plt.savefig(f"{graph_name.lower()}_diffusion_models.png")
    
    return {
        'SI': si_stats,
        'SIR': sir_stats,
        'LinearCascade': lc_stats
    }

def main():
    print("Information Diffusion Analysis using Epidemic Models")
    random.seed(42)  
    
    datasets = ["Karate", "Dolphins", "Football", "Facebook", "Twitter"]
    results = {}
    
    for dataset in datasets:
        print(f"\nAnalyzing {dataset} network...")
        try:
            graph = load_dataset(dataset)
            print(f"  Network loaded: {len(graph.nodes())} nodes, {len(graph.edges())} edges")
            
            stats = compare_models(dataset, graph, seed=42)
            results[dataset] = stats
            
            print(f"  SI Model: {stats['SI']['infection_rate']*100:.1f}% of network infected in {stats['SI']['steps']} steps")
            print(f"  SIR Model: {stats['SIR']['recovery_rate']*100:.1f}% of network recovered in {stats['SIR']['steps']} steps")
            print(f"  Linear Cascade: {stats['LinearCascade']['activation_rate']*100:.1f}% of network activated in {stats['LinearCascade']['steps']} steps")
        except Exception as e:
            print(f"Error analyzing {dataset}: {e}")
    
    
    fig, ax = plt.subplots(figsize=(12, 8))
    
    x = np.arange(len(datasets))
    width = 0.25
    
    si_rates = [results.get(d, {}).get('SI', {}).get('infection_rate', 0) for d in datasets]
    sir_rates = [results.get(d, {}).get('SIR', {}).get('recovery_rate', 0) for d in datasets]
    lc_rates = [results.get(d, {}).get('LinearCascade', {}).get('activation_rate', 0) for d in datasets]
    
    rects1 = ax.bar(x - width, si_rates, width, label='SI Model')
    rects2 = ax.bar(x, sir_rates, width, label='SIR Model')
    rects3 = ax.bar(x + width, lc_rates, width, label='Linear Cascade')
    
    ax.set_ylabel('Diffusion Rate')
    ax.set_title('Comparison of Diffusion Models across Datasets')
    ax.set_xticks(x)
    ax.set_xticklabels(datasets)
    ax.legend()
    
    plt.grid(axis='y', linestyle='--', alpha=0.7)
    plt.tight_layout()
    plt.savefig("diffusion_model_comparison.png")
    plt.show()
    
    print("\nAnalysis complete. Results visualized and saved as PNG files.")
    
    
    print("\nMost Suitable Models for Each Dataset:")
    for dataset in datasets:
        if dataset not in results:
            continue
            
        stats = results[dataset]
        if not stats:
            continue
            
        
        
        
        rates = {
            'SI': stats.get('SI', {}).get('infection_rate', 0), 
            'SIR': stats.get('SIR', {}).get('recovery_rate', 0),
            'LinearCascade': stats.get('LinearCascade', {}).get('activation_rate', 0)
        }
        
        best_model = max(rates.items(), key=lambda x: x[1])[0]
        print(f"  {dataset}: {best_model} (Diffusion Rate: {rates[best_model]*100:.1f}%)")

if __name__ == "__main__":
    main()
