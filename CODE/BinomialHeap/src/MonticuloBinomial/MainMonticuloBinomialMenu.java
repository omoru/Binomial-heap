package MonticuloBinomial;
import java.util.Scanner;

public class MainMonticuloBinomialMenu {

	public static void main(String[] args)

	{
		Scanner scan = new Scanner(System.in);
		MonticuloBinomialConPrints mb = new MonticuloBinomialConPrints();

		do {
			printMenu();
			int choice = scan.nextInt();      
			
			switch (choice){
			case 0:
				return;
			case 1 : 
				System.out.println("Introduce numero a insertar:");
				mb.insert(scan.nextInt()); 
				break;                          
			case 2 : 
				mb.borraMin();                   
				break;    
			case 3 : 
				System.out.println("Numero de nodos = "+ mb.getTam());
				break;                                   
			case 4 : 
				int clave,new_value;
				System.out.println("Introduce la clave a decrecer:");
				clave = scan.nextInt();
				System.out.println("Introduce el nuevo valor:");
				new_value = scan.nextInt();
				mb.decreaseKey(clave,new_value);
				break;                                   
			case 5 : 
				mb.vaciaMonticulo();
				break;         
			case 6 : 
				System.out.println("Minimo: " + mb.minimo().key);
				break;    
			default :  
				System.out.println("Entrada no valida \n ");
				break;   
			}

			mb.displayHeap();
		} while (true);  

	}

	private static void printMenu() {
		System.out.println("-----Menu monticulo binomial-----");
		System.out.println("1. Insertar un numero en el monticulo. ");
		System.out.println("2. Borrar el minimo del monticulo. ");
		System.out.println("3. Ver numero de nodos del monticulo. ");            
		System.out.println("4. Decrecer clave de un nodo proporcionando la clave. ");
		System.out.println("5. Vaciar monticulo ");
		System.out.println("6. Ver el minimo del monticulo");
		System.out.println("0. Exit");
	}
}
