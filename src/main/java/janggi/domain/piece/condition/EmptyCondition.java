package janggi.domain.piece.condition;

import janggi.domain.board.BoardChecker;
import janggi.domain.board.Position;
import janggi.domain.piece.Camp;
import java.util.List;

public class EmptyCondition implements MoveCondition {

    private static final String PATH_NOT_EMPTY = "[ERROR] 경로 상에 기물이 존재합니다.";
    private static final String SAME_CAMP_PIECE_AT_DESTINATION = "[ERROR] 목적지에 같은 진영의 기물이 존재합니다.";

    @Override
    public void checkPath(List<Position> path, Camp camp, BoardChecker board) {
        for (int i = 0; i < path.size() - 1; i++) {
            validateEmptyPosition(path.get(i), board);
        }
        validateDestination(path.getLast(), camp, board);
    }

    private void validateEmptyPosition(Position position, BoardChecker board) {
        if (board.hasPieceAt(position)) {
            throw new IllegalArgumentException(PATH_NOT_EMPTY);
        }
    }

    private void validateDestination(Position destination, Camp camp, BoardChecker board) {
        if (board.isSameCampPieceAt(destination, camp)) {
            throw new IllegalArgumentException(SAME_CAMP_PIECE_AT_DESTINATION);
        }
    }
}
