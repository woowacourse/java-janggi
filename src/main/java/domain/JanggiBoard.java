package domain;

import domain.piece.Piece;

import java.util.Map;

public class JanggiBoard {
    public static final int ROW_SIZE = 10;
    public static final int COL_SIZE = 9;
    public static final int BOUNDARY_START = 1;


    private final Map<JanggiCoordinate, Piece> board;

    public JanggiBoard(Map<JanggiCoordinate, Piece> board) {
        this.board = board;
    }

    public boolean isOccupied(JanggiCoordinate coordinate) {
        return board.containsKey(coordinate);
    }

    public Piece findPieceByCoordinate(JanggiCoordinate coordinate) {
        if (isOccupied(coordinate)) {
            return board.get(coordinate);
        }
        throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다.");
    }

    public void movePiece(JanggiCoordinate from, JanggiCoordinate to) {
        Piece piece = findPieceByCoordinate(from);
        board.put(to, piece);
        board.remove(from);
    }
}
