package janggi.domain.space.piece;

import janggi.domain.board.Path;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class Pho extends Piece {

    public Pho(Team team) {
        super(team, PieceType.PHO);
    }

    @Override
    public void validateMove(Position from, Position to) {
        if (!isMovable(from, to)) {
            throw new IllegalStateException("해당 위치로 포가 이동할 수 없습니다.");
        }
    }

    @Override
    protected void validateSpecificArrival(Piece piece) {
        if (isSameType(piece)) {
            throw new IllegalArgumentException("이동하려는 위치에 상대팀의 포가 존재합니다.");
        }
    }

    @Override
    public void validateRoutes(List<Piece> pieces) {
        if (pieces.size() != 1) {
            throw new IllegalArgumentException("포는 이동 경로 사이에 포를 제외한 하나의 말이 있어야 합니다.");
        }

        if (isContainsPho(pieces)) {
            throw new IllegalArgumentException("포는 이동 경로 사이에 포를 제외한 하나의 말이 있어야 합니다.");
        }
    }

    private boolean isContainsPho(List<Piece> pieces) {
        return pieces.stream()
                .anyMatch(piece -> piece.isSameType(this));
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

        return (Math.abs(dx) == 0 && Math.abs(dy) > 1) ||
                (Math.abs(dx) > 1 && Math.abs(dy) == 0);
    }

    private boolean isDigonalMove(Position from, Position to) {
        return from.isDiagonalMoveInCastle(to);
    }
}
