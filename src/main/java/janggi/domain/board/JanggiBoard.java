package janggi.domain.board;

import janggi.domain.Dynasty;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PiecesOnPath;
import janggi.domain.piece.Soldier;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JanggiBoard {

    private static final Map<Point, Piece> PIECE_INITIAL_POSITIONS = new HashMap<>() {
        {
            put(new Point(1, 1), new Chariot(Dynasty.HAN));
            put(new Point(1, 4), new Guard(Dynasty.HAN));
            put(new Point(1, 6), new Guard(Dynasty.HAN));
            put(new Point(1, 9), new Chariot(Dynasty.HAN));
            put(new Point(2, 5), new General(Dynasty.HAN));
            put(new Point(3, 2), new Cannon(Dynasty.HAN));
            put(new Point(3, 8), new Cannon(Dynasty.HAN));
            put(new Point(4, 1), new Soldier(Dynasty.HAN));
            put(new Point(4, 3), new Soldier(Dynasty.HAN));
            put(new Point(4, 5), new Soldier(Dynasty.HAN));
            put(new Point(4, 7), new Soldier(Dynasty.HAN));
            put(new Point(4, 9), new Soldier(Dynasty.HAN));

            put(new Point(10, 1), new Chariot(Dynasty.CHU));
            put(new Point(10, 4), new Guard(Dynasty.CHU));
            put(new Point(10, 6), new Guard(Dynasty.CHU));
            put(new Point(10, 9), new Chariot(Dynasty.CHU));
            put(new Point(9, 5), new General(Dynasty.CHU));
            put(new Point(8, 2), new Cannon(Dynasty.CHU));
            put(new Point(8, 8), new Cannon(Dynasty.CHU));
            put(new Point(7, 1), new Soldier(Dynasty.CHU));
            put(new Point(7, 3), new Soldier(Dynasty.CHU));
            put(new Point(7, 5), new Soldier(Dynasty.CHU));
            put(new Point(7, 7), new Soldier(Dynasty.CHU));
            put(new Point(7, 9), new Soldier(Dynasty.CHU));
        }
    };

    private final Map<Point, Piece> pieces;

    public JanggiBoard(Map<Point, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public static JanggiBoard of(BoardSetUp hanBoardSetUp, BoardSetUp chuBoardSetUp) {
        Map<Point, Piece> boardPieces = new HashMap<>(PIECE_INITIAL_POSITIONS);
        boardPieces.putAll(hanBoardSetUp.getPiecePositions());
        boardPieces.putAll(chuBoardSetUp.getPiecePositions());
        return new JanggiBoard(boardPieces);
    }

    public void move(Dynasty dynasty, Point from, Point to) {
        Piece piece = findPiece(from);
        if (piece.isEmptyPiece()) {
            throw new IllegalArgumentException("시작 위치에 기물이 존재하지 않습니다.");
        }
        if (!piece.isDynasty(dynasty)) {
            throw new IllegalArgumentException("자신의 나라 기물만 움직일 수 있습니다.");
        }
        List<Point> movePath = piece.movePath(from, to);
        if (piece.canMove(toPiecesOnPath(movePath))) {
            pieces.remove(from);
            pieces.put(to, piece);
        }
    }

    private Piece findPiece(Point point) {
        return pieces.getOrDefault(point, new EmptyPiece());
    }

    private PiecesOnPath toPiecesOnPath(List<Point> movePath) {
        List<Piece> piecesOnPth = movePath.stream()
                .map(this::findPiece)
                .toList();
        return new PiecesOnPath(piecesOnPth);
    }

    @Override
    public boolean equals(Object o) {
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

    public Map<Point, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }
}
