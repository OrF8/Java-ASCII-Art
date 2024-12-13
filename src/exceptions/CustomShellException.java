package exceptions;

/**
 * Thrown upon invalid requests for changes in the character set.
 */
public class CustomShellException extends Exception {

    private static final String INVALID_OPERATION_REQUEST = "Did not %s due to %s.";

    /**
     * Receives a command type and creates a new exception.
     * @param commandType "add" / "remove" command.
     */
    public CustomShellException(String commandType, String failureReason){
        super(String.format(INVALID_OPERATION_REQUEST, commandType, failureReason));
    }

    /**
     * Receives a message to print upon exception.
     * @param message The message to print.
     */
    public CustomShellException(String message) {
        super(message);
    }
}
