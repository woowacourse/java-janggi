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

    private final List<Piece> pieceSetUpOrder;

    BoardSetUp(List<Piece> pieceSetUpOrder) {
        this.pieceSetUpOrder = pieceSetUpOrder;
    }

    public Map<Position, BoardPiece> getDynastySetUp(Dynasty dynasty, BoardSetUp boardSetUp) {
        int xPoint = 10;
        List<Integer> yPoints = List.of(2, 3, 7, 8);
        if (dynasty == Dynasty.HAN) {
            xPoint = 1;
        }
        Map<Position, BoardPiece> setup = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            Piece piece = boardSetUp.pieceSetUpOrder.get(i);
            setup.put(new Position(xPoint, yPoints.get(i)), new BoardPiece(piece, dynasty));
        }
        return setup;
    }
}
