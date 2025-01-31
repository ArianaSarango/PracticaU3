package com.example.rest;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import controller.tda.list.LinkedList;
import models.Hotel;
import controller.tda.graph.GraphLabelNoDirect;
import controller.tda.graph.algoritmos.Floyd;
import controller.tda.graph.algoritmos.Bellman;

public class negros {

    public static void main(String[] args) {
        System.out.printf("%-20s %-30s %-30s%n", "Tamaño de datos", "Floyd-Warshall (ms)", "Bellman-Ford (ms)");

        try {
            // Generar datos aleatorios para los hoteles
            int maxHotels = 30; // Número máximo de hoteles para generar
            LinkedList<Hotel> lp = generateRandomHotels(maxHotels);

            // Crear las tablas de tiempos para 10, 20 y 30 datos
            for (int n : new int[]{10, 20, 30}) {
                // Aseguramos que la lista tiene al menos 'n' elementos
                if (lp.getSize() < n) {
                    break;
                }

                // Preparamos el grafo para 'n' vértices
                GraphLabelNoDirect<Hotel> graph = new GraphLabelNoDirect<>(n, Hotel.class);
                Hotel[] m = lp.toArray();
                HashMap<String, List<String>> adyacencias = new HashMap<>();

                for (int i = 0; i < n; i++) {
                    graph.labelsVerticeL(i + 1, m[i]);
                    adyacencias.put(m[i].getNombre(), new ArrayList<>());
                }

                for (int i = 0; i < n; i++) {
                    if (i < n - 1) {
                        // Generación de distancia aleatoria entre 1 y 10
                        float randomDistance = 1 + new Random().nextInt(10);
                        graph.insertEdgeL(m[i], m[i + 1], randomDistance);
                        adyacencias.get(m[i].getNombre()).add(m[i + 1].getNombre());
                        adyacencias.get(m[i + 1].getNombre()).add(m[i].getNombre());
                    }
                }

                float[][] adjacencyMatrix = graph.getAdjacencyMatrix();

                // Medir tiempo de ejecución de Floyd-Warshall
                long startTimeFloyd = System.nanoTime();
                Floyd.floydWarshall(adjacencyMatrix);
                long endTimeFloyd = System.nanoTime();
                long durationFloyd = (endTimeFloyd - startTimeFloyd) / 1000000;

                // Medir tiempo de ejecución de Bellman-Ford
                long startTimeBellman = System.nanoTime();
                Bellman.bellmanFord(adjacencyMatrix, 0);
                long endTimeBellman = System.nanoTime();
                long durationBellman = (endTimeBellman - startTimeBellman) / 1000000;

                // Imprimir resultados en formato de tabla en la consola
                System.out.printf("%-20d %-30d %-30d%n", n, durationFloyd, durationBellman);
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.toString());
        }
    }

    // Método para generar hoteles aleatorios
    private static LinkedList<Hotel> generateRandomHotels(int maxHotels) {
        LinkedList<Hotel> hotels = new LinkedList<>();
        Random rand = new Random();
        
        for (int i = 0; i < maxHotels; i++) {
            // Crear un nombre de hotel aleatorio
            String hotelName = "Hotel " + (i + 1);
            Hotel hotel = new Hotel(i, hotelName, hotelName, null, null, hotelName);
            hotels.add(hotel);
        }

        return hotels;
    }
}
