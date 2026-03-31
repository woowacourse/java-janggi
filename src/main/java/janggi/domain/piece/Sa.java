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
    public boolean isValidMovePattern(Position start, Position end) {
        return findMovePath(start, end).isPresent();
    }

    @Override
    public Optional<MovePath> findMovePath(Position start, Position end) {
        int dx = end.getX() - start.getX();
        int dy = end.getY() - start.getY();
        int distanceX = Math.abs(dx);
        int distanceY = Math.abs(dy);
        if (isSamePosition(distanceX, distanceY)) {
            return Optional.empty();
        }
        if (!isOneStep(distanceX, distanceY)) {
            return Optional.empty();
        }
        return paths.stream()
            .filter(path -> path.matches(dx, dy))
            .findFirst();
    }

    private boolean isSamePosition(int distanceX, int distanceY) {
        return distanceX == 0 && distanceY == 0;
    }

    private boolean isOneStep(int distanceX, int distanceY) {
        return distanceX <= 1 && distanceY <= 1;
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
}
