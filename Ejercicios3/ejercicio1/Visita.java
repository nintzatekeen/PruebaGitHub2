package ejercicio1;

import java.io.Serializable;

public class Visita implements Serializable{
	//yoksetioxd
	public String getNombre() {
		return nombre;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public void setHora_visita(Hora hora_visita) {
		this.hora_visita = hora_visita;
	}
	public Hora getHora_visita() {
		return hora_visita;
	}
	private String nombre;
	private int cantidad;
	private Hora hora_visita;
	public Visita(String name, int amount, Hora time) {
		nombre=name;
		cantidad=amount;
		hora_visita=time;
	}
}
