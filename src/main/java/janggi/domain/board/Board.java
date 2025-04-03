package janggi.domain.board;

import janggi.domain.camp.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.type.MoveType;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Board {

    private static final int COLUMN = 9;
    private static final int ROW = 10;
    public static final double LATE_START_BONUS_SCORE = 1.5;

    private final PalaceArea palaceArea;
    private final Map<Point, Piece> placedPieces;

    public Board() {
        this.placedPieces = new HashMap<>();
        this.palaceArea = new PalaceArea();
    }

    public void placePiece(Point point, Piece piece) {
        validatePoint(point);
        placedPieces.put(point, piece);
    }

    private void validatePoint(Point point) {
        if (!point.isXBetween(0, COLUMN) || !point.isYBetween(0, ROW)) {
            throw new IllegalArgumentException(String.format("기물의 위치는 %d x %d 영역을 벗어날 수 없습니다.", COLUMN, ROW));
        }
    }

    public void move(Point from, Point to) {
        validateMoveRequest(from, to);
        Piece movingPiece = peek(from);
        MoveType moveType = MoveType.NORMAL;
        if (isInPalace(from) && isInPalace(to)) {
            moveType = MoveType.PALACE;
        }
        validateMovementRule(moveType, movingPiece, from, to);
        validateRoute(moveType, movingPiece, from, to);
        validateCatchable(movingPiece, to);
        executeMove(movingPiece, from, to);
    }

    private void validateMoveRequest(Point from, Point to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("같은 위치로 이동할 수 없습니다.");
        }
        validatePoint(from);
        validatePoint(to);
    }

    public Piece peek(Point point) {
        if (!placedPieces.containsKey(point)) {
            throw new IllegalArgumentException("해당 위치에서 기물을 찾을 수 없습니다.");
        }
        return placedPieces.get(point);
    }

    private boolean isInPalace(Point point) {
        return palaceArea.contains(point);
    }

    private void validateMovementRule(MoveType moveType, Piece movingPiece, Point from, Point to) {
        movingPiece.validateMovementRule(moveType, from, to);
    }

    private void validateRoute(MoveType moveType, Piece movingPiece, Point from, Point to) {
        if (!movingPiece.getPieceType().isRoutable()) {
            return;
        }
        Set<Point> route = movingPiece.findRoute(moveType, from, to);
        Set<Piece> piecesByPoint = findPiecesByPoint(route);
        movingPiece.validateRouteObstacles(piecesByPoint);
    }

    private void validateCatchable(Piece movingPiece, Point to) {
        if (placedPieces.containsKey(to)) {
            Piece targetPiece = peek(to);
            movingPiece.validateCatch(targetPiece);
        }
    }

    private void executeMove(Piece movingPiece, Point from, Point to) {
        placedPieces.remove(from);
        placedPieces.put(to, movingPiece);
    }

    private Set<Piece> findPiecesByPoint(Set<Point> route) {
        return route.stream()
                .filter(placedPieces::containsKey)
                .map(this::peek)
                .collect(Collectors.toSet());
    }

    public double calculateHanScore() {
        double hanScore = placedPieces.values().stream()
                .filter(piece -> piece.getCamp() == Camp.HAN)
                .mapToInt(piece -> piece.getPieceType().getScore())
                .sum();
        return hanScore + LATE_START_BONUS_SCORE;
    }

    public double calculateChuScore() {
        return placedPieces.values().stream()
                .filter(piece -> piece.getCamp() == Camp.CHU)
                .mapToInt(piece -> piece.getPieceType().getScore())
                .sum();
    }
}
