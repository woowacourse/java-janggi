package janggi.domain.piece.condition;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceType;
import janggi.exception.ExceptionMessage;
import java.util.List;

public class OnePieceExistsCondition implements MoveCondition {

    public static final int PASS_PIECE_COUNT = 1;

    @Override
    public void checkPath(List<Position> path, Camp camp, BoardChecker board, PieceType pieceType) {
        int countOfPiece = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Position position = path.get(i);
            countOfPiece += countPieceAt(board, pieceType, position);
        }

        validateExactPieceCount(countOfPiece);
        validateDestination(path.getLast(), camp, board, pieceType);
    }

    private int countPieceAt(BoardChecker board, PieceType pieceType, Position position) {
        if (board.hasPieceAt(position)) {
            validateSamePieceRule(board, pieceType, position);
            return 1;
        }
        return 0;
    }

    private void validateSamePieceRule(BoardChecker board, PieceType pieceType, Position position) {
        if (board.hasSamePieceRuleAt(position, pieceType)) {
            throw new IllegalArgumentException(ExceptionMessage.SAME_PIECE_TYPE_IN_PATH.getMessage());
        }
    }

    private void validateExactPieceCount(int countOfPiece) {
        if (countOfPiece != PASS_PIECE_COUNT) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_JUMPED_PIECE_COUNT.getMessage());
        }
    }

    private void validateDestination(Position destination, Camp camp, BoardChecker board, PieceType pieceType) {
        if (board.isSameCampPieceAt(destination, camp)) {
            throw new IllegalArgumentException(ExceptionMessage.SAME_CAMP_PIECE_AT_DESTINATION.getMessage());
        }

        if (board.hasSamePieceRuleAt(destination, pieceType)) {
            throw new IllegalArgumentException(ExceptionMessage.SAME_PIECE_TYPE_AT_DESTINATION.getMessage());
        }
    }
}
