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

    private final Map<Point, Piece> board;

    private Board(Map<Point, Piece> board) {
        this.board = board;
    }

    public static Board setUp(BoardSetUp choBoardSetUp, BoardSetUp hanBoardSetUp) {
        Map<Point, Piece> board = new HashMap<>();
        board.putAll(choBoardSetUp.generate(Side.CHO));
        board.putAll(hanBoardSetUp.generate(Side.HAN));

        return new Board(board);
    }

    public final Map<Point, Piece> getBoard() {
        return new HashMap<>(board);
    }

    public Set<Point> destinations(Point from) {
        Piece piece = getPieceAtPoint(from);
        List<CandidatePath> candidatePaths = piece.createCandidatePaths(from);
        Map<Point, Piece> piecesOnPaths = findPiecesOnPaths(candidatePaths);

        return piece.availablePoints(candidatePaths, piecesOnPaths)
                .stream()
                .filter(point -> isDestinationOtherSide(piece, point))
                .collect(Collectors.toSet());
    }

    public void moveTo(Point from, Point to) {
        Piece fromPiece = getPieceAtPoint(from);
        Set<Point> destinations = destinations(from);

        if (!destinations.contains(to)) {
            throw new IllegalArgumentException("%s의 이동 가능한 좌표가 아닙니다.".formatted(fromPiece.getName()));
        }

        board.put(to, fromPiece);
        board.remove(from);
    }

    public Side getPointPieceSide(Point point) {
        Piece piece = getPieceAtPoint(point);
        return piece.getSide();
    }

    private boolean isDestinationOtherSide(Piece piece, Point destination) {
        if (isNotTherePiece(destination)) {
            return true;
        }
        return getPieceAtPoint(destination)
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

            Piece piece = getPieceAtPoint(point);
            pieces.put(point, piece);
        }
        return pieces;
    }

    private boolean isNotTherePiece(Point point) {
        return !board.containsKey(point);
    }

    private Piece getPieceAtPoint(Point point) {
        if (isNotTherePiece(point)) {
            throw new IllegalArgumentException("빈 공간 입니다.");
        }
        return board.get(point);
    }

}
