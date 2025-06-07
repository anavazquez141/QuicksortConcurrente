package Algoritmos;

import java.util.Arrays; 

// Clase que implementa el algoritmo Quicksort de forma secuencial
public  class SecuencialQuicksort { 

	// Método que aplica Quicksort recursivamente en una porción del arreglo
    public  static  void  quickSort ( int [] array, int low, int high) { 
    	// Verifica si hay más de un elemento en la porción
        if (low < high) { 
        	// Particiona el arreglo y obtiene el índice del pivote
            int  pivotIndex  = partition(array, low, high); 

         // Llama recursivamente para ordenar las dos mitades
             quickSort(array, low, pivotIndex - 1 ); // Parte izquierda del pivote
             quickSort(array, pivotIndex + 1 , high); // Parte derecha del pivote
        } 
    } 

   // Método que realiza la partición de la porción del arreglo
    public  static  int  partition ( int [] array, int low, int high) { 
        // Elegir el último elemento como pivote 
        int  pivot  = array[high]; // Se toma el último elemento como pivote
        int  i  = low - 1 ; // Índice del elemento menor

     // Recorre la porcion desde 'low' hasta 'high - 1'
        for ( int  j  = low; j < high; j++) { 
        	// Si el elemento actual es menor o igual al pivote
            if (array[j] <= pivot) { 
                i++; 

             // Intercambia array[i] con array[j]
                int  temp  = array[i]; 
                array[i] = array[j]; 
                array[j] = temp; 
            } 
        } 

        // Intercambia el elemento pivote con el elemento en i+1 
        int  temp  = array[i + 1 ]; 
        array[i + 1 ] = array[high]; 
        array[high] = temp; 

       // Devuelve la posición final del pivote
        return i + 1 ; 
    } 

    // Método auxiliar para imprimir el arreglo 
    public  static  void  printArray ( int [] array) { 
        System.out.println(Arrays.toString(array)); 
    } 

    // Método principal para ejecutar el algoritmo
    public  static  void  main (String[] args) { 
        int [] arr = { 9 , 7 , 5 , 11 , 12 , 2 , 14 ,3 , 10 , 6 }; // Arreglo de prueba
        System.out.println( "Arreglo original:" ); 
        printArray(arr); // Imprime el arreglo
        
     // Medición de tiempo para comparar con el algoritmo concurrente
        long inicio = System.nanoTime(); // Comienzo del tiempo

        // Llama al algoritmo de ordenamiento
         quickSort(arr, 0 , arr.length - 1 ); 
         
         long fin = System.nanoTime(); // Finaliza la medicion
         long duracionNs = fin - inicio; // Calcula el tiempo total en nanosegundos
         double duracionMs = duracionNs / 1_000_000.0; // Convierte a milisegundos
         
         System.out.println( "Arreglo ordenado:" ); 
         printArray(arr); 
         
      // Muestra el tiempo de ejecución en milisegundos
         System.out.println("\nTiempo de ejecución: " + duracionMs + " ms");

    } 
}