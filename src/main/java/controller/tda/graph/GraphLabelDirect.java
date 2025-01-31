package controller.tda.graph;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.HashMap;

import controller.tda.list.LinkedList;
import controller.tda.LabelException;

public class GraphLabelDirect<E> extends GraphDirect{
    protected E labels[];
    protected HashMap <E, Integer> dictVertices;
    private Class<E> clazz;

    public GraphLabelDirect(Integer nro_vertices, Class<E> clazz) {
        super(nro_vertices);
        this.clazz = clazz;
        labels = (E[]) Array.newInstance(clazz, nro_vertices + 1);
        dictVertices = new HashMap<>();
    }

    public Boolean is_edgeL(E v1, E v2) throws Exception {
        if (isLabelsGraph()) {
            return is_edge(getVerticeL(v1), getVerticeL(v2));
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public void insertEdgeL(E v1, E v2, Float weight) throws Exception {
        if (isLabelsGraph()) {
            add_edge(getVerticeL(v1), getVerticeL(v2), weight);
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public void insertEdgeL(E v1, E v2) throws Exception {
        if (isLabelsGraph()) {
            insertEdgeL(v1, v2, Float.NaN);
            //add_edge(getVerticeL(v1), getVerticeL(v2), Float.NaN);
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public LinkedList<Adycencia> adycenciasL(E v) throws Exception {
        if (isLabelsGraph()) {
            return adycencias(getVerticeL(v));
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public void labelsVerticeL(Integer v, E data){
        labels[v] = data;
        dictVertices.put(data, v);
    }

    public Boolean isLabelsGraph(){
        Boolean band = true;
        for(int i = 1; i < labels.length; i++){
            if(labels[i] == null){
                band = false;
                break;
            }
        }
        return band;
    }
    
    public Integer getVerticeL(E label){
        return dictVertices.get(label);
    }

    public E getLabelL(Integer v1){
        return labels[v1];
    }

    @Override
public String toString() {
    String grafo = "";
    try {
        for (int i = 1; i <= this.nro_vertices(); i++) {
            grafo += "Vértice [" + getLabelL(i).toString() + "] tiene las siguientes adyacencias:\n";
            LinkedList<Adycencia> lista = adycencias(i);
            if (!lista.isEmpty()) {
                Adycencia[] ady = lista.toArray();
                for (Adycencia a : ady) {
                    grafo += "  -> " + getLabelL(a.getDestination()).toString() + "\n";
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return grafo;
}

public String drawGraph() {
    String grafo = "var nodes = new vis.DataSet([" + "\n";
    try {
        // Crear nodos con tooltips que muestren sus adyacencias
        for (int i = 1; i <= this.nro_vertices(); i++) {
            LinkedList<Adycencia> lista = adycencias(i);
            String tooltip = "Adyacencias: ";
            if (!lista.isEmpty()) {
                Adycencia[] ady = lista.toArray();
                for (Adycencia a : ady) {
                    tooltip += getLabelL(a.getDestination()).toString() + ", ";
                }
                tooltip = tooltip.substring(0, tooltip.length() - 2); // Quitar la última coma
            } else {
                tooltip += "Ninguna";
            }
            grafo += "{id: " + i + ", label: \"" + getLabelL(i).toString() + "\", title: \"" + tooltip + "\"}," + "\n";
        }
        grafo += "]);" + "\n";

        // Crear las aristas (adyacencias)
        grafo += "var edges = new vis.DataSet([" + "\n";
        for (int i = 1; i <= this.nro_vertices(); i++) {
            LinkedList<Adycencia> lista = adycencias(i);
            if (!lista.isEmpty()) {
                Adycencia[] ady = lista.toArray();
                for (Adycencia a : ady) {
                    grafo += "{from: " + i + ", to: " + a.getDestination() + "}," + "\n";
                }
            }
        }
        grafo += "]);" + "\n";

        // Configuración del grafo con tooltips
        grafo += "var container = document.getElementById(\"mynetwork\");" + "\n";
        grafo += "var data = {" + "\n";
        grafo += "  nodes: nodes," + "\n";
        grafo += "  edges: edges," + "\n";
        grafo += "};" + "\n";
        grafo += "var options = {" + "\n";
        grafo += "  interaction: {hover: true}" + "\n"; // Activa tooltips al pasar el mouse
        grafo += "};" + "\n";
        grafo += "var network = new vis.Network(container, data, options);" + "\n";

        // Guardar en archivo
        FileWriter file = new FileWriter("src" + File.separatorChar + "resources" + File.separatorChar + "graph" + File.separatorChar + "graph.js");
        file.write(grafo);
        file.flush();
        file.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return grafo;
}


}
