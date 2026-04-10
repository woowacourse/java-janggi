package janggi.domain.space.piece;

import janggi.domain.board.Path;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class Ma extends Piece {

    public Ma(Team team) {
        super(team, PieceType.MA);
    }

    @Override
    public void validateMove(Position from, Position to) {
        if (!isMovable(from, to)) {
            throw new IllegalStateException("해당 위치로 마가 이동할 수 없습니다.");
        }
    }

    @Override
    public Path getPath(Position from, Position to) {
        int dx = from.deltaX(to);
        int dy = from.deltaY(to);

        List<Position> positions = new ArrayList<>();
        positions.add(new Position(from.x() + dx / 2, from.y() + dy / 2));
        return new Path(positions);
    }

    private boolean isMovable(Position from, Position to) {
        int dx = from.deltaX(to);
        int dy = from.deltaY(to);

        return (Math.abs(dx) == 1 && Math.abs(dy) == 2) ||
                (Math.abs(dx) == 2 && Math.abs(dy) == 1);
    }
}
