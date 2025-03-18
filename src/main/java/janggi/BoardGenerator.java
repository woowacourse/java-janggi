package janggi;

import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import janggi.piece.Team;
import java.util.HashMap;
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
        Map<Position, Piece> board = generateGeneralMap();
        board.put(new Position(Row.ZERO, Column.ONE), Horse.of(Team.HAN));
        board.put(new Position(Row.ZERO, Column.TWO), Elephant.of(Team.HAN));
        board.put(new Position(Row.ZERO, Column.SIX), Elephant.of(Team.HAN));
        board.put(new Position(Row.ZERO, Column.SEVEN), Horse.of(Team.HAN));

        board.put(new Position(Row.NINE, Column.ONE), Horse.of(Team.CHO));
        board.put(new Position(Row.NINE, Column.TWO), Elephant.of(Team.CHO));
        board.put(new Position(Row.NINE, Column.SIX), Elephant.of(Team.CHO));
        board.put(new Position(Row.NINE, Column.SEVEN), Horse.of(Team.CHO));

        return new Board(board);
    }

    private static Board generateOuterSetup() {
        Map<Position, Piece> board = generateGeneralMap();
        board.put(new Position(Row.ZERO, Column.ONE), Elephant.of(Team.HAN));
        board.put(new Position(Row.ZERO, Column.TWO), Horse.of(Team.HAN));
        board.put(new Position(Row.ZERO, Column.SIX), Horse.of(Team.HAN));
        board.put(new Position(Row.ZERO, Column.SEVEN), Elephant.of(Team.HAN));

        board.put(new Position(Row.NINE, Column.ONE), Elephant.of(Team.CHO));
        board.put(new Position(Row.NINE, Column.TWO), Horse.of(Team.CHO));
        board.put(new Position(Row.NINE, Column.SIX), Horse.of(Team.CHO));
        board.put(new Position(Row.NINE, Column.SEVEN), Elephant.of(Team.CHO));
        return new Board(board);
    }

    private static Board generateRightSetup() {
        Map<Position, Piece> board = generateGeneralMap();
        board.put(new Position(Row.ZERO, Column.ONE), Elephant.of(Team.HAN));
        board.put(new Position(Row.ZERO, Column.TWO), Horse.of(Team.HAN));
        board.put(new Position(Row.ZERO, Column.SIX), Elephant.of(Team.HAN));
        board.put(new Position(Row.ZERO, Column.SEVEN), Horse.of(Team.HAN));

        board.put(new Position(Row.NINE, Column.ONE), Elephant.of(Team.CHO));
        board.put(new Position(Row.NINE, Column.TWO), Horse.of(Team.CHO));
        board.put(new Position(Row.NINE, Column.SIX), Elephant.of(Team.CHO));
        board.put(new Position(Row.NINE, Column.SEVEN), Horse.of(Team.CHO));
        return new Board(board);
    }

    private static Board generateLeftSetup() {
        Map<Position, Piece> board = generateGeneralMap();
        board.put(new Position(Row.ZERO, Column.ONE), Horse.of(Team.HAN));
        board.put(new Position(Row.ZERO, Column.TWO), Elephant.of(Team.HAN));
        board.put(new Position(Row.ZERO, Column.SIX), Horse.of(Team.HAN));
        board.put(new Position(Row.ZERO, Column.SEVEN), Elephant.of(Team.HAN));

        board.put(new Position(Row.NINE, Column.ONE), Horse.of(Team.CHO));
        board.put(new Position(Row.NINE, Column.TWO), Elephant.of(Team.CHO));
        board.put(new Position(Row.NINE, Column.SIX), Horse.of(Team.CHO));
        board.put(new Position(Row.NINE, Column.SEVEN), Elephant.of(Team.CHO));
        return new Board(board);
    }

    private static Map<Position, Piece> generateGeneralMap() {
        Map<Position, Piece> board = new HashMap<>();
        generateGeneralHanMap(board);
        generateGeneralChoMap(board);
        return board;
    }

    private static void generateGeneralHanMap(final Map<Position, Piece> board) {
        board.put(new Position(Row.ONE, Column.FOUR), General.of(Team.HAN));
        board.put(new Position(Row.ZERO, Column.ZERO), Chariot.of(Team.HAN));
        board.put(new Position(Row.ZERO, Column.EIGHT), Chariot.of(Team.HAN));
        board.put(new Position(Row.TWO, Column.ONE), Cannon.of(Team.HAN));
        board.put(new Position(Row.TWO, Column.SEVEN), Cannon.of(Team.HAN));
        board.put(new Position(Row.ZERO, Column.THREE), Guard.of(Team.HAN));
        board.put(new Position(Row.ZERO, Column.FIVE), Guard.of(Team.HAN));
        board.put(new Position(Row.THREE, Column.ZERO), Soldier.of(Team.HAN));
        board.put(new Position(Row.THREE, Column.TWO), Soldier.of(Team.HAN));
        board.put(new Position(Row.THREE, Column.FOUR), Soldier.of(Team.HAN));
        board.put(new Position(Row.THREE, Column.SIX), Soldier.of(Team.HAN));
        board.put(new Position(Row.THREE, Column.EIGHT), Soldier.of(Team.HAN));
    }

    private static void generateGeneralChoMap(final Map<Position, Piece> board) {
        board.put(new Position(Row.EIGHT, Column.FOUR), General.of(Team.CHO));
        board.put(new Position(Row.NINE, Column.ZERO), Chariot.of(Team.CHO));
        board.put(new Position(Row.NINE, Column.EIGHT), Chariot.of(Team.CHO));
        board.put(new Position(Row.SEVEN, Column.ONE), Cannon.of(Team.CHO));
        board.put(new Position(Row.SEVEN, Column.SEVEN), Cannon.of(Team.CHO));
        board.put(new Position(Row.NINE, Column.THREE), Guard.of(Team.CHO));
        board.put(new Position(Row.NINE, Column.FIVE), Guard.of(Team.CHO));
        board.put(new Position(Row.SIX, Column.ZERO), Soldier.of(Team.CHO));
        board.put(new Position(Row.SIX, Column.TWO), Soldier.of(Team.CHO));
        board.put(new Position(Row.SIX, Column.FOUR), Soldier.of(Team.CHO));
        board.put(new Position(Row.SIX, Column.SIX), Soldier.of(Team.CHO));
        board.put(new Position(Row.SIX, Column.EIGHT), Soldier.of(Team.CHO));
    }
}
