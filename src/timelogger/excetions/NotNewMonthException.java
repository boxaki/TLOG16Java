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
public class NotNewMonthException extends Exception {

    /**
     * Creates a new instance of <code>NotNewMonthException</code> without
     * detail message.
     */
    public NotNewMonthException() {
    }

    /**
     * Constructs an instance of <code>NotNewMonthException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NotNewMonthException(String msg) {
        super(msg);
    }
}
