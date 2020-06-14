package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private Graph<String, DefaultWeightedEdge> grafo;
	private FoodDao dao;
	private List<String> vertici;
	
	private Double pesoMax;
	private List<String> best;
	
	public Model() {
		this.dao = new FoodDao();
	}
	
	public String creaGrafo(int C) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		this.vertici = this.dao.getPortionDisplayName(C);
		Graphs.addAllVertices(this.grafo, vertici);
		
		List<Arco> archi = this.dao.getArchi();
		for(Arco a : archi) {
			if(this.grafo.containsVertex(a.getV1()) && this.grafo.containsVertex(a.getV2())) {
				if(this.grafo.getEdge(a.getV1(), a.getV2()) == null)
					Graphs.addEdgeWithVertices(this.grafo, a.getV1(), a.getV2(), a.getPeso());
			}
		}
		
		return String.format("Grafo creato con %d vertici e %d archi.\n", this.grafo.vertexSet().size(),
				this.grafo.edgeSet().size());
	}
	
	public List<String> getVertici() {
		return this.vertici;
	}
	
	public List<PorzioneAdiacente> getAdiacenti(String partenza) {
		List<String> vicini = Graphs.neighborListOf(this.grafo, partenza);
		List<PorzioneAdiacente> result = new ArrayList<>();
		
		for(String v : vicini) {
			Double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(partenza, v));
			result.add(new PorzioneAdiacente(v, peso));
		}
		
		return result;
	}
	
	public void trovaPercorso(String partenza, int N) {
		this.best = null; //new ArrayList<>();
		this.pesoMax = 0.0;
		List<String> parziale = new ArrayList<>();
		
		parziale.add(partenza);
		search(parziale, 0, N);
	}
	
	/**
	 * Soluzione parziale -> cammino che parte dal vertice iniziale
	 * Livello -> lunghezza del cammino
	 * Condizione di terminazione -> lunghezza del cammino == N
	 * Funzione da valutare -> peso del cammino
	 * Aggiungo di volta in volta gli adiacenti che non sono ancora stati inseriti nel cammino, partendo dalla sol
	 * parziale che avrà solo il vertice di partenza inizialmente
	 */
	private void search(List<String> parziale, int livello, int N) {
		
		if(parziale.size() == N+1) { //livello == N+1
			Double peso = pesoCammino(parziale);
			if(peso > this.pesoMax) {
				this.pesoMax = peso;
				this.best = new ArrayList<>(parziale);
			}
			return;
		}
		
		//Livello - 1 perchè a liv 1 voglio il vertice in 0 ...
		List<String> vicini = Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1));
		for(String v : vicini) {
			if(!parziale.contains(v)) {
				parziale.add(v);
				search(parziale, livello+1, N);
				parziale.remove(parziale.size()-1);
			}
		}
	}

	private Double pesoCammino(List<String> parziale) {
		Double peso = 0.0;
		for(int i = 1; i < parziale.size(); i++) {
			Double p = this.grafo.getEdgeWeight(this.grafo.getEdge(parziale.get(i-1), parziale.get(i)));
			peso += p;
		}
		return peso;
	}
	
	public List<String> getBest() {
		return best;
	}

	public Double getPesoMax() {
		return pesoMax;
	}
}
