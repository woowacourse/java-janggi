package janggi.fixture;

import janggi.Team;
import janggi.board.Board;
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

import static janggi.fixture.PositionFixture.createPosition;

public class BoardFixture {

    public static Board createBasicBoard() {
        Map<Position, Piece> board = new HashMap<>();
        initializeRedTeam(board);
        initializeGreenTeam(board);
        return new Board(board);
    }

    private static void initializeRedTeam(Map<Position, Piece> board) {
        Team team = Team.RED;
        board.put(createPosition(0, 6), new Soldier(team));
        board.put(createPosition(2, 6), new Soldier(team));
        board.put(createPosition(4, 6), new Soldier(team));
        board.put(createPosition(6, 6), new Soldier(team));
        board.put(createPosition(8, 6), new Soldier(team));

        board.put(createPosition(1, 7), new Canon(team));
        board.put(createPosition(7, 7), new Canon(team));

        board.put(createPosition(4, 8), new General(team));

        board.put(createPosition(0, 9), new Chariot(team));
        board.put(createPosition(8, 9), new Chariot(team));

        board.put(createPosition(1, 9), new Elephant(team));
        board.put(createPosition(6, 9), new Elephant(team));

        board.put(createPosition(2, 9), new Horse(team));
        board.put(createPosition(7, 9), new Horse(team));

        board.put(createPosition(3, 9), new Guard(team));
        board.put(createPosition(5, 9), new Guard(team));
    }

    private static void initializeGreenTeam(Map<Position, Piece> board) {
        Team team = Team.GREEN;
        board.put(createPosition(0, 3), new Soldier(team));
        board.put(createPosition(2, 3), new Soldier(team));
        board.put(createPosition(4, 3), new Soldier(team));
        board.put(createPosition(6, 3), new Soldier(team));
        board.put(createPosition(8, 3), new Soldier(team));

        board.put(createPosition(1, 2), new Canon(team));
        board.put(createPosition(7, 2), new Canon(team));

        board.put(createPosition(4, 1), new General(team));

        board.put(createPosition(0, 0), new Chariot(team));
        board.put(createPosition(8, 0), new Chariot(team));

        board.put(createPosition(1, 0), new Elephant(team));
        board.put(createPosition(6, 0), new Elephant(team));

        board.put(createPosition(2, 0), new Horse(team));
        board.put(createPosition(7, 0), new Horse(team));

        board.put(createPosition(3, 0), new Guard(team));
        board.put(createPosition(5, 0), new Guard(team));
    }
}
