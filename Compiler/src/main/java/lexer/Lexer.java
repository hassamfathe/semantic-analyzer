
package lexer;

import lexer.Token;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String input;
    private int pos = 0;
    private int line = 1;

    public Lexer (String input) {
        this.input = input;
    }

    public List<Token> tokenize () {
        List<Token> tokens = new ArrayList<>();

        while (pos < input.length()) {
            char current = input.charAt(pos);

            if (current == '\n') {
                line++;
                pos++;
            } else if (Character.isWhitespace(current)) {
                pos++;
            } else if (Character.isLetter(current)) {
                tokens.add(readIdentifier());
            } else if (Character.isDigit(current)) {
                tokens.add(readNumber());
            } else {
                tokens.add(new Token(String.valueOf(current), String.valueOf(current), line));
                pos++;  
            }

        }
        return tokens;
    }

    private Token readIdentifier() {
        int start = pos;
        while (pos < input.length() && Character.isLetter(input.charAt(pos)))
            pos++;

        String value = input.substring(start, pos);
        String type = switch (value) {
            case "int", "float" -> "TYPE";
            default -> "IDENTIFIER";
        };
        return new Token(type, value, line);
    }

    private Token readNumber() {
        int start = pos;
        while (pos < input.length() && Character.isDigit(input.charAt(pos)))
            pos++;

        return new Token("NUMBER", input.substring(start, pos), line);
    }

}
