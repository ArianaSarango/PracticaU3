package com.example.rest;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("myresource")
public class MyResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {        
        HashMap<String, Object> mapa = new HashMap<>();        
        GraphLabelNoDirect<Hotel> graph = new GraphLabelNoDirect<>(5, Hotel.class);
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // JSON formateado
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
                    adyacencias.put(m[i].getNombre(), new ArrayList<>()); // Inicializar adyacencias
                }

                // Agregar conexiones (adyacencias)
                for (int i = 0; i < lp.getSize(); i++) {
                    if (i < lp.getSize() - 1) {
                        graph.insertEdgeL(m[i], m[i + 1], 5.0f);
                        adyacencias.get(m[i].getNombre()).add(m[i + 1].getNombre());
                        adyacencias.get(m[i + 1].getNombre()).add(m[i].getNombre());
                    }
                }
            }
            // graph.drawGraph(); // Method undefined, so it is commented out
            // Convertir la estructura a JSON
            String jsonGraph = gson.toJson(adyacencias);

            // Guardar en src/resources/graph/graph.json
            File fileDir = new File("src" + File.separator + "resources" + File.separator + "graph");
            if (!fileDir.exists()) fileDir.mkdirs(); // Crear directorio si no existe

            FileWriter file = new FileWriter(fileDir + File.separator + "graph.json");
            file.write(jsonGraph);
            file.flush();
            file.close();

            mapa.put("msg", "OK");
            mapa.put("data", jsonGraph);

        } catch (Exception e) {
            mapa.put("msg", "Error");
            mapa.put("data", e.toString());
            return Response.status(Status.BAD_REQUEST).entity(mapa).build();
        }

        return Response.ok(mapa).build();
    }
}
