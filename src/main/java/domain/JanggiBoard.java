package domain;

import domain.pattern.Pattern;
import domain.piece.Empty;
import domain.piece.Piece;
import domain.piece.포;
import java.util.List;
import java.util.Map;

public class JanggiBoard {

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
        Piece pieceInDanger = janggiBoard.get(afterPosition);
        if (!pieceInDanger.isEmpty()) {
            if (piece.getSide() != pieceInDanger.getSide()) {
                pieceInDanger.capture();
            }
        }
        janggiBoard.put(afterPosition, piece);
    }

    private void move포(Piece piece, Position beforePosition, Position afterPosition) {
        boolean isHurdle = isExistHurdle(piece, beforePosition, afterPosition);
        if (!isHurdle) {
            throw new IllegalArgumentException();
        }
        if (getHurdleCount(piece, beforePosition, afterPosition) > 1) {
            throw new IllegalStateException();
        }
        if (getFirstHurdlePiece(beforePosition, afterPosition).getClass().equals(포.class)) {
            throw new IllegalStateException();
        }
        janggiBoard.put(beforePosition, new Empty());
        Piece pieceInDanger = janggiBoard.get(afterPosition);
        if (!pieceInDanger.isEmpty()) {
            if (!pieceInDanger.getClass().equals(포.class)) {
                throw new IllegalStateException();
            }
            if (piece.getSide() != pieceInDanger.getSide()) {
                pieceInDanger.capture();
            }
        }
        janggiBoard.put(afterPosition, piece);
    }

    private boolean isExistHurdle(Piece piece, Position beforePosition, Position afterPosition) {
        List<Pattern> patterns = piece.findPath(beforePosition, afterPosition);
        List<Pattern> patternsWithoutDestination = patterns.subList(0, patterns.size() - 1);

        Position newPosition = beforePosition;
        for (Pattern pattern : patternsWithoutDestination) {
            newPosition = newPosition.moveOnePosition(pattern);
            if (!getPieceFrom(newPosition).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private Piece getFirstHurdlePiece(Position beforePosition, Position afterPosition) {
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
