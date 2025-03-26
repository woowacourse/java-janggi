package janggi.board;

import janggi.Team;
import janggi.board.position.Column;
import janggi.board.position.Position;
import janggi.board.position.Row;
import janggi.piece.Canon;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import java.util.HashMap;
import java.util.Map;

public class BoardInitializer {
    public Board makeBoard() {
        Map<Position, Piece> board = new HashMap<>();
        initializeRedTeam(board);
        initializeGreenTeam(board);
        return new Board(board);
    }

    private static void initializeRedTeam(Map<Position, Piece> board) {
        Team team = Team.RED;
        board.put(new Position(Column.from(0), Row.from(6)), new Soldier(team));
        board.put(new Position(Column.from(2), Row.from(6)), new Soldier(team));
        board.put(new Position(Column.from(4), Row.from(6)), new Soldier(team));
        board.put(new Position(Column.from(6), Row.from(6)), new Soldier(team));
        board.put(new Position(Column.from(8), Row.from(6)), new Soldier(team));

        board.put(new Position(Column.from(1), Row.from(7)), new Canon(team));
        board.put(new Position(Column.from(7), Row.from(7)), new Canon(team));

        board.put(new Position(Column.from(4), Row.from(8)), new General(team));

        board.put(new Position(Column.from(0), Row.from(9)), new Chariot(team));
        board.put(new Position(Column.from(8), Row.from(9)), new Chariot(team));

        board.put(new Position(Column.from(1), Row.from(9)), new Elephant(team));
        board.put(new Position(Column.from(6), Row.from(9)), new Elephant(team));

        board.put(new Position(Column.from(2), Row.from(9)), new Horse(team));
        board.put(new Position(Column.from(7), Row.from(9)), new Horse(team));

        board.put(new Position(Column.from(3), Row.from(9)), new Guard(team));
        board.put(new Position(Column.from(5), Row.from(9)), new Guard(team));
    }

    private static void initializeGreenTeam(Map<Position, Piece> board) {
        Team team = Team.GREEN;
        board.put(new Position(Column.from(0), Row.from(3)), new Soldier(team));
        board.put(new Position(Column.from(2), Row.from(3)), new Soldier(team));
        board.put(new Position(Column.from(4), Row.from(3)), new Soldier(team));
        board.put(new Position(Column.from(6), Row.from(3)), new Soldier(team));
        board.put(new Position(Column.from(8), Row.from(3)), new Soldier(team));

        board.put(new Position(Column.from(1), Row.from(2)), new Canon(team));
        board.put(new Position(Column.from(7), Row.from(2)), new Canon(team));

        board.put(new Position(Column.from(4), Row.from(1)), new General(team));

        board.put(new Position(Column.from(0), Row.from(0)), new Chariot(team));
        board.put(new Position(Column.from(8), Row.from(0)), new Chariot(team));

        board.put(new Position(Column.from(1), Row.from(0)), new Elephant(team));
        board.put(new Position(Column.from(6), Row.from(0)), new Elephant(team));

        board.put(new Position(Column.from(2), Row.from(0)), new Horse(team));
        board.put(new Position(Column.from(7), Row.from(0)), new Horse(team));

        board.put(new Position(Column.from(3), Row.from(0)), new Guard(team));
        board.put(new Position(Column.from(5), Row.from(0)), new Guard(team));
    }
}
