package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceRule;
import janggi.exception.ExceptionMessage;
import java.util.List;

public class JumpingSlidingRule extends SlidingRule {

    private static final int REQUIRED_PIECE_COUNT = 1;

    @Override
    public void validate(Position source, Position destination, Camp camp, BoardChecker board, PieceRule pieceRule) {
        DirectionInformation directionInformation = new DirectionInformation(source, destination);

        List<Position> path = findPath(source, directionInformation);
        checkJumpingPath(path, board, pieceRule);
    }

    private void checkJumpingPath(List<Position> path, BoardChecker board, PieceRule pieceRule) {
        int jumpedPieceCount = countJumpedPiece(path, board, pieceRule);
        validateJumpedPieceCount(jumpedPieceCount);
        validateDestination(board, pieceRule, path.getLast());
    }

    private int countJumpedPiece(List<Position> path, BoardChecker board, PieceRule pieceRule) {
        int jumpedPieceCount = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Position position = path.get(i);
            if (!board.hasPieceAt(position)) {
                continue;
            }
            validateDifferentPieceRule(board, pieceRule, position);
            jumpedPieceCount++;
        }
        return jumpedPieceCount;
    }

    private void validateDifferentPieceRule(BoardChecker board, PieceRule pieceRule, Position position) {
        if (board.hasSamePieceRuleAt(position, pieceRule)) {
            throw new IllegalArgumentException(ExceptionMessage.SAME_PIECE_TYPE_IN_PATH.getMessage());
        }
    }

    private void validateJumpedPieceCount(int jumpedPieceCount) {
        if (jumpedPieceCount != REQUIRED_PIECE_COUNT) {
            throw new IllegalArgumentException(
                    ExceptionMessage.INVALID_JUMPED_PIECE_COUNT.getMessage(REQUIRED_PIECE_COUNT)
            );
        }
    }

    private void validateDestination(BoardChecker board, PieceRule pieceRule, Position position) {
        if (board.hasSamePieceRuleAt(position, pieceRule)) {
            throw new IllegalArgumentException(ExceptionMessage.SAME_PIECE_TYPE_AT_DESTINATION.getMessage());
        }
    }
}
