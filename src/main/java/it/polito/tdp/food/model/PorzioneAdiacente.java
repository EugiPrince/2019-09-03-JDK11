package it.polito.tdp.food.model;

public class PorzioneAdiacente {

	private String nomePorzione;
	private Double peso;
	
	/**
	 * @param nomePorzione
	 * @param peso
	 */
	public PorzioneAdiacente(String nomePorzione, Double peso) {
		super();
		this.nomePorzione = nomePorzione;
		this.peso = peso;
	}

	public String getNomePorzione() {
		return nomePorzione;
	}

	public void setNomePorzione(String nomePorzione) {
		this.nomePorzione = nomePorzione;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}
}
