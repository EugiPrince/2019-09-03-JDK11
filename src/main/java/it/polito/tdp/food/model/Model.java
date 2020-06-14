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
}
