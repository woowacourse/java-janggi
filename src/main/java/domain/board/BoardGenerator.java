package domain.board;

import domain.piece.Byeong;
import domain.piece.Cha;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Po;
import domain.piece.Sa;
import domain.piece.Team;
import domain.piece.Wang;
import view.command.SangMaOrderCommand;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static domain.board.Board.*;

public class BoardGenerator {

    private static final Point HAN_WANG = Point.of(2, 5);
    private static final Point HAN_WANG_UP_LEFT = Point.of(1, 4);
    private static final Point HAN_WANG_UP_RIGHT = Point.of(1, 6);
    private static final Point HAN_WANG_DOWN_LEFT = Point.of(3, 4);
    private static final Point HAN_WANG_DOWN_RIGHT = Point.of(3, 6);

    private static final Point CHO_WANG = Point.of(9, 5);
    private static final Point CHO_WANG_UP_LEFT = Point.of(8, 4);
    private static final Point CHO_WANG_UP_RIGHT = Point.of(8, 6);
    private static final Point CHO_WANG_DOWN_LEFT = Point.of(10, 4);
    private static final Point CHO_WANG_DOWN_RIGHT = Point.of(10, 6);

    public Board generateBoard(final SangMaOrderCommand hanSangMaOrderCommand,
                               final SangMaOrderCommand choSangMaOrderCommand) {
        Map<Point, Node> nodeByPoint = initializeNodesAndEdges();
        Map<Node, Piece> pieceByNode = initializePiecePosition(nodeByPoint, hanSangMaOrderCommand, choSangMaOrderCommand);
        return new Board(pieceByNode, nodeByPoint);
    }

    public Map<Point, Node> initializeNodesAndEdges() {
        Map<Point, Node> nodeByPoint = new HashMap<>();
        createNodes(nodeByPoint);
        createEdgesByNode(nodeByPoint);
        return nodeByPoint;
    }

    private void createNodes(Map<Point, Node> nodeByPoint) {
        for (int row = START_ROW_INDEX; row <= END_ROW_INDEX; row++) {
            for (int column = START_COLUMN_INDEX; column <= END_COLUMN_INDEX; column++) {
                Point point = Point.of(row, column);
                Node currentNode = new Node(point);
                nodeByPoint.put(point, currentNode);
            }
        }
    }

    private void createEdgesByNode(Map<Point, Node> nodeByPoint) {
        for (int row = START_ROW_INDEX; row <= END_ROW_INDEX; row++) {
            for (int column = START_COLUMN_INDEX; column <= END_COLUMN_INDEX; column++) {
                Point point = Point.of(row, column);
                Node currentNode = nodeByPoint.get(point);
                currentNode.addAllEdges(createEdges(point, nodeByPoint));
            }
        }
    }

    private List<Edge> createEdges(final Point point, final Map<Point, Node> nodeByPoint) {
        List<Edge> edges = new ArrayList<>();
        for (Direction direction : Direction.BASIC_DIRECTIONS) {
            int nextRow = point.row() + direction.deltaRow();
            int nextColumn = point.column() + direction.deltaColumn();
            if (Point.isInvalidRange(nextRow, nextColumn)) {
                continue;
            }
            Node nextNode = nodeByPoint.get(Point.of(nextRow, nextColumn));
            Edge edge = new Edge(nextNode, direction);
            edges.add(edge);
        }
        addPalaceEdges(point, nodeByPoint, edges);
        return edges;
    }

    private void addPalaceEdges(Point point, Map<Point, Node> nodeByPoint, List<Edge> edges) {
        addWangPointEdges(point, nodeByPoint, edges);

        addWangUpLeftPointEdges(point, nodeByPoint, edges);
        addWangUpRightPointEdges(point, nodeByPoint, edges);
        addWangDownLeftPointEdges(point, nodeByPoint, edges);
        addWangDownRightPointEdges(point, nodeByPoint, edges);
    }

    private void addWangPointEdges(Point point, Map<Point, Node> nodeByPoint, List<Edge> edges) {
        if (point.equals(HAN_WANG) || point.equals(CHO_WANG)) {
            for (Direction direction : Direction.DIAGONAL_DIRECTIONS) {
                addDiagonal(point, nodeByPoint, edges, direction);
            }
        }
    }

    private void addWangUpLeftPointEdges(Point point, Map<Point, Node> nodeByPoint, List<Edge> edges) {
        if (point.equals(HAN_WANG_UP_LEFT) || point.equals(CHO_WANG_UP_LEFT)) {
            addDiagonal(point, nodeByPoint, edges, Direction.DOWN_RIGHT);
        }
    }

    private void addWangUpRightPointEdges(Point point, Map<Point, Node> nodeByPoint, List<Edge> edges) {
        if (point.equals(HAN_WANG_UP_RIGHT) || point.equals(CHO_WANG_UP_RIGHT)) {
            addDiagonal(point, nodeByPoint, edges, Direction.DOWN_LEFT);
        }
    }

    private void addWangDownLeftPointEdges(Point point, Map<Point, Node> nodeByPoint, List<Edge> edges) {
        if (point.equals(HAN_WANG_DOWN_LEFT) || point.equals(CHO_WANG_DOWN_LEFT)) {
            addDiagonal(point, nodeByPoint, edges, Direction.UP_RIGHT);
        }
    }

    private void addWangDownRightPointEdges(Point point, Map<Point, Node> nodeByPoint, List<Edge> edges) {
        if (point.equals(HAN_WANG_DOWN_RIGHT) || point.equals(CHO_WANG_DOWN_RIGHT)) {
            addDiagonal(point, nodeByPoint, edges, Direction.UP_LEFT);
        }
    }

    private void addDiagonal(Point point, Map<Point, Node> nodeByPoint, List<Edge> edges, Direction direction) {
        int nextRow = point.row() + direction.deltaRow();
        int nextColumn = point.column() + direction.deltaColumn();
        Node nextNode = nodeByPoint.get(Point.of(nextRow, nextColumn));
        Edge edge = new Edge(nextNode, direction);
        edges.add(edge);
    }

    public Map<Node, Piece> initializePiecePosition(final Map<Point, Node> nodeByPoint,
                                                    final SangMaOrderCommand hanSangMaOrderCommand,
                                                    final SangMaOrderCommand choSangMaOrderCommand) {
        Map<Node, Piece> board = new HashMap<>();
        List<Point> hanSangMaPoints = List.of(Point.of(1, 2), Point.of(1, 3), Point.of(1, 7), Point.of(1, 8));
        initializeHanPiecePosition(hanSangMaPoints, hanSangMaOrderCommand, nodeByPoint, board);

        List<Point> choSangMaPoints = List.of(Point.of(10, 2), Point.of(10, 3), Point.of(10, 7), Point.of(10, 8));
        initializeChoPiecePosition(choSangMaPoints, choSangMaOrderCommand, nodeByPoint, board);

        return board;
    }

    private void initializeHanPiecePosition(final List<Point> sangMaPoints,
                                            final SangMaOrderCommand sangMaOrderCommand,
                                            final Map<Point, Node> nodeByPoint,
                                            final Map<Node, Piece> board) {
        board.put(nodeByPoint.get(Point.of(4, 1)), new Byeong(Team.HAN));
        board.put(nodeByPoint.get(Point.of(4, 3)), new Byeong(Team.HAN));
        board.put(nodeByPoint.get(Point.of(4, 5)), new Byeong(Team.HAN));
        board.put(nodeByPoint.get(Point.of(4, 7)), new Byeong(Team.HAN));
        board.put(nodeByPoint.get(Point.of(4, 9)), new Byeong(Team.HAN));

        board.put(nodeByPoint.get(Point.of(3, 2)), new Po(Team.HAN));
        board.put(nodeByPoint.get(Point.of(3, 8)), new Po(Team.HAN));

        board.put(nodeByPoint.get(Point.of(2, 5)), new Wang(Team.HAN));

        board.put(nodeByPoint.get(Point.of(1, 1)), new Cha(Team.HAN));
        board.put(nodeByPoint.get(Point.of(1, 4)), new Sa(Team.HAN));
        board.put(nodeByPoint.get(Point.of(1, 6)), new Sa(Team.HAN));
        board.put(nodeByPoint.get(Point.of(1, 9)), new Cha(Team.HAN));
        Deque<Piece> sangMaOrder = createSangMaOrder(sangMaOrderCommand, Team.HAN);
        for (Point point : sangMaPoints) {
            board.put(nodeByPoint.get(point), sangMaOrder.removeFirst());
        }
    }

    private void initializeChoPiecePosition(final List<Point> sangMaPoints,
                                            final SangMaOrderCommand sangMaOrderCommand,
                                            final Map<Point, Node> nodeByPoint,
                                            final Map<Node, Piece> board) {
        board.put(nodeByPoint.get(Point.of(7, 1)), new Byeong(Team.CHO));
        board.put(nodeByPoint.get(Point.of(7, 3)), new Byeong(Team.CHO));
        board.put(nodeByPoint.get(Point.of(7, 5)), new Byeong(Team.CHO));
        board.put(nodeByPoint.get(Point.of(7, 7)), new Byeong(Team.CHO));
        board.put(nodeByPoint.get(Point.of(7, 9)), new Byeong(Team.CHO));

        board.put(nodeByPoint.get(Point.of(8, 2)), new Po(Team.CHO));
        board.put(nodeByPoint.get(Point.of(8, 8)), new Po(Team.CHO));

        board.put(nodeByPoint.get(Point.of(9, 5)), new Wang(Team.CHO));

        board.put(nodeByPoint.get(Point.of(10, 1)), new Cha(Team.CHO));
        board.put(nodeByPoint.get(Point.of(10, 4)), new Sa(Team.CHO));
        board.put(nodeByPoint.get(Point.of(10, 6)), new Sa(Team.CHO));
        board.put(nodeByPoint.get(Point.of(10, 9)), new Cha(Team.CHO));
        Deque<Piece> sangMaOrder = createSangMaOrder(sangMaOrderCommand, Team.CHO);
        for (Point point : sangMaPoints) {
            board.put(nodeByPoint.get(point), sangMaOrder.removeFirst());
        }
    }

    private Deque<Piece> createSangMaOrder(final SangMaOrderCommand sangMaOrderCommand, final Team team) {
        List<PieceType> pieceTypes = sangMaOrderCommand.getPieceTypes();
        Deque<Piece> pieces = new ArrayDeque<>();
        for (PieceType pieceType : pieceTypes) {
            pieces.addLast(PieceType.createSangMaPiece(team, pieceType));
        }
        return pieces;
    }

    public Board loadBoard(final Map<Point, Piece> savedBoard) {
        Map<Point, Node> nodeByPoint = initializeNodesAndEdges();
        Map<Node, Piece> pieceByNode = new HashMap<>();
        Set<Point> points = savedBoard.keySet();
        for (Point point : points) {
            Node node = nodeByPoint.get(point);
            pieceByNode.put(node, savedBoard.get(point));
        }
        return new Board(pieceByNode, nodeByPoint);
    }
}
