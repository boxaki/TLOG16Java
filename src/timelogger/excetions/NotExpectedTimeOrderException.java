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
public class NotExpectedTimeOrderException extends Exception {

    /**
     * Creates a new instance of <code>NotExpectedTimeOrderException</code>
     * without detail message.
     */
    public NotExpectedTimeOrderException() {
    }

    /**
     * Constructs an instance of <code>NotExpectedTimeOrderException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public NotExpectedTimeOrderException(String msg) {
        super(msg);
    }
}
