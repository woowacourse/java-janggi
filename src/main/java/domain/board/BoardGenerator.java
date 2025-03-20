package domain.board;

import static domain.board.Board.END_COLUMN_INDEX;
import static domain.board.Board.END_ROW_INDEX;
import static domain.board.Board.START_COLUMN_INDEX;
import static domain.board.Board.START_ROW_INDEX;

import domain.PieceType;
import domain.piece.Byeong;
import domain.piece.Cha;
import domain.piece.Ma;
import domain.piece.Piece;
import domain.piece.Po;
import domain.piece.Sa;
import domain.piece.Sang;
import domain.piece.Team;
import domain.piece.Wang;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import view.SangMaOrderCommand;

public class BoardGenerator {

    public Board generateBoard(SangMaOrderCommand hanSangMaOrderCommand, SangMaOrderCommand choSangMaOrderCommand) {
        Map<Point, Node> nodeByPoint = initializeNodesAndEdges();
        Map<Node, Piece> pieceByNode = initializePiecePosition(nodeByPoint,
                hanSangMaOrderCommand,
                choSangMaOrderCommand);
        return new Board(pieceByNode, nodeByPoint);
    }

    public Map<Point, Node> initializeNodesAndEdges() {
        Map<Point, Node> nodeByPoint = new HashMap<>();

        for (int row = START_ROW_INDEX; row <= END_ROW_INDEX; row++) {
            for (int column = START_COLUMN_INDEX; column <= END_COLUMN_INDEX; column++) {
                Point point = Point.of(row, column);
                Node currentNode = new Node(point);
                nodeByPoint.put(point, currentNode);
            }
        }

        for (int row = START_ROW_INDEX; row <= END_ROW_INDEX; row++) {
            for (int column = START_COLUMN_INDEX; column <= END_COLUMN_INDEX; column++) {
                Point point = Point.of(row, column);
                Node currentNode = nodeByPoint.get(point);
                currentNode.addAllEdges(createEdges(row, column, nodeByPoint));
            }
        }

        return nodeByPoint;
    }

    private List<Edge> createEdges(int row, int column, Map<Point, Node> nodeByPoint) {
        List<Edge> edges = new ArrayList<>();
        for (Direction direction : List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)) {
            int nextRow = row + direction.deltaRow();
            int nextColumn = column + direction.deltaColumn();
            if (!isInRange(nextRow, nextColumn)) {
                continue;
            }
            Node nextNode = nodeByPoint.get(Point.of(nextRow, nextColumn));
            edges.add(new Edge(nextNode, direction));
        }
        return edges;
    }

    public Map<Node, Piece> initializePiecePosition(Map<Point, Node> nodeByPoint,
                                                    SangMaOrderCommand hanSangMaOrderCommand,
                                                    SangMaOrderCommand choSangMaOrderCommand) {
        Map<Node, Piece> board = new HashMap<>();
        List<Point> hanSangMaPoints = List.of(Point.of(1, 2), Point.of(1, 3), Point.of(1, 7), Point.of(1, 8));
        initializeHanPiecePosition(hanSangMaPoints, hanSangMaOrderCommand, nodeByPoint, board);

        List<Point> choSangMaPoints = List.of(Point.of(10, 2), Point.of(10, 3), Point.of(10, 7), Point.of(10, 8));
        initializeChoPiecePosition(choSangMaPoints, choSangMaOrderCommand, nodeByPoint, board);

        return board;
    }

    private void initializeHanPiecePosition(List<Point> sangMaPoints,
                                            SangMaOrderCommand sangMaOrderCommand,
                                            Map<Point, Node> nodeByPoint, Map<Node, Piece> board) {
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

    private void initializeChoPiecePosition(List<Point> sangMaPoints,
                                            SangMaOrderCommand sangMaOrderCommand,
                                            Map<Point, Node> nodeByPoint, Map<Node, Piece> board) {
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

    private Deque<Piece> createSangMaOrder(SangMaOrderCommand sangMaOrderCommand, Team team) {
        List<PieceType> pieceTypes = sangMaOrderCommand.getPieceTypes();
        Deque<Piece> pieces = new ArrayDeque<>();
        for (PieceType pieceType : pieceTypes) {
            pieces.addLast(createPiece(pieceType, team));
        }
        return pieces;
    }

    private Piece createPiece(PieceType pieceType, Team team) {
        Piece piece;
        switch (pieceType) {
            case SANG -> piece = new Sang(team);
            case MA -> piece = new Ma(team);
            default -> throw new IllegalArgumentException("상 또는 마가 아닙니다.");
        }
        return piece;
    }

    private boolean isInRange(int row, int column) {
        return START_ROW_INDEX <= row && row <= END_ROW_INDEX
                && START_COLUMN_INDEX <= column && column <= END_COLUMN_INDEX;
    }
}
