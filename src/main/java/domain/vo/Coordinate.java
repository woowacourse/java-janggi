package domain.vo;

import domain.game.Position;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coordinate {
    private static final Pattern POSITION_PATTERN = Pattern.compile("^[a-iA-I][0-9] [a-iA-I][0-9]$");
    private static final int START_POSITION_INDEX = 0;
    private static final int END_POSITION_INDEX = 1;
    private static final int COL_POSITION_INDEX = 0;
    private static final int ROW_POSITION_INDEX = 1;
    private final Position start;
    private final Position end;

    private Coordinate(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    public static Coordinate toCoordinate(String input) {
        validateInput(input);
        List<String> tokens = Arrays.stream(input.split(" "))
                .toList();

        return new Coordinate(toPosition(tokens.get(START_POSITION_INDEX)), toPosition(tokens.get(END_POSITION_INDEX)));
    }

    private static Position toPosition(String token) {
        Col col = Col.toCol(token.charAt(COL_POSITION_INDEX));
        Row row = Row.toRow(token.charAt(ROW_POSITION_INDEX));
        return new Position(col, row);
    }

    private static void validateInput(String input) {
        Matcher matcher = POSITION_PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("[ERROR] 좌표 형식이 틀렸습니다.");
        }
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }
}
