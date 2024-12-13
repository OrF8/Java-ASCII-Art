package ascii_art;

import exceptions.CharacterSetException;

import java.io.IOException;
import java.util.*;

/**
 * TODO: Docstring
 */
public class Shell {

    // Constants for user input
    private static final String WAIT_FOR_USER_INPUT = ">>> ";
    private static final String EXIT_INPUT = "exit";
    private static final String PRINT_CHARS_LIST_INPUT = "chars";
    private static final String ADD_CHARS_TO_LIST = "add";
    private static final String REMOVE_CHARS_FROM_LIST = "remove";
    private static final String CHANGE_RESOLUTION = "res";
    private static final String ROUND_METHOD = "round";
    private static final String OUTPUT_FORMAT = "output";
    private static final String RUN_ALGORITHM = "asciiArt";

    // Error messages TODO: Where does this belong to?
    private static final String INVALID_USER_INPUT_MESSAGE = "Did not execute due to incorrect command.";
    private static final String INSUFFICIENT_CHARACTER_SET_SIZE = "Did not execute. Charset is too small.";

    // Default values constants
    private static final String HTML_OUTPUT_FONT = "Courier New";
    private static final int DEFAULT_RESOLUTION_VALUE = 2;
    private static final Character[] DEFAULT_CHARACTER_SET = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};


    // ASCII values
    private static final char SPACE_ASCII_CODE = 32;
    private static final char FIRST_ASCII_CHARACTER = 32;
    private static final char LAST_ASCII_CHARACTER = 126;

    // "add"/"remove" shell command constants
    private static final String SPACE_ADDITION_REQUEST = "space";
    private static final String ADD_ALL_ASCII_REQUEST = "all";
    private static final String REMOVE_ALL_ASCII_REQUEST = ADD_ALL_ASCII_REQUEST;
    private static final String HYPHEN_SEPARATOR = "-";
    private static final int VALID_RANGE_STRING_LENGTH = 2;
    private static final int SECOND_CHAR = 2;


    // private fields
    private final HashSet<Character> characterSet;
    private int resolution;
    private OutputMethod outputMethod;
    private RoundMethod roundMethod;

    /**
     * TODO: Docs
     */
    private enum OutputMethod {
        CONSOLE("console"),
        HTML("html");

        private final String value;

        // Constructor
        OutputMethod(String value) {
            this.value = value;
        }

        // Getter method to retrieve the value
        public String getValue() {
            return value;
        }
    }

    /**
     * TODO: Docs
     */
    private enum RoundMethod {
        UP("up"),
        DOWN("down"),
        ABSOLUTE("abs");

        private final String value;

        // Constructor
        RoundMethod(String value) {
            this.value = value;
        }

        // Getter method to retrieve the value
        public String getValue() {
            return value;
        }
    }

    /**
     * TODO
     */
    public Shell() {
        // set up default values for the algorithm
        this.characterSet = new HashSet<>(Arrays.asList(DEFAULT_CHARACTER_SET));
        this.resolution = DEFAULT_RESOLUTION_VALUE;
        this.roundMethod = RoundMethod.ABSOLUTE;
        this.outputMethod = OutputMethod.CONSOLE;
    }

    /**
     * Prints the current character list to System.out in increasing ASCII values,
     * seperated by commas and spaces.
     */
    private void printCharList() {
        // turn the character set to a tree set in order to efficiently sort it
        TreeSet<Character> sortedCharacterSet = new TreeSet<>(this.characterSet);
        System.out.println(sortedCharacterSet); // print the characters with ", " separator
    }

    /**
     * Adds characters to the character list.
     * Valid argument options:
     * <li>single character - A single character to add to the list.</li>
     * <li>all - Adds all ASCII characters from 32 to 126 to the list.</li>
     * <li>space - Adds the character space ' ' to the list.</li>
     * <li>character range - Adds characters from start to finish of the given range.
     * <p>Examples: a-g or g-a</p></li>
     *
     * @param argument As stated above.
     * @throws CharacterSetException In case of invalid input
     */
    private void addCharsToList(String argument) throws CharacterSetException {
        // Create a new exception to throw in case of an invalid input.
        CharacterSetException characterSetException = new CharacterSetException(ADD_CHARS_TO_LIST);

        if (argument.equals(SPACE_ADDITION_REQUEST)) { // Add space to the character set
            this.characterSet.add(SPACE_ASCII_CODE);
        } else if (argument.equals(ADD_ALL_ASCII_REQUEST)) { // Add all ASCII characters to the set
            operateOnAsciiCharactersInRange(FIRST_ASCII_CHARACTER, LAST_ASCII_CHARACTER, ADD_CHARS_TO_LIST);
        } else if (argument.length() == 1) { // Add a single character to the set
            char characterValue = argument.toCharArray()[0];
            // Add the character iff it is in the ASCII table range.
            if (isInAsciiTable(characterValue)) {
                this.characterSet.add(characterValue);
            } else { // Character is not in the ASCII table, throw exception.
                throw characterSetException;
            }
        } else if (argument.contains(HYPHEN_SEPARATOR)) { // Given range of characters to add to the set.
            commandCharactersInRange(argument.split(HYPHEN_SEPARATOR), ADD_CHARS_TO_LIST);
        } else {
            throw characterSetException;
        }
    }

    /**
     * Removes characters to the character list.
     * Valid argument options:
     * <li>single character - A single character to remove from the list.</li>
     * <li>all - Removes all ASCII characters from 32 to 126 from the list.</li>
     * <li>space - Removes the character space ' ' from the list.</li>
     * <li>character range - Removes characters from start to finish of the given range.
     * <p>Examples: a-g or g-a</p></li>
     *
     * @param argument As stated above.
     * @throws CharacterSetException In case of invalid input
     */
    private void removeCharsFromList(String argument) throws CharacterSetException {
        // TODO: What to do if user asks to remove a char that isn't in ASCII range? Throw Excpetion?
        // Create a new exception to throw in case of an invalid input.
        CharacterSetException characterSetException = new CharacterSetException(REMOVE_CHARS_FROM_LIST);

        if (argument.equals(SPACE_ADDITION_REQUEST)) { // Remove space from the character set
            this.characterSet.remove(SPACE_ASCII_CODE);
        } else if (argument.equals(REMOVE_ALL_ASCII_REQUEST)) { // Remove all ASCII characters from the set
            operateOnAsciiCharactersInRange(
                    FIRST_ASCII_CHARACTER, LAST_ASCII_CHARACTER, REMOVE_CHARS_FROM_LIST
            );
        } else if (argument.length() == 1) { // Remove a single character from the set
            this.characterSet.remove(argument.toCharArray()[0]);
        } else if (argument.contains(HYPHEN_SEPARATOR)) { // Given range of characters to remove from the set.
            commandCharactersInRange(argument.split(HYPHEN_SEPARATOR), REMOVE_CHARS_FROM_LIST);
        } else {
            throw characterSetException;
        }
    }

    /**
     * TODO: COMPLETE
     * @param stringArray
     * @param command
     * @throws CharacterSetException
     */
    private void commandCharactersInRange(String[] stringArray, String command) throws CharacterSetException {
        CharacterSetException characterSetException = new CharacterSetException(command);
        if (stringArray.length != VALID_RANGE_STRING_LENGTH) {
            throw characterSetException;
        } else {
            char param1 = stringArray[0].charAt(0);
            char param2 = stringArray[1].charAt(0);
            if (!isInAsciiTable(param1) || !isInAsciiTable(param2)) {
                throw characterSetException;
            }
            char fromChar = (char) Math.min(param1, param2);
            char toChar = (char) Math.max(param1, param2);
            //
            operateOnAsciiCharactersInRange(fromChar, toChar, command);
        }
    }

    /**
     * Checks if a given character is in the range of the ASCII table.
     *
     * @param c The value of the character to check.
     * @return <code>true</code> if the character is in ASCII range, <code>false</code> otherwise.
     */
    private boolean isInAsciiTable(char c) {
        return c >= FIRST_ASCII_CHARACTER && c <= LAST_ASCII_CHARACTER;
    }

    /**
     * Implements the given command on all ASCII characters in range (fromChar)-(toChar) to the
     * characterSet (inclusive).
     * @param fromChar <code>char</code> to command chars from.
     * @param toChar <code>char</code> to command chars up to.
     * @param command "add" or "remove" command to operate on the set. Assumes valid command.
     */
    private void operateOnAsciiCharactersInRange(char fromChar, char toChar, String command) {
        // Since all characters are treated as integers, iterate from space = 32, to '~' = 126
        for (char c = fromChar; c <= toChar; c++) {
            if (command.equals(ADD_CHARS_TO_LIST)) {
                this.characterSet.add(c); // Since this is a HashSet, will not add an existing character.
            } else {
                this.characterSet.remove(c);
            }
        }
    }


    /**
     * Responsible for translating the commands given from the user and execute them.
     * <p>List of valid commands:</p>
     * <li>exit - Exit the shell.</li>
     * <li>chars - View the current character set.</li>
     * <li>add - Add characters to the current character set.</li>
     * <li>remove - Remove characters to the current character set.</li>
     * <li>res - Control the picture's resolution.</li>
     * <li>round - Change rounding method when matching an ASCII character.</li>
     * <li>output - Choose output format: .html file or console.</li>
     * <li>asciiArt - Run the algorithm with the current parameters.</li>
     *
     * @param imageName Path to the image to activate the algorithm on.
     */
    public void run(String imageName) {
        try {
            String input = "";
            while (!input.equals(EXIT_INPUT)) {
                System.out.print(WAIT_FOR_USER_INPUT);
                input = KeyboardInput.readLine();
                // parse the command from the user
                String[] args = input.split(" ");
                String command = args[0];

                // handle the user's request according to the command
                switch (command) {
                    case PRINT_CHARS_LIST_INPUT:
                        printCharList();
                        break;
                    case ADD_CHARS_TO_LIST:
                        addCharsToList(args[1]);
                        break;
                    case REMOVE_CHARS_FROM_LIST:
                        removeCharsFromList(args[1]);
                        break;
//                    case CHANGE_RESOLUTION:
//                        changeOutputResolution(args[1]);
//                        break;
//                    case ROUND_METHOD:
//                        changeRoundMethod(args[1]);
//                        break;
//                    case OUTPUT_FORMAT:
//                        changeOutputFormat(args[1]);
//                    case RUN_ALGORITHM:

                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }




    public static void main(String[] args) {
        Shell newShellSession = new Shell();
        newShellSession.run("or_is_gay");
    }


}
