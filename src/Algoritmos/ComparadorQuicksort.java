package Algoritmos;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

//Clase principal que compara QuickSort secuencial y concurrente
public class ComparadorQuicksort {

	// Método para generar un arreglo aleatorio de tamaño n
    public static int[] generarArrayAleatorio(int n) {
        Random rand = new Random(); // Generador de números aleatorios
        int[] array = new int[n];  // Crea un arreglo de tamaño n
        for (int i = 0; i < n; i++) {
            array[i] = rand.nextInt(100000); // Asigna valores aleatorios entre 0 y 99.999
        }
        return array;  // Devuelve el arreglo generado
    }

   // Método para copiar un arreglo
    public static int[] copiarArray(int[] original) {
        return Arrays.copyOf(original, original.length);  // Clona el arreglo original
    }

    // Método principal
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Objeto para leer entrada desde consola
        System.out.print("Ingrese el tamaño del array: ");
        int n = scanner.nextInt(); // Lee el valor de n desde teclado

     // Se genera un arreglo aleatorio y se crean dos copias para pruebas
        int[] arrayOriginal = generarArrayAleatorio(n);
        int[] copiaSecuencial = copiarArray(arrayOriginal);
        int[] copiaConcurrente = copiarArray(arrayOriginal);

        // Medición de tiempo para el algoritmo secuencial
        long inicioSec = System.nanoTime(); // Inicio el cronómetro
        SecuencialQuicksort.quickSort(copiaSecuencial, 0, copiaSecuencial.length - 1);
        long finSec = System.nanoTime(); // Fin del crónometro
        long tiempoSecuencialNs = finSec - inicioSec;        
        double tiempoSecuencialMs = tiempoSecuencialNs / 1_000_000.0; // Convierte a milisegundos

        // Medición de tiempo para el algoritmo concurrente
        ForkJoinPool pool = new ForkJoinPool(); // Crea el pool de hilos
        long inicioCon = System.nanoTime();   // Inicio del cronómetro
        pool.invoke(new ConcurrentQuicksort(copiaConcurrente, 0, copiaConcurrente.length - 1));
        pool.shutdown(); // Cierra el pool 
        long finCon = System.nanoTime(); // Fin del crónometro
        long tiempoConcurrenteNs = finCon - inicioCon;
        double tiempoConcurrenteMs = tiempoConcurrenteNs / 1_000_000.0; // Convierte a milisegundos

        // Mostrar resultados
        System.out.println("\nResultados de ejecución:");
        System.out.println("Tamaño del array: " + n);
        System.out.printf("Tiempo Secuencial: %.2f ms%n", tiempoSecuencialMs);
        System.out.printf("Tiempo Concurrente: %.2f ms%n", tiempoConcurrenteMs);
        
        // Verificación de igualdad en los resultados del Array
        boolean iguales = Arrays.equals(copiaSecuencial, copiaConcurrente);

        if (iguales) {
            System.out.println("\nAmbos algoritmos ordenaron correctamente. Los arrays son iguales.");
        } else {
            System.out.println("\nLos resultados difieren. Hay un error en algún algoritmo."); 
        }
    }
}
