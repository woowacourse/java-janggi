package janggi.domain.board;

import janggi.domain.Dynasty;
import janggi.domain.piece.BoardPiece;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.ChuSoldier;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.HanSoldier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JanggiBoard {

    private static final Map<Point, BoardPiece> PIECE_INITIAL_POSITIONS = new HashMap<>() {
        {
            put(new Point(1, 1), new BoardPiece(new Chariot(), Dynasty.HAN));
            put(new Point(1, 4), new BoardPiece(new Guard(), Dynasty.HAN));
            put(new Point(1, 6), new BoardPiece(new Guard(), Dynasty.HAN));
            put(new Point(1, 9), new BoardPiece(new Chariot(), Dynasty.HAN));
            put(new Point(2, 5), new BoardPiece(new General(), Dynasty.HAN));
            put(new Point(3, 2), new BoardPiece(new Cannon(), Dynasty.HAN));
            put(new Point(3, 8), new BoardPiece(new Cannon(), Dynasty.HAN));
            put(new Point(4, 1), new BoardPiece(new HanSoldier(), Dynasty.HAN));
            put(new Point(4, 3), new BoardPiece(new HanSoldier(), Dynasty.HAN));
            put(new Point(4, 5), new BoardPiece(new HanSoldier(), Dynasty.HAN));
            put(new Point(4, 7), new BoardPiece(new HanSoldier(), Dynasty.HAN));
            put(new Point(4, 9), new BoardPiece(new HanSoldier(), Dynasty.HAN));

            put(new Point(10, 1), new BoardPiece(new Chariot(), Dynasty.CHU));
            put(new Point(10, 4), new BoardPiece(new Guard(), Dynasty.CHU));
            put(new Point(10, 6), new BoardPiece(new Guard(), Dynasty.CHU));
            put(new Point(10, 9), new BoardPiece(new Chariot(), Dynasty.CHU));
            put(new Point(9, 5), new BoardPiece(new General(), Dynasty.CHU));
            put(new Point(8, 2), new BoardPiece(new Cannon(), Dynasty.CHU));
            put(new Point(8, 8), new BoardPiece(new Cannon(), Dynasty.CHU));
            put(new Point(7, 1), new BoardPiece(new ChuSoldier(), Dynasty.CHU));
            put(new Point(7, 3), new BoardPiece(new ChuSoldier(), Dynasty.CHU));
            put(new Point(7, 5), new BoardPiece(new ChuSoldier(), Dynasty.CHU));
            put(new Point(7, 7), new BoardPiece(new ChuSoldier(), Dynasty.CHU));
            put(new Point(7, 9), new BoardPiece(new ChuSoldier(), Dynasty.CHU));
        }
    };

    private final Map<Point, BoardPiece> boardPieces;

    public JanggiBoard(Map<Point, BoardPiece> boardPieces) {
        this.boardPieces = new HashMap<>(boardPieces);
    }

    public static JanggiBoard of(BoardSetUp hanBoardSetUp, BoardSetUp chuBoardSetUp) {
        HashMap<Point, BoardPiece> pieceMap = new HashMap<>(PIECE_INITIAL_POSITIONS);
        pieceMap.putAll(hanBoardSetUp.getDynastySetUp(Dynasty.HAN));
        pieceMap.putAll(chuBoardSetUp.getDynastySetUp(Dynasty.CHU));
        return new JanggiBoard(pieceMap);
    }

    public void move(Dynasty dynasty, Point start, Point end) {
        BoardPiece startPiece = getStartPiece(start);
        if (isPointSameDynasty(end, dynasty)) {
            throw new IllegalArgumentException("이미 놓여져 있는 기물이 존재합니다.");
        }
        if (startPiece.canMove(this, dynasty, start, end)) {
            boardPieces.remove(start);
            boardPieces.put(end, startPiece);
        }
    }

    public boolean isExistPiece(Point point) {
        return boardPieces.containsKey(point);
    }

    public boolean isExistCannon(Point point) {
        return boardPieces.containsKey(point) && boardPieces.get(point).isEqualPieceType(new Cannon());
    }

    private BoardPiece getStartPiece(Point start) {
        if (!boardPieces.containsKey(start)) {
            throw new IllegalArgumentException("시작 위치에 기물이 존재하지 않습니다.");
        }
        return boardPieces.get(start);
    }

    private boolean isPointSameDynasty(Point end, Dynasty currentTurnDynasty) {
        if (boardPieces.containsKey(end)) {
            BoardPiece endPointPiece = boardPieces.get(end);
            return endPointPiece.isSameDynasty(currentTurnDynasty);
        }
        return false;
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
        return Objects.equals(boardPieces, that.boardPieces);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(boardPieces);
    }

    public Map<Point, BoardPiece> getBoardPieces() {
        return Collections.unmodifiableMap(boardPieces);
    }
}
