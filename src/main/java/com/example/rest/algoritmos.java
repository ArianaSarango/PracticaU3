package com.example.rest;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import controller.tda.list.LinkedList;
import models.Hotel;
import controller.Dao.servicies.HotelServices;
import controller.tda.graph.GraphLabelNoDirect;
import controller.tda.graph.algoritmos.Floyd;
import controller.tda.graph.algoritmos.Bellman;

@Path("algoritmos")
public class algoritmos {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response ejecutarAlgoritmos() {        
        HashMap<String, Object> mapa = new HashMap<>();
        GraphLabelNoDirect<Hotel> graph = new GraphLabelNoDirect<>(5, Hotel.class);
        HashMap<String, List<String>> adyacencias = new HashMap<>();

        try {
            HotelServices ps = new HotelServices();
            LinkedList<Hotel> lp = ps.listAll();

            if (!lp.isEmpty()) {
                graph = new GraphLabelNoDirect<>(lp.getSize(), Hotel.class);
                Hotel[] m = lp.toArray();

                // Etiquetar los nodos
                for (int i = 0; i < lp.getSize(); i++) {
                    graph.labelsVerticeL(i + 1, m[i]);
                    adyacencias.put(m[i].getNombre(), new ArrayList<>());
                }

                // Agregar conexiones (esto es solo un ejemplo)
                for (int i = 0; i < lp.getSize(); i++) {
                    if (i < lp.getSize() - 1) {
                        graph.insertEdgeL(m[i], m[i + 1], 5.0f);
                        adyacencias.get(m[i].getNombre()).add(m[i + 1].getNombre());
                        adyacencias.get(m[i + 1].getNombre()).add(m[i].getNombre());
                    }
                }
            }

            // Obtener la matriz de adyacencia
            float[][] adjacencyMatrix = graph.getAdjacencyMatrix();

            // Medir el tiempo de ejecución de Floyd-Warshall
            long startTimeFloyd = System.nanoTime();  // Registrar tiempo antes de ejecutar Floyd
            System.out.println("Ejecutando Floyd-Warshall...");
            float[][] floydResult = Floyd.floydWarshall(adjacencyMatrix);
            long endTimeFloyd = System.nanoTime();  // Registrar tiempo después de ejecutar Floyd
            long durationFloyd = (endTimeFloyd - startTimeFloyd) / 1000000; // Convertir a milisegundos
            System.out.println("Floyd-Warshall ejecutado en: " + durationFloyd + " ms");
            printMatrix(floydResult);

            // Medir el tiempo de ejecución de Bellman-Ford
            long startTimeBellman = System.nanoTime();  // Registrar tiempo antes de ejecutar Bellman
            System.out.println("Ejecutando Bellman...");
            // Suponiendo que deseas iniciar desde el vértice 0
            float[][] bellmanResult = Bellman.bellmanFord(adjacencyMatrix, 0);  // 0 es el índice del vértice fuente
            long endTimeBellman = System.nanoTime();  // Registrar tiempo después de ejecutar Bellman
            long durationBellman = (endTimeBellman - startTimeBellman) / 1000000; // Convertir a milisegundos
            System.out.println("Bellman-Ford ejecutado en: " + durationBellman + " ms");
            printMatrix(bellmanResult);

            mapa.put("msg", "OK");
            mapa.put("data", "Resultados en consola");

        } catch (Exception e) {
            mapa.put("msg", "Error");
            mapa.put("data", e.toString());
            return Response.status(Status.BAD_REQUEST).entity(mapa).build();
        }

        return Response.ok(mapa).build();
    }

    // Método para imprimir matrices (Floyd-Warshall)
    private void printMatrix(float[][] matrix) {
        for (float[] row : matrix) {
            for (float val : row) {
                System.out.print((val == Float.MAX_VALUE ? "INF" : val) + "\t");
            }
            System.out.println();
        }
    }
}
