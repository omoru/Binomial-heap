package Complexities;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import MonticuloBinomial.MonticuloBinomial;

public class MainComplexityUnion {
	static int numero_mediciones_por_punto = 1000;
	static int tam_min_monticulo = 2;
	static int tam_max_monticulo = 100002;
	static int clave_max = tam_max_monticulo + tam_min_monticulo ;// limite para generar clave aleatoria a insertar 8 entre [0..clave_max]



	public static void main(String[] args)

	{

		//-----------CONSTRUIR MONTICULOS-----------
		ArrayList<Integer> lista_tamanios = new ArrayList<Integer>();
		ArrayList<Double> insertion_times = new ArrayList<Double>();
	
		long inicio,fin;
		double time_acum = 0;
		for(int i = 0; i < numero_mediciones_por_punto; i++) {
				MonticuloBinomial mb = createRandomHeap(i);
				inicio = System.nanoTime();
				mb.insert(i+1000);
				fin = System.nanoTime();
				time_acum += (fin - inicio);	
			}
	
		for(int tam = tam_min_monticulo; tam <= tam_max_monticulo;tam*=2) {
			
			lista_tamanios.add(tam);
			time_acum = 0;
			

			for(int i = 0; i < numero_mediciones_por_punto; i++) {
				MonticuloBinomial mb1 = createRandomHeap(tam);
				MonticuloBinomial mb2 = createRandomHeap(tam);
				inicio = System.nanoTime();
				mb1.unionMonticulos(mb2.get_nodos());
				fin = System.nanoTime();
				time_acum += (fin - inicio);	
			}
			insertion_times.add((double) (time_acum / numero_mediciones_por_punto));
			System.out.println(tam);
		}
		
		//--guardar

		FileWriter writer;
		
		try {
			writer = new FileWriter("times.txt");
			for (int j = 0; j < insertion_times.size(); j++) {
				writer.append(String.valueOf(insertion_times.get(j)));
				writer.append("\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int len = lista_tamanios.size();
		try {
			writer = new FileWriter("tamanios.txt");
			for (int j = 0; j < len; j++) {
				writer.append(String.valueOf(lista_tamanios.get(j)));
				writer.append("\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	


	private static MonticuloBinomial createRandomHeap(int n_nodos) {
		if(n_nodos == 0){
			return new MonticuloBinomial();
		}
		Integer[] a = new Integer[n_nodos];
		for(int i =0; i < n_nodos;i++) {
			a[i]=i + 1;
		}
		MonticuloBinomial mb = new MonticuloBinomial();
		List<Integer> l = Arrays.asList(a);
		Collections.shuffle(l);
		for(int i = 0; i < n_nodos;i++)
			mb.insert(l.get(i));
		return mb;

	}
}
