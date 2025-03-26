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
import piece.Soldier;
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
                new Horse(new Position(calculateRow(team, 0), 3), team),
                new Horse(new Position(calculateRow(team, 0), 8), team)
        ));
    }

    private void putElephant(final Team team, final Board board) {
        board.putPieces(List.of(
                new Elephant(new Position(calculateRow(team, 0), 2), team),
                new Elephant(new Position(calculateRow(team, 0), 7), team)
        ));
    }

    private void putGuard(final Team team, final Board board) {
        board.putPieces(List.of(
                new Guard(new Position(calculateRow(team, 0), 4), team),
                new Guard(new Position(calculateRow(team, 0), 6), team)
        ));
    }

    private void putChariot(final Team team, final Board board) {
        board.putPieces(List.of(
                new Chariot(new Position(calculateRow(team, 0), 1), team),
                new Chariot(new Position(calculateRow(team, 0), 9), team)
        ));
    }

    private void putKing(final Team team, final Board board) {
        board.putPieces(List.of(
                new King(new Position(calculateRow(team, 1), 5), team),
                new King(new Position(calculateRow(team, 1), 5), team)
        ));
    }

    private void putCannon(final Team team, final Board board) {
        board.putPieces(List.of(
                new Cannon(new Position(calculateRow(team, 2), 2), team),
                new Cannon(new Position(calculateRow(team, 2), 8), team)
        ));
    }

    private void putSoldiers(final Team team, final Board board) {
        board.putPieces(IntStream.range(1, 10)
                .filter(BoardInitializer::isSoldierColumn)
                .mapToObj(column -> new Soldier(
                        new Position(calculateRow(team, 3), column), team)
                ).collect(Collectors.toUnmodifiableList()));
    }

    private int calculateRow(final Team team, final int rankLine) {
        return team.getInitRow() + team.convertRowOffsetByTeam(rankLine);
    }

    private static boolean isSoldierColumn(final int column) {
        return column % 2 != 0;
    }

}
