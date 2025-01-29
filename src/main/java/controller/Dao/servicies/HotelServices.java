package controller.Dao.servicies;
import controller.tda.list.LinkedList;
import models.Hotel;
import controller.Dao.HotelDao;

public class HotelServices {
    private HotelDao obj;
    public HotelServices(){
        obj = new HotelDao();
    }
    public Boolean save() throws Exception{
        return obj.save();
    }
    public Boolean update() throws Exception{
        return obj.update();
    }
    public Boolean delete(Integer id) throws Exception {
        return obj.delete(id); // Llamar al método delete de HotelDao
    }
    

    public LinkedList listAll(){
        return obj.getlistAll();

    }

    public Hotel getHotel(){
        return obj.getHotel();
    }

    public void setHotel( Hotel Hotel){
        obj.setHotel(Hotel);
    }

    public Hotel get(Integer id) throws Exception {
        return obj.get(id);
    }

    
}
