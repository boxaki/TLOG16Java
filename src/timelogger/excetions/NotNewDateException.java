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
public class NotNewDateException extends Exception {

    /**
     * Creates a new instance of <code>NotNewDateException</code> without detail
     * message.
     */
    public NotNewDateException() {
    }

    /**
     * Constructs an instance of <code>NotNewDateException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NotNewDateException(String msg) {
        super(msg);
    }
}
