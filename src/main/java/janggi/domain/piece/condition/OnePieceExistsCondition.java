package janggi.domain.piece.condition;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceRule;
import janggi.exception.ExceptionMessage;
import java.util.List;

public class OnePieceExistsCondition implements MoveCondition {

    private static final int PASS_PIECE_COUNT = 1;

    @Override
    public void checkPath(List<Position> path, Camp camp, BoardChecker board, PieceRule pieceRule) {
        int countOfPiece = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Position position = path.get(i);
            countOfPiece += countPieceAt(board, pieceRule, position);
        }

        validateExactPieceCount(countOfPiece);
        validateDestination(path.getLast(), camp, board, pieceRule);
    }

    private int countPieceAt(BoardChecker board, PieceRule pieceRule, Position position) {
        if (board.hasPieceAt(position)) {
            validateDifferentPieceRule(board, pieceRule, position);
            return 1;
        }
        return 0;
    }

    private void validateDifferentPieceRule(BoardChecker board, PieceRule pieceRule, Position position) {
        if (board.hasSamePieceRuleAt(position, pieceRule)) {
            throw new IllegalArgumentException(ExceptionMessage.SAME_PIECE_TYPE_IN_PATH.getMessage());
        }
    }

    private void validateExactPieceCount(int countOfPiece) {
        if (countOfPiece != PASS_PIECE_COUNT) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_JUMPED_PIECE_COUNT.getMessage(PASS_PIECE_COUNT));
        }
    }

    private void validateDestination(Position destination, Camp camp, BoardChecker board, PieceRule pieceRule) {
        if (board.isSameCampPieceAt(destination, camp)) {
            throw new IllegalArgumentException(ExceptionMessage.SAME_CAMP_PIECE_AT_DESTINATION.getMessage());
        }

        if (board.hasSamePieceRuleAt(destination, pieceRule)) {
            throw new IllegalArgumentException(ExceptionMessage.SAME_PIECE_TYPE_AT_DESTINATION.getMessage());
        }
    }
}
