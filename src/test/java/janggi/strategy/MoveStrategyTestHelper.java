package janggi.strategy;

import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import java.util.ArrayList;
import java.util.List;

public class MoveStrategyTestHelper {
    public static List<Position> toList(Path path) {
        List<Position> list = new ArrayList<>();
        path.forEach(list::add);
        return list;
    }

    public static Paths createRoute(List<Position> positions) {
        Path path = new Path();
        positions.forEach(path::makePath);
        Paths paths = new Paths();
        paths.addPath(path);
        return paths;
    }

    public static Piece createPiece(Side side, PieceType type) {
        return new Piece(side, type, "1");
    }
}
