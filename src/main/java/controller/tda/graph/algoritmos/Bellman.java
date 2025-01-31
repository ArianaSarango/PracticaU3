package controller.tda.graph.algoritmos;

import java.util.Arrays;

public class Bellman {
    final static float INF = Float.MAX_VALUE;

    public static float[][] bellmanFord(float[][] graph, int src) {
        int V = graph.length;
        float[][] dist = new float[V][V];

        // Inicializar la matriz de distancias con los valores del grafo
        for (int i = 0; i < V; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }

        // Aplicar Bellman-Ford a cada nodo como fuente
        for (int start = 0; start < V; start++) {
            float[] tempDist = new float[V];
            Arrays.fill(tempDist, INF);
            tempDist[start] = 0;

            // Relajación de aristas (V-1 veces)
            for (int i = 1; i < V; i++) {
                for (int u = 0; u < V; u++) {
                    for (int v = 0; v < V; v++) {
                        if (graph[u][v] != INF && tempDist[u] != INF && tempDist[u] + graph[u][v] < tempDist[v]) {
                            tempDist[v] = tempDist[u] + graph[u][v];
                        }
                    }
                }
            }

            // Comprobar ciclos negativos
            for (int u = 0; u < V; u++) {
                for (int v = 0; v < V; v++) {
                    if (graph[u][v] != INF && tempDist[u] != INF && tempDist[u] + graph[u][v] < tempDist[v]) {
                        System.out.println("Ciclo negativo detectado desde el nodo " + start);
                        return null;
                    }
                }
            }

            // Copiar resultados a la matriz de distancias finales
            System.arraycopy(tempDist, 0, dist[start], 0, V);
        }

        return dist;
    }
}
