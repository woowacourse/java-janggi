package janggi.direction.obstacle;

import janggi.direction.Movement;
import janggi.piece.board.Board;
import janggi.position.Path;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;

public class ObstacleJumpingObstacle implements ObstacleStrategy {

    @Override
    public void checkObstacle(final Position currentPosition, final Position arrivalPosition,
                              final Movement movement, final Board board) {
        final Path path = movement.makePath(currentPosition, arrivalPosition);

        final int count = computeCountExistPieceExceptLastPosition(path, board);
        if (count != 1) {
            throw new IllegalArgumentException("[ERROR] 오직 하나의 기물만 뛰어넘을 수 있습니다.");
        }
        if (isObstacleJumping(path, board)) {
            throw new IllegalArgumentException("[ERROR] 같은 종류의 기물을 뛰어넘거나 잡을 수 없습니다.");
        }
    }

    @Override
    public boolean isObstacleJumping() {
        return true;
    }

    private int computeCountExistPieceExceptLastPosition(final Path path, final Board board) {
        final List<Position> positions = new ArrayList<>(path.getPositions());
        if (!positions.isEmpty()) {
            positions.removeLast();
        }

        return (int) positions.stream()
                .filter(board::hasPiece)
                .count();
    }

    private boolean isObstacleJumping(final Path path, final Board board) {
        return path.getPositions().stream()
                .filter(board::hasPiece)
                .anyMatch(position -> findObstacleJumping(board, position));
    }

    private boolean findObstacleJumping(final Board board, final Position givenPosition) {
        return board.getBoard().entrySet().stream()
                .filter(entry -> entry.getKey().equals(givenPosition))
                .anyMatch(entry -> entry.getValue().isObstacleJumping());
    }
}
