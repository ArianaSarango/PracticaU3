package com.example.rest;

import controller.Dao.servicies.HotelServices;
import java.util.HashMap;
import controller.tda.list.LinkedList;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import com.google.gson.Gson;

@Path("hotel")
public class HotelApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllHotels() {
        HashMap map = new HashMap<>();
        HotelServices ps = new HotelServices();
        map.put("msg", "Ok");
        map.put("data", ps.listAll().toArray());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHotel(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
        HotelServices ps = new HotelServices();
        try {
            ps.setHotel(ps.get(id));
        } catch (Exception e) {

        }

        map.put("msg", "Ok");
        map.put("data", ps.getHotel());

        if (ps.getHotel() == null || ps.getHotel().getIdHotel() == 0) {
            map.put("msg", "No se encontró hotel con ese identificador");
            return Response.status(Status.NOT_FOUND).entity(map).build();
        }

        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap map) {
        HashMap res = new HashMap<>();
        Gson g = new Gson();
        String a = g.toJson(map);
        System.out.println("***** " + a);

        try {
            HotelServices ps = new HotelServices();
            ps.getHotel().setNombre(map.get("nombre").toString());
            ps.getHotel().setTelefono(map.get("telefono").toString());
            ps.getHotel().setLatitud(map.get("latitud").toString());
            ps.getHotel().setLongitud(map.get("celular").toString());
            ps.getHotel().setHorario(map.get("horario").toString());


            ps.save();
            res.put("msg", "Ok");
            res.put("data", "Guardado correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("Error en save data" + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }

        // todo
        // Validation

    }

    @Path("/listType")

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getType() {
        HashMap map = new HashMap<>();
        HotelServices ps = new HotelServices();
        map.put("msg", "Ok");
        map.put("data", ps.getHotel());
        return Response.ok(map).build();
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap map) {
        HashMap res = new HashMap<>();
        

        try {
            HotelServices ps = new HotelServices();
            ps.setHotel(ps.get(Integer.parseInt(map.get("idHotel").toString())));
            ps.getHotel().setNombre(map.get("nombre").toString());
            ps.getHotel().setTelefono(map.get("telefono").toString());
            ps.getHotel().setLatitud(map.get("latitud").toString());
            ps.getHotel().setLongitud(map.get("longitud").toString());
            ps.getHotel().setHorario(map.get("horario").toString());

            ps.update();
            res.put("msg", "Ok");
            res.put("data", "Guardado correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("Error en save data" + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }

    }
    @Path("/delete")
@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
    public Response delete(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();

        try {
            HotelServices ps = new HotelServices();
            Integer id = Integer.parseInt(map.get("idHotel").toString());

            Boolean success = ps.delete(id);
            if (success) {
                res.put("msg", "Ok");
                res.put("data", "Eliminado correctamente");
                return Response.ok(res).build();
            } else {
                res.put("msg", "Error");
                res.put("data", "Hotel no encontrada");
                return Response.status(Status.NOT_FOUND).entity(res).build();
            }
        } catch (Exception e) {
            System.out.println("Error en delete data" + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

}
