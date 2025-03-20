package move;

import direction.Point;
import java.util.Optional;
import piece.Piece;
import piece.Pieces;

public class CannonMovement implements MovementRule {

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

        int count = 0;
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                if(x == minX && y == minY) {
                    continue;
                }
                Optional<Piece> piece = pieces.findByPoint(new Point(x, y));

                if (piece.isPresent()) {
                    if (piece.get().getName().toLowerCase().equals("n")) {
                        throw new IllegalArgumentException("포가 있음");
                    }

                    count++;
                }
            }
        }

        if (count == 1) {
            return new Point(to.x(), to.y());
        }

        throw new IllegalArgumentException();
    }
}
