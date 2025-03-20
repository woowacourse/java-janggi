package janggi;

import janggi.board.Position;
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

public class Application {
    public static void main(String[] args) {
        Map<Position, Piece> board = new HashMap<>();
        initializeRedTeam(board);
        initializeGreenTeam(board);
    }

    private static void initializeRedTeam(Map<Position, Piece> board) {
        Team team = Team.RED;
        board.put(new Position(0, 6), new Soldier(team));
        board.put(new Position(2, 6), new Soldier(team));
        board.put(new Position(4, 6), new Soldier(team));
        board.put(new Position(6, 6), new Soldier(team));
        board.put(new Position(8, 6), new Soldier(team));

        board.put(new Position(1, 7), new Canon(team));
        board.put(new Position(7, 7), new Canon(team));

        board.put(new Position(4, 8), new General(team));

        board.put(new Position(0, 9), new Chariot(team));
        board.put(new Position(8, 9), new Chariot(team));

        board.put(new Position(1, 9), new Elephant(team));
        board.put(new Position(6, 9), new Elephant(team));

        board.put(new Position(2, 9), new Horse(team));
        board.put(new Position(7, 9), new Horse(team));

        board.put(new Position(3, 9), new Guard(team));
        board.put(new Position(5, 9), new Guard(team));
    }

    private static void initializeGreenTeam(Map<Position, Piece> board) {
        Team team = Team.GREEN;
        board.put(new Position(0, 3), new Soldier(team));
        board.put(new Position(2, 3), new Soldier(team));
        board.put(new Position(4, 3), new Soldier(team));
        board.put(new Position(6, 3), new Soldier(team));
        board.put(new Position(8, 3), new Soldier(team));

        board.put(new Position(1, 2), new Canon(team));
        board.put(new Position(7, 2), new Canon(team));

        board.put(new Position(4, 1), new General(team));

        board.put(new Position(0, 0), new Chariot(team));
        board.put(new Position(8, 0), new Chariot(team));

        board.put(new Position(1, 0), new Elephant(team));
        board.put(new Position(6, 0), new Elephant(team));

        board.put(new Position(2, 0), new Horse(team));
        board.put(new Position(7, 0), new Horse(team));

        board.put(new Position(3, 0), new Guard(team));
        board.put(new Position(5, 0), new Guard(team));
    }
}
