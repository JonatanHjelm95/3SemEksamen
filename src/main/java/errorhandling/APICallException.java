/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package errorhandling;

/**
 *
 * @author Niels Bang
 */
public class APICallException extends Exception {

    /**
     * Creates a new instance of <code>APICallException</code> without detail
     * message.
     */
    public APICallException() {
    }

    /**
     * Constructs an instance of <code>APICallException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public APICallException(String msg) {
        super(msg);
    }
}
