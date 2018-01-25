/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timelogger.excetions;

/**
 *
 * @author Akos Varga
 */
public class FutureWorkException extends Exception {

    /**
     * Creates a new instance of <code>FutureWorkException</code> without detail
     * message.
     */
    public FutureWorkException() {
    }

    /**
     * Constructs an instance of <code>FutureWorkException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public FutureWorkException(String msg) {
        super(msg);
    }
}
