package ascii_art;

import java.util.Scanner;

/**
 * A singleton class responsible for reading input from the keyboard.
 */
class KeyboardInput
{
    private static KeyboardInput keyboardInputObject = null;
    private final Scanner scanner;
    
    private KeyboardInput()
    {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Returns the KeyboardInput instance.
     * @return The KeyboardInput instance.
     */
    public static KeyboardInput getObject()
    {
        if(KeyboardInput.keyboardInputObject == null)
        {
            KeyboardInput.keyboardInputObject = new KeyboardInput();
        }
        return KeyboardInput.keyboardInputObject;
    }

    /**
     * Reads a line from the keyboard.
     * @return The line read from the keyboard.
     */
    public static String readLine()
    {
        return KeyboardInput.getObject().scanner.nextLine().trim();
    }
}