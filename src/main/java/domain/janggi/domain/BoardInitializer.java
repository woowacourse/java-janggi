package domain.janggi.domain;

import domain.janggi.piece.Cannon;
import domain.janggi.piece.Chariot;
import domain.janggi.piece.Elephant;
import domain.janggi.piece.Guard;
import domain.janggi.piece.Horse;
import domain.janggi.piece.King;
import domain.janggi.piece.Solider;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardInitializer {

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

    public Board init() {
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

    private void putHorse(final Color color, final Board board) {
        board.putPieces(List.of(
                new Horse(new Position(calculateRow(color, 0), FIRST_HORSE_COLUMN_INDEX), color, board),
                new Horse(new Position(calculateRow(color, 0), SECOND_HORSE_COLUMN_INDEX), color, board)
        ));
    }

    private void putElephant(final Color color, final Board board) {
        board.putPieces(List.of(
                new Elephant(new Position(calculateRow(color, 0), FIRST_ELEPHANT_COLUMN_INDEX), color, board),
                new Elephant(new Position(calculateRow(color, 0), SECOND_ELEPHANT_COLUMN_INDEX), color, board)
        ));
    }

    private void putGuard(final Color color, final Board board) {
        board.putPieces(List.of(
                new Guard(new Position(calculateRow(color, 0), FIRST_GUARD_COLUMN_INDEX), color, board),
                new Guard(new Position(calculateRow(color, 0), SECOND_GUARD_COLUMN_INDEX), color, board)
        ));
    }

    private void putChariot(final Color color, final Board board) {
        board.putPieces(List.of(
                new Chariot(new Position(calculateRow(color, 0), FIRST_CHARIOT_COLUMN_INDEX), color, board),
                new Chariot(new Position(calculateRow(color, 0), SECOND_CHARIOT_COLUMN_INDEX), color, board)
        ));
    }

    private void putKing(final Color color, final Board board) {
        board.putPieces(List.of(
                new King(new Position(calculateRow(color, 1), KING_COLUMN_INDEX), color, board),
                new King(new Position(calculateRow(color, 1), KING_COLUMN_INDEX), color, board)
        ));
    }

    private void putCannon(final Color color, final Board board) {
        board.putPieces(List.of(
                new Cannon(new Position(calculateRow(color, 2), FIRST_CANNON_COLUMN_INDEX), color, board),
                new Cannon(new Position(calculateRow(color, 2), SECOND_CANNON_COLUMN_INDEX), color, board)
        ));
    }

    private void putSoldiers(final Color color, final Board board) {
        board.putPieces(IntStream.range(1, 10)
                .filter(BoardInitializer::isSoldierColumn)
                .mapToObj(column -> generateSolider(color, board, column))
                .collect(Collectors.toUnmodifiableList()));
    }

    private Solider generateSolider(Color color, Board board, int column) {
        return new Solider(
                new Position(calculateRow(color, 3), column),
                color,
                board
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
