package board;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import piece.Cannon;
import piece.Chariot;
import piece.Elephant;
import piece.Guard;
import piece.Horse;
import piece.King;
import piece.Solider;
import piece.Team;

public class BoardInitializer {

    public Board init() {
        Board board = new Board();
        Arrays.stream(Team.values()).forEach(team -> {
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

    private void putHorse(final Team team, final Board board) {
        board.putPieces(List.of(
                new Horse(new Position(3, calculateRow(team, 0)), team, board),
                new Horse(new Position(8, calculateRow(team, 0)), team, board)
        ));
    }

    private void putElephant(final Team team, final Board board) {
        board.putPieces(List.of(
                new Elephant(new Position(2, calculateRow(team, 0)), team, board),
                new Elephant(new Position(7, calculateRow(team, 0)), team, board)
        ));
    }

    private void putGuard(final Team team, final Board board) {
        board.putPieces(List.of(
                new Guard(new Position(4, calculateRow(team, 0)), team, board),
                new Guard(new Position(6, calculateRow(team, 0)), team, board)
        ));
    }

    private void putChariot(final Team team, final Board board) {
        board.putPieces(List.of(
                new Chariot(new Position(1, calculateRow(team, 0)), team, board),
                new Chariot(new Position(9, calculateRow(team, 0)), team, board)
        ));
    }

    private void putKing(final Team team, final Board board) {
        board.putPieces(List.of(
                new King(new Position(5, calculateRow(team, 1)), team, board),
                new King(new Position(5, calculateRow(team, 1)), team, board)
        ));
    }

    private void putCannon(final Team team, final Board board) {
        board.putPieces(List.of(
                new Cannon(new Position(2, calculateRow(team, 2)), team, board),
                new Cannon(new Position(8, calculateRow(team, 2)), team, board)
        ));
    }

    private void putSoldiers(final Team team, final Board board) {
        board.putPieces(IntStream.range(1, 10)
                .filter(BoardInitializer::isSoldierColumn)
                .mapToObj(column -> new Solider(
                        new Position(column, calculateRow(team, 3)),
                        team,
                        board)
                ).collect(Collectors.toUnmodifiableList()));
    }

    private int calculateRow(final Team team, final int rankLine) {
        return team.getInitRow() + team.convertRowOffsetByTeam(rankLine);
    }

    private static boolean isSoldierColumn(final int column) {
        return column % 2 != 0;
    }

}
