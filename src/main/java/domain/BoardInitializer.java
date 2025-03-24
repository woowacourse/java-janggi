package domain;

import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Solider;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardInitializer {

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
                new Horse(new Position(calculateRow(color, 0), 3), color, board),
                new Horse(new Position(calculateRow(color, 0), 8), color, board)
        ));
    }

    private void putElephant(final Color color, final Board board) {
        board.putPieces(List.of(
                new Elephant(new Position(calculateRow(color, 0), 2), color, board),
                new Elephant(new Position( calculateRow(color, 0), 7), color, board)
        ));
    }

    private void putGuard(final Color color, final Board board) {
        board.putPieces(List.of(
                new Guard(new Position(calculateRow(color, 0), 4), color, board),
                new Guard(new Position(calculateRow(color, 0), 6), color, board)
        ));
    }

    private void putChariot(final Color color, final Board board) {
        board.putPieces(List.of(
                new Chariot(new Position(calculateRow(color, 0), 1), color, board),
                new Chariot(new Position(calculateRow(color, 0), 9), color, board)
        ));
    }

    private void putKing(final Color color, final Board board) {
        board.putPieces(List.of(
                new King(new Position(calculateRow(color, 1), 5), color, board),
                new King(new Position(calculateRow(color, 1), 5), color, board)
        ));
    }

    private void putCannon(final Color color, final Board board) {
        board.putPieces(List.of(
                new Cannon(new Position(calculateRow(color, 2), 2), color, board),
                new Cannon(new Position(calculateRow(color, 2), 8), color, board)
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
        return color.getInitRow() + color.convertRowOffsetByTeam(rankLine);
    }

    private static boolean isSoldierColumn(final int column) {
        return column % 2 != 0;
    }
}
