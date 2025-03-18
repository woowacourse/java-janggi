package janggi;

import janggi.piece.Cannon;
import janggi.piece.Elephant;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.King;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import janggi.piece.Tank;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(final Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public static Board init() {
        Map<Position, Piece> board = new HashMap<>();

        board.put(new Position(0,0), new Tank(Side.BLUE));
        board.put(new Position(1,0), new Elephant(Side.BLUE));
        board.put(new Position(2,0), new Horse(Side.BLUE));
        board.put(new Position(3,0), new Guard(Side.BLUE));

        board.put(new Position(5,0), new Guard(Side.BLUE));
        board.put(new Position(6,0), new Horse(Side.BLUE));
        board.put(new Position(7,0), new Elephant(Side.BLUE));
        board.put(new Position(8,0), new Tank(Side.BLUE));

        board.put(new Position(4,1), new King(Side.BLUE));

        board.put(new Position(1,2), new Cannon(Side.BLUE));
        board.put(new Position(7,2), new Cannon(Side.BLUE));

        board.put(new Position(0,3), new Soldier(Side.BLUE));
        board.put(new Position(2,3), new Soldier(Side.BLUE));
        board.put(new Position(4,3), new Soldier(Side.BLUE));
        board.put(new Position(6,3), new Soldier(Side.BLUE));
        board.put(new Position(8,3), new Soldier(Side.BLUE));

        board.put(new Position(0,9), new Tank(Side.RED));
        board.put(new Position(1,9), new Elephant(Side.RED));
        board.put(new Position(2,9), new Horse(Side.RED));
        board.put(new Position(3,9), new Guard(Side.RED));

        board.put(new Position(5,9), new Guard(Side.RED));
        board.put(new Position(6,9), new Horse(Side.RED));
        board.put(new Position(7,9), new Elephant(Side.RED));
        board.put(new Position(8,9), new Tank(Side.RED));

        board.put(new Position(4,8), new King(Side.RED));

        board.put(new Position(1,7), new Cannon(Side.RED));
        board.put(new Position(7,7), new Cannon(Side.RED));

        board.put(new Position(0,6), new Soldier(Side.RED));
        board.put(new Position(2,6), new Soldier(Side.RED));
        board.put(new Position(4,6), new Soldier(Side.RED));
        board.put(new Position(6,6), new Soldier(Side.RED));
        board.put(new Position(8,6), new Soldier(Side.RED));
        return new Board(board);
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }
}
