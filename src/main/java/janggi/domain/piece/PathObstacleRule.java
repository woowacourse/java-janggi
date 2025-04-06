package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.domain.piece.position.Path;
import janggi.domain.piece.position.Position;
import java.util.ArrayList;
import java.util.List;

public enum PathObstacleRule {

    CANNOT_PASS_OBSTACLES {
        @Override
        public void validatePathObstacles(final Path path, final Board board) {
            if (containsObstacles(path, board)) {
                throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재하여 이동할 수 없습니다.");
            }
        }
    },
    MUST_JUMP_EXACTLY_ONE_OBSTACLE {
        private static final int OBSTACLE_JUMPING_THRESHOLD = 1;

        @Override
        public void validatePathObstacles(final Path path, final Board board) {
            final int count = countObstacles(path, board);
            if (count != OBSTACLE_JUMPING_THRESHOLD) {
                throw new IllegalArgumentException("[ERROR] 오직 하나의 기물만 뛰어넘을 수 있습니다.");
            }
            if (containsSameTypePiece(path, board)) {
                throw new IllegalArgumentException("[ERROR] 같은 종류의 기물을 뛰어넘거나 잡을 수 없습니다.");
            }
        }
    };

    public abstract void validatePathObstacles(Path path, Board board);

    private static boolean containsObstacles(final Path path, final Board board) {
        final List<Position> positions = new ArrayList<>(path.getPositions());
        positions.removeLast();
        return positions.stream()
                .anyMatch(board::hasPiece);
    }

    private static int countObstacles(final Path path, final Board board) {
        final List<Position> positions = new ArrayList<>(path.getPositions());
        if (!positions.isEmpty()) {
            positions.removeLast();
        }
        return (int) positions.stream()
                .filter(board::hasPiece)
                .count();
    }

    private static boolean containsSameTypePiece(final Path path, final Board board) {
        return path.getPositions().stream()
                .filter(board::hasPiece)
                .anyMatch(position -> hasObstacleJumpingType(board, position));
    }

    private static boolean hasObstacleJumpingType(final Board board, final Position givenPosition) {
        return board.getBoard().entrySet().stream()
                .filter(entry -> entry.getKey().equals(givenPosition))
                .anyMatch(entry -> entry.getValue().canJumpObstacle());
    }
}
