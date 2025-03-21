package janggi.util;

import java.util.ArrayList;
import java.util.List;

public class CommandParser {

    public static List<Integer> parseMoveCommand(final String input) {
        List<Integer> moveInfo = new ArrayList<>();
        moveInfo.add(Integer.parseInt(String.valueOf(input.charAt(5))));
        moveInfo.add(Integer.parseInt(String.valueOf(input.charAt(6))));
        moveInfo.add(Integer.parseInt(String.valueOf(input.charAt(8))));
        moveInfo.add(Integer.parseInt(String.valueOf(input.charAt(9))));
        return moveInfo;
    }
}
