package janggi.domain.piece;

import janggi.domain.Path;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.ArrayList;
import java.util.List;

public class Sang extends Piece {

    private static final int SHORT_STEP = 2;
    private static final int LONG_STEP = 3;

    public Sang(Team team) {
        super(team, PieceType.SANG);
    }

    @Override
    public void validateMove(Position from, Position to) {
        if (isValidMovePattern(from, to)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로 상이 이동할 수 없습니다.");
        }
    }

    @Override
    public Path getPath(Position from, Position to) {
        int dx = from.deltaX(to);
        int dy = from.deltaY(to);

        List<Position> positions = new ArrayList<>();
        positions.add(new Position(from.x() + dx / 3, from.y() + dy / 3));
        positions.add(new Position(from.x() + dx * 2 / 3, from.y() + dy * 2 / 3));
        return new Path(positions);
    }

    private boolean isValidMovePattern(Position from, Position to) {
        int dx = from.deltaX(to);
        int dy = from.deltaY(to);

        return (Math.abs(dx) == LONG_STEP && Math.abs(dy) == SHORT_STEP) ||
                (Math.abs(dx) == SHORT_STEP && Math.abs(dy) == LONG_STEP);
    }
}
