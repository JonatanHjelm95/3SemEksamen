/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package errorhandling;

/**
 *
 * @author Martin
 */
public class ReeQuestException extends Exception{
    public ReeQuestException(String message) {
        super(message);
    }

    public ReeQuestException() {
        super("Requested item could not be found");
    }
}
