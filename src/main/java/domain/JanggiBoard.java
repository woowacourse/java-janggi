package domain;

import com.google.common.collect.Table;
import domain.pattern.Pattern;
import java.util.List;

public class JanggiBoard {

    private final Table<Integer, Integer, Piece> janggiBoard;

    public JanggiBoard() {
        this.janggiBoard = JanggiBoardFactory.createJanggiBoard();
    }

    public Table<Integer, Integer, Piece> getJanggiBoard() {
        return janggiBoard;
    }

    public Piece getPieceFrom(int row, int column) {
        return janggiBoard.get(row, column);
    }

    public void move(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        if (afterRow < 0 || afterRow > 9 || afterColumn < 1 || afterColumn > 9) {
            throw new IllegalArgumentException();
        }

        Piece piece = getPieceFrom(beforeRow, beforeColumn);

        if (piece.getClass().equals(포.class)) {
            move포(piece, beforeRow, beforeColumn, afterRow, afterColumn);
        }
        if (!piece.getClass().equals(포.class)) {
            moveOtherPiece(piece, beforeRow, beforeColumn, afterRow, afterColumn);
        }
    }

    private void moveOtherPiece(Piece piece, int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        boolean isHurdle = isExistHurdle(piece, beforeRow, beforeColumn, afterRow, afterColumn);
        if (isHurdle) {
            throw new IllegalArgumentException();
        }

        janggiBoard.put(beforeRow, beforeColumn, new Empty());
        janggiBoard.put(afterRow, afterColumn, piece);
    }

    private void move포(Piece piece, int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        canMove포(beforeRow, beforeColumn, afterRow, afterColumn);
        boolean isHurdle = isExistHurdle(piece, beforeRow, beforeColumn, afterRow, afterColumn);
        if (!isHurdle) {
            throw new IllegalArgumentException();
        }

        janggiBoard.put(beforeRow, beforeColumn, new Empty());
        janggiBoard.put(afterRow, afterColumn, piece);
    }

    private boolean isExistHurdle(Piece piece, int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        int transformedBeforeRow = beforeRow;
        int transformedAfterRow = afterRow;

        if (beforeRow == 0) {
            transformedBeforeRow = 10;
        }
        if (afterRow == 0) {
            transformedAfterRow = 10;
        }
        List<Pattern> patterns = piece.findPath(transformedBeforeRow, beforeColumn, transformedAfterRow, afterColumn);
        for (Pattern pattern : patterns) {
            if (beforeRow + pattern.getX() < 0) {
                transformedBeforeRow = 9;
            } else {
            }
            transformedBeforeRow += pattern.getX();
            beforeColumn += pattern.getY();
            if (!getPieceFrom(transformedBeforeRow, beforeColumn).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void canMove포(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
//        장애물 가져오기
        Piece hurdlePiece = getHurdlePiece(beforeRow, beforeColumn, afterRow, afterColumn);
        if (hurdlePiece.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private Piece getHurdlePiece(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        Piece piece = getPieceFrom(beforeRow, beforeColumn);
        Piece hurdlePiece = new Empty();
        List<Pattern> patterns = piece.findPath(beforeRow, beforeColumn, afterRow, afterColumn);
        for (Pattern pattern : patterns) {
            if (beforeRow + pattern.getX() < 0) {
                beforeRow = 9;
            } else {
                beforeRow += pattern.getX();
            }
            beforeColumn += pattern.getY();
            if (!getPieceFrom(beforeRow, beforeColumn).isEmpty()) {
                hurdlePiece = getPieceFrom(beforeRow, beforeColumn);
            }
        }
        return hurdlePiece;
    }
}
