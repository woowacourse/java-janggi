package janggi.direction.obstacle;

import janggi.direction.Movement;
import janggi.piece.board.Board;
import janggi.position.Path;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;

public class ObstacleBlockStrategy implements ObstacleStrategy {

    @Override
    public void checkObstacle(final Position currentPosition, final Position arrivalPosition,
                              final Movement movement, final Board board) {
        final Path path = movement.makePath(currentPosition, arrivalPosition);
        if (hasPieceInMiddle(path, board)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재하여 이동할 수 없습니다.");
        }
    }

    @Override
    public boolean isObstacleJumping() {
        return false;
    }

    private boolean hasPieceInMiddle(final Path path, final Board board) {
        final List<Position> positions = new ArrayList<>(path.getPositions());
        positions.removeLast();
        return positions.stream()
                .anyMatch(board::hasPiece);
    }
}
