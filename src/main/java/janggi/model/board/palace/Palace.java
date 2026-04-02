package janggi.model.board.palace;

import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import java.util.Set;

public final class Palace {

    private static final Set<Position> CHO_AREA = Set.of(
            new Position(Row.ZERO, Column.FOUR),
            new Position(Row.ZERO, Column.FIVE),
            new Position(Row.ZERO, Column.SIX),

            new Position(Row.NINE, Column.FOUR),
            new Position(Row.NINE, Column.FIVE),
            new Position(Row.NINE, Column.SIX),

            new Position(Row.EIGHT, Column.FOUR),
            new Position(Row.EIGHT, Column.FIVE),
            new Position(Row.EIGHT, Column.SIX)
    );

    private static final Set<Position> HAN_AREA = Set.of(
            new Position(Row.ONE, Column.FOUR),
            new Position(Row.ONE, Column.FIVE),
            new Position(Row.ONE, Column.SIX),

            new Position(Row.TWO, Column.FOUR),
            new Position(Row.TWO, Column.FIVE),
            new Position(Row.TWO, Column.SIX),

            new Position(Row.THREE, Column.FOUR),
            new Position(Row.THREE, Column.FIVE),
            new Position(Row.THREE, Column.SIX)
    );

    private static final Set<UndirectedLine> CHO_LINES = Set.of(
            // 내부 간선
            new UndirectedLine(new Position(Row.NINE, Column.FIVE), new Position(Row.EIGHT, Column.FIVE)),
            new UndirectedLine(new Position(Row.NINE, Column.FIVE), new Position(Row.EIGHT, Column.SIX)),
            new UndirectedLine(new Position(Row.NINE, Column.FIVE), new Position(Row.NINE, Column.SIX)),
            new UndirectedLine(new Position(Row.NINE, Column.FIVE), new Position(Row.ZERO, Column.SIX)),
            new UndirectedLine(new Position(Row.NINE, Column.FIVE), new Position(Row.ZERO, Column.FIVE)),
            new UndirectedLine(new Position(Row.NINE, Column.FIVE), new Position(Row.ZERO, Column.FOUR)),
            new UndirectedLine(new Position(Row.NINE, Column.FIVE), new Position(Row.NINE, Column.FOUR)),
            new UndirectedLine(new Position(Row.NINE, Column.FIVE), new Position(Row.EIGHT, Column.FOUR)),

            // 외곽선
            new UndirectedLine(new Position(Row.NINE, Column.FOUR), new Position(Row.EIGHT, Column.FOUR)),
            new UndirectedLine(new Position(Row.NINE, Column.FOUR), new Position(Row.ZERO, Column.FOUR)),
            new UndirectedLine(new Position(Row.EIGHT, Column.FIVE), new Position(Row.EIGHT, Column.FOUR)),
            new UndirectedLine(new Position(Row.EIGHT, Column.FIVE), new Position(Row.EIGHT, Column.SIX)),
            new UndirectedLine(new Position(Row.NINE, Column.SIX), new Position(Row.EIGHT, Column.SIX)),
            new UndirectedLine(new Position(Row.NINE, Column.SIX), new Position(Row.ZERO, Column.SIX)),
            new UndirectedLine(new Position(Row.ZERO, Column.FIVE), new Position(Row.ZERO, Column.FOUR)),
            new UndirectedLine(new Position(Row.ZERO, Column.FIVE), new Position(Row.ZERO, Column.SIX))
    );

    private static final Set<UndirectedLine> HAN_LINES = Set.of(
            // 내부 간선
            new UndirectedLine(new Position(Row.TWO, Column.FIVE), new Position(Row.ONE, Column.FIVE)),
            new UndirectedLine(new Position(Row.TWO, Column.FIVE), new Position(Row.ONE, Column.SIX)),
            new UndirectedLine(new Position(Row.TWO, Column.FIVE), new Position(Row.TWO, Column.SIX)),
            new UndirectedLine(new Position(Row.TWO, Column.FIVE), new Position(Row.THREE, Column.SIX)),
            new UndirectedLine(new Position(Row.TWO, Column.FIVE), new Position(Row.THREE, Column.FIVE)),
            new UndirectedLine(new Position(Row.TWO, Column.FIVE), new Position(Row.THREE, Column.FOUR)),
            new UndirectedLine(new Position(Row.TWO, Column.FIVE), new Position(Row.TWO, Column.FOUR)),
            new UndirectedLine(new Position(Row.TWO, Column.FIVE), new Position(Row.ONE, Column.FOUR)),

            // 외곽선
            new UndirectedLine(new Position(Row.ONE, Column.FOUR), new Position(Row.ONE, Column.FIVE)),
            new UndirectedLine(new Position(Row.ONE, Column.FIVE), new Position(Row.ONE, Column.SIX)),
            new UndirectedLine(new Position(Row.ONE, Column.FOUR), new Position(Row.TWO, Column.FOUR)),
            new UndirectedLine(new Position(Row.TWO, Column.FOUR), new Position(Row.THREE, Column.FOUR)),
            new UndirectedLine(new Position(Row.THREE, Column.FOUR), new Position(Row.THREE, Column.FIVE)),
            new UndirectedLine(new Position(Row.THREE, Column.FIVE), new Position(Row.THREE, Column.SIX)),
            new UndirectedLine(new Position(Row.ONE, Column.SIX), new Position(Row.TWO, Column.SIX)),
            new UndirectedLine(new Position(Row.TWO, Column.SIX), new Position(Row.THREE, Column.SIX))
    );

    private Palace() {
    }

    public static boolean isInPalace(Position position) {
        return CHO_AREA.contains(position) || HAN_AREA.contains(position);
    }

    public static boolean isNotAdjacent(Position from, Position to) {
        UndirectedLine line = new UndirectedLine(from, to);

        return !CHO_LINES.contains(line)
                && !HAN_LINES.contains(line);
    }
}
