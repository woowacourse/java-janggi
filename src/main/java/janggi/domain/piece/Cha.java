package janggi.domain.piece;

import janggi.domain.Path;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.ArrayList;
import java.util.List;

public class Cha extends Piece {

    public Cha(Team team) {
        super(team, PieceType.CHA);
    }

    @Override
    public void validateMove(Position from, Position to) {
        if (isMovable(from, to)) {
            return;
        }
        throw new IllegalArgumentException("해당 위치로 차가 이동할 수 없습니다.");
    }

    @Override
    public Path getPath(Position from, Position to) {
        int dx = from.deltaX(to);
        int dy = from.deltaY(to);

        if (dx != 0) {
            List<Position> positions = getHorizontalPath(from, dx);
            return new Path(positions);
        }

        List<Position> positions = getVerticalPath(from, dy);
        return new Path(positions);
    }

    private List<Position> getHorizontalPath(Position from, int dx) {
        List<Position> positions = new ArrayList<>();
        for (int i = 1; i < dx; i++) {
            positions.add(new Position(from.x() + i, from.y()));
        }
        return positions;
    }

    private List<Position> getVerticalPath(Position from, int dy) {
        List<Position> positions = new ArrayList<>();
        for (int i = 1; i < dy; i++) {
            positions.add(new Position(from.x(), from.y() + i));
        }
        return positions;
    }

    private boolean isMovable(Position from, Position to) {
        int dx = from.deltaX(to);
        int dy = from.deltaY(to);

        return (Math.abs(dx) == 0 && Math.abs(dy) > 0) ||
                (Math.abs(dx) > 0 && Math.abs(dy) == 0);
    }
}
