package com.example.rest;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;  // Import correcto para Response

import controller.Dao.servicies.HotelServices;
import controller.tda.list.LinkedList;


@Path("myresource")
public class MyResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        // Map<String, Object> para mayor claridad
        Map<String, Object> mapa = new HashMap<>();
        HotelServices pd = new HotelServices(); 
        String aux = "";
        
        try {
            // Verifica si la lista de personas está vacía
            aux = "La lista de personas está vacía: " + pd.listAll().isEmpty();
            
            // Genera una lista de números aleatorios
            LinkedList<Double> listaA = new LinkedList<>();
            for (int i = 0; i < 15; i++) {
                double roundedNumber = Math.round((Math.random() * 100) * 100.0) / 100.0;
                listaA.add(roundedNumber);
            }

            pd.getHotel().setNombre("Doraemon");
            pd.getHotel().setDireccion("Calle Doraemon");
            pd.getHotel().setTelefono("0989700718");
            pd.getHotel().setLatitud("0.0");
            pd.getHotel().setLongitud("0.0");
            pd.getHotel().setHorario("00:00");
       
            pd.save();
            
        } catch (Exception e) {
            System.out.println("Error al procesar: " + e.getMessage());
            mapa.put("msg", "Error");
            mapa.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mapa).build();
        }

        // Agrega la información al mapa de respuesta
        mapa.put("msg", "Ok");
        mapa.put("data", "Test: " + aux);
        
        // Construye y devuelve la respuesta correctamente
        return Response.ok(mapa).build();
    }
}