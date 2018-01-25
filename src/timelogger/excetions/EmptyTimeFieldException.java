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
public class EmptyTimeFieldException extends Exception {

    /**
     * Creates a new instance of <code>EmptyTimeField</code> without detail
     * message.
     */
    public EmptyTimeFieldException() {
    }

    /**
     * Constructs an instance of <code>EmptyTimeField</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public EmptyTimeFieldException(String msg) {
        super(msg);
    }
}
