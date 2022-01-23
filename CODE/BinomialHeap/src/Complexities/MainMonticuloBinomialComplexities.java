package Complexities;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import MonticuloBinomial.MonticuloBinomial;


public class MainMonticuloBinomialComplexities {

	static int numero_mediciones_por_punto = 10;
	static int tam_min_monticulo = 50000;
	static int tam_max_monticulo = 100000;
	static int avance = 1000;
	static int clave_max = tam_max_monticulo;// limite para generar clave aleatoria a insertar 8 entre [0..clave_max]



	public static void main(String[] args)

	{

		//-----------CONSTRUIR MONTICULOS-----------
		ArrayList<Integer> lista_tamanios = new ArrayList<Integer>();
		ArrayList<Double> insertion_times = new ArrayList<Double>();
	
		
		for(int tam = tam_min_monticulo; tam <= tam_max_monticulo;tam+=avance) {
			lista_tamanios.add(tam);
		}

		for(int tam = tam_min_monticulo; tam <= tam_max_monticulo;tam+=avance) {
			//creamos un array de monticulos aleatorios del mismo tamaño
			ArrayList<MonticuloBinomial> array_mb = new ArrayList<MonticuloBinomial>();
			for(int i = 0; i < numero_mediciones_por_punto; i++) {
				MonticuloBinomial mb = createRandomHeap(tam);
				array_mb.add(mb);
			}
			//GENERAMOS CLAVE ALEATORIA
			Random rand = new Random();
			int clave = rand.nextInt(clave_max);
			long inicio = System.nanoTime();
			for(int k = 0; k < numero_mediciones_por_punto;k++) {
				array_mb.get(k).insert(clave);
			}
			long fin = System.nanoTime();
			double time = (double)((fin - inicio) / numero_mediciones_por_punto);			
			insertion_times.add(time);

		}



		//--guardar

		FileWriter writer;
		
		try {
			writer = new FileWriter("C:\\Users\\Oscar\\Desktop\\practiamarp\\times.txt");
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
			writer = new FileWriter("C:\\Users\\Oscar\\Desktop\\practiamarp\\tamanios.txt");
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
		Integer[] a = new Integer[clave_max];
		for(int i =0; i < a.length;i++) {
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
