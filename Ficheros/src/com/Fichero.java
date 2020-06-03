package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author Alberto
 * Programa que muestra un men� para manejar ficheros de lectura o escritura.
 */
public class Fichero {
	
	 /* Se crea una variable de clase Scanner para manejar todas las entradas de teclado
	 * con el mismo objeto Scanner y minimizar los posibles problemas derivados de su uso.
	 * La creamos static porque vamos a utilizar el programa sin crear un objeto de la
	 * clase actual "Fichero"
	 */
	static Scanner sc = new Scanner(System.in);
	
	/**
	 * Muestra un men� principal. Al elegir las opciones se invocan a los m�todos espec�ficos
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Bienvenido al programa de aprendizaje de lectura/escritura de ficheros");
		
		boolean terminar = false;
		
		//En entrada/salida es muy importante controlar la excepciones.
		try {
			while(!terminar) {
				
				
				System.out.println("\n\n*********MEN� PRINCIPAL*********\n\n");
				
				System.out.println("1.- Opciones de lectura.");
				System.out.println("2.- Opciones de escritura.");
				System.out.println("3.- Salir.");			
				System.out.print("Selecciona la opci�n deseada: ");	
				
				int opcion = sc.nextInt();
				
				switch (opcion) {
					case 1: //Opciones de lectura
						mostrarOpcionesLectura();
						break;
					
					case 2: //Opciones de escritura
						mostrarOpcionesEscritura();
						break;

					case 3:
						System.out.println("Gracias por utilizar este programa.");
						terminar = true;
						break;
						
					default:
						System.out.println("Opci�n incorrecta. Elige una opci�n correcta del men�.");				
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			sc.close();
		}
	}
	
	
	/**
	 * M�todo que muestra un men� con opciones de lectura.
	 * @throws Exception
	 */
	static void mostrarOpcionesLectura() throws IOException,Exception { //Indicamos que se podr�a lanzar una excepci�n para que sea manejada por el main
				
		try {

			System.out.println("\n\n*******OPCIONES DE LECTURA**********\n\n");

			System.out.print("Por favor, introduce la ruta absoluta o relativa del fichero: ");
			String ruta = sc.next();

			System.out.println("\n---------\n");

			System.out.println("1.- Comprobar si existe el fichero.");
			System.out.println("2.- Comprobar si se puede crear el fichero en esa ubicaci�n.");
			System.out.println("3.- Crear el fichero en esa ubicaci�n.");
			System.out.println("4.- Leer e imprimir toda la informaci�n con Scanner.");
			System.out.println("5.- Leer e imprimir toda la informaci�n con FileReader-BufferedReader.");
			System.out.println("6.- Leer e imprimir la informaci�n de un fichero binario");
			System.out.println("7.- Leer objetos serializados del tipo Casa.");
			System.out.print("Selecciona la opci�n deseada: ");	

			int opcion = sc.nextInt();

			//Se crea una referencia en la ruta introducida por el usuario con un objeto File
			File f = new File(ruta); 
			/* Muchos objetos de clases de escritura/lectura utilizan la clase File
			 * para indicar la ubicaci�n del fichero. Es posible omitir la utilizaci�n
			 * de la clase File y solamente utilizar un String con la direcci�n al fichero.
			 * Se suele recomendar el uso de File porque nos ofrece m�todos interesantes
			 */

			switch (opcion) {
			case 1: //Comprobar si existe el fichero

				if(f.exists()) 
					System.out.println("El fichero ya existe y est� ubicado en: "+f.getAbsolutePath());
				else 
					System.out.println("El fichero no existe en: "+f.getAbsolutePath());

				break;

			case 2: //Comprobar si se puede crear el fichero en la ruta indicada

				if(f.canRead()) 
					System.out.println("Es posible crear o modificar el fichero en la ubicaci�n: "+f.getAbsolutePath());
				else
					System.out.println("El sistema no permite crear el fichero en la ubicaci�n: "+f.getAbsolutePath());

				break;

			case 3: //Crear un fichero en la ruta indicada

				boolean retornoCrearOK = f.createNewFile(); //Devolver� true si ha podido crear el fichero

				if(retornoCrearOK)
					System.out.println("Se ha creado el fichero correctamente en: "+f.getAbsolutePath());
				else
					System.out.println("No se ha podido crear el fichero en: "+f.getAbsolutePath());

				break;

			case 4: /*Leer e imprimir toda la informaci�n con Scanner.
				      Si en la ruta indicada no hay fichero, el sistema lanzar� una excepci�n FileNotFoundException
				      Esta excepci�n es recogida en este m�todo para indicar que no se ha encontrado el fichero y para 
				      evitar que el programa finalice*/

				//Se crea un Scanner con toda la informaci�n que contiene el archivo
				Scanner lector = new Scanner(f);

				System.out.println("Informacion que contiene el fichero: ");

				while (lector.hasNextLine()) { //Vamos a leer l�nea por l�nea
					System.out.println(lector.nextLine());
				}

				lector.close(); //Es importante cerrar el archivo le�do con Scanner		

				break;

			case 5: /*Leer e imprimir toda la informaci�n con FileReader-BufferedReader.
					  Si en la ruta indicada no hay fichero, el sistema lanzar� una excepci�n FileNotFoundException
					  Esta excepci�n es recogida en este m�todo para indicar que no se ha encontrado el fichero y para 
					  evitar que el programa finalice */

				//FileReader necesita la ruta del fichero que contiene f (File)
				//FileReader contiene pocos m�todos de lectura, se suele utilizar con BufferedReader
				FileReader fileR = new FileReader (f); 

				//BufferedReader utiliza un b�ffer por lo que reduce el acceso al fichero en disco, incrementando la eficiencia
				BufferedReader bufferR = new BufferedReader(fileR); //Se le pasa el FileReader creado anteriormente

				//Las instrucciones anteriores se suelen utiliza en la misma l�nea, por ejemplo:
				// bufferR = new BufferedReader(new FileReader(f));

				// Lectura del fichero
				String linea;
				//readLine devuelve la l�nea le�da o null si ha llegado al final de archivo
				//Imprimimos la informaci�n hasta que el lector devuelva null
				while( (linea=bufferR.readLine()) != null ) {
					System.out.println(linea);
				}

				//Es importante cerrar todos las conexiones
				fileR.close();
				bufferR.close();

				break;

			case 6: //Leer un archivo binario

				//No utilizamos un b�ffer para leer para simplificar el c�digo de ejemplo de esta pr�ctica
				FileInputStream fis = new FileInputStream(f);
				DataInputStream dis = new DataInputStream(fis);

				int datoLeido; //Se almacena el n�mero de datos le�dos
				try { 
					while (true) {
						/* Lee de un entero en un entero (int)
						 * Realmente lee 4 bytes que ocupa un entero e
						 * interpreta que esos 0 y 1 como un entero
						 * Cuando llega al final de archivo lanza
						 * EOFException que utilizamos para saber el final.
						 */
						datoLeido = dis.readInt();  
						System.out.println(datoLeido);  
					}
				} catch (EOFException e) { //Se ha llegado al final del fichero
					System.out.println("Fin del fichero");
					fis.close();
					dis.close();
				}

				break;
				
			case 7: //Leer fichero con objetos de la clase Casa

				FileInputStream flujoFichero = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(flujoFichero);
				 
				/*
				 * Para leer necesitamos seguir el mismo orden de guardado, ya
				 * que se trata de datos binarios y se realizan leyendo
				 * unos bytes determinados en funci�n de lo que ocupa cada
				 * tipo de objetos.
				 * Es necesario hacer un cast del tipo de objeto.
				 */
				String cadena = (String) ois.readObject(); 	//Objeto tipo String
				Casa casa1 = (Casa) ois.readObject(); 	//Objetos tipo Casa
				Casa casa2 = (Casa) ois.readObject();
				
				System.out.println("Los datos que se han le�do son: ");
				System.out.println(cadena);
				System.out.println(casa1.toString());
				System.out.println(casa2.toString());				
				
				//Cerrar las conexiones
				ois.close();	
				flujoFichero.close();

				break;


			default:
				System.out.println("Opci�n incorrecta. Elige una opci�n del men�.");				
			}
			
		}
		catch (FileNotFoundException e) {
			//No se para la ejecuci�n porque lo interpretamos como un error menor
			System.err.println("No existe el fichero en la direcci�n indicada. Se devuelve la ejecuci�n al men� principal");
		}	 
		catch (IOException e) {
			throw e; //Lanzamos la excepci�n para que sea manejada por el m�todo main.
		}	
		catch (Exception e) {
			throw e; //Lanzamos la excepci�n para que sea manejada por el m�todo main.
		}	
	}
	
	
	/**
	 * M�todo que muestra un men� con opciones de escritura.
	 * @throws IOException
	 */
	static void mostrarOpcionesEscritura() throws IOException, Exception{
		
		try {

			System.out.println("\n\n*******OPCIONES DE ESCRITURA**********\n\n");

			System.out.print("Por favor, introduce la ruta absoluta o relativa del fichero: ");
			String ruta = sc.next();

			System.out.println("\n---------\n");

			System.out.println("1.- Comprobar si existe el fichero.");
			System.out.println("2.- Comprobar si se puede crear el fichero en esa ubicaci�n.");
			System.out.println("3.- Crear el fichero en esa ubicaci�n.");
			System.out.println("4.- Escribir informaci�n con PrintWriter.");
			System.out.println("5.- Escribir informaci�n con BufferedWriter.");
			System.out.println("6.- Crear un fichero binario con 10 n�meros enteros");
			System.out.println("7.- Crear un fichero con objetos de la clase Casa");
			System.out.print("Selecciona la opci�n deseada: ");	

			int opcion = sc.nextInt();

			//Se crea una referencia en la ruta introducida por el usuario con la clase File
			File f = new File(ruta); 
			/* Muchos objetos de clases de escritura/lectura utilizan la clase File
			 * para indicar la ubicaci�n del fichero. Es posible omitir la utilizaci�n
			 * de la clase File y solamente utilizar un String con la direcci�n al fichero.
			 * Se suele recomendar el uso de File porque nos ofrece m�todos interesantes
			 */


			switch (opcion) {
			case 1: //Comprobar si existe el fichero

				if(f.exists()) 
					System.out.println("El fichero ya existe y est� ubicado en: "+f.getAbsolutePath());
				else 
					System.out.println("El fichero no existe en: "+f.getAbsolutePath());

				break;

			case 2: //Comprobar si se puede crear el fichero en la ruta indicada

				if(f.canRead()) 
					System.out.println("Es posible crear o modificar el fichero en la ubicaci�n: "+f.getAbsolutePath());
				else
					System.out.println("El sistema no permite crear el fichero en la ubicaci�n: "+f.getAbsolutePath());

				break;

			case 3: //Crear un fichero en la ruta indicada

				boolean retornoCrearOK = f.createNewFile(); //Devolver� true si ha podido crear el fichero

				if(retornoCrearOK)
					System.out.println("Se ha creado el fichero correctamente en: "+f.getAbsolutePath());
				else
					System.out.println("No se ha podido crear el fichero en: "+f.getAbsolutePath());

				break;

			case 4: /*Escribir informaci�n con PrintWriter*/

				/* PrintWriter crea un fichero en la ruta indicada. 
				 * Si el fichero existe, lo borra y lo vuelve a crear.
				 * Para a�adir texto al final tendr�amos que utilizar 
				 * un objeto FileWriter (ejemplo en la opci�n 5 del switch). 
				 */
				PrintWriter pw = new PrintWriter(f);

				//PrintWriter ofrece m�todos parecidos a los de System.out
				pw.println("Se crea el archivo con PrintWriter sin la opci�n de a�adir.");
				pw.print("Se introduce texto con print");
				pw.println("Ahora con println");
				pw.println("\n\nFin del fichero\n************");

				//Se debe cerrar el archivo
				pw.close();
				
				System.out.println("Se ha introducido texto en el fichero: "+f.getAbsolutePath());

				break;

			case 5: /*Escribir informaci�n con BufferedWriter.*/

				/* En esta ocasi�n queremos a�adir texto al final del fichero. Se 
				 * debe crear un objeto FileWriter con la opci�n true en el constructor
				 * BufferedWriter siempre se tiene que crear con un FileWriter 
				 */
				FileWriter fw = new FileWriter(f,true); //Si no existe el fichero, lo crea.
				BufferedWriter bufferEscribir = new BufferedWriter(fw);
				PrintWriter esc = new PrintWriter(bufferEscribir);

				esc.println("- Estoy a�adiendo texto al final de un fichero con un b�ffer\n***");


				//Se deben cerrar los objetos
				bufferEscribir.flush(); //Importante para que se vuelque la informaci�n en disco antes de cerrar el archivo
				bufferEscribir.close();
				esc.close();						
				fw.close();

				break;

			case 6: //Crear un fichero binario con 10 n�meros enteros

				//Ser�a recomendable utilizar un bucle pero no lo utilizaremos para simplificar el ejemplo
				FileOutputStream fos = new FileOutputStream(f);
				DataOutputStream dos = new DataOutputStream(fos);

				for (int i = 0; i < 10; i++) {
					dos.writeInt(i);
				}

				dos.close();				     
				fos.close();
				
				System.out.println("Se ha creado el archivo binario correctamente");

				break;
				
			case 7: //Crear un fichero con objetos de la clase Casa
				
				FileOutputStream flujoFichero = new FileOutputStream(f);
				ObjectOutputStream oos = new ObjectOutputStream(flujoFichero);
				
				Casa casa1 = new Casa("Due�o 1", 300000);
				Casa casa2 = new Casa("Due�o 2", 150000);
						 
				// Escribir objetos en el fichero
			 	// Se pueden almacenar objetos de tipo String
				oos.writeObject("Guardo 2 objetos Casa en el fichero: "+f.getAbsoluteFile());
				oos.writeObject(casa1); 		   	
				oos.writeObject(casa2);
				
				flujoFichero.close();
				oos.close();
				
				System.out.println("Objetos Casa creados correctamente en "+f.getAbsolutePath());
				
				break;

			default:
				System.out.println("Opci�n incorrecta. Elige una opci�n del men�.");				
			}

		}
		catch (FileNotFoundException e) {
			//No se para la ejecuci�n porque lo interpretamos como un error menor
			System.err.println("No existe el fichero en la direcci�n indicada. Se devuelve la ejecuci�n al men� principal");
		}	 
		catch (IOException e) {
			throw e; //Lanzamos la excepci�n para que sea manejada por el m�todo main.
		}	
		catch (Exception e) {
			throw e; //Lanzamos la excepci�n para que sea manejada por el m�todo main.
		}	
		
	}
}
