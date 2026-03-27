package janggi.domain.board;

import janggi.domain.board.coordinate.Path;
import janggi.domain.board.coordinate.PathStrategy;
import janggi.domain.board.coordinate.Point;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.piece.Pattern;
import janggi.domain.piece.unit.Advisor;
import janggi.domain.piece.unit.Cannon;
import janggi.domain.piece.unit.Chariot;
import janggi.domain.piece.unit.Empty;
import janggi.domain.piece.unit.General;
import janggi.domain.piece.unit.Piece;
import janggi.domain.piece.unit.Soldier;
import janggi.domain.side.Side;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Point, Piece> board;

    private Board(Map<Point, Piece> board) {
//        validateSize(board);
//        validatePiece(board);
        this.board = board;
    }

    public static Board setUp(BoardSetUp choBoardSetUp, BoardSetUp hanBoardSetUp) {
        Map<Point, Piece> board = createCommonBoard();
        board.putAll(choBoardSetUp.generate(Side.CHO));
        board.putAll(hanBoardSetUp.generate(Side.HAN));

        return new Board(board);
    }

    private static Map<Point, Piece> createCommonBoard() {
        Map<Point, Piece> board = new HashMap<>();

        board.put(new Point(0, 0), new Chariot(Side.CHO));
        board.put(new Point(0, 3), new Advisor(Side.CHO));
        board.put(new Point(0, 5), new Advisor(Side.CHO));
        board.put(new Point(0, 8), new Chariot(Side.CHO));

        board.put(new Point(1, 4), new General(Side.CHO));

        board.put(new Point(2, 1), new Cannon(Side.CHO));
        board.put(new Point(2, 7), new Cannon(Side.CHO));

        board.put(new Point(3, 0), new Soldier(Side.CHO));
        board.put(new Point(3, 2), new Soldier(Side.CHO));
        board.put(new Point(3, 4), new Soldier(Side.CHO));
        board.put(new Point(3, 6), new Soldier(Side.CHO));
        board.put(new Point(3, 8), new Soldier(Side.CHO));

        board.put(new Point(6, 8), new Soldier(Side.HAN));
        board.put(new Point(6, 6), new Soldier(Side.HAN));
        board.put(new Point(6, 4), new Soldier(Side.HAN));
        board.put(new Point(6, 2), new Soldier(Side.HAN));
        board.put(new Point(6, 0), new Soldier(Side.HAN));

        board.put(new Point(7, 7), new Cannon(Side.HAN));
        board.put(new Point(7, 1), new Cannon(Side.HAN));

        board.put(new Point(8, 6), new General(Side.HAN));

        board.put(new Point(9, 8), new Chariot(Side.HAN));
        board.put(new Point(9, 5), new Advisor(Side.HAN));
        board.put(new Point(9, 3), new Advisor(Side.HAN));
        board.put(new Point(9, 0), new Chariot(Side.HAN));

        return board;
    }

    public final Map<Point, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }

    public List<Point> destinations(Point from) {
        Piece piece = board.getOrDefault(from, new Empty());
        List<Path> paths = convertToPath(piece.patterns(), from, piece.pathStrategy());
        Map<Point, Piece> piecesOnPaths = findPiecesOnPaths(paths);

        return piece.availablePoints(paths, piecesOnPaths)
                .stream()
                .filter(point -> isDestinationOtherSide(piece, point))
                .toList();
    }

    public List<Path> convertToPath(List<Pattern> patterns, Point from, PathStrategy pathStrategy) {
        return patterns.stream()
                .map(pattern -> convertToPath(pattern, from, pathStrategy))
                .toList();
    }

    private Path convertToPath(Pattern pattern, Point from, PathStrategy pathStrategy) {
        return new Path(pattern, from, pathStrategy);
    }

    public final boolean isDestinationOtherSide(Piece piece, Point destination) {
        return board.get(destination).isOtherSide(piece.getSide());
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
            Piece piece = board.getOrDefault(point, new Empty());
            pieces.put(point, piece);
        }
        return pieces;
    }

    public boolean isTherePiece(Point point) {
        return board.containsKey(point);
    }
}
