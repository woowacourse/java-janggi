package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.side.TeamType;
import java.util.List;
import java.util.Optional;

public class Sa implements Piece {

    private final TeamType teamType;
    private final PieceType pieceType;
    private final List<MovePath> paths;

    public Sa(TeamType teamType) {
        this.teamType = teamType;
        pieceType = PieceType.SA;
        paths = List.of(
            new MovePath(List.of(Delta.createUp())),
            new MovePath(List.of(Delta.createDown())),
            new MovePath(List.of(Delta.createLeft())),
            new MovePath(List.of(Delta.createRight())),
            new MovePath(List.of(Delta.createRightUp())),
            new MovePath(List.of(Delta.createRightDown())),
            new MovePath(List.of(Delta.createLeftUp())),
            new MovePath(List.of(Delta.createLeftDown()))
        );
    }

    @Override
    public void validateCanMove(Position start, Position end, Board board) {
        if (!isValidMovePattern(start, end)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
    }

    @Override
    public String name() {
        return pieceType.getName();
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public TeamType getTeamType() {
        return teamType;
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

        return paths.stream()
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
