package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.board.Point;
import janggi.domain.piece.movement.FiniteMovePath;
import janggi.domain.piece.movement.MovePath;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Soldier implements Piece {

    private static final Set<MovePath> PATHS = Set.of(
            new FiniteMovePath(Direction.UP),
            new FiniteMovePath(Direction.LEFT),
            new FiniteMovePath(Direction.RIGHT)
    );

    private final Dynasty dynasty;

    public Soldier(Dynasty dynasty) {
        this.dynasty = dynasty;
    }

    @Override
    public boolean isEmptyPiece() {
        return false;
    }

    @Override
    public List<Point> movePath(Point from, Point to) {
        MovePath movePath = PATHS.stream()
                .filter(each -> each.canMove(from, to))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없는 목적지입니다."));

        return movePath.movePoints(from, to);
    }

    @Override
    public boolean canMove(PiecesOnPath piecesOnPath) {
        if (piecesOnPath.isDestinationOfDynasty(dynasty)) {
            throw new IllegalArgumentException("목적지에 같은 나라의 기물이 있어 갈 수 없습니다.");
        }
        return piecesOnPath.isAllEmptyWithoutDestination();
    }

    @Override
    public boolean isDynasty(Dynasty dynasty) {
        return this.dynasty == dynasty;
    }

    @Override
    public boolean isSamePiece(Piece piece) {
        return piece instanceof Soldier;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Soldier soldier = (Soldier) o;
        return dynasty == soldier.dynasty;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dynasty);
    }
}
