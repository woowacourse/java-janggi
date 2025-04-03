package janggi.domain.board;

import janggi.dao.PieceDao;
import janggi.domain.camp.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.type.MoveType;
import janggi.infra.DatabaseConfig;
import janggi.infra.DatabaseConnector;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Board {

    private static final int COLUMN = 9;
    private static final int ROW = 10;
    public static final double LATE_START_BONUS_SCORE = 1.5;
    public static final int GENERAL_PIECE_COUNT = 2;

    private final PalaceArea palaceArea;
    private final PieceDao pieceDao;

    public Board() {
        this.palaceArea = new PalaceArea();
        DatabaseConfig DBConfig = new DatabaseConfig("localhost:13306", "janggi",
                "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "root");
        DatabaseConnector DBConnector = new DatabaseConnector(DBConfig);
        this.pieceDao = new PieceDao(DBConnector);
    }

    public void placePiece(Point point, Piece piece) {
        validatePoint(point);
        pieceDao.addPiece(piece, point);
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
        if (pieceDao.findByPoint(point) == null) {
            throw new IllegalArgumentException("해당 위치에서 기물을 찾을 수 없습니다.");
        }
        return pieceDao.findByPoint(point);
    }

    private boolean isInPalace(Point point) {
        return palaceArea.contains(point);
    }

    private void validateMovementRule(MoveType moveType, Piece movingPiece, Point from, Point to) {
        movingPiece.validateMovementRule(moveType, from, to);
    }

    private void validateRoute(MoveType moveType, Piece movingPiece, Point from, Point to) {
        Set<Point> route = movingPiece.findRoute(moveType, from, to);
        Set<Piece> piecesByPoint = findPiecesByPoint(route);
        movingPiece.validateRouteObstacles(piecesByPoint);
    }

    private void validateCatchable(Piece movingPiece, Point to) {
        if (pieceDao.findByPoint(to) != null) {
            Piece targetPiece = peek(to);
            movingPiece.validateCatch(targetPiece);
        }
    }

    private void executeMove(Piece movingPiece, Point from, Point to) {
        pieceDao.deletePieceByPoint(from);
        if (pieceDao.findByPoint(to) != null) {
            pieceDao.updatePieceByPoint(to, movingPiece);
        }
        if (pieceDao.findByPoint(to) == null) {
            pieceDao.addPiece(movingPiece, to);
        }
    }

    private Set<Piece> findPiecesByPoint(Set<Point> route) {
        return route.stream()
                .filter((point) -> pieceDao.findByPoint(point) != null)
                .map(this::peek)
                .collect(Collectors.toSet());
    }

    public boolean isGameOver() {
        return pieceDao.getGeneralCount() != GENERAL_PIECE_COUNT;
    }

    public Camp findWinningCamp() {
        return pieceDao.findWinningCamp();
    }

    public double calculateHanScore() {
        List<Piece> hanPieces = pieceDao.findAllCampPieces(Camp.HAN);
        double hanScore = hanPieces.stream()
                .mapToInt(piece -> piece.getPieceType().getScore())
                .sum();
        return hanScore + LATE_START_BONUS_SCORE;
    }

    public double calculateChuScore() {
        List<Piece> chuPieces = pieceDao.findAllCampPieces(Camp.CHU);
        return chuPieces.stream()
                .mapToInt(piece -> piece.getPieceType().getScore())
                .sum();
    }

    public void resetBoard() {
        pieceDao.clearTable();
    }

    public PieceDao getPieceDao() {
        return pieceDao;
    }
}
