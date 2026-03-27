package janggi.domain.piece.condition;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceRule;
import java.util.List;

public class OnePieceExistsCondition implements MoveCondition {

    @Override
    public void checkPath(List<Position> path, Camp camp, BoardChecker board, PieceRule pieceRule) {
        int countOfPiece = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Position position = path.get(i);
            countOfPiece += countPieceAt(board, pieceRule, position);
        }

        validateExactPieceCount(countOfPiece);
        validateGoalPosition(path.getLast(), camp, board, pieceRule);
    }

    private int countPieceAt(BoardChecker board, PieceRule pieceRule, Position position) {
        if (board.hasPieceAt(position)) {
            validateSamePieceRule(board, pieceRule, position);
            return 1;
        }
        return 0;
    }

    private void validateSamePieceRule(BoardChecker board, PieceRule pieceRule, Position position) {
        if (board.hasSamePieceRuleAt(position, pieceRule)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
        }
    }

    private void validateExactPieceCount(int flag) {
        if (flag != 1) {
            throw new IllegalArgumentException("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
        }
    }

    private void validateGoalPosition(Position lastPosition, Camp camp, BoardChecker board, PieceRule pieceRule) {
        if (board.isSameCampPieceAt(lastPosition, camp) || board.hasSamePieceRuleAt(lastPosition, pieceRule)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
        }
    }
}

// todo : PieceRule 제거

// todo : view 구현

// todo : 상수 처리
// todo : 예외 구체화
