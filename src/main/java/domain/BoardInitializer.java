package domain;

import domain.piece.Byeong;
import domain.piece.Cha;
import domain.piece.Ma;
import domain.piece.Piece;
import domain.piece.Po;
import domain.piece.Sa;
import domain.piece.Sang;
import domain.piece.Wang;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import view.SangMaOrderCommand;

public class BoardInitializer {

    // 노드와 엣지 정보 초기화
    public Map<Point, Node> initializeNodesAndEdges() {
        Map<Point, Node> nodeByPoint = new HashMap<>();

        for (int row = Board.START_ROW_INDEX; row <= Board.END_ROW_INDEX; row++) {
            for (int column = Board.START_COLUMN_INDEX; column <= Board.END_COLUMN_INDEX; column++) {
                Point point = Point.of(row, column);
                List<Edge> edges = new ArrayList<>();
                for (Direction direction : List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)) {
                    int nextRow = row + direction.deltaRow();
                    int nextColumn = column + direction.deltaColumn();
                    if (!Board.isInRange(nextRow, nextColumn)) {
                        continue;
                    }
                    Node nextNode = nodeByPoint.get(Point.of(nextRow, nextColumn));
                    edges.add(new Edge(nextNode, direction));
                }
                Node currentNode = new Node(point, edges);
                nodeByPoint.put(point, currentNode);
            }
        }

        return nodeByPoint;
    }

    // 상마 순서에 따라 기물 배치
    public Map<Node, Piece> initializePiecePosition(Map<Point, Node> nodeByPoint,
                                                    SangMaOrderCommand choSangMaOrderCommand,
                                                    SangMaOrderCommand hanSangMaOrderCommand) {
        Map<Node, Piece> board = new HashMap<>();

        List<Point> choSangMaPoints = List.of(Point.of(10, 2), Point.of(10, 3), Point.of(10, 7), Point.of(10, 8));
        initializePiecePositionByTeam(Team.CHO, choSangMaPoints, choSangMaOrderCommand, nodeByPoint, board);

        List<Point> hanSangMaPoints = List.of(Point.of(1, 2), Point.of(1, 3), Point.of(1, 7), Point.of(1, 8));
        initializePiecePositionByTeam(Team.HAN, hanSangMaPoints, hanSangMaOrderCommand, nodeByPoint, board);

        return board;
    }

    private void initializePiecePositionByTeam(Team team, List<Point> sangMaPoints,
                                               SangMaOrderCommand sangMaOrderCommand,
                                               Map<Point, Node> nodeByPoint, Map<Node, Piece> board) {
        board.put(nodeByPoint.get(Point.of(7, 1)), new Byeong(team));
        board.put(nodeByPoint.get(Point.of(7, 3)), new Byeong(team));
        board.put(nodeByPoint.get(Point.of(7, 5)), new Byeong(team));
        board.put(nodeByPoint.get(Point.of(7, 7)), new Byeong(team));
        board.put(nodeByPoint.get(Point.of(7, 9)), new Byeong(team));

        board.put(nodeByPoint.get(Point.of(8, 2)), new Po(team));
        board.put(nodeByPoint.get(Point.of(8, 8)), new Po(team));

        board.put(nodeByPoint.get(Point.of(9, 5)), new Wang(team));

        board.put(nodeByPoint.get(Point.of(10, 1)), new Cha(team));
        board.put(nodeByPoint.get(Point.of(10, 4)), new Sa(team));
        board.put(nodeByPoint.get(Point.of(10, 6)), new Sa(team));
        board.put(nodeByPoint.get(Point.of(10, 9)), new Cha(team));

        Deque<Piece> sangMaOrder = createSangMaOrder(sangMaOrderCommand, team);
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
}
