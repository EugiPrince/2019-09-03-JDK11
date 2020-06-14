package it.polito.tdp.food.model;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private Graph<String, DefaultWeightedEdge> grafo;
	private FoodDao dao;
	
	public Model() {
		this.dao = new FoodDao();
	}
	
	public void creaGrafo(int C) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		List<String> vertici = this.dao.getPortionDisplayName(C);
		Graphs.addAllVertices(this.grafo, vertici);
		
		System.out.println(this.grafo);
	}
}
