package console.util;

import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import java.util.HashMap;
import java.util.Map;

public class PositionConverter {
    private Map<Character, Row> convertRow = new HashMap<>();
    private Map<Character, Column> convertColumn = new HashMap<>();

    {
        convertRow.put('0', Row.ZERO);
        convertRow.put('1', Row.ONE);
        convertRow.put('2', Row.TWO);
        convertRow.put('3', Row.THREE);
        convertRow.put('4', Row.FOUR);
        convertRow.put('5', Row.FIVE);
        convertRow.put('6', Row.SIX);
        convertRow.put('7', Row.SEVEN);
        convertRow.put('8', Row.EIGHT);
        convertRow.put('9', Row.NINE);
    }

    {
        convertColumn.put('a', Column.A);
        convertColumn.put('b', Column.B);
        convertColumn.put('c', Column.C);
        convertColumn.put('d', Column.D);
        convertColumn.put('e', Column.E);
        convertColumn.put('f', Column.F);
        convertColumn.put('g', Column.G);
        convertColumn.put('h', Column.H);
        convertColumn.put('i', Column.I);
    }

    {
        convertColumn.put('A', Column.A);
        convertColumn.put('B', Column.B);
        convertColumn.put('C', Column.C);
        convertColumn.put('D', Column.D);
        convertColumn.put('E', Column.E);
        convertColumn.put('F', Column.F);
        convertColumn.put('G', Column.G);
        convertColumn.put('H', Column.H);
        convertColumn.put('I', Column.I);
    }

    public Position convert(String input) {
        try {
            return new Position(convertColumn.get(input.charAt(0)), convertRow.get(input.charAt(1)));
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력 형식입니다.");
        }
    }
}
