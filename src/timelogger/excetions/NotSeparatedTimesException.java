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
public class NotSeparatedTimesException extends Exception {

    /**
     * Creates a new instance of <code>NotSeparatedTimesException</code> without
     * detail message.
     */
    public NotSeparatedTimesException() {
    }

    /**
     * Constructs an instance of <code>NotSeparatedTimesException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public NotSeparatedTimesException(String msg) {
        super(msg);
    }
}
