package exceptions;

/**
 * Custom exception class for the shell.
 * Used to throw exceptions when the user inputs an invalid command.
 */
public class CustomShellException extends Exception {

    private static final String INVALID_OPERATION_REQUEST = "Did not %s due to %s.";

    /**
     * Receives a command type and creates a new exception.
     * @param commandType "add" / "remove" command.
     * @param failureReason The reason for the failure.
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
