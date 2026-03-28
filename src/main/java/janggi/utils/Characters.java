package janggi.utils;

public final class Characters {

    private Characters() {

    }

    public static char toFullWidth(final char character) {
        if (character == ' ') {
            return '\u3000';
        }
        if (character >= '!' && character <= '~') {
            return (char) (character + 0xFEE0);
        }

        return character;
    }
}
