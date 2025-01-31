package controller.tda.graph;

import java.util.HashMap;

public class GraphLabelNoDirect<T> {
    private int numVertices;
    private float[][] adjacencyMatrix;
    private HashMap<T, Integer> vertexIndex; // Mapeo de objeto a índice en la matriz

    public GraphLabelNoDirect(int numVertices, Class<T> clazz) {
        this.numVertices = numVertices;
        adjacencyMatrix = new float[numVertices][numVertices];
        vertexIndex = new HashMap<>();

        // Inicializar matriz con valores infinitos (excepto la diagonal)
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (i == j) {
                    adjacencyMatrix[i][j] = 0;
                } else {
                    adjacencyMatrix[i][j] = Float.MAX_VALUE;
                }
            }
        }
    }

    // Método para etiquetar un vértice
    public void labelsVerticeL(int index, T label) {
        vertexIndex.put(label, index - 1); // Guardamos el índice
    }

    // Método para insertar una arista entre dos nodos con un peso
    public void insertEdgeL(T from, T to, float weight) {
        Integer i = vertexIndex.get(from);
        Integer j = vertexIndex.get(to);
        if (i != null && j != null) {
            adjacencyMatrix[i][j] = weight;
            adjacencyMatrix[j][i] = weight; // Grafo no dirigido
        }
    }

    // Método para obtener la matriz de adyacencia
    public float[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }
}
