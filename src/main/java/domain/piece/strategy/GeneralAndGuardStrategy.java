package domain.piece.strategy;

import domain.board.BoardChecker;
import domain.board.Position;

public class GeneralAndGuardStrategy implements MoveStrategy {

    private static final String CAN_MOVE_ONLY_ONE_POSITION_ERROR_MESSAGE = "[ERROR] 한 칸만 움직일 수 있습니다.";

    @Override
    public void move(Position from, Position to, BoardChecker checker) {
        int dx = Math.abs(from.calculateDx(to));
        int dy = Math.abs(from.calculateDy(to));

        if (dx + dy != 1) {
            throw new IllegalArgumentException(CAN_MOVE_ONLY_ONE_POSITION_ERROR_MESSAGE);
        }
    }
}
