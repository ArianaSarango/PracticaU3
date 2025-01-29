package controller.tda.stack;
import controller.tda.list.LinkedList;
import controller.tda.list.ListEmptyException;

public class OperationStack<E> extends LinkedList<E> {
    private Integer top; 
    
    public OperationStack(Integer top) {
        this.top = top;
    }

    public Integer getTop() {
        return top;
    }

    public Boolean verify() {
        return getSize() >= top;
    }

    public Boolean isEmpty() {
        return getSize() == 0;
    }

    public void push(E datos) throws ListEmptyException {
        if (!verify()) {
            add(datos);
        } else {
            throw new ListEmptyException("La pila está llena");
        }
    }

    public E pop() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("La pila está vacía");
        } else {
            return deleteLast(); 
        }
    }
}
