package domain.piece;

import domain.MoveInfos;
import domain.direction.Directions;
import domain.direction.PieceDirection;
import domain.piece.category.PieceCategory;
import domain.spatial.Position;
import java.util.List;
import java.util.Objects;

public abstract class Piece {

    protected final Directions directions;
    protected final Position position;

    public Piece(final Position position, final Directions directions) {
        this.position = position;
        this.directions = directions;
    }

    public abstract PieceCategory getCategory();

    public abstract Piece move(final Position target, final MoveInfos moveInfos);

    public List<Position> getPaths(final Position target) {
        List<Position> paths = getPrimaryPaths(target);
        if (paths.isEmpty()) {
            paths = getDiagonalPaths(target);
            validateDiagonalPaths(paths);
        }
        return paths;
    }

    public boolean isKing() {
        return false;
    }

    public boolean isSamePosition(final Position position) {
        return this.position.equals(position);
    }

    private List<Position> getPrimaryPaths(final Position target) {
        return directions.getPaths(position, target);
    }

    private List<Position> getDiagonalPaths(final Position target) {
        return PieceDirection.DIAGONAL.get().getPaths(position, target);
    }

    private void validateDiagonalPaths(List<Position> paths) {
        if (paths.isEmpty()) {
            throw new IllegalArgumentException("이동할 수 없는 좌표입니다. 다시 확인해주세요.");
        }
        if (!paths.getLast().isWithinPalace()) {
            throw new IllegalArgumentException("궁성 이동의 경우 밖으로 이동할 수 없습니다.");
        }
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Piece piece = (Piece) object;
        return Objects.equals(directions, piece.directions) && Objects.equals(position, piece.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(directions, position);
    }

    public Directions getDirections() {
        return directions;
    }

    public Position getPosition() {
        return position;
    }
}
