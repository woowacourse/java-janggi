package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.side.TeamType;
import java.util.List;
import java.util.Optional;

public class Ma extends Piece {

    private static final List<MovePath> PATHS = List.of(
            new MovePath(List.of(Delta.UP, Delta.RIGHT_UP)),
            new MovePath(List.of(Delta.UP, Delta.LEFT_UP)),
            new MovePath(List.of(Delta.DOWN, Delta.RIGHT_DOWN)),
            new MovePath(List.of(Delta.DOWN, Delta.LEFT_DOWN)),
            new MovePath(List.of(Delta.LEFT, Delta.LEFT_UP)),
            new MovePath(List.of(Delta.LEFT, Delta.LEFT_DOWN)),
            new MovePath(List.of(Delta.RIGHT, Delta.RIGHT_UP)),
            new MovePath(List.of(Delta.RIGHT, Delta.RIGHT_DOWN))
    );

    public Ma(TeamType teamType) {
        super(teamType, PieceType.MA);
    }

    @Override
    public void validateCanMove(Position start, Position end, Board board) {
        if (!isValidMovePattern(start, end)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        if (!isObstaclesNotExist(start, end, board)) {
            throw new IllegalArgumentException("이동 경로에 기물이 존재하여 이동할 수 없습니다.");
        }
    }

    private boolean isValidMovePattern(Position start, Position end) {
        return findMovePath(start, end).isPresent();
    }

    private boolean isObstaclesNotExist(Position startPosition, Position endPosition, Board board) {
        Optional<MovePath> movePath = findMovePath(startPosition, endPosition);
        if (movePath.isEmpty()) {
            return false;
        }
        return movePath.get().intermediatePositions(startPosition, endPosition).stream()
                .noneMatch(board::hasPiece);
    }

    private Optional<MovePath> findMovePath(Position start, Position end) {
        if (isSamePosition(start, end)) {
            return Optional.empty();
        }

        int dx = start.deltaX(end);
        int dy = start.deltaY(end);

        return PATHS.stream()
            .filter(path -> path.matches(dx, dy))
            .findFirst();
    }

    private boolean isSamePosition(Position start, Position end) {
        return start.isSamePosition(end);
    }
}
