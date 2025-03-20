package domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardInitializer {

    public Board init() {
        Board board = new Board();
        Arrays.stream(Team.values()).forEach(team -> putSoldiers(team, board));
        Arrays.stream(Team.values()).forEach(team -> putCannon(team, board));
        Arrays.stream(Team.values()).forEach(team -> putKing(team, board));
        Arrays.stream(Team.values()).forEach(team -> putChariot(team, board));
        Arrays.stream(Team.values()).forEach(team -> putGuard(team, board));
        Arrays.stream(Team.values()).forEach(team -> putElephant(team, board));
        Arrays.stream(Team.values()).forEach(team -> putHorse(team, board));
        return board;
    }

    private void putHorse(Team team, Board board) {
        board.putPieces(List.of(
                new Horse(new Position(3, calculateFirstRow(team)), team),
                new Horse(new Position(8, calculateFirstRow(team)), team)
        ));
    }
    private void putElephant(Team team, Board board) {
        board.putPieces(List.of(
                new Elephant(new Position(2, calculateFirstRow(team)), team),
                new Elephant(new Position(7, calculateFirstRow(team)), team)
        ));
    }

    private void putGuard(Team team, Board board) {
        board.putPieces(List.of(
                new Guard(new Position(4, calculateFirstRow(team)), team),
                new Guard(new Position(6, calculateFirstRow(team)), team)
        ));
    }

    private void putChariot(Team team, Board board) {
        board.putPieces(List.of(
                new Chariot(new Position(1, calculateFirstRow(team)), team),
                new Chariot(new Position(9, calculateFirstRow(team)), team)
        ));
    }

    private void putKing(Team team, Board board) {
        board.putPieces(List.of(
                new King(new Position(5, calculateKingRow(team)), team),
                new King(new Position(5, calculateKingRow(team)), team)
        ));
    }

    private void putCannon(Team team, Board board) {
        board.putPieces(List.of(
                new Cannon(new Position(2, calculateCannonRow(team)), team),
                new Cannon(new Position(8, calculateCannonRow(team)), team)
        ));
    }

    private void putSoldiers(Team team, Board board) {
        board.putPieces(IntStream.range(1, 10)
                .filter(BoardInitializer::isSoldierColumn)
                .mapToObj(column -> new Solider(
                        new Position(column, calculateSoldierRow(team)),
                        team)
                ).collect(Collectors.toUnmodifiableList()));
    }

    private int calculateCannonRow(Team team) {
        return team.getInitRow() + team.convertRowOffsetByTeam(2);
    }

    private int calculateKingRow(Team team) {
        return team.getInitRow() + team.convertRowOffsetByTeam(1);
    }

    private int calculateFirstRow(Team team) {
        return team.getInitRow() + team.convertRowOffsetByTeam(0);
    }

    private int calculateSoldierRow(Team team) {
        return team.getInitRow() + team.convertRowOffsetByTeam(3);
    }

    private static boolean isSoldierColumn(int column) {
        return column % 2 != 0;
    }
}
