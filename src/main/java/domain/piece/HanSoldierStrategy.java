package domain.piece;

import domain.ErrorMessage;
import domain.Path;
import domain.board.Position;

import java.util.List;

public class HanSoldierStrategy implements MoveStrategy {

    @Override
    public List<Position> getPathPositions(Position from, Position to) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();

        int straight = -1;

        boolean isMoveStraight = dx == 0 && dy == straight;
        boolean isMoveLeft = dx == -1 && dy == 0;
        boolean isMoveRight = dx == 1 && dy == 0;

        if (!(isMoveLeft || isMoveRight || isMoveStraight)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
        return List.of();
    }

    @Override
    public void canMove(List<Path> paths, Piece to) {
        if (!paths.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.PATH_BLOCKED.getMessage());
        }
    }
}
