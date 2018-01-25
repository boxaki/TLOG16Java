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
public class WeekendNotEnabledException extends Exception {

    /**
     * Creates a new instance of <code>WeekendNotEnabledException</code> without
     * detail message.
     */
    public WeekendNotEnabledException() {
    }

    /**
     * Constructs an instance of <code>WeekendNotEnabledException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public WeekendNotEnabledException(String msg) {
        super(msg);
    }
}
