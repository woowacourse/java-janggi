package domain.board;

import domain.piece.Cha;
import domain.piece.ChoByeong;
import domain.piece.HanByeong;
import domain.piece.Ma;
import domain.piece.Piece;
import domain.piece.Po;
import domain.piece.Sa;
import domain.piece.Sang;
import domain.piece.Wang;
import domain.piece.character.PieceType;
import domain.piece.character.Team;
import domain.point.Point;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import view.SangMaOrderCommand;

public class BoardGenerator {

    private final PathFinderFactory pathFinderFactory = new PathFinderFactory();

    public Board generateInitialBoard(final SangMaOrderCommand choSangMaOrderCommand,
                                      final SangMaOrderCommand hanSangMaOrderCommand) {
        PathFinder pathFinder = pathFinderFactory.createDefaultPathFinder();
        Map<Point, Piece> pieceByPoint = createPieces(choSangMaOrderCommand, hanSangMaOrderCommand);

        return new Board(pieceByPoint, pathFinder);
    }

    private Map<Point, Piece> createPieces(
            final SangMaOrderCommand choSangMaOrderCommand,
            final SangMaOrderCommand hanSangMaOrderCommand) {
        Map<Point, Piece> pieceByPoint = new HashMap<>();
        List<Point> choSangMaPoints = List.of(Point.of(10, 2), Point.of(10, 3), Point.of(10, 7), Point.of(10, 8));
        initializeChoPieces(choSangMaPoints, choSangMaOrderCommand, pieceByPoint);

        List<Point> hanSangMaPoints = List.of(Point.of(1, 2), Point.of(1, 3), Point.of(1, 7), Point.of(1, 8));
        initializeHanPieces(hanSangMaPoints, hanSangMaOrderCommand, pieceByPoint);

        return pieceByPoint;
    }

    private void initializeHanPieces(final List<Point> sangMaPoints,
                                     final SangMaOrderCommand sangMaOrderCommand,
                                     final Map<Point, Piece> pieceByPoint) {
        pieceByPoint.put(Point.of(4, 1), new HanByeong());
        pieceByPoint.put(Point.of(4, 3), new HanByeong());
        pieceByPoint.put(Point.of(4, 5), new HanByeong());
        pieceByPoint.put(Point.of(4, 7), new HanByeong());
        pieceByPoint.put(Point.of(4, 9), new HanByeong());

        pieceByPoint.put(Point.of(3, 2), new Po(Team.HAN));
        pieceByPoint.put(Point.of(3, 8), new Po(Team.HAN));

        pieceByPoint.put(Point.of(2, 5), new Wang(Team.HAN));

        pieceByPoint.put(Point.of(1, 1), new Cha(Team.HAN));
        pieceByPoint.put(Point.of(1, 4), new Sa(Team.HAN));
        pieceByPoint.put(Point.of(1, 6), new Sa(Team.HAN));
        pieceByPoint.put(Point.of(1, 9), new Cha(Team.HAN));
        Deque<Piece> sangMaOrder = createSangMaByCommand(sangMaOrderCommand, Team.HAN);
        for (Point point : sangMaPoints) {
            pieceByPoint.put(point, sangMaOrder.removeFirst());
        }
    }

    private void initializeChoPieces(final List<Point> sangMaPoints,
                                     final SangMaOrderCommand sangMaOrderCommand,
                                     final Map<Point, Piece> pieceByPoint) {
        pieceByPoint.put(Point.of(7, 1), new ChoByeong());
        pieceByPoint.put(Point.of(7, 3), new ChoByeong());
        pieceByPoint.put(Point.of(7, 5), new ChoByeong());
        pieceByPoint.put(Point.of(7, 7), new ChoByeong());
        pieceByPoint.put(Point.of(7, 9), new ChoByeong());

        pieceByPoint.put(Point.of(8, 2), new Po(Team.CHO));
        pieceByPoint.put(Point.of(8, 8), new Po(Team.CHO));

        pieceByPoint.put(Point.of(9, 5), new Wang(Team.CHO));

        pieceByPoint.put(Point.of(10, 1), new Cha(Team.CHO));
        pieceByPoint.put(Point.of(10, 4), new Sa(Team.CHO));
        pieceByPoint.put(Point.of(10, 6), new Sa(Team.CHO));
        pieceByPoint.put(Point.of(10, 9), new Cha(Team.CHO));
        Deque<Piece> sangMaOrder = createSangMaByCommand(sangMaOrderCommand, Team.CHO);
        for (Point point : sangMaPoints) {
            pieceByPoint.put(point, sangMaOrder.removeFirst());
        }
    }

    private Deque<Piece> createSangMaByCommand(final SangMaOrderCommand sangMaOrderCommand,
                                               final Team team) {
        List<PieceType> pieceTypes = sangMaOrderCommand.getPieceTypes();
        Deque<Piece> pieces = new ArrayDeque<>();
        for (PieceType pieceType : pieceTypes) {
            pieces.addLast(createMovablePiece(pieceType, team));
        }
        return pieces;
    }

    private Piece createMovablePiece(final PieceType pieceType, final Team team) {
        return switch (pieceType) {
            case SANG -> new Sang(team);
            case MA -> new Ma(team);
            default -> throw new IllegalArgumentException("[ERROR] 상 또는 마가 아닙니다.");
        };
    }
}
