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
public class NotTheSameMonthException extends Exception {

    /**
     * Creates a new instance of <code>NotTheSameMonthException</code> without
     * detail message.
     */
    public NotTheSameMonthException() {
    }

    /**
     * Constructs an instance of <code>NotTheSameMonthException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NotTheSameMonthException(String msg) {
        super(msg);
    }
}