package domain;

import domain.pattern.Pattern;
import domain.piece.Piece;
import domain.piece.state.Moved포;
import domain.piece.포;
import java.util.List;

public class MoveValidator {
    private final JanggiBoard janggiBoard;

    public MoveValidator(JanggiBoard janggiBoard) {
        this.janggiBoard = janggiBoard;
    }

    public void validateMove(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        Piece piece = janggiBoard.getPieceFrom(beforePosition);
        Piece targetPiece = janggiBoard.getPieceFrom(afterPosition);

        validateDestinationPiece(piece, targetPiece);
        validateMovePattern(piece, beforePosition, afterPosition);
    }

    private void validateDestinationPiece(Piece piece, Piece targetPiece) {
        if (piece.isSameSide(targetPiece.getSide())) {
            throw new IllegalStateException("목적지에 같은 팀의 기물이 존재하여 이동할 수 없습니다.");
        }
    }

    private void validateMovePattern(Piece piece, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        List<Pattern> patterns = piece.findMovablePath(beforePosition, afterPosition);

        if (piece.getState() instanceof Moved포) {
            validateMoveWithHurdle(beforePosition, afterPosition, patterns);
        } else {
            validateMoveWithoutHurdle(beforePosition, afterPosition, patterns);
        }
    }

    private void validateMoveWithoutHurdle(JanggiPosition beforePosition, JanggiPosition afterPosition,
                                           List<Pattern> patterns) {
        if (isExistHurdle(beforePosition, patterns)) {
            throw new IllegalArgumentException("경로에 장애물이 있어서 기물을 움직일 수 없습니다.");
        }
    }

    private void validateMoveWithHurdle(JanggiPosition beforePosition, JanggiPosition afterPosition,
                                        List<Pattern> patterns) {
        if (getHurdleCount(beforePosition, patterns) > 1) {
            throw new IllegalStateException("경로에 장애물이 2개 이상 있어서 움직일 수 없습니다.");
        }
        if (getFirstHurdlePiece(beforePosition, patterns).getClass().equals(포.class)) {
            throw new IllegalStateException("포는 포를 넘을 수 없습니다.");
        }
    }

    private boolean isExistHurdle(JanggiPosition beforePosition, List<Pattern> patterns) {
        List<Pattern> patternsWithoutDestination = patterns.subList(0, patterns.size() - 1);
        JanggiPosition newPosition = beforePosition;
        for (Pattern pattern : patternsWithoutDestination) {
            newPosition = newPosition.moveOnePosition(pattern);
            if (janggiBoard.isExistPiece(newPosition)) {
                return true;
            }
        }
        return false;
    }

    private int getHurdleCount(JanggiPosition beforePosition, List<Pattern> patterns) {
        int count = 0;
        JanggiPosition newPosition = beforePosition;
        for (Pattern pattern : patterns) {
            newPosition = newPosition.moveOnePosition(pattern);
            if (janggiBoard.isExistPiece(newPosition)) {
                count++;
            }
        }
        return count;
    }

    private Piece getFirstHurdlePiece(JanggiPosition beforePosition, List<Pattern> patterns) {
        JanggiPosition newPosition = beforePosition;
        for (Pattern pattern : patterns) {
            newPosition = newPosition.moveOnePosition(pattern);
            if (janggiBoard.isExistPiece(newPosition)) {
                return janggiBoard.getPieceFrom(newPosition);
            }
        }
        return null;
    }
}
