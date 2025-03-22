package domain.board;

import domain.JanggiCoordinate;
import domain.piece.Piece;

import java.util.Map;

public class JanggiBoard {
    private static final int ROW_SIZE = 10;
    private static final int COL_SIZE = 9;
    private static final int BOUNDARY_START = 1;

    private Map<JanggiCoordinate, Piece> board;

    public JanggiBoard(Map<JanggiCoordinate, Piece> board) {
        this.board = board;
    }

    public boolean isOutOfBoundary(JanggiCoordinate coordinate) {
        int row = coordinate.getRow();
        int col = coordinate.getCol();

        return row < BOUNDARY_START || row > ROW_SIZE || col < BOUNDARY_START || col > COL_SIZE;
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
}
