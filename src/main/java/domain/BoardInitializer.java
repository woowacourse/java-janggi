package domain;

import domain.piece.Byeong;
import domain.piece.Cha;
import domain.piece.Ma;
import domain.piece.Piece;
import domain.piece.Po;
import domain.piece.Sa;
import domain.piece.Sang;
import domain.piece.Wang;
import view.SangMaOrderCommand;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardInitializer {

    // 노드와 엣지 정보 초기화
    public Map<Point, Node> initializeNodesAndEdges() {
        Map<Point, Node> nodeByPoint = new HashMap<>();

        for (int row = Board.START_ROW_INDEX; row <= Board.END_ROW_INDEX; row++) {
            for (int column = Board.START_COLUMN_INDEX; column <= Board.END_COLUMN_INDEX; column++) {
                Point point = Point.of(row, column);
                Node node = new Node(point, List.of());
                nodeByPoint.put(point, node);
            }
        }

        for (int row = Board.START_ROW_INDEX; row <= Board.END_ROW_INDEX; row++) {
            for (int column = Board.START_COLUMN_INDEX; column <= Board.END_COLUMN_INDEX; column++) {
                Point point = Point.of(row, column);
                Node currentNode = nodeByPoint.get(point);

                for (Direction direction : List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)) {
                    int nextRow = row + direction.deltaRow();
                    int nextColumn = column + direction.deltaColumn();
                    if (!Board.isInRange(nextRow, nextColumn)) {
                        continue;
                    }
                    Node nextNode = nodeByPoint.get(Point.of(nextRow, nextColumn));
                    Edge edge = new Edge(nextNode, direction);
                    currentNode.addEdge(edge);
                }
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

    private void initializePiecePositionByTeam(Team team, List<Point> sangMaPoints, SangMaOrderCommand sangMaOrderCommand,
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
        for(Point point: sangMaPoints) {
            board.put(nodeByPoint.get(point), sangMaOrder.removeFirst());
        }
    }
        
    private Deque<Piece> createSangMaOrder(SangMaOrderCommand sangMaOrderCommand, Team team) {
        List<PieceType> pieceTypes = sangMaOrderCommand.getPieceTypes();
        Deque<Piece> pieces = new ArrayDeque<>();
        for (PieceType pieceType : pieceTypes) {
            Piece piece = createPiece(pieceType, team);
            pieces.addLast(piece);
        }
        return pieces;
    }

    private Piece createPiece(PieceType pieceType, Team team) {
        Piece piece;
        switch (pieceType) {
            case SANG -> piece = new Sang(team);
            case MA -> piece = new Ma(team);
            default -> throw new IllegalArgumentException("존재하지 않는 기물 종류입니다.");
        }
        return piece;
    }
}
