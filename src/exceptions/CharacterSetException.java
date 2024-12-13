package exceptions;

import java.io.IOException;

/**
 * Thrown upon invalid requests for changes in the character set.
 */
public class CharacterSetException extends IOException {

    private static final String INVALID_SET_OPERATION_REQUEST = "Did not %s due to incorrect format.";

    /**
     * Receives a command type and creates a new exception.
     * @param commandType "add" / "remove" command.
     */
    public CharacterSetException (String commandType){
        super(String.format(INVALID_SET_OPERATION_REQUEST, commandType));
    }
}
