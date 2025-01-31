package controller.tda.graph.algoritmos;

public class Floyd {
    private static final float INF = Float.MAX_VALUE;

    public static float[][] floydWarshall(float[][] graph) {
        int V = graph.length;
        float[][] dist = new float[V][V];

        // Copiar matriz original
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                dist[i][j] = graph[i][j];
            }
        }

        // Aplicar el algoritmo de Floyd-Warshall
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dist[i][k] != INF && dist[k][j] != INF && (dist[i][k] + dist[k][j] < dist[i][j])) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
        return dist;
    }
}

