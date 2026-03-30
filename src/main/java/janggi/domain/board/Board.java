package janggi.domain.board;

import janggi.domain.board.point.Point;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.piece.path.CandidatePath;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Board {
    private static final int MIN_X = 0;
    private static final int MAX_X = 9;

    private static final int MIN_Y = 0;
    private static final int MAX_Y = 8;
    private final Map<Point, Piece> board;

    private Board(Map<Point, Piece> board) {
        board.keySet().forEach(this::validateRange);
        this.board = board;
    }

    public static boolean isInRange(int nx, int ny) {
        return nx >= MIN_X && nx <= MAX_X && ny >= MIN_Y && ny <= MAX_Y;
    }

    public static Board setUp(BoardSetUp choBoardSetUp, BoardSetUp hanBoardSetUp) {
        Map<Point, Piece> board = new HashMap<>();
        board.putAll(choBoardSetUp.generate(Side.CHO));
        board.putAll(hanBoardSetUp.generate(Side.HAN));

        return new Board(board);
    }

    private void validateRange(Point point) {
        if (!isInRange(point.x(), point.y())) {
            throw new IllegalStateException("좌표의 범위는 {0,0} ~ {8,9} 입니다.");
        }
    }

    public final Map<Point, Piece> getBoard() {
        return new HashMap<>(board);
    }

    public Set<Point> destinations(Point from) {
        Piece piece = getPieceAt(from);
        List<CandidatePath> candidatePaths = piece.createCandidatePaths(from);
        Map<Point, Piece> piecesOnPaths = findPiecesOnPaths(candidatePaths);

        return piece.availablePoints(candidatePaths, piecesOnPaths)
                .stream()
                .filter(point -> isDestinationOtherSide(piece, point))
                .collect(Collectors.toSet());
    }

    public void moveTo(Point from, Point to) {
        Piece fromPiece = getPieceAt(from);
        Set<Point> destinations = destinations(from);

        if (!destinations.contains(to)) {
            throw new IllegalArgumentException("%s의 이동 가능한 좌표가 아닙니다.".formatted(fromPiece.getName()));
        }

        board.put(to, fromPiece);
        board.remove(from);
    }

    public Side getSideAt(Point point) {
        Piece piece = getPieceAt(point);
        return piece.getSide();
    }

    private boolean isDestinationOtherSide(Piece piece, Point destination) {
        if (isNotTherePiece(destination)) {
            return true;
        }
        return getPieceAt(destination)
                .isOtherSide(piece.getSide());
    }

    private Map<Point, Piece> findPiecesOnPaths(List<CandidatePath> candidateCandidatePaths) {
        Map<Point, Piece> piecesOnPaths = new HashMap<>();

        for (CandidatePath candidatePath : candidateCandidatePaths) {
            Map<Point, Piece> piecesOnPath = findPiecesOnCandidatePath(candidatePath);
            piecesOnPaths.putAll(piecesOnPath);
        }
        return piecesOnPaths;
    }

    private Map<Point, Piece> findPiecesOnCandidatePath(CandidatePath candidatePath) {
        Map<Point, Piece> pieces = new HashMap<>();

        for (Point point : candidatePath.getPath()) {
            if (isNotTherePiece(point)) {
                continue;
            }

            Piece piece = getPieceAt(point);
            pieces.put(point, piece);
        }
        return pieces;
    }

    private boolean isNotTherePiece(Point point) {
        return !board.containsKey(point);
    }

    private Piece getPieceAt(Point point) {
        if (isNotTherePiece(point)) {
            throw new IllegalArgumentException("빈 공간 입니다.");
        }
        return board.get(point);
    }

}
