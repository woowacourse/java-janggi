package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.side.TeamType;
import java.util.List;
import java.util.Optional;

public class Jol extends Piece {

    private static final List<MovePath> CHU_PATHS = List.of(
            new MovePath(List.of(Delta.UP)),
            new MovePath(List.of(Delta.LEFT)),
            new MovePath(List.of(Delta.RIGHT))
    );

    private static final List<MovePath> HAN_PATHS = List.of(
            new MovePath(List.of(Delta.DOWN)),
            new MovePath(List.of(Delta.LEFT)),
            new MovePath(List.of(Delta.RIGHT))
    );

    public Jol(TeamType teamType) {
        super(teamType, PieceType.JOL);
    }

    @Override
    public void validateCanMove(Position start, Position end, Board board) {
        if (!isValidMovePattern(start, end)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
    }

    private boolean isValidMovePattern(Position start, Position end) {
        return findMovePath(start, end).isPresent();
    }

    private Optional<MovePath> findMovePath(Position start, Position end) {
        if (isSamePosition(start, end)) {
            return Optional.empty();
        }

        int dx = start.deltaX(end);
        int dy = start.deltaY(end);

        return selectPaths(getTeamType()).stream()
            .filter(path -> path.matches(dx, dy))
            .findFirst();
    }

    private boolean isSamePosition(Position start, Position end) {
        return start.isSamePosition(end);
    }

    private List<MovePath> selectPaths(TeamType teamType) {
        if (teamType == TeamType.HAN) {
            return HAN_PATHS;
        }
        return CHU_PATHS;
    }
}
