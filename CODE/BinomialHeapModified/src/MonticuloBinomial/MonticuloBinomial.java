package MonticuloBinomial;

public class MonticuloBinomial {

	private NodoMonticuloBinomial nodos;
	private NodoMonticuloBinomial nodo_minimo;
	private int n_nodos;

	public MonticuloBinomial(){
		this.nodos = null;
		this.nodo_minimo= null;
		this.n_nodos = 0;
	}


	public NodoMonticuloBinomial get_nodos() {
		return this.nodos;
	}
	
	public NodoMonticuloBinomial get_nodo_minimo() {
		return this.nodo_minimo;
	}
	
	
	//-------------------------------------------Operaciones monticulo--------------------------------------------

	//Decrecer una clave dado el nodo y el nuevo valor
	public void decreaseKey(NodoMonticuloBinomial nodo,int value) {
		if(nodo.key < value) {
//			System.out.println("Error, valor mas grande que el nodo al decrecer");
			return;
		}

		NodoMonticuloBinomial parent = nodo.parent;
		nodo.key = value;
		
		//Flotamos el nodo hasta que sea menor que su padre
		while(parent != null && nodo.key < parent.key) {
//			System.out.println("Intercambiamos nodo con clave " + nodo.key + " por el nodo con clave " + parent.key);
			//intercambiamos la clave con su padre
			int aux = nodo.key;
			nodo.key = parent.key;
			parent.key = aux;

			nodo = parent;
			parent = parent.parent;
		}
		if(value < this.nodo_minimo.key)
			this.nodo_minimo=nodo;
	}

	//Decrecer una clave dado el valor y el nuevo valor
	public void decreaseKey(int key, int value) {
		if(key < value) {
//			System.out.println("Error, valor mas grande que el nodo al decrecer");
			return;
		}
		//Buscamos el nodo con clave == key
		NodoMonticuloBinomial nodo = this.nodos.findNode(key);
		if (nodo == null)
			return;
		decreaseKey(nodo,value);
		if(value < this.nodo_minimo.key)
			this.nodo_minimo=nodo;
	}

	//Busca el minimo en la lista principal
	public NodoMonticuloBinomial BuscaMinimo() {
		if(this.nodos == null) {
//			System.out.println("El monticulo esta vacio");
			return null;
		}
		NodoMonticuloBinomial aux = this.nodos;
		NodoMonticuloBinomial min_node  = this.nodos;
		int min = aux.key;
		while(aux != null) {
			if(aux.key < min) {
				min = aux.key;
				min_node = aux;
			}
			aux = aux.sibling;
		}
		return min_node;
	}

	//Inserta la clave en el monticulo
	public void insert(int clave) {
		//Creamos un b0 con la nueva clave
		NodoMonticuloBinomial new_node = new NodoMonticuloBinomial(clave);
		if(this.nodos == null) {
			this.nodos = new_node;
			this.n_nodos = 1;
			this.nodo_minimo=new_node;
//			System.out.println("El monticulo estaba vacio y se inserta el nodo con clave "+ clave);
		}
		else {
//			System.out.println("Se une el monticulo actual con el nodo con clave "+ clave +" :");
			unionMonticulos(new_node);
			this.nodo_minimo=BuscaMinimo();
			n_nodos++;

		}

	}

	//borra el nodo minimo del monticulo
	public void borraMin() {
		if(this.nodos==null) {
//			System.out.println("No hay nodos que borrar");
			return;
		}

		NodoMonticuloBinomial min_node = this.nodo_minimo;
		NodoMonticuloBinomial x = this.nodos;
		NodoMonticuloBinomial prev_x = null;
		//Necesitamos cortar el nodo min del monticulo
//		System.out.println("Cortamos el nodo minimo del monticulo.");
		while(x != min_node) {
			prev_x =x;
			x = x.sibling;
		}

		if(prev_x == null)
			this.nodos = x.sibling;
		else
			prev_x.sibling = x.sibling;

		//Una vez cortado, eliminamos el minimo(apuntado por x) y subimos sus hijos a la lista principal:
//		System.out.println("Subimos sus hijos a la lista principal.");
		x = x.child;
		NodoMonticuloBinomial new_node = x;

		//Indicamos que ahora no tienen padre los nodos que suben a la lista principal
		while( x != null) {
			x.parent = null;
			x = x.sibling;
		}

		if(this.nodos == null && new_node == null) {
//			System.out.println("El monticulo se queda vacio");
			n_nodos = 0;
		}

		else if(this.nodos == null && new_node != null) {
//			System.out.println("El monticulo habia quedado vacio al cortar el nodo minimo y ahora tiene los hijos en orden inverso al que estaban");
			this.nodos = new_node.reverse(null);
			n_nodos = this.nodos.getSize();
		}
		else if(this.nodos != null && new_node == null) {
//			System.out.println("El nodo minimo no tenia hijos");
			n_nodos = this.nodos.getSize();
		}

		else {
//			System.out.println("El nodo minimo tenia hijos y se hace la union de estos en orden inverso con el monticulo");
			unionMonticulos(new_node.reverse(null));
			this.n_nodos = this.nodos.getSize();
		}
		this.nodo_minimo = BuscaMinimo();

	}

	//union de este monticulo con el que se le indica por parametros
	public void unionMonticulos(NodoMonticuloBinomial other_heap) {

		//Hacemos un merge de los dos monticulos
		merge(other_heap);
		
		//Lo reestructuramos para que no pierda la propiedad de ser monticulo binomial
		NodoMonticuloBinomial prev_x = null;
		NodoMonticuloBinomial x = this.nodos;
		NodoMonticuloBinomial next_x = this.nodos.sibling;

		while(next_x != null) {
			//casos 1 y 2 : avanzamos ventana
			if((x.degree != next_x.degree) || 
					(next_x.sibling != null && next_x.sibling.degree == x.degree)) {
//				System.out.println("---Caso 1 o caso 2, avanzamos puntero a "+ next_x.key );
				prev_x = x;
				x = next_x;

			}
			else{	
				//case 3
				if( x.key <= next_x.key) {
//					System.out.println("---Caso 3, colgamos el nodo con clave "+ next_x.key + 
//							" del nodo con clave " + x.key);
					x.sibling = next_x.sibling;
					binomial_link(next_x,x);
				}
				//case 4
				else {
//					System.out.println("---Caso 4, colgamos el nodo con clave "+ x.key + 
//							" del nodo con clave " + next_x.key);
					if(prev_x == null) {
						this.nodos = next_x;
					}
					else prev_x.sibling = next_x;

					binomial_link(x, next_x);
					x = next_x;
				}
			}

			next_x = x.sibling;
		}
	}
	
	//Pintar monticulo
	public void displayHeap(){
		System.out.print("\nMonticulo(de abajo a arriba y de izq a dcha) : ");
		displayHeap(nodos);
		String binario = Integer.toBinaryString(this.n_nodos);
		System.out.println("\nRepresentacion binaria del numero de nodos: "+ binario +'\n');
	}
	
	//Vacia el monticulo
	public void vaciaMonticulo(){
		nodos = null;
		nodo_minimo=null;
		n_nodos = 0;
	}




	//------------------------------------------funciones auxiliares---------------------------------------------


	//Cuelga el nodo y del nodo z
	private void binomial_link(NodoMonticuloBinomial y, NodoMonticuloBinomial z) {
		y.parent = z;
		y.sibling = z.child;
		z.child = y;
		z.degree = z.degree + 1;
	}

	//Une los nodos de los 2 monticulos de forma ordenada por grado creciente
	private void merge(NodoMonticuloBinomial other_heap) {
		NodoMonticuloBinomial puntero1 = this.nodos;
		NodoMonticuloBinomial puntero2 = other_heap;

		while(puntero1 != null && puntero2 != null) {

			//si los grados son iguales, se colocan juntos
			if(puntero1.degree == puntero2.degree) {
				NodoMonticuloBinomial aux = puntero2;
				puntero2 = puntero2.sibling; // avanzamos al siguiente nodo del monticulo
				//introducimos el nodo en el monticulo 1
				aux.sibling = puntero1.sibling;
				puntero1.sibling = aux;
				// avanzamos puntero del monticulo 1
				puntero1 = puntero1.sibling;
			}
			else {

				//si es menor el arbol binomial del monticulo 1, tenemos que mirar si insertamos
				// el del monticulo 2 o si solo avanzamos puntero del monticulo1

				if(puntero1.degree < puntero2.degree) {
					if(puntero1.sibling == null || puntero1.sibling.degree > puntero2.degree) {
						NodoMonticuloBinomial aux = puntero2;
						puntero2 = puntero2.sibling; // avanzamos al siguiente nodo del monticulo
						//introducimos el nodo en el monticulo 1
						aux.sibling = puntero1.sibling;
						puntero1.sibling = aux;
						// avanzamos puntero del monticulo 1
					}
					puntero1 = puntero1.sibling;
				}
				else if(puntero1.degree > puntero2.degree) {
					NodoMonticuloBinomial aux = puntero1;
					puntero1 = puntero2;
					puntero2 = puntero2.sibling;
					puntero1.sibling=aux;
					if(aux == this.nodos)
						nodos = puntero1;
				}

			}

		}

		if(puntero1 == null) {
			puntero1 = this.nodos;
			while(puntero1.sibling != null)
				puntero1 = puntero1.sibling;
			puntero1.sibling = puntero2;	
		}
	}



	private void displayHeap(NodoMonticuloBinomial heap){
		if (heap != null){
			displayHeap(heap.child);
			System.out.print(heap.key +" ");
			displayHeap(heap.sibling);
		}
	}

	//DEvuelve el numero de nodos del monticulo
	public int getTam() {
		return this.n_nodos;
	}    
}