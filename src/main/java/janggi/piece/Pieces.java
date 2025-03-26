package janggi.piece;

import janggi.position.Position;
import java.util.HashMap;
import java.util.Map;

public class Pieces {

    public static Map<Position, Piece> init() {
        Map<Position, Piece> board = new HashMap<>();
        board.putAll(createRedSidePieces());
        board.putAll(createBlueSidePieces());
        return board;
    }

    private static Map<Position, Piece> createRedSidePieces() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 1), new Tank(Color.RED));
        board.put(new Position(2, 1), new Elephant(Color.RED));
        board.put(new Position(3, 1), new Horse(Color.RED));
        board.put(new Position(4, 1), new Guard(Color.RED));

        board.put(new Position(6, 1), new Guard(Color.RED));
        board.put(new Position(7, 1), new Horse(Color.RED));
        board.put(new Position(8, 1), new Elephant(Color.RED));
        board.put(new Position(9, 1), new Tank(Color.RED));

        board.put(new Position(5, 2), new King(Color.RED));

        board.put(new Position(2, 3), new Cannon(Color.RED));
        board.put(new Position(8, 3), new Cannon(Color.RED));

        board.put(new Position(1, 4), new Soldier(Color.RED));
        board.put(new Position(3, 4), new Soldier(Color.RED));
        board.put(new Position(5, 4), new Soldier(Color.RED));
        board.put(new Position(7, 4), new Soldier(Color.RED));
        board.put(new Position(9, 4), new Soldier(Color.RED));
        return board;
    }

    private static Map<Position, Piece> createBlueSidePieces() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 10), new Tank(Color.BLUE));
        board.put(new Position(2, 10), new Elephant(Color.BLUE));
        board.put(new Position(3, 10), new Horse(Color.BLUE));
        board.put(new Position(4, 10), new Guard(Color.BLUE));

        board.put(new Position(6, 10), new Guard(Color.BLUE));
        board.put(new Position(7, 10), new Horse(Color.BLUE));
        board.put(new Position(8, 10), new Elephant(Color.BLUE));
        board.put(new Position(9, 10), new Tank(Color.BLUE));

        board.put(new Position(5, 9), new King(Color.BLUE));

        board.put(new Position(2, 8), new Cannon(Color.BLUE));
        board.put(new Position(8, 8), new Cannon(Color.BLUE));

        board.put(new Position(1, 7), new Soldier(Color.BLUE));
        board.put(new Position(3, 7), new Soldier(Color.BLUE));
        board.put(new Position(5, 7), new Soldier(Color.BLUE));
        board.put(new Position(7, 7), new Soldier(Color.BLUE));
        board.put(new Position(9, 7), new Soldier(Color.BLUE));
        return board;
    }
}
