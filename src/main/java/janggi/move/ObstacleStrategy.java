package janggi.move;

import janggi.piece.board.Board;
import janggi.position.Path;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;

public enum ObstacleStrategy {

    BLOCK {
        @Override
        public void checkObstacle(final Path path, final Board board) {
            if (hasPieceInMiddle(path, board)) {
                throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재하여 이동할 수 없습니다.");
            }
        }
    },
    JUMPING {
        private static final int OBSTACLE_JUMPING_THRESHOLD = 1;

        @Override
        public void checkObstacle(final Path path, final Board board) {
            final int count = computeCountExistPieceExceptLastPosition(path, board);
            if (count != OBSTACLE_JUMPING_THRESHOLD) {
                throw new IllegalArgumentException("[ERROR] 오직 하나의 기물만 뛰어넘을 수 있습니다.");
            }
            if (isObstacleJumping(path, board)) {
                throw new IllegalArgumentException("[ERROR] 같은 종류의 기물을 뛰어넘거나 잡을 수 없습니다.");
            }
        }
    };

    public abstract void checkObstacle(Path path, Board board);


    private static boolean hasPieceInMiddle(final Path path, final Board board) {
        final List<Position> positions = new ArrayList<>(path.getPositions());
        positions.removeLast();
        return positions.stream()
                .anyMatch(board::hasPiece);
    }

    private static int computeCountExistPieceExceptLastPosition(final Path path, final Board board) {
        final List<Position> positions = new ArrayList<>(path.getPositions());
        if (!positions.isEmpty()) {
            positions.removeLast();
        }

        return (int) positions.stream()
                .filter(board::hasPiece)
                .count();
    }

    private static boolean isObstacleJumping(final Path path, final Board board) {
        return path.getPositions().stream()
                .filter(board::hasPiece)
                .anyMatch(position -> findObstacleJumping(board, position));
    }

    private static boolean findObstacleJumping(final Board board, final Position givenPosition) {
        return board.getBoard().entrySet().stream()
                .filter(entry -> entry.getKey().equals(givenPosition))
                .anyMatch(entry -> entry.getValue().isObstacleJumping());
    }
}
