package ejercicio1;

import java.io.Serializable;

public class Artista implements Serializable{
	private String nombre;
	private char categoria;
	public Artista(String nombre, char categoria) {
		this.nombre = nombre;
		this.categoria = categoria;
	}
	public String getNombre() {
		return nombre;
	}
	public char getCategoria() {
		return categoria;
	}
	
}
