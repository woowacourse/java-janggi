package janggi;

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

        board.put(new Position(0,0), new Piece(Side.BLUE, PieceType.TANK));
        board.put(new Position(1,0), new Piece(Side.BLUE, PieceType.ELEPHANT));
        board.put(new Position(2,0), new Piece(Side.BLUE, PieceType.HORSE));
        board.put(new Position(3,0), new Piece(Side.BLUE, PieceType.GUARD));

        board.put(new Position(5,0), new Piece(Side.BLUE, PieceType.GUARD));
        board.put(new Position(6,0), new Piece(Side.BLUE, PieceType.HORSE));
        board.put(new Position(7,0), new Piece(Side.BLUE, PieceType.ELEPHANT));
        board.put(new Position(8,0), new Piece(Side.BLUE, PieceType.TANK));

        board.put(new Position(4,1), new Piece(Side.BLUE, PieceType.KING));

        board.put(new Position(1,2), new Piece(Side.BLUE, PieceType.CANNON));
        board.put(new Position(7,2), new Piece(Side.BLUE, PieceType.CANNON));

        board.put(new Position(0,3), new Piece(Side.BLUE, PieceType.SOLDIER));
        board.put(new Position(2,3), new Piece(Side.BLUE, PieceType.SOLDIER));
        board.put(new Position(4,3), new Piece(Side.BLUE, PieceType.SOLDIER));
        board.put(new Position(6,3), new Piece(Side.BLUE, PieceType.SOLDIER));
        board.put(new Position(8,3), new Piece(Side.BLUE, PieceType.SOLDIER));

        board.put(new Position(0,9), new Piece(Side.RED, PieceType.TANK));
        board.put(new Position(1,9), new Piece(Side.RED, PieceType.ELEPHANT));
        board.put(new Position(2,9), new Piece(Side.RED, PieceType.HORSE));
        board.put(new Position(3,9), new Piece(Side.RED, PieceType.GUARD));

        board.put(new Position(5,9), new Piece(Side.RED, PieceType.GUARD));
        board.put(new Position(6,9), new Piece(Side.RED, PieceType.HORSE));
        board.put(new Position(7,9), new Piece(Side.RED, PieceType.ELEPHANT));
        board.put(new Position(8,9), new Piece(Side.RED, PieceType.TANK));

        board.put(new Position(4,8), new Piece(Side.RED, PieceType.KING));

        board.put(new Position(1,7), new Piece(Side.RED, PieceType.CANNON));
        board.put(new Position(7,7), new Piece(Side.RED, PieceType.CANNON));

        board.put(new Position(0,6), new Piece(Side.RED, PieceType.SOLDIER));
        board.put(new Position(2,6), new Piece(Side.RED, PieceType.SOLDIER));
        board.put(new Position(4,6), new Piece(Side.RED, PieceType.SOLDIER));
        board.put(new Position(6,6), new Piece(Side.RED, PieceType.SOLDIER));
        board.put(new Position(8,6), new Piece(Side.RED, PieceType.SOLDIER));
        return new Board(board);
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }
}
