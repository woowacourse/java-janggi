package domain;

import domain.pattern.Pattern;
import domain.piece.Empty;
import domain.piece.Piece;
import domain.piece.포;
import java.util.List;
import java.util.Map;

public class JanggiBoard {

    private final Map<JanggiPosition, Piece> janggiBoard;

    public JanggiBoard() {
        this.janggiBoard = JanggiBoardFactory.createJanggiBoard();
    }

    public Map<JanggiPosition, Piece> getJanggiBoard() {
        return janggiBoard;
    }

    public Piece getPieceFrom(JanggiPosition position) {
        return janggiBoard.get(position);
    }

    public void move(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        afterPosition.validateBound();
        Piece piece = getPieceFrom(beforePosition);

        if (piece.getClass().equals(포.class)) {
            move포(piece, beforePosition, afterPosition);
        }
        if (!piece.getClass().equals(포.class)) {
            moveOtherPiece(piece, beforePosition, afterPosition);
        }
        janggiBoard.put(afterPosition, piece);
    }

    private void moveOtherPiece(Piece piece, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        validateMoveOtherPiece(piece, beforePosition, afterPosition);

        janggiBoard.put(beforePosition, new Empty());
        Piece pieceInDanger = janggiBoard.get(afterPosition);
        if (!pieceInDanger.isEmpty()) {
            if (piece.getSide() != pieceInDanger.getSide()) {
                pieceInDanger.capture();
            }
        }
    }

    private void validateMoveOtherPiece(Piece piece, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        boolean isHurdle = isExistHurdle(piece, beforePosition, afterPosition);
        if (isHurdle) {
            throw new IllegalArgumentException();
        }
    }

    private void move포(Piece piece, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        boolean isHurdle = isExistHurdle(piece, beforePosition, afterPosition);
        validateMove포(piece, beforePosition, afterPosition, isHurdle);

        janggiBoard.put(beforePosition, new Empty());
        Piece pieceInDanger = janggiBoard.get(afterPosition);
        if (!pieceInDanger.isEmpty()) {
            capturePieceIfNot포AndNotMySide(piece, pieceInDanger);
        }
    }

    private void validateMove포(Piece piece, JanggiPosition beforePosition, JanggiPosition afterPosition,
                               boolean isHurdle) {
        if (!isHurdle) {
            throw new IllegalArgumentException();
        }
        if (getHurdleCount(piece, beforePosition, afterPosition) > 1) {
            throw new IllegalStateException();
        }
        if (getFirstHurdlePiece(beforePosition, afterPosition).getClass().equals(포.class)) {
            throw new IllegalStateException();
        }
    }

    private static void capturePieceIfNot포AndNotMySide(Piece piece, Piece pieceInDanger) {
        if (!pieceInDanger.getClass().equals(포.class)) {
            throw new IllegalStateException();
        }
        if (piece.getSide() != pieceInDanger.getSide()) {
            pieceInDanger.capture();
        }
    }

    private boolean isExistHurdle(Piece piece, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        List<Pattern> patterns = piece.findPath(beforePosition, afterPosition);
        List<Pattern> patternsWithoutDestination = patterns.subList(0, patterns.size() - 1);

        JanggiPosition newPosition = beforePosition;
        for (Pattern pattern : patternsWithoutDestination) {
            newPosition = newPosition.moveOnePosition(pattern);
            if (isExistPiece(newPosition)) {
                return true;
            }
        }
        return false;
    }

    private Piece getFirstHurdlePiece(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        Piece piece = getPieceFrom(beforePosition);
        Piece hurdlePiece = new Empty();
        List<Pattern> patterns = piece.findPath(beforePosition, afterPosition);
        JanggiPosition newPosition = beforePosition;
        for (Pattern pattern : patterns) {
            newPosition = newPosition.moveOnePosition(pattern);
            if (isExistPiece(newPosition)) {
                hurdlePiece = getPieceFrom(newPosition);
            }
        }
        return hurdlePiece;
    }

    private int getHurdleCount(Piece piece, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        List<Pattern> patterns = piece.findPath(beforePosition, afterPosition);
        int count = 0;
        JanggiPosition newPosition = beforePosition;
        for (Pattern pattern : patterns) {
            newPosition = newPosition.moveOnePosition(pattern);
            if (isExistPiece(newPosition)) {
                count++;
            }
        }
        return count;
    }

    private boolean isExistPiece(JanggiPosition newPosition) {
        return !getPieceFrom(newPosition).isEmpty();
    }
}
