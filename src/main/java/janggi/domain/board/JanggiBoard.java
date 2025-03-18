package janggi.domain.board;

import janggi.domain.Dynasty;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.ChuPawn;
import janggi.domain.piece.Guard;
import janggi.domain.piece.HanPawn;
import janggi.domain.piece.King;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Rook;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JanggiBoard {

    private static final HashMap<Point, Piece> PIECE_INITIAL_POSITIONS = new HashMap<>() {
        {
            put(new Point(1, 1), new Rook(Dynasty.HAN));
            put(new Point(1, 4), new Guard(Dynasty.HAN));
            put(new Point(1, 6), new Guard(Dynasty.HAN));
            put(new Point(1, 9), new Rook(Dynasty.HAN));
            put(new Point(2, 5), new King(Dynasty.HAN));
            put(new Point(3, 2), new Cannon(Dynasty.HAN));
            put(new Point(3, 8), new Cannon(Dynasty.HAN));
            put(new Point(4, 1), new HanPawn());
            put(new Point(4, 3), new HanPawn());
            put(new Point(4, 5), new HanPawn());
            put(new Point(4, 7), new HanPawn());
            put(new Point(4, 9), new HanPawn());

            put(new Point(10, 1), new Rook(Dynasty.CHU));
            put(new Point(10, 4), new Guard(Dynasty.CHU));
            put(new Point(10, 6), new Guard(Dynasty.CHU));
            put(new Point(10, 9), new Rook(Dynasty.CHU));
            put(new Point(9, 5), new King(Dynasty.CHU));
            put(new Point(8, 2), new Cannon(Dynasty.CHU));
            put(new Point(8, 8), new Cannon(Dynasty.CHU));
            put(new Point(7, 1), new ChuPawn());
            put(new Point(7, 3), new ChuPawn());
            put(new Point(7, 5), new ChuPawn());
            put(new Point(7, 7), new ChuPawn());
            put(new Point(7, 9), new ChuPawn());
        }
    };

    private final Map<Point, Piece> pieces;

    public JanggiBoard(Map<Point, Piece> pieces) {
        this.pieces = pieces;
    }

    public static JanggiBoard of(HanBoardSetUp hanBoardSetUp, ChuBoardSetUp chuBoardSetUp) {
        Map<Point, Piece> pieces = new HashMap<>(PIECE_INITIAL_POSITIONS);
        pieces.putAll(hanBoardSetUp.getPiecePositions());
        pieces.putAll(chuBoardSetUp.getPiecePositions());
        return new JanggiBoard(pieces);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JanggiBoard that = (JanggiBoard) o;
        return Objects.equals(pieces, that.pieces);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pieces);
    }

    public boolean isExistPiece(Point point) {
        return pieces.containsKey(point);
    }

    public boolean isExistSameDynasty(Point point, Dynasty dynasty) {
        if (!isExistPiece(point)) {
            return false;
        }
        return pieces.get(point).isSameDynasty(dynasty);
    }
}
