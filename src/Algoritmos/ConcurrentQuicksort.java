package Algoritmos;

import java.util.concurrent.RecursiveAction;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

// La clase ConcurrentQuicksort extiende RecursiveAction, lo que permite dividir tareas de forma paralela sin devolver resultados
public class ConcurrentQuicksort extends RecursiveAction {
	
    private int[] array;       // Arreglo que se va a ordenar
    private int low, high;     // Límites inferior y superior de la porcion del arreglo actual

    // Constructor: recibe el arreglo y los índices que definen la porcion a ordenar
    public ConcurrentQuicksort(int[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
    }

    // Método compute: define cómo se divide y procesa la tarea
    @Override
    protected void compute() {
        // Si hay más de un elemento en la porcion del arreglo
        if (low < high) {
            // Se obtiene el índice del pivote al particionar
            int pi = partition(array, low, high);

         // Ejecuta en paralelo dos tareas sobre las porciones izquierda y derecha del arreglo
            invokeAll(
                new ConcurrentQuicksort(array, low, pi - 1),
                new ConcurrentQuicksort(array, pi + 1, high)
            );
        }
    }

   // Método que realiza la partición de la porción del arreglo
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];    // Se toma el último elemento como pivote
        int i = (low - 1);        // Índice del elemento menor

        // Recorre la porcion desde 'low' hasta 'high - 1'
        for (int j = low; j < high; j++) {
        	// Si el elemento actual es menor o igual al pivote
            if (arr[j] <= pivot) {
                i++;
                // Intercambia arr[i] con arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Coloca el pivote en su posición final
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1; // Devuelve la posición final del pivote
    }
    
    // Método auxiliar para imprimir el arreglo
    public  static  void  printArray ( int [] array) { 
        System.out.println(Arrays.toString(array)); 
    } 

    // Método principal para ejecutar el algoritmo
    public static void main(String[] args) {
        int[] array = { 9 , 7 , 5 , 11 , 12 , 2 , 14 ,3 , 10 , 6 }; // Arreglo de prueba
        
        System.out.println( "Arreglo original:" ); 
        printArray(array); //Imprime el arreglo 

        ForkJoinPool pool = new ForkJoinPool(); // Crea un pool de hilos para ejecutar las tareas

        // Medición de tiempo para comparar con el algoritmo secuencial
        long inicio = System.nanoTime();

     // Ejecuta el algoritmo concurrente sobre toda la porción del arreglo
        pool.invoke(new ConcurrentQuicksort(array, 0, array.length - 1));
        pool.shutdown(); // Cierra el pool
        
        long fin = System.nanoTime(); // Finaliza la medicion 
         
        // Muestra el arreglo ordenado
        System.out.println("Arreglo ordenado:");
        printArray(array);
        
     // Conversión de nanosegundos a milisegundos
        long duracionNs = fin - inicio;
        double duracionMs = duracionNs / 1_000_000.0;

        System.out.println("\nTiempo de ejecución: " + duracionMs + " ms"); // Tiempo total

    }
}