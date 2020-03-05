package ejercicio1;

import java.io.IOException;
import java.util.HashMap;

public class Prueba1 {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Artista a1=new Artista("Juan", 'a');
		Artista a2=new Artista("Fulano", 'b');
		Concierto c1=new Concierto(a1, 288);
		Concierto c2=new Concierto(a2, 100);
		HashMap<Concierto, Integer> mapa=new HashMap<Concierto, Integer>();
		mapa.put(c1, 288);
		mapa.put(c2, 99);
		GestorConciertos gc1=new GestorConciertos(mapa);
		gc1.serializar("ficheros/ejer0/conciertos.bin");
		gc1.visualizar("ficheros/ejer0/conciertos.bin");

	}

}
