package controller.tda;

public class OverFlowException extends Exception{
    
    public OverFlowException(){
    }
/**
 * Constructs an instance of <code>OverFlowException</code> with the specified detail message.
 * 
 * 
 * @param msg the detail message.
 */
public OverFlowException(String msg) {
    super(msg);
}
}