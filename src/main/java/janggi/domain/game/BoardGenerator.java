package janggi.domain.game;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class BoardGenerator {

    private static final Map<SetupOption, Supplier<Board>> SETUP_MENU = Map.of(
            SetupOption.INNER_SETUP, BoardGenerator::generateInnerSetup,
            SetupOption.OUTER_SETUP, BoardGenerator::generateOuterSetup,
            SetupOption.RIGHT_SETUP, BoardGenerator::generateRightSetup,
            SetupOption.LEFT_SETUP, BoardGenerator::generateLeftSetup
    );

    public static Board generate(SetupOption setupOption) {
        return SETUP_MENU.get(setupOption).get();
    }

    private static Board generateInnerSetup() {
        return generateSetup(
                List.of(new Horse(Team.HAN), new Elephant(Team.HAN), new Elephant(Team.HAN), new Horse(Team.HAN),
                        new Horse(Team.CHO), new Elephant(Team.CHO), new Elephant(Team.CHO), new Horse(Team.CHO)));
    }

    private static Board generateOuterSetup() {
        return generateSetup(
                List.of(new Elephant(Team.HAN), new Horse(Team.HAN), new Horse(Team.HAN), new Elephant(Team.HAN),
                        new Elephant(Team.CHO), new Horse(Team.CHO), new Horse(Team.CHO), new Elephant(Team.CHO)));
    }

    private static Board generateRightSetup() {
        return generateSetup(
                List.of(new Elephant(Team.HAN), new Horse(Team.HAN), new Elephant(Team.HAN), new Horse(Team.HAN),
                        new Elephant(Team.CHO), new Horse(Team.CHO), new Elephant(Team.CHO), new Horse(Team.CHO)));
    }

    private static Board generateLeftSetup() {
        return generateSetup(
                List.of(new Horse(Team.HAN), new Elephant(Team.HAN), new Horse(Team.HAN), new Elephant(Team.HAN),
                        new Horse(Team.CHO), new Elephant(Team.CHO), new Horse(Team.CHO), new Elephant(Team.CHO)));
    }

    private static Board generateSetup(final List<? extends Piece> pieces) {
        Map<Position, Piece> board = generateGeneralMap();
        board.put(new Position(Column.ONE, Row.ZERO), pieces.get(0));
        board.put(new Position(Column.TWO, Row.ZERO), pieces.get(1));
        board.put(new Position(Column.SIX, Row.ZERO), pieces.get(2));
        board.put(new Position(Column.SEVEN, Row.ZERO), pieces.get(3));
        board.put(new Position(Column.ONE, Row.NINE), pieces.get(4));
        board.put(new Position(Column.TWO, Row.NINE), pieces.get(5));
        board.put(new Position(Column.SIX, Row.NINE), pieces.get(6));
        board.put(new Position(Column.SEVEN, Row.NINE), pieces.get(7));
        return new Board(board);
    }

    private static Map<Position, Piece> generateGeneralMap() {
        Map<Position, Piece> board = new HashMap<>();
        generateGeneralHanMap(board);
        generateGeneralChoMap(board);
        return board;
    }

    private static void generateGeneralHanMap(final Map<Position, Piece> board) {
        board.put(new Position(Column.FOUR, Row.ONE), new General(Team.HAN));
        board.put(new Position(Column.ZERO, Row.ZERO), new Chariot(Team.HAN));
        board.put(new Position(Column.EIGHT, Row.ZERO), new Chariot(Team.HAN));
        board.put(new Position(Column.ONE, Row.TWO), new Cannon(Team.HAN));
        board.put(new Position(Column.SEVEN, Row.TWO), new Cannon(Team.HAN));
        board.put(new Position(Column.THREE, Row.ZERO), new Guard(Team.HAN));
        board.put(new Position(Column.FIVE, Row.ZERO), new Guard(Team.HAN));
        board.put(new Position(Column.ZERO, Row.THREE), new Soldier(Team.HAN));
        board.put(new Position(Column.TWO, Row.THREE), new Soldier(Team.HAN));
        board.put(new Position(Column.FOUR, Row.THREE), new Soldier(Team.HAN));
        board.put(new Position(Column.SIX, Row.THREE), new Soldier(Team.HAN));
        board.put(new Position(Column.EIGHT, Row.THREE), new Soldier(Team.HAN));
    }

    private static void generateGeneralChoMap(final Map<Position, Piece> board) {
        board.put(new Position(Column.FOUR, Row.EIGHT), new General(Team.CHO));
        board.put(new Position(Column.ZERO, Row.NINE), new Chariot(Team.CHO));
        board.put(new Position(Column.EIGHT, Row.NINE), new Chariot(Team.CHO));
        board.put(new Position(Column.ONE, Row.SEVEN), new Cannon(Team.CHO));
        board.put(new Position(Column.SEVEN, Row.SEVEN), new Cannon(Team.CHO));
        board.put(new Position(Column.THREE, Row.NINE), new Guard(Team.CHO));
        board.put(new Position(Column.FIVE, Row.NINE), new Guard(Team.CHO));
        board.put(new Position(Column.ZERO, Row.SIX), new Soldier(Team.CHO));
        board.put(new Position(Column.TWO, Row.SIX), new Soldier(Team.CHO));
        board.put(new Position(Column.FOUR, Row.SIX), new Soldier(Team.CHO));
        board.put(new Position(Column.SIX, Row.SIX), new Soldier(Team.CHO));
        board.put(new Position(Column.EIGHT, Row.SIX), new Soldier(Team.CHO));
    }
}
