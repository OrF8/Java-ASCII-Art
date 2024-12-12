import ascii_art.AsciiArtAlgorithm;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        Image image = new Image(args[0]);
        String input = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

        // Convert the string to a char array
        char[] charArray = input.toCharArray();
        AsciiArtAlgorithm algo = new AsciiArtAlgorithm(image, charArray, 1024);
        char[][] chars = algo.run();
        ConsoleAsciiOutput output = new ConsoleAsciiOutput();
        HtmlAsciiOutput output2 = new HtmlAsciiOutput("family.html", "Courier New");
        output.out(chars);
        output2.out(chars);
    }

}
