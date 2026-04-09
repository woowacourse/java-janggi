package janggi.domain.board;

import janggi.domain.board.coordination.BoardCoordination;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.piece.Piece;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Board implements BoardInfo {
    private final Map<Point, Piece> pieces;

    public Board(Map<Point, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public static Board setUp(BoardSetUp choBoardSetUp, BoardSetUp hanBoardSetUp) {
        Map<Point, Piece> board = new HashMap<>();
        board.putAll(choBoardSetUp.generate(Side.CHO));
        board.putAll(hanBoardSetUp.generate(Side.HAN));

        return new Board(board);
    }

    public Map<Point, Piece> getPieces() {
        return new HashMap<>(pieces);
    }

    public Set<Point> destinations(Point from) {
        Piece piece = getPieceAt(from)
                .orElseThrow(() -> new IllegalArgumentException("해당 Point에 기물이 없어, 목적지가 없습니다."));

        return new HashSet<>(piece.availablePoints(from, this));
    }


    public void moveTo(Point from, Point to) {
        Piece fromPiece = getPieceAt(from).orElseThrow(
                () -> new IllegalArgumentException("해당 Point에 기물이 없어, 움직일 수 없습니다."));

        pieces.put(to, fromPiece);
        pieces.remove(from);
    }

    public Side getSideAt(Point point) {
        Piece piece = getPieceAt(point).orElseThrow(
                () -> new IllegalArgumentException("해당 Point에 기물이 없어, Side를 확인 할 수 없습니다."));
        return piece.getSide();
    }

    private Optional<Piece> getPieceAt(Point point) {
        return Optional.ofNullable(pieces.get(point));
    }

    @Override
    public boolean isEmpty(Point point) {
        return getPieceAt(point).isEmpty();
    }

    @Override
    public boolean isSameType(Point point, Piece piece) {
        Piece pointPiece = getPieceAt(point)
                .orElseThrow(() -> new IllegalStateException("point에 Piece가 없어, 같은 기물인지 확인 할 수 없습니다."));

        return piece.isSameType(pointPiece);
    }

    @Override
    public boolean isOtherSide(Piece from, Point destination) {
        Piece to = getPieceAt(destination).orElse(null);
        if (to == null) {
            return true;
        }
        return to.isDifferentSide(from.getSide());
    }

    @Override
    public boolean isInRange(Point point) {
        return BoardCoordination.isInRange(point);
    }
}
