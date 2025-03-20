package util;

import domain.Position;

public class PositionConvertor {
    public static Position changeInputToPosition(String input){
        return Position.of(Character.getNumericValue(input.charAt(1)), input.charAt(0) - 'a');
    }
}
