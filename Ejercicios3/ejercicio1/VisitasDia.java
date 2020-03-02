package ejercicio1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
public class VisitasDia {
	private int[]dia;
	ArrayList<Visita>visitas;
	HashMap<Hora, Integer>cuentapersonas;
	public VisitasDia(int[]day) {
		dia=day;
		visitas=new ArrayList<Visita>();
		cuentapersonas=new HashMap<Hora, Integer>();
	}
	public boolean aniadeVisita(Visita v) throws IOException {
		File f= new File("tiempos_visita.txt");
		if(!f.exists()) {
			System.out.println("Error: no se pudo encontrar el archivo 'tiempos_visita.txt'");
			return false;
		}
		if(available(v.getHora_visita())) {
			if(capacityCheck(v.getHora_visita(), v.getCantidad())) {
				visitas.add(v);
				int gente=0;
				if(cuentapersonas.containsKey(v.getHora_visita()))
					gente=cuentapersonas.get(v.getHora_visita());
				cuentapersonas.put(v.getHora_visita(), gente+v.getCantidad());
				return true;
			}
			else {
				System.out.println("Error: Aforo máximo excedido");
				return false;
			}		
		}
		else {
			System.out.println("La hora a la que trata de acceder no está disponible");
			return false;
		}
	}
	
	public void guardarAFichero(String fichero) throws FileNotFoundException, IOException {
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(fichero));
		oos.writeObject(visitas);
		oos.close();
	}
	
	public int cargarVisitas(String fichero) throws FileNotFoundException, IOException, ClassNotFoundException {
		int cont=0;
		File f=new File(fichero);
		if(!f.exists())
			System.out.println("Error: el fichero no existe");
		else {
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(f));
			ArrayList<Visita> aux=(ArrayList<Visita>)ois.readObject();
			ois.close(); 
			for(Visita v:visitas) {
				if((available(v.getHora_visita()))&&(capacityCheck(v.getHora_visita(), v.getCantidad()))) {
					visitas.add(v);
					cont++;
					int gente=0;
					if(cuentapersonas.containsKey(v.getHora_visita()))
						gente=cuentapersonas.get(v.getHora_visita());
					cuentapersonas.put(v.getHora_visita(), gente+v.getCantidad());
				}
			}
		}
		return cont;
	}
	
	public static void verFichero(String fichero) throws FileNotFoundException, IOException, ClassNotFoundException {
		File f=new File(fichero);
		if(!f.exists())
			System.out.println("Fichero no encontrado");
		else {
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(f));
			ArrayList<Visita>aux=(ArrayList<Visita>)ois.readObject();
			ois.close();
			for(Visita v:aux) {
				System.out.println("Nombre: "+v.getNombre()+", cantidad de personas: "+v.getCantidad()+", hora: "+v.getHora_visita().getHora()+":"+v.getHora_visita().getMinutos());
			}
		}
	}
	
	public boolean actualizarVista(String nombre) throws IOException {
		for(Visita v:visitas) {
			if(v.getNombre().equals(nombre)) {
				char res;
				do {
				System.out.println("¿Desea cambiar la hora de visita(H) o el número de personas(N)?");
				res=Consola.leeChar();
				}while((res!='H')&&(res!='h')&&(res!='n')&&(res!='N'));
				if((res=='H')||(res=='h')) {
					int hor;
					int min;
					Hora ordua;
					System.out.println("Introduzca la hora:");
					hor=Consola.leeInt();
					System.out.println("Introduzca los minutos:");
					min=Consola.leeInt();
					ordua=new Hora(hor, min);
					if(available(ordua)) {
						v.setHora_visita(ordua);
						return true;
					}
				}
				else {
					int numpers;
					do {
					System.out.println("Introduzca nuevo número de personas:");
					numpers=Consola.leeInt();
					}while(numpers<1);
					int diferencia=numpers-v.getCantidad();
					if(capacityCheck(v.getHora_visita(), diferencia)) {
						v.setCantidad(numpers);
						return true;
					}
				}	
			}
		}
		return false;
	}
	
	public void crearInforme() throws FileNotFoundException {
		File f=new File("ficheros/ejer3.1/report_"+dia[1]+"_"+dia[0]);
		PrintWriter pw=new PrintWriter(f);
		Iterator<Visita> it=visitas.iterator();
		while(it.hasNext()) {
			Visita v=it.next();
			pw.println(v.getNombre()+"\t"+v.getHora_visita().getHora()+":"+v.getHora_visita().getMinutos()+"\t"+v.getCantidad()+" personas");
		}
		pw.close();
	}
	
	public HashMap<Hora, Integer> mapaLibres() throws IOException{
		HashMap<Hora, Integer> aux=new HashMap<Hora, Integer>();
		File f=new File("tiempos_visita.txt");
		if(!f.exists()) {
			System.out.println("Fichero no encontrado");
			return null;
		}
		BufferedReader br=new BufferedReader(new FileReader(f));
		String linea=br.readLine();
		while(linea!=null) {
			String[] str=linea.split("\t");
			Hora hr=new Hora(Integer.valueOf(str[0]), Integer.valueOf(str[1]));
			aux.put(hr, 50);
			linea=br.readLine();
		}
		br.close();
		for(Visita v:visitas) {
			aux.put(v.getHora_visita(), aux.get(v.getHora_visita())-v.getCantidad());
		}
		return aux;
	}
	
	public Hora tiempoVisitaMasCercano(int hora, int minutos) throws IOException {
		int minut=hora*60+minutos;
		ArrayList<Hora>libr=new ArrayList<Hora>();
		Iterator<Hora> it=mapaLibres().keySet().iterator();
		while(it.hasNext()) {
			Hora aux1=it.next();
			if(mapaLibres().get(aux1)>0) {
				libr.add(aux1);
			}
		}
		int min=Integer.MAX_VALUE;
		Hora ret=new Hora(12,00);
		for(Hora h:libr) {
			int aux2=h.getHora()*60+h.getMinutos();
			int diferencia=Math.abs(aux2-minut);
			if(diferencia<min) {
				min=diferencia;
				ret=h;
			}
		}
		return ret;
	}
	
	
	
	//Métodos que no pide el ejercicio
	public boolean available(Hora otrahora) throws IOException {
		File f= new File("tiempos_visita.txt");
		if(!f.exists()) {
			return false;
		}
		BufferedReader br=new BufferedReader(new FileReader(""));
		String linea=br.readLine();
		while(linea!=null) {
			String[] aux=linea.split("\t");
			Hora horaux=new Hora(Integer.valueOf(aux[0]), Integer.valueOf(aux[1]));
			if(horaux.equals(otrahora)) {
				br.close();
				return true;
			}
			linea=br.readLine();
		}
		br.close();
		return false;
	}
	public boolean capacityCheck(Hora aux, int personas) {
		if(cuentapersonas.containsKey(aux)) {
			if(cuentapersonas.get(aux)+personas>50)
				return false;
		}
		return true;
	}
	
}
