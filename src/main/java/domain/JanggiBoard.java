package domain;

import domain.pattern.Pattern;
import java.util.List;
import java.util.Map;

public class JanggiBoard {

    //    private final Table<Integer, Integer, Piece> janggiBoard;
    private final Map<Position, Piece> janggiBoard;

    public JanggiBoard() {
        this.janggiBoard = JanggiBoardFactory.createJanggiBoard();
    }

    public Map<Position, Piece> getJanggiBoard() {
        return janggiBoard;
    }

    public Piece getPieceFrom(Position position) {
        return janggiBoard.get(position);
    }

    public void move(Position beforePosition, Position afterPosition) {
        if (afterPosition.x() < 0 || afterPosition.x() > 9 || afterPosition.y() < 1 || afterPosition.y() > 9) {
            throw new IllegalArgumentException();
        }

        Piece piece = getPieceFrom(beforePosition);

        if (piece.getClass().equals(포.class)) {
            move포(piece, beforePosition, afterPosition);
        }
        if (!piece.getClass().equals(포.class)) {
            moveOtherPiece(piece, beforePosition, afterPosition);
        }
    }

    private void moveOtherPiece(Piece piece, Position beforePosition, Position afterPosition) {
        boolean isHurdle = isExistHurdle(piece, beforePosition, afterPosition);
        if (isHurdle) {
            throw new IllegalArgumentException();
        }

        janggiBoard.put(beforePosition, new Empty());
        janggiBoard.put(afterPosition, piece);
    }

    private void move포(Piece piece, Position beforePosition, Position afterPosition) {
        validateMove포(beforePosition, afterPosition);
        boolean isHurdle = isExistHurdle(piece, beforePosition, afterPosition);
        if (!isHurdle) {
            throw new IllegalArgumentException();
        }
        if (getHurdleCount(piece, beforePosition, afterPosition) > 1) {
            throw new IllegalStateException();
        }
        janggiBoard.put(beforePosition, new Empty());
        janggiBoard.put(afterPosition, piece);
    }

    private boolean isExistHurdle(Piece piece, Position beforePosition, Position afterPosition) {
        List<Pattern> patterns = piece.findPath(beforePosition, afterPosition);

        Position newPosition = beforePosition;
        for (Pattern pattern : patterns) {
            newPosition = newPosition.moveOnePosition(pattern);
            if (!getPieceFrom(newPosition).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void validateMove포(Position beforePosition, Position afterPosition) {
        Piece hurdlePiece = getHurdlePiece(beforePosition, afterPosition);
        if (hurdlePiece.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private Piece getHurdlePiece(Position beforePosition, Position afterPosition) {
        Piece piece = getPieceFrom(beforePosition);
        Piece hurdlePiece = new Empty();
        List<Pattern> patterns = piece.findPath(beforePosition, afterPosition);
        Position newPosition = beforePosition;
        for (Pattern pattern : patterns) {
            newPosition = newPosition.moveOnePosition(pattern);
            if (!getPieceFrom(newPosition).isEmpty()) {
                hurdlePiece = getPieceFrom(newPosition);
            }
        }
        return hurdlePiece;
    }

    private int getHurdleCount(Piece piece, Position beforePosition, Position afterPosition) {
        List<Pattern> patterns = piece.findPath(beforePosition, afterPosition);
        int count = 0;
        Position newPosition = beforePosition;
        for (Pattern pattern : patterns) {
            newPosition = newPosition.moveOnePosition(pattern);
            if (!getPieceFrom(newPosition).isEmpty()) {
                count++;
            }
        }
        return count;
    }
}
