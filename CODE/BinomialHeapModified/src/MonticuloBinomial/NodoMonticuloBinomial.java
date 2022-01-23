package MonticuloBinomial;

public class NodoMonticuloBinomial {
	
	int key;
	int degree;
	NodoMonticuloBinomial child;
	NodoMonticuloBinomial parent;
	NodoMonticuloBinomial sibling;
	
	public NodoMonticuloBinomial(int clave) {
		this.key = clave;
		this.degree = 0;
		this.child = null;
		this.parent = null;
		this.sibling = null;
	}
	

	public int getSize() {
		int size_child;
		int size_sibling;
		if(this.child == null)
			size_child = 0;
		else {
			size_child = this.child.getSize();
		}
		
		if(this.sibling == null)
			size_sibling = 0;
		else
			size_sibling = sibling.getSize();
		
		return 1 + size_child + size_sibling;
		 
	}

	//Invierte el sentido de los hermanos (reverse)
	public NodoMonticuloBinomial reverse(NodoMonticuloBinomial sibling_aux) {
		
		NodoMonticuloBinomial reversed;
		if(this.sibling != null)
			reversed = this.sibling.reverse(this);
		else
			reversed = this;
		this.sibling = sibling_aux;
		return reversed;
	}

	//Devuelve el nodo con la clave indicada
	public NodoMonticuloBinomial findNode(int clave) {
		
		NodoMonticuloBinomial aux = this;
		NodoMonticuloBinomial ret = null;
		while(aux != null) {
			if(aux.key == clave)
				return aux;
			if(aux.child == null)
				aux = aux.sibling;
			else {
				
				ret = aux.child.findNode(clave);
				if( ret == null)
					aux = aux.sibling;
				else
					return ret;
			}
		}
		return ret;
	}
	
	
}
