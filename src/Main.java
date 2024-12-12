import ascii_art.AsciiArtAlgorithm;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;

import java.io.IOException;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) throws IOException {
        String input = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
        char[] z = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        HashSet<Character> charSet = new HashSet<>();
        for (char c : z) {
            charSet.add(c);
        }
        // Convert the string to a char array
        AsciiArtAlgorithm algo = new AsciiArtAlgorithm(args[0], charSet, 1024);
        char[][] chars = algo.run();
        HtmlAsciiOutput output = new HtmlAsciiOutput("family.html", "Courier New");
        output.out(chars);
        algo = new AsciiArtAlgorithm(args[0], charSet, 2048);
        chars = algo.run();
        output = new HtmlAsciiOutput("family2.html", "Courier New");
        output.out(chars);
        algo = new AsciiArtAlgorithm(args[0], charSet, 2048);
        chars = algo.run();
        output = new HtmlAsciiOutput("family3.html", "Courier New");
        output.out(chars);
        algo = new AsciiArtAlgorithm(args[1], charSet, 1024);
        chars = algo.run();
        output = new HtmlAsciiOutput("friends.html", "Courier New");
        output.out(chars);
        for (char c : input.toCharArray()) {
            charSet.add(c);
        }
        algo = new AsciiArtAlgorithm(args[1], charSet, 1024);
        chars = algo.run();
        output = new HtmlAsciiOutput("friends2.html", "Courier New");
        output.out(chars);
    }

}
