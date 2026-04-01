package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.side.TeamType;
import java.util.List;
import java.util.Optional;

public class Gung extends Piece {

    private static final List<MovePath> PATHS = List.of(
            new MovePath(List.of(Delta.UP)),
            new MovePath(List.of(Delta.DOWN)),
            new MovePath(List.of(Delta.RIGHT)),
            new MovePath(List.of(Delta.LEFT)),
            new MovePath(List.of(Delta.RIGHT_UP)),
            new MovePath(List.of(Delta.RIGHT_DOWN)),
            new MovePath(List.of(Delta.LEFT_UP)),
            new MovePath(List.of(Delta.LEFT_DOWN))
    );

    public Gung(TeamType teamType) {
        super(teamType, PieceType.GUNG);
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
        if (!isOneStep(start, end)) {
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

    private boolean isOneStep(Position start, Position end) {
        return start.isOneStep(end);
    }
}
