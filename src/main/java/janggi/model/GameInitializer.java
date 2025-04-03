package janggi.model;

import janggi.model.piece.Cannon;
import janggi.model.piece.Chariot;
import janggi.model.piece.Elephant;
import janggi.model.piece.Guard;
import janggi.model.piece.Horse;
import janggi.model.piece.King;
import janggi.model.piece.Soldier;
import java.util.Arrays;
import java.util.stream.IntStream;

public class GameInitializer {

    public static final int FIRST_HORSE_COLUMN_INDEX = 3;
    public static final int SECOND_HORSE_COLUMN_INDEX = 8;
    public static final int FIRST_ELEPHANT_COLUMN_INDEX = 2;
    public static final int SECOND_ELEPHANT_COLUMN_INDEX = 7;
    public static final int FIRST_GUARD_COLUMN_INDEX = 4;
    public static final int SECOND_GUARD_COLUMN_INDEX = 6;
    public static final int FIRST_CHARIOT_COLUMN_INDEX = 1;
    public static final int SECOND_CHARIOT_COLUMN_INDEX = 9;
    public static final int KING_COLUMN_INDEX = 5;
    public static final int FIRST_CANNON_COLUMN_INDEX = 2;
    public static final int SECOND_CANNON_COLUMN_INDEX = 8;

    public Board generateBoard() {
        Board board = new Board();
        Arrays.stream(Color.values()).forEach(team -> {
            putSoldiers(team, board);
            putCannon(team, board);
            putKing(team, board);
            putChariot(team, board);
            putGuard(team, board);
            putElephant(team, board);
            putHorse(team, board);
        });
        return board;
    }

    public Turn generateTurn() {
        return new Turn(Color.BLUE);
    }

    private void putHorse(final Color color, final Board board) {
        board.putPiece(new Position(calculateRow(color, 0), FIRST_HORSE_COLUMN_INDEX), new Horse(color));
        board.putPiece(new Position(calculateRow(color, 0), SECOND_HORSE_COLUMN_INDEX), new Horse(color));
    }

    private void putElephant(final Color color, final Board board) {
        board.putPiece(new Position(calculateRow(color, 0), FIRST_ELEPHANT_COLUMN_INDEX), new Elephant(color));
        board.putPiece(new Position(calculateRow(color, 0), SECOND_ELEPHANT_COLUMN_INDEX), new Elephant(color));
    }

    private void putGuard(final Color color, final Board board) {
        board.putPiece(new Position(calculateRow(color, 0), FIRST_GUARD_COLUMN_INDEX), new Guard(color));
        board.putPiece(new Position(calculateRow(color, 0), SECOND_GUARD_COLUMN_INDEX), new Guard(color));
    }

    private void putChariot(final Color color, final Board board) {
        board.putPiece(new Position(calculateRow(color, 0), FIRST_CHARIOT_COLUMN_INDEX), new Chariot(color));
        board.putPiece(new Position(calculateRow(color, 0), SECOND_CHARIOT_COLUMN_INDEX), new Chariot(color));
    }

    private void putKing(final Color color, final Board board) {
        board.putPiece(new Position(calculateRow(color, 1), KING_COLUMN_INDEX), new King(color));
        board.putPiece(new Position(calculateRow(color, 1), KING_COLUMN_INDEX), new King(color));
    }

    private void putCannon(final Color color, final Board board) {
        board.putPiece(new Position(calculateRow(color, 2), FIRST_CANNON_COLUMN_INDEX), new Cannon(color));
        board.putPiece(new Position(calculateRow(color, 2), SECOND_CANNON_COLUMN_INDEX), new Cannon(color));
    }

    private void putSoldiers(final Color color, final Board board) {
        IntStream.range(1, 10)
                        .filter(GameInitializer::isSoldierColumn)
                        .forEach(column -> board.putPiece(
                                new Position(calculateRow(color, 3), column),
                                new Soldier(color))
                        );
    }
    private int calculateRow(final Color color, final int rankLine) {
        return color.getInitRow() + adjustRowOffsetByColor(color, rankLine);
    }

    private int adjustRowOffsetByColor(final Color color, int rankLine) {
        if (color == Color.BLUE) {
            return -rankLine;
        }
        return rankLine;
    }

    private static boolean isSoldierColumn(final int column) {
        return column % 2 != 0;
    }
}
