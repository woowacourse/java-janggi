package janggi.domain.board;

import janggi.domain.board.point.Point;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.piece.Movement;
import janggi.domain.piece.path.CandidatePath;
import janggi.domain.piece.path.PathStrategy;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Board {
    private static final Dimension BOARD_DIMENSION = new BoardDimension();
    private static final Dimension PALACE_DIMENSION = new PalaceDimension();

    private final Map<Point, Piece> pieces;

    protected Board(Map<Point, Piece> pieces) {
        pieces.keySet().forEach(BOARD_DIMENSION::validateRange);
        this.pieces = new HashMap<>(pieces);
    }

    public static Board setUp(BoardSetUp choBoardSetUp, BoardSetUp hanBoardSetUp) {
        Map<Point, Piece> board = new HashMap<>();
        board.putAll(choBoardSetUp.generate(Side.CHO));
        board.putAll(hanBoardSetUp.generate(Side.HAN));

        return new Board(board);
    }

    public final Map<Point, Piece> getPieces() {
        return new HashMap<>(pieces);
    }


    public Set<Point> destinations(Point from) {
        Set<Point> destinations = pieceDestinations(from);
        if (PALACE_DIMENSION.isInRange(from.x(), from.y())) {
            destinations.addAll(palaceDestinations(from));
        }

        return destinations;
    }

    private Set<Point> palaceDestinations(Point from) {
        Piece piece = getPieceAt(from)
                .orElseThrow(() -> new IllegalArgumentException("해당 Point에 기물이 없어, 목적지가 없습니다."));
        List<CandidatePath> candidatePaths = convertToCandidatePaths(
                PalaceMovements.getMovements(from), from, piece.pathStrategy(), PALACE_DIMENSION);

        return piece.availablePoints(candidatePaths, findPiecesOnPaths(candidatePaths))
                .stream()
                .filter(point -> canMove(piece, point))
                .collect(Collectors.toSet());
    }

    private Set<Point> pieceDestinations(Point from) {
        Piece piece = getPieceAt(from)
                .orElseThrow(() -> new IllegalArgumentException("해당 Point에 기물이 없어, 목적지가 없습니다."));
        List<CandidatePath> candidatePaths = convertToCandidatePaths(piece.createCandidateMovement(), from,
                piece.pathStrategy(), BOARD_DIMENSION);

        return piece.availablePoints(candidatePaths, findPiecesOnPaths(candidatePaths))
                .stream()
                .filter(point -> canMove(piece, point))
                .collect(Collectors.toSet());
    }


    private List<CandidatePath> convertToCandidatePaths(List<Movement> movements,
                                                        Point from,
                                                        PathStrategy pathStrategy,
                                                        Dimension dimension) {
        return movements.stream()
                .map(movement -> new CandidatePath(movement, from, pathStrategy, dimension))
                .toList();
    }


    public void moveTo(Point from, Point to) {
        Piece fromPiece = getPieceAt(from).orElseThrow(
                () -> new IllegalArgumentException("해당 Point에 기물이 없어, 움직일 수 없습니다."));
        Set<Point> destinations = destinations(from);

        if (!destinations.contains(to)) {
            throw new IllegalArgumentException("%s의 이동 가능한 좌표가 아닙니다.".formatted(fromPiece.getName()));
        }

        pieces.put(to, fromPiece);
        pieces.remove(from);
    }

    public Side getSideAt(Point point) {
        Piece piece = getPieceAt(point).orElseThrow(
                () -> new IllegalArgumentException("해당 Point에 기물이 없어, Side를 확인 할 수 없습니다."));
        return piece.getSide();
    }

    private boolean canMove(Piece from, Point destination) {
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
        if (!pieces.containsKey(point)) {
            return Optional.empty();
        }
        return Optional.of(pieces.get(point));
    }

}
