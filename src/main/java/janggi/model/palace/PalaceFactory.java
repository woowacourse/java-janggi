package janggi.model.palace;

import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import janggi.model.position.absolute.UndirectedLine;
import java.util.Set;

public class PalaceFactory {

    public Palaces create() {
        return new Palaces(
                new Palace(choPositions(), choEdges()),
                new Palace(hanPositions(), hanEdges())
        );
    }

    private Set<Position> choPositions() {
        return Set.of(
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
    }

    private Set<UndirectedLine> choEdges() {
        return Set.of(
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
    }

    private Set<Position> hanPositions() {
        return Set.of(
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
    }

    private Set<UndirectedLine> hanEdges() {
        return Set.of(
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
    }
}
