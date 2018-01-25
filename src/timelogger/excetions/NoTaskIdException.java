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
public class NoTaskIdException extends Exception {

    /**
     * Creates a new instance of <code>NoTaskIdException</code> without detail
     * message.
     */
    public NoTaskIdException() {
    }

    /**
     * Constructs an instance of <code>NoTaskIdException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NoTaskIdException(String msg) {
        super(msg);
    }
}
