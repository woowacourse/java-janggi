package janggi.domain.board;

import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.path.CandidatePath;
import janggi.domain.path.Movement;
import janggi.domain.piece.Piece;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Board {
    private final Map<Point, Piece> pieces;

    protected Board(Map<Point, Piece> pieces) {
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

    public Set<Point> destinations(List<Movement> movements, Point from, Predicate<Point> predicate) {
        Piece piece = getPieceAt(from)
                .orElseThrow(() -> new IllegalArgumentException("해당 Point에 기물이 없어, 목적지가 없습니다."));
        List<CandidatePath> candidatePaths = movements.stream()
                .map(movement -> new CandidatePath(movement, from, piece.pathStrategy(), predicate))
                .toList();

        return piece.availablePoints(candidatePaths, findPiecesOnPaths(candidatePaths))
                .stream()
                .filter(point -> isThereOtherSidePiece(piece, point))
                .collect(Collectors.toSet());
    }

    public List<Movement> getPieceMovements(Point from) {
        return getPieceAt(from)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 포인트에 Piece가 존재하지 않아 Movement를 생성할 수 없습니다."))
                .getMovements();
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

    private boolean isThereOtherSidePiece(Piece from, Point destination) {
        Piece to = getPieceAt(destination).orElse(null);
        if (to == null) {
            return true;
        }
        return to.isDifferentSide(from.getSide());
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
        Map<Point, Piece> piecesOnPath = new HashMap<>();

        for (Point point : candidatePath.getPath()) {
            Piece piece = getPieceAt(point).orElse(null);
            if (piece == null) {
                continue;
            }
            piecesOnPath.put(point, piece);
        }
        return piecesOnPath;
    }

    private Optional<Piece> getPieceAt(Point point) {
        return Optional.ofNullable(pieces.get(point));
    }

}
