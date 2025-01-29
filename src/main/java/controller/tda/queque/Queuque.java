package controller.tda.queque;
import controller.tda.list.ListEmptyException;

public class Queuque<E>{
    private OperationQueuque<E> queuqu;

    public Queuque(Integer top) {
        this.queuqu = new OperationQueuque<>(top);
    }

    public Integer getTop(){
        return queuqu.getSize();
    }

    public Integer getSize() {
        return queuqu.getSize();
    }

    public Boolean verify(){
        return queuqu.verify();
    }

    public void queuque(E data) throws ListEmptyException {
        queuqu.queuque(data);
    }

    public void clear(){
        queuqu.reset();
    }
    
    public E dequeuque() throws ListEmptyException {
        return queuqu.dequeuque();
    }
    
}
