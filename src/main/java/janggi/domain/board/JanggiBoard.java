package janggi.domain.board;

import janggi.domain.Dynasty;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.ChuSoldier;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.HanSoldier;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
            put(new Point(4, 1), new HanSoldier());
            put(new Point(4, 3), new HanSoldier());
            put(new Point(4, 5), new HanSoldier());
            put(new Point(4, 7), new HanSoldier());
            put(new Point(4, 9), new HanSoldier());

            put(new Point(10, 1), new Chariot(Dynasty.CHU));
            put(new Point(10, 4), new Guard(Dynasty.CHU));
            put(new Point(10, 6), new Guard(Dynasty.CHU));
            put(new Point(10, 9), new Chariot(Dynasty.CHU));
            put(new Point(9, 5), new General(Dynasty.CHU));
            put(new Point(8, 2), new Cannon(Dynasty.CHU));
            put(new Point(8, 8), new Cannon(Dynasty.CHU));
            put(new Point(7, 1), new ChuSoldier());
            put(new Point(7, 3), new ChuSoldier());
            put(new Point(7, 5), new ChuSoldier());
            put(new Point(7, 7), new ChuSoldier());
            put(new Point(7, 9), new ChuSoldier());
        }
    };

    private static final List<Point> HAN_PALACE = List.of(
            new Point(1, 4),
            new Point(1, 5),
            new Point(1, 6),
            new Point(2, 4),
            new Point(2, 5),
            new Point(2, 6),
            new Point(3, 4),
            new Point(3, 5),
            new Point(3, 6)
    );

    private static final List<Point> CHU_PALACE = List.of(
            new Point(8, 4),
            new Point(8, 5),
            new Point(8, 6),
            new Point(9, 4),
            new Point(9, 5),
            new Point(9, 6),
            new Point(10, 4),
            new Point(10, 5),
            new Point(10, 6)
    );

    private final Map<Point, Piece> boardPieces;

    public JanggiBoard(Map<Point, Piece> boardPieces) {
        this.boardPieces = new HashMap<>(boardPieces);
    }

    public static JanggiBoard of(BoardSetUp hanBoardSetUp, BoardSetUp chuBoardSetUp) {
        HashMap<Point, Piece> pieceMap = new HashMap<>(PIECE_INITIAL_POSITIONS);
        pieceMap.putAll(hanBoardSetUp.getDynastySetUp(Dynasty.HAN));
        pieceMap.putAll(chuBoardSetUp.getDynastySetUp(Dynasty.CHU));
        return new JanggiBoard(pieceMap);
    }

    public GameState move(Dynasty dynasty, Point start, Point end) {
        Piece startPiece = getStartPiece(start);
        if (isPointSameDynasty(end, dynasty)) {
            throw new IllegalArgumentException("이미 놓여져 있는 기물이 존재합니다.");
        }
        if (startPiece.canMove(this, dynasty, start, end)) {
            Piece removed = boardPieces.remove(start);
            boardPieces.put(end, startPiece);
            if (removed.isEqualPieceType(PieceType.GENERAL)) {
                return GameState.GAME_END;
            }
        }
        return GameState.RUN;
    }

    public boolean isExistPiece(Point point) {
        return boardPieces.containsKey(point);
    }

    public boolean isExistCannon(Point point) {
        return boardPieces.containsKey(point) && boardPieces.get(point).isEqualPieceType(PieceType.CANNON);
    }

    public boolean isNoObstacleOnPath(List<Point> path) {
        path.removeFirst();
        path.removeLast();
        for (Point point : path) {
            if (boardPieces.containsKey(point)) {
                return false;
            }
        }
        return true;
    }

    public int calculatePieceOnPath(List<Point> path) {
        path.removeFirst();
        path.removeLast();
        int pieceCount = 0;
        for (Point point : path) {
            if (boardPieces.containsKey(point)) {
                pieceCount++;
            }
        }
        return pieceCount;
    }

    public boolean hasPieceTypeOnPath(List<Point> path, PieceType pieceType) {
        path.removeFirst();
        for (Point point : path) {
            if (boardPieces.containsKey(point)) {
                if (boardPieces.get(point).isEqualPieceType(pieceType)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Piece getStartPiece(Point start) {
        if (!boardPieces.containsKey(start)) {
            throw new IllegalArgumentException("시작 위치에 기물이 존재하지 않습니다.");
        }
        return boardPieces.get(start);
    }

    private boolean isPointSameDynasty(Point end, Dynasty currentTurnDynasty) {
        if (boardPieces.containsKey(end)) {
            Piece endPointPiece = boardPieces.get(end);
            return endPointPiece.isSameDynasty(currentTurnDynasty);
        }
        return false;
    }

    public int calculateScore(Dynasty dynasty) {
        int score = 0;
        for (Entry<Point, Piece> entry : boardPieces.entrySet()) {
            Piece piece = entry.getValue();
            if (entry.getValue().isSameDynasty(dynasty)) {
                score += piece.getScore();
            }
        }
        return score;
    }

    public Dynasty getWinnerDynasty() {
        for (Piece piece : boardPieces.values()) {
            if (piece.getPieceType() == PieceType.GENERAL && piece.getDynasty() == Dynasty.HAN) {
                return Dynasty.CHU;

            }
        }
        return Dynasty.HAN;
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

    public Map<Point, Piece> getBoardPieces() {
        return Collections.unmodifiableMap(boardPieces);
    }

    public List<Point> getPalaceArea(Dynasty dynasty) {
        if (dynasty.equals(Dynasty.HAN)) {
            return HAN_PALACE;
        }
        return CHU_PALACE;
    }
}
