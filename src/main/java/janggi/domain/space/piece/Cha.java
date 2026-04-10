package janggi.domain.space.piece;

import janggi.domain.board.Path;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class Cha extends Piece {

    public Cha(Team team) {
        super(team, PieceType.CHA);
    }

    @Override
    public void validateMove(Position from, Position to) {
        if (!isMovable(from, to)) {
            throw new IllegalStateException("해당 위치로 차가 이동할 수 없습니다.");
        }
    }

    @Override
    public Path getPath(Position from, Position to) {
        if (from.x() != to.x()) {
            List<Position> positions = getHorizontalPath(from, to);
            return new Path(positions);
        }

        List<Position> positions = getVerticalPath(from, to);
        return new Path(positions);
    }

    private List<Position> getHorizontalPath(Position from, Position to) {
        List<Position> positions = new ArrayList<>();
        int step = Integer.compare(to.x(), from.x());

        for (int x = from.x() + step; x != to.x(); x += step) {
            positions.add(new Position(x, from.y()));
        }

        return positions;
    }

    private List<Position> getVerticalPath(Position from, Position to) {
        List<Position> positions = new ArrayList<>();
        int step = Integer.compare(to.y(), from.y());

        for (int y = from.y() + step; y != to.y(); y += step) {
            positions.add(new Position(from.x(), y));
        }

        return positions;
    }

    private boolean isMovable(Position from, Position to) {
        return isStraightMove(from, to) || isDigonalMove(from, to);
    }

    private boolean isStraightMove(Position from, Position to) {
        int dx = from.deltaX(to);
        int dy = from.deltaY(to);

        return (Math.abs(dx) == 0 && Math.abs(dy) > 0) ||
                (Math.abs(dx) > 0 && Math.abs(dy) == 0);
    }

    private boolean isDigonalMove(Position from, Position to) {
        return from.isDiagonalMoveInCastle(to);
    }
}
