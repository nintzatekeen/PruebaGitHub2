package ejercicio1;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Ejercicio1 {
	public static void main(String[] args) throws IOException {
		File f=new File("nums.bin");
		DataOutputStream dos=new DataOutputStream(new FileOutputStream(f));
		System.out.println("Introduzca número");
		int num=Consola.leeInt();
		while(num!=0){
			dos.writeInt(num);
			System.out.println("Introduzca número");
			num=Consola.leeInt();
		}
		dos.close();
		DataInputStream dis=new DataInputStream(new FileInputStream(f));
		BufferedWriter bw=new BufferedWriter(new FileWriter(new File("nums.txt")));
		while(dis.available()>0) {
			bw.write(String.valueOf(dis.readInt()));
			bw.newLine();
		}
		bw.close();
		dis.close();
	}
}
