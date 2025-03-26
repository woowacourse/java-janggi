package janggi.domain.board;

import janggi.domain.Dynasty;
import janggi.domain.piece.BoardPiece;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum BoardSetUp {
    INNER_ELEPHANT(List.of(
            new Horse(),
            new Elephant(),
            new Elephant(),
            new Horse()
    )),
    OUTER_ELEPHANT(List.of(
            new Elephant(),
            new Horse(),
            new Horse(),
            new Elephant()
    )),
    RIGHT_ELEPHANT(List.of(
            new Horse(),
            new Elephant(),
            new Horse(),
            new Elephant()
    )),
    LEFT_ELEPHANT(List.of(
            new Elephant(),
            new Horse(),
            new Elephant(),
            new Horse()
    ));

    private static final List<Integer> Y_POINTS = List.of(2, 3, 7, 8);

    private final List<Piece> pieceSetUpOrder;

    BoardSetUp(List<Piece> pieceSetUpOrder) {
        this.pieceSetUpOrder = pieceSetUpOrder;
    }

    public Map<Point, BoardPiece> getDynastySetUp(Dynasty dynasty) {
        int xPoint = getXPointByDynasty(dynasty);
        Map<Point, BoardPiece> setup = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            Piece piece = this.pieceSetUpOrder.get(i);
            setup.put(new Point(xPoint, Y_POINTS.get(i)), new BoardPiece(piece, dynasty));
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
