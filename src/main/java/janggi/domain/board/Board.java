package janggi.domain.board;

import janggi.domain.board.coordinate.Path;
import janggi.domain.board.coordinate.PathStrategy;
import janggi.domain.board.coordinate.Point;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.piece.Pattern;
import janggi.domain.piece.unit.Empty;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Board {
    public static final int X_SIZE = 9;
    public static final int Y_SIZE = 8;


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

    public static boolean isInRange(int nx, int ny) {
        return nx >= 0 && nx <= Board.X_SIZE && ny >= 0 && ny <= Board.Y_SIZE;
    }

    public final Map<Point, Piece> getBoard() {
        return new HashMap<>(board);
    }

    public Set<Point> destinations(Point from) {
        Piece piece = getPieceAtPoint(from);
        List<Path> paths = convertToPaths(piece.patterns(), from, piece.pathStrategy());
        Map<Point, Piece> piecesOnPaths = findPiecesOnPaths(paths);

        return piece.availablePoints(paths, piecesOnPaths)
                .stream()
                .filter(point -> isDestinationOtherSide(piece, point))
                .collect(Collectors.toSet());
    }

    public void moveTo(Point from, Point to) {
        if (isNotTherePiece(from)) {
            throw new IllegalArgumentException("빈 공간은 선택 할 수 없습니다.");
        }
        Piece fromPiece = getPieceAtPoint(from);
        Set<Point> destinations = destinations(from);

        if (!destinations.contains(to)) {
            throw new IllegalArgumentException("%s의 이동 가능한 좌표가 아닙니다.".formatted(fromPiece.getName()));
        }

        board.put(to, fromPiece);
        board.remove(from);
    }

    public Side getPointPieceSide(Point point) {
        if (!board.containsKey(point)) {
            throw new IllegalArgumentException("해당 좌표에는 기물이 없습니다.");
        }
        Piece piece = board.get(point);

        return piece.getSide();
    }

    private List<Path> convertToPaths(List<Pattern> patterns, Point from, PathStrategy pathStrategy) {
        return patterns.stream()
                .map(pattern -> convertToPath(pattern, from, pathStrategy))
                .toList();
    }

    private Path convertToPath(Pattern pattern, Point from, PathStrategy pathStrategy) {
        return new Path(pattern, from, pathStrategy);
    }

    private boolean isDestinationOtherSide(Piece piece, Point destination) {
        if (isNotTherePiece(destination)) {
            return true;
        }
        return getPieceAtPoint(destination)
                .isOtherSide(piece.getSide());
    }

    private Map<Point, Piece> findPiecesOnPaths(List<Path> paths) {
        Map<Point, Piece> piecesOnPaths = new HashMap<>();

        for (Path path : paths) {
            Map<Point, Piece> piecesOnPath = findPiecesOnPath(path);
            piecesOnPaths.putAll(piecesOnPath);
        }
        return piecesOnPaths;
    }

    private Map<Point, Piece> findPiecesOnPath(Path path) {
        Map<Point, Piece> pieces = new HashMap<>();
        for (Point point : path.getPath()) {
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
        return board.getOrDefault(point, Empty.INSTANCE);
    }

}
