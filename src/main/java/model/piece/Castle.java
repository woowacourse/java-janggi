package model.piece;

import java.util.ArrayList;
import java.util.List;
import model.position.Movement;
import model.position.Column;
import model.position.Position;
import model.position.Row;

public class Castle {

    private static final Castle INSTANCE = new Castle();

    private Castle() {}

    public static Castle getInstance() {
        return INSTANCE;
    }

    private final List<Position> moveDiagonalArea = List.of(
        new Position(Column.ONE, Row.FOUR),
        new Position(Column.ONE, Row.SIX),
        new Position(Column.TWO, Row.FIVE),
        new Position(Column.THREE, Row.FOUR),
        new Position(Column.THREE, Row.SIX),
        new Position(Column.EIGHT, Row.FOUR),
        new Position(Column.EIGHT, Row.SIX),
        new Position(Column.NINE, Row.FIVE),
        new Position(Column.TEN, Row.FOUR),
        new Position(Column.TEN, Row.SIX)
    );

    private final List<Position> castle = List.of(
        new Position(Column.ONE, Row.FOUR), new Position(Column.ONE, Row.FIVE), new Position(Column.ONE, Row.SIX),
        new Position(Column.TWO, Row.FOUR), new Position(Column.TWO, Row.FIVE), new Position(Column.TWO, Row.SIX),
        new Position(Column.THREE, Row.FOUR), new Position(Column.THREE, Row.FIVE), new Position(Column.THREE, Row.SIX),
        new Position(Column.EIGHT, Row.FOUR), new Position(Column.EIGHT, Row.FIVE), new Position(Column.EIGHT, Row.SIX),
        new Position(Column.NINE, Row.FOUR), new Position(Column.NINE, Row.FIVE), new Position(Column.NINE, Row.SIX),
        new Position(Column.TEN, Row.FOUR), new Position(Column.TEN, Row.FIVE), new Position(Column.TEN, Row.SIX));

    public boolean inCastle(Position position) {
        return castle.contains(position);
    }

    public List<Movement> decideMovements(
        List<Movement> movements,
        List<Movement> movementsInCastle,
        Position departure,
        Position arrival
    ) {
        List<Movement> decidedMovements = new ArrayList<>(movements);
        if (canMoveDiagonal(departure) && inCastle(arrival)) {
            decidedMovements.addAll(movementsInCastle);
        }
        return decidedMovements;
    }

    private boolean canMoveDiagonal(Position position) {
        return moveDiagonalArea.contains(position);
    }
}
