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
                List.of(Horse.of(Team.HAN), Elephant.of(Team.HAN), Elephant.of(Team.HAN), Horse.of(Team.HAN),
                        Horse.of(Team.CHO), Elephant.of(Team.CHO), Elephant.of(Team.CHO), Horse.of(Team.CHO)));
    }

    private static Board generateOuterSetup() {
        return generateSetup(
                List.of(Elephant.of(Team.HAN), Horse.of(Team.HAN), Horse.of(Team.HAN), Elephant.of(Team.HAN),
                        Elephant.of(Team.CHO), Horse.of(Team.CHO), Horse.of(Team.CHO), Elephant.of(Team.CHO)));
    }

    private static Board generateRightSetup() {
        return generateSetup(
                List.of(Elephant.of(Team.HAN), Horse.of(Team.HAN), Elephant.of(Team.HAN), Horse.of(Team.HAN),
                        Elephant.of(Team.CHO), Horse.of(Team.CHO), Elephant.of(Team.CHO), Horse.of(Team.CHO)));
    }

    private static Board generateLeftSetup() {
        return generateSetup(
                List.of(Horse.of(Team.HAN), Elephant.of(Team.HAN), Horse.of(Team.HAN), Elephant.of(Team.HAN),
                        Horse.of(Team.CHO), Elephant.of(Team.CHO), Horse.of(Team.CHO), Elephant.of(Team.CHO)));
    }

    private static Board generateSetup(final List<? extends Piece> pieces) {
        Map<Position, Piece> board = generateGeneralMap();
        board.put(new Position(Row.ZERO, Column.ONE), pieces.get(0));
        board.put(new Position(Row.ZERO, Column.TWO), pieces.get(1));
        board.put(new Position(Row.ZERO, Column.SIX), pieces.get(2));
        board.put(new Position(Row.ZERO, Column.SEVEN), pieces.get(3));
        board.put(new Position(Row.NINE, Column.ONE), pieces.get(4));
        board.put(new Position(Row.NINE, Column.TWO), pieces.get(5));
        board.put(new Position(Row.NINE, Column.SIX), pieces.get(6));
        board.put(new Position(Row.NINE, Column.SEVEN), pieces.get(7));
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
