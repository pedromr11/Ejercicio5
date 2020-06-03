package com;

import java.io.Serializable;

/**
 * @author Alberto
 * Clase que implementa la interface Serializable para 
 * permitir que el objeto sea almacenado en un fichero
 */
public class Casa implements Serializable {
	
	/*
	 * Es importante indicar el número de serie de este objeto
	 * De esta forma, se podría comprobar si las versiones de la 
	 * clase Casa son las mismas para guardar que para leer.
	 * Un ejemplo puede ser que hayamos guardado un objeto Casa
	 * hace 10 años y ahora queramos cargarlo. Si las versiones
	 * son diferentes no se podría leer porque el tamaño de los 
	 * objetos son diferentes. Java, con el número de serie
	 * lo detectaría y nos indicaría que la versión es diferente. 
	 */
	private static final long serialVersionUID = 223434;
	private String nombreDueno;
	private int valor;
	
	
	/**
	 * 
	 * @param nombreDueno
	 * @param valor
	 */
	public Casa(String nombreDueno, int valor) {
		this.nombreDueno = nombreDueno;
		this.valor = valor;
	}
	
	/**
	 * @return el nombreDueno
	 */
	public String getNombreDueno() {
		return nombreDueno;
	}
	
	/**
	 * @param nombreDueno el nombreDueno a establecer
	 */
	public void setNombreDueno(String nombreDueno) {
		this.nombreDueno = nombreDueno;
	}
	
	/**
	 * @return el valor
	 */
	public int getValor() {
		return valor;
	}
	
	/**
	 * @param valor el valor a establecer
	 */
	public void setValor(int valor) {
		this.valor = valor;
	}
	
	@Override
	public String toString() {
		return "Nombre del dueño: "+nombreDueno + " Valor de la casa: "+valor;
	}
}
