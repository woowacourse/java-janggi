package move;

import direction.Point;
import java.util.Optional;
import piece.Piece;
import piece.Pieces;

public class ChariotMovement implements MovementRule {

    @Override
    public Point move(Pieces pieces, Point from, Point to) {
        if(from.x() != to.x() && from.y() != to.y()) {
            throw new IllegalArgumentException();
        }
        if(from.equals(to)) {
            throw new IllegalArgumentException();
        }
        int minX = Math.min(from.x(), to.x());
        int maxX = Math.max(from.x(), to.x());
        int minY = Math.min(from.y(), to.y());
        int maxY = Math.max(from.y(), to.y());

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                if(x == minX && y == minY) {
                    continue;
                }
                Optional<Piece> piece = pieces.findByPoint(new Point(x, y));

                if (piece.isPresent()) {
                    throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재합니다.");
                }
            }
        }

        return new Point(to.x(), to.y());
    }
}
