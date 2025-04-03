package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class JanggiBoard {

    private final Map<Point, Piece> boardPieces;

    public JanggiBoard(Map<Point, Piece> boardPieces) {
        this.boardPieces = new HashMap<>(boardPieces);
    }

    public static JanggiBoard of(Map<Point, Piece> pieces, BoardSetUp hanBoardSetUp, BoardSetUp chuBoardSetUp) {
        HashMap<Point, Piece> pieceMap = new HashMap<>(pieces);
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
            boardPieces.remove(start);
            boardPieces.put(end, startPiece);
            if (isGeneralDie()) {
                return GameState.GAME_END;
            }
        }
        return GameState.RUN;
    }

    private boolean isGeneralDie() {
        return boardPieces.values().stream()
                .filter(piece -> piece.isEqualPieceType(PieceType.GENERAL))
                .count() < 2;
    }

    public boolean isExistPiece(Point point) {
        return boardPieces.containsKey(point);
    }

    public boolean isExistCannon(Point point) {
        return boardPieces.containsKey(point) && boardPieces.get(point).isEqualPieceType(PieceType.CANNON);
    }

    public boolean isNoObstacleOnPath(List<Point> path) {
        removePathFirst(path);
        removePathLast(path);
        return path.stream()
                .anyMatch(boardPieces::containsKey);
    }

    public int calculatePieceOnPath(List<Point> path) {
        removePathFirst(path);
        removePathLast(path);
        int pieceCount = 0;
        for (Point point : path) {
            if (boardPieces.containsKey(point)) {
                pieceCount++;
            }
        }
        return pieceCount;
    }

    public boolean hasPieceTypeOnPath(List<Point> path, PieceType pieceType) {
        removePathFirst(path);
        return path.stream()
                .filter(boardPieces::containsKey)
                .anyMatch(point -> boardPieces.get(point).isEqualPieceType(pieceType));
    }

    private void removePathLast(List<Point> path) {
        if(path.isEmpty()) {
            throw new IllegalArgumentException("path가 존재하지 않습니다.");
        }
        path.removeLast();
    }

    private void removePathFirst(List<Point> path) {
        if(path.isEmpty()) {
            throw new IllegalArgumentException("path가 존재하지 않습니다.");
        }
        path.removeFirst();
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
        return boardPieces.values().stream()
                .filter(piece -> piece.isSameDynasty(dynasty))
                .mapToInt(Piece::getScore)
                .sum();
    }

    public Dynasty getWinnerDynasty() {
        for (Piece piece : boardPieces.values()) {
            if (piece.getPieceType() == PieceType.GENERAL && piece.getDynasty() == Dynasty.HAN) {
                return Dynasty.HAN;
            }
        }
        return Dynasty.CHU;
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
}
