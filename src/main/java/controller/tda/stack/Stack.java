package controller.tda.stack;
import controller.tda.list.ListEmptyException;

public class Stack<E> {
    private OperationStack<E> operationStack;

    public Stack(Integer cant) {
        this.operationStack = new OperationStack<>(cant);
    }

    public void push(E element) throws ListEmptyException {
        operationStack.push(element);
    }

    public Integer getSize() {
        return operationStack.getSize();
    }

    public void clear() {
        operationStack.reset();
    }

    public Integer getTop() {
        return operationStack.getTop();
    }

    public E pop() throws ListEmptyException {
        return operationStack.pop();
    }

    public E top() throws ListEmptyException {
        return operationStack.pop(); 
    }
}
