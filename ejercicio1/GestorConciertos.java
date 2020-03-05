package ejercicio1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

public class GestorConciertos {
	HashMap<Concierto, Integer>mapaconciertos;

	public GestorConciertos(HashMap<Concierto, Integer> mapaconciertos) {
		this.mapaconciertos = mapaconciertos;
	}
	public void serializar(String nomfich) throws IOException {
		Iterator<Concierto>it=mapaconciertos.keySet().iterator();
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(nomfich));
		while(it.hasNext()) {
			Concierto aux=it.next();
			if(mapaconciertos.get(aux)>=aux.getAforo()) {
				oos.writeObject(aux);
			}
		}
		oos.writeObject(null);
		oos.close();
	}
	public void visualizar(String nomfich) throws FileNotFoundException, IOException, ClassNotFoundException {
		File f=new File(nomfich);
		if(!f.exists()) {
			System.out.println("No existe el fichero especificado");
		}
		else {
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(f));
			Concierto aux=(Concierto)ois.readObject();
			while(aux!=null) {
				System.out.println("Artista: "+aux.getArt().getNombre()+", categoría: "+aux.getArt().getCategoria()+" | aforo: "+aux.getAforo());
				aux=(Concierto)ois.readObject();
			}
			ois.close();
		}
	}
	
}
