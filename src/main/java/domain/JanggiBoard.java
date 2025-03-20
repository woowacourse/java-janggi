package domain;

import domain.pattern.Pattern;
import domain.piece.Empty;
import domain.piece.JanggiPiece;
import domain.piece.포;
import java.util.List;
import java.util.Map;

public class JanggiBoard {

    private final Map<JanggiPosition, JanggiPiece> janggiBoard;

    public JanggiBoard() {
        this.janggiBoard = JanggiBoardFactory.createJanggiBoard();
    }

    public Map<JanggiPosition, JanggiPiece> getJanggiBoard() {
        return janggiBoard;
    }

    public JanggiPiece getPieceFrom(JanggiPosition position) {
        return janggiBoard.get(position);
    }

    public void move(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        afterPosition.validateBound();
        JanggiPiece piece = getPieceFrom(beforePosition);

        if (piece.getClass().equals(포.class)) {
            move포(piece, beforePosition, afterPosition);
        }
        if (!piece.getClass().equals(포.class)) {
            moveOtherPiece(piece, beforePosition, afterPosition);
        }
        validateDestinationPiece(piece, afterPosition);
        janggiBoard.put(afterPosition, piece);
    }

    private void validateDestinationPiece(JanggiPiece piece, JanggiPosition afterPosition) {
        if (piece.getSide() == getPieceFrom(afterPosition).getSide()) {
            throw new IllegalStateException("목적지에 같은 팀의 기물이 존재하여 이동할 수 없습니다.");
        }
    }

    private void moveOtherPiece(JanggiPiece piece, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        validateMoveOtherPiece(piece, beforePosition, afterPosition);

        janggiBoard.put(beforePosition, new Empty());
        JanggiPiece pieceInDanger = janggiBoard.get(afterPosition);
        if (!pieceInDanger.isEmpty()) {
            pieceInDanger.captureIfNotMySide(piece.getSide());
        }
    }

    private void validateMoveOtherPiece(JanggiPiece piece, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        boolean isHurdle = isExistHurdle(piece, beforePosition, afterPosition);
        if (isHurdle) {
            throw new IllegalArgumentException("경로에 장애물이 있어서 기물을 움직일 수 없습니다.");
        }
    }

    private void move포(JanggiPiece piece, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        boolean isHurdle = isExistHurdle(piece, beforePosition, afterPosition);
        validateMove포(piece, beforePosition, afterPosition, isHurdle);

        janggiBoard.put(beforePosition, new Empty());
        JanggiPiece pieceInDanger = janggiBoard.get(afterPosition);
        if (!pieceInDanger.isEmpty()) {
            capturePieceIfNot포AndNotMySide(piece, pieceInDanger);
        }
    }

    private void validateMove포(JanggiPiece piece, JanggiPosition beforePosition, JanggiPosition afterPosition,
                               boolean isHurdle) {
        if (!isHurdle) {
            throw new IllegalArgumentException("경로에 장애물이 없어서 움직일 수 없습니다.");
        }
        if (getHurdleCount(piece, beforePosition, afterPosition) > 1) {
            throw new IllegalStateException("경로에 장애물이 2개 이상 있어서 움직일 수 없습니다.");
        }
        if (getFirstHurdlePiece(beforePosition, afterPosition).getClass().equals(포.class)) {
            throw new IllegalStateException("포는 포를 넘을 수 없습니다.");
        }
    }

    private static void capturePieceIfNot포AndNotMySide(JanggiPiece piece, JanggiPiece pieceInDanger) {
        if (!pieceInDanger.getClass().equals(포.class)) {
            throw new IllegalStateException("포는 포를 잡을 수 없습니다.");
        }
        pieceInDanger.captureIfNotMySide(piece.getSide());
    }

    private boolean isExistHurdle(JanggiPiece piece, JanggiPosition beforePosition, JanggiPosition afterPosition) {
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

    private JanggiPiece getFirstHurdlePiece(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        JanggiPiece piece = getPieceFrom(beforePosition);
        JanggiPiece hurdlePiece = new Empty();
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

    private int getHurdleCount(JanggiPiece piece, JanggiPosition beforePosition, JanggiPosition afterPosition) {
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
