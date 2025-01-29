package controller.Dao;

import models.Hotel;
import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class HotelDao extends AdapterDao<Hotel> {
    private Hotel hotel;
    private LinkedList<Hotel> listAll;

    public HotelDao() {
        super(Hotel.class);
        this.listAll = new LinkedList<>();
    }

    public Hotel getHotel() {
        if (hotel == null) {
            hotel = new Hotel();
        }
        return this.hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public LinkedList<Hotel> getlistAll() {
        if (listAll.isEmpty()) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize() + 1;
        hotel.setIdHotel(id);
        this.persist(this.hotel);
        this.listAll = getlistAll();
        return true;
    }

    public Boolean update() throws Exception {
        try {
            this.merge(getHotel(), getHotel().getIdHotel() - 1);
            this.listAll = getlistAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<Hotel> list = getlistAll();
        Hotel hotel = get(id);
        if (hotel != null) {
            list.remove(hotel);
            String info = g.toJson(list.toArray());
            saveFile(info);
            this.listAll = list;
            return true;
        } else {
            System.out.println("Hotel con id " + id + " no encontrada.");
            return false;
        }
    }

}
