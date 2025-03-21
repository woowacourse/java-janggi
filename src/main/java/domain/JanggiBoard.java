package domain;

import domain.pattern.Pattern;
import domain.piece.Empty;
import domain.piece.Piece;
import domain.piece.state.Moved포;
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

    public void move(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        Piece piece = getPieceFrom(beforePosition);
        Piece targetPiece = getPieceFrom(afterPosition);

        validateDestinationPiece(piece, targetPiece);

        changeState(piece, targetPiece);
        changePosition(beforePosition, afterPosition);
    }

    public Piece getPieceFrom(JanggiPosition beforePosition) {
        validateBound(beforePosition);
        return janggiBoard.get(beforePosition);
    }

    private void validateBound(JanggiPosition position) {
        position.validateBound();
    }

    private void validateDestinationPiece(Piece piece, Piece targetPiece) {
        if (piece.isSameSide(targetPiece.getSide())) {
            throw new IllegalStateException("목적지에 같은 팀의 기물이 존재하여 이동할 수 없습니다.");
        }
    }

    private void changeState(Piece piece, Piece targetPiece) {
        piece.updateState();

        if (!targetPiece.isEmpty()) {
            targetPiece.captureIfNotMySide(piece);
        }
    }

    private void changePosition(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        Piece piece = getPieceFrom(beforePosition);

        if (piece.getState() instanceof Moved포) {
            validateMove포(piece, beforePosition, afterPosition);
        }
        if (!(piece.getState() instanceof Moved포)) {
            validateMoveOtherPiece(piece, beforePosition, afterPosition);
        }

        janggiBoard.put(beforePosition, new Empty());
        janggiBoard.put(afterPosition, piece);
    }

    private void validateMove포(Piece piece, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        boolean isHurdle = isExistHurdle(piece, beforePosition, afterPosition);
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

    private void validateMoveOtherPiece(Piece piece, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        boolean isHurdle = isExistHurdle(piece, beforePosition, afterPosition);
        if (isHurdle) {
            throw new IllegalArgumentException("경로에 장애물이 있어서 기물을 움직일 수 없습니다.");
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
