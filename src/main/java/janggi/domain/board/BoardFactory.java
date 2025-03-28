package janggi.domain.board;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.King;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Side;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Tank;
import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    private BoardFactory() {
    }

    public static Board initBoard() {
        Map<Position, Piece> board = new HashMap<>();
        board.putAll(createRedSidePieces());
        board.putAll(createBlueSidePieces());
        return new Board(board);
    }

    private static Map<Position, Piece> createRedSidePieces() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 10), new Tank(Side.RED));
        board.put(new Position(2, 10), new Elephant(Side.RED));
        board.put(new Position(3, 10), new Horse(Side.RED));
        board.put(new Position(4, 10), new Guard(Side.RED));

        board.put(new Position(6, 10), new Guard(Side.RED));
        board.put(new Position(7, 10), new Horse(Side.RED));
        board.put(new Position(8, 10), new Elephant(Side.RED));
        board.put(new Position(9, 10), new Tank(Side.RED));

        board.put(new Position(5, 9), new King(Side.RED));

        board.put(new Position(2, 8), new Cannon(Side.RED));
        board.put(new Position(8, 8), new Cannon(Side.RED));

        board.put(new Position(1, 7), new Soldier(Side.RED));
        board.put(new Position(3, 7), new Soldier(Side.RED));
        board.put(new Position(5, 7), new Soldier(Side.RED));
        board.put(new Position(7, 7), new Soldier(Side.RED));
        board.put(new Position(9, 7), new Soldier(Side.RED));
        return board;
    }

    private static Map<Position, Piece> createBlueSidePieces() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 1), new Tank(Side.BLUE));
        board.put(new Position(2, 1), new Elephant(Side.BLUE));
        board.put(new Position(3, 1), new Horse(Side.BLUE));
        board.put(new Position(4, 1), new Guard(Side.BLUE));

        board.put(new Position(6, 1), new Guard(Side.BLUE));
        board.put(new Position(7, 1), new Horse(Side.BLUE));
        board.put(new Position(8, 1), new Elephant(Side.BLUE));
        board.put(new Position(9, 1), new Tank(Side.BLUE));

        board.put(new Position(5, 2), new King(Side.BLUE));

        board.put(new Position(2, 3), new Cannon(Side.BLUE));
        board.put(new Position(8, 3), new Cannon(Side.BLUE));

        board.put(new Position(1, 4), new Soldier(Side.BLUE));
        board.put(new Position(3, 4), new Soldier(Side.BLUE));
        board.put(new Position(5, 4), new Soldier(Side.BLUE));
        board.put(new Position(7, 4), new Soldier(Side.BLUE));
        board.put(new Position(9, 4), new Soldier(Side.BLUE));
        return board;
    }
}
