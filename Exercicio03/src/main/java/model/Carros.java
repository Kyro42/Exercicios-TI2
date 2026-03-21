package model;

public class Carros {
	private int id;
	private String nome;
	private String marca;
	private double valor;
	private Boolean eletrico;
	
	public Carros() {
		this.id = -1;
		this.nome = "";
		this.marca = "";
		this.valor = 0.0;
		this.eletrico = false;
	}
	public Carros(int id, String nome, String marca, double valor, Boolean eletrico) {
		this.id = id;
		this.nome = nome;
		this.marca = marca;
		this.valor = valor;
		this.eletrico = eletrico;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public Boolean getEletrico() {
		return eletrico;
	}
	public void setEletrico(Boolean eletrico) {
		this.eletrico = eletrico;
	}
	@Override
	public String toString() {
		return "Carro [ID = " + id + ", nome = " + nome + ", marca = " + marca + ", valor = " + valor + ", Eletrico? " + (eletrico ? "sim" : "nao") + "]";
	}	
}
