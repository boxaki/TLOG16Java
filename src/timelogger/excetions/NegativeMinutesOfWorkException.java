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
public class NegativeMinutesOfWorkException extends Exception {

    /**
     * Creates a new instance of <code>NegativeMinutesOfWorkException</code>
     * without detail message.
     */
    public NegativeMinutesOfWorkException() {
    }

    /**
     * Constructs an instance of <code>NegativeMinutesOfWorkException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public NegativeMinutesOfWorkException(String msg) {
        super(msg);
    }
}
