package models;

public class Hotel {
    private int idHotel;
    private String nombre;
    private String telefono;
    private Double latitud = 0.0;  // Corrected
    private Double longitud = 0.0; // Corrected
    private String direccion;
    private String horario;

    public int getIdHotel() {
        return idHotel;
    }

    public Hotel(int idHotel, String nombre, String telefono, Double latitud, Double longitud, String direccion) {
        this.idHotel = idHotel;
        this.nombre = nombre;
        this.telefono = telefono;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
        this.horario = horario;
    }
    // Getters and Setters
    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public String getHorario() {
        return horario;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
    

    public Hotel() {
    }

    @Override
    public String toString() {
        return nombre;
    }
}