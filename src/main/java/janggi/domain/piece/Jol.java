package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.side.TeamType;
import java.util.List;
import java.util.Optional;

public class Jol implements Piece {

    private final TeamType teamType;
    private final PieceType pieceType;
    private final List<MovePath> paths;

    public Jol(TeamType teamType) {
        this.teamType = teamType;
        pieceType = PieceType.JOL;
        paths = createPaths();
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
        int dx = end.getX() - start.getX();
        int dy = end.getY() - start.getY();
        if (isSamePosition(start, end)) {
            return Optional.empty();
        }
        return paths.stream()
            .filter(path -> path.matches(dx, dy))
            .findFirst();
    }

    private boolean isSamePosition(Position start, Position end) {
        return start.isSamePosition(end);
    }

    private List<MovePath> createPaths() {
        if (teamType == TeamType.HAN) {
            return List.of(
                new MovePath(List.of(Delta.createDown())),
                new MovePath(List.of(Delta.createLeft())),
                new MovePath(List.of(Delta.createRight()))
            );
        }
        return List.of(
            new MovePath(List.of(Delta.createUp())),
            new MovePath(List.of(Delta.createLeft())),
            new MovePath(List.of(Delta.createRight()))
        );
    }
}
