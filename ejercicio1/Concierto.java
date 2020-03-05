package ejercicio1;

import java.io.Serializable;

public class Concierto implements Serializable{
	public Artista getArt() {
		return art;
	}
	public int getAforo() {
		return aforo;
	}
	private Artista art;
	private int aforo;
	public Concierto(Artista art, int aforo) {
		this.art = art;
		this.aforo = aforo;
	}
	
}
