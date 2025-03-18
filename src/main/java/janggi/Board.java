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

        board.put(new Position(1,1), new Tank(Side.BLUE));
        board.put(new Position(2,1), new Elephant(Side.BLUE));
        board.put(new Position(3,1), new Horse(Side.BLUE));
        board.put(new Position(4,1), new Guard(Side.BLUE));

        board.put(new Position(6,1), new Guard(Side.BLUE));
        board.put(new Position(7,1), new Horse(Side.BLUE));
        board.put(new Position(8,1), new Elephant(Side.BLUE));
        board.put(new Position(9,1), new Tank(Side.BLUE));

        board.put(new Position(5,2), new King(Side.BLUE));

        board.put(new Position(2,3), new Cannon(Side.BLUE));
        board.put(new Position(8,3), new Cannon(Side.BLUE));

        board.put(new Position(1,4), new Soldier(Side.BLUE));
        board.put(new Position(3,4), new Soldier(Side.BLUE));
        board.put(new Position(5,4), new Soldier(Side.BLUE));
        board.put(new Position(7,4), new Soldier(Side.BLUE));
        board.put(new Position(9,4), new Soldier(Side.BLUE));

        board.put(new Position(1,10), new Tank(Side.RED));
        board.put(new Position(2,10), new Elephant(Side.RED));
        board.put(new Position(3,10), new Horse(Side.RED));
        board.put(new Position(4,10), new Guard(Side.RED));

        board.put(new Position(6,10), new Guard(Side.RED));
        board.put(new Position(7,10), new Horse(Side.RED));
        board.put(new Position(8,10), new Elephant(Side.RED));
        board.put(new Position(9,10), new Tank(Side.RED));

        board.put(new Position(5,9), new King(Side.RED));

        board.put(new Position(2,8), new Cannon(Side.RED));
        board.put(new Position(8,8), new Cannon(Side.RED));

        board.put(new Position(1,7), new Soldier(Side.RED));
        board.put(new Position(3,7), new Soldier(Side.RED));
        board.put(new Position(5,7), new Soldier(Side.RED));
        board.put(new Position(7,7), new Soldier(Side.RED));
        board.put(new Position(9,7), new Soldier(Side.RED));
        return new Board(board);
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }
}
