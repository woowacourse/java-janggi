package janggi.domain.board;

import janggi.domain.piece.Elephant;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public enum BoardSetUp {
    INNER_ELEPHANT(List.of(
            Horse::new,
            Elephant::new,
            Elephant::new,
            Horse::new
    )),
    OUTER_ELEPHANT(List.of(
            Elephant::new,
            Horse::new,
            Horse::new,
            Elephant::new
    )),
    RIGHT_ELEPHANT(List.of(
            Horse::new,
            Elephant::new,
            Horse::new,
            Elephant::new
    )),
    LEFT_ELEPHANT(List.of(
            Elephant::new,
            Horse::new,
            Elephant::new,
            Horse::new
    ));

    private static final List<Integer> Y_POINTS = List.of(2, 3, 7, 8);

    private final List<Function<Dynasty, Piece>> pieceSetUpOrder;

    BoardSetUp(List<Function<Dynasty, Piece>> pieceSetUpOrder) {
        this.pieceSetUpOrder = pieceSetUpOrder;
    }

    public Map<Point, Piece> getDynastySetUp(Dynasty dynasty) {
        int xPoint = getXPointByDynasty(dynasty);
        Map<Point, Piece> setup = new HashMap();
        for (int i = 0; i < 4; i++) {
            Piece piece = this.pieceSetUpOrder.get(i).apply(dynasty);
            setup.put(new Point(xPoint, Y_POINTS.get(i)), piece);
        }
        return setup;
    }

    private int getXPointByDynasty(Dynasty dynasty) {
        int xPoint = 10;
        if (dynasty == Dynasty.HAN) {
            xPoint = 1;
        }
        return xPoint;
    }
}
