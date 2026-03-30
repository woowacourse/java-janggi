package janggi.domain.piece;

import janggi.domain.Path;
import janggi.domain.Position;
import janggi.domain.Space;
import janggi.domain.Team;
import java.util.ArrayList;
import java.util.List;

public class Pho extends Piece {

    private static final int MIN_STEP = 2;
    private static final int NO_MOVE = 0;
    private static final int BLOCKING_PIECE_COUNT = 1;

    public Pho(Team team) {
        super(team, PieceType.PHO);
    }

    @Override
    public void validateMove(Position from, Position to) {
        if (isValidMovePattern(from, to)) {
            return;
        }
        throw new IllegalArgumentException("해당 위치로 포가 이동할 수 없습니다.");
    }

    @Override
    public void validateArrival(Space space) {
        super.validateArrival(space);

        Piece piece = (Piece) space;
        if (piece.isSameType(PieceType.PHO)) {
            throw new IllegalArgumentException("이동하려는 위치에 상대팀의 포가 존재합니다.");
        }
    }

    @Override
    public void validateRoutes(List<Piece> pieces) {
        if (pieces.size() != BLOCKING_PIECE_COUNT) {
            throw new IllegalArgumentException("포는 이동 경로 사이에 포를 제외한 하나의 말이 있어야 합니다.");
        }

        if (isContainsPho(pieces)) {
            throw new IllegalArgumentException("포는 이동 경로 사이에 포를 제외한 하나의 말이 있어야 합니다.");
        }
    }

    private boolean isContainsPho(List<Piece> pieces) {
        return pieces.stream()
            .anyMatch(piece -> piece.isSameType(PieceType.PHO));
    }

    @Override
    public Path getPath(Position from, Position to) {
        int dx = from.deltaX(to);
        int dy = from.deltaY(to);

        if (dx != NO_MOVE) {
            List<Position> positions = getHorizontalPath(from, dx);
            return new Path(positions);
        }

        List<Position> positions = getVerticalPath(from, dy);
        return new Path(positions);
    }

    private List<Position> getHorizontalPath(Position from, int dx) {
        List<Position> positions = new ArrayList<>();
        for (int i = MIN_STEP; i < dx; i++) {
            positions.add(new Position(from.x() + i, from.y()));
        }
        return positions;
    }

    private List<Position> getVerticalPath(Position from, int dy) {
        List<Position> positions = new ArrayList<>();
        for (int i = MIN_STEP; i < dy; i++) {
            positions.add(new Position(from.x(), from.y() + i));
        }
        return positions;
    }

    private boolean isValidMovePattern(Position from, Position to) {
        int dx = from.deltaX(to);
        int dy = from.deltaY(to);

        return (Math.abs(dx) == NO_MOVE && Math.abs(dy) >= MIN_STEP) ||
            (Math.abs(dx) >= MIN_STEP && Math.abs(dy) == NO_MOVE);
    }
}
