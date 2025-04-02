package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.score.Score;
import domain.score.ScoreCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

    public static final int START_ROW_INDEX = 1;
    public static final int END_ROW_INDEX = 10;
    public static final int START_COLUMN_INDEX = 1;
    public static final int END_COLUMN_INDEX = 9;

    private static final int HAN_PALACE_START_ROW_INDEX = 1;
    private static final int HAN_PALACE_END_ROW_INDEX = 3;
    private static final int PALACE_START_COLUMN_INDEX = 4;
    private static final int PALACE_END_COLUMN_INDEX = 6;
    private static final int CHO_PALACE_START_ROW_INDEX = 8;
    private static final int CHO_PALACE_END_ROW_INDEX = 10;

    private final Map<Node, Piece> board;
    private final Map<Point, Node> nodeByPoint;

    public Board(final Map<Node, Piece> board, final Map<Point, Node> nodeByPoint) {
        this.board = board;
        this.nodeByPoint = nodeByPoint;
    }

    public Node findNodeByPoint(final Point point) {
        if (!nodeByPoint.containsKey(point)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에 노드가 존재하지 않습니다.");
        }
        return nodeByPoint.get(point);
    }

    public boolean hasPieceTeamByNode(final Node node, final Team team) {
        if (!existsPieceByNode(node)) {
            return false;
        }
        Piece piece = findPieceByNode(node);
        return piece.hasTeam(team);
    }

    public boolean existsPieceByNode(final Node node) {
        return board.containsKey(node);
    }

    public void movePiece(final Node sourceNode, final Node destinationNode, final Board board) {
        Piece sourcePiece = findPieceByNode(sourceNode);
        sourcePiece.validateMove(sourceNode, destinationNode, board);
        putPiece(destinationNode, sourcePiece);
        removePieceByNode(sourceNode);
    }

    public Piece findPieceByNode(final Node node) {
        if (!existsPieceByNode(node)) {
            throw new IllegalArgumentException("[ERROR] 해당 노드에 기물이 존재하지 않습니다.");
        }
        return board.get(node);
    }

    public void putPiece(final Node node, final Piece piece) {
        board.put(node, piece);
    }

    public void removePieceByNode(final Node node) {
        if (!existsPieceByNode(node)) {
            return;
        }
        Piece piece = board.get(node);
        board.remove(node, piece);
    }

    public boolean isOpponentWangDead(final Team team) {
        return board.keySet().stream()
                .filter(node -> hasPieceTeamByNode(node, team.inverse()))
                .noneMatch(node -> hasPieceTypeByNode(node, PieceType.WANG));
    }

    public boolean hasPieceTypeByNode(final Node node, final PieceType pieceType) {
        if (!existsPieceByNode(node)) {
            return false;
        }
        Piece piece = board.get(node);
        return piece.type() == pieceType;
    }

    public boolean isPalaceArea(final Node destinationNode) {
        List<Point> points = getPalacePoint();
        List<Node> nodes = points.stream()
                .map(this::findNodeByPoint)
                .toList();
        return nodes.stream()
                .anyMatch(node -> node.isSameNode(destinationNode));
    }

    private List<Point> getPalacePoint() {
        List<Point> points = new ArrayList<>();
        addHanPalacePoints(points);
        addChoPalacePoints(points);
        return points;
    }

    private void addHanPalacePoints(List<Point> points) {
        for (int i = HAN_PALACE_START_ROW_INDEX; i <= HAN_PALACE_END_ROW_INDEX; i++) {
            for (int j = PALACE_START_COLUMN_INDEX; j <= PALACE_END_COLUMN_INDEX; j++) {
                points.add(Point.of(i, j));
            }
        }
    }

    private void addChoPalacePoints(List<Point> points) {
        for (int i = CHO_PALACE_START_ROW_INDEX; i <= CHO_PALACE_END_ROW_INDEX; i++) {
            for (int j = PALACE_START_COLUMN_INDEX; j <= PALACE_END_COLUMN_INDEX; j++) {
                points.add(Point.of(i, j));
            }
        }
    }

    public Map<Team, Score> calculateTotalScoreByTeam(final ScoreCalculator scoreCalculator) {
        List<Point> points = getPoints();
        List<Piece> piecesOfCho = getPiecesByTeam(points, Team.CHO);
        List<Piece> piecesOfHan = getPiecesByTeam(points, Team.HAN);
        return scoreCalculator.calculateTotalScoreByTeam(piecesOfCho, piecesOfHan);
    }

    private List<Point> getPoints() {
        return IntStream.rangeClosed(START_ROW_INDEX, END_ROW_INDEX)
                .boxed()
                .flatMap(i -> IntStream.rangeClosed(START_COLUMN_INDEX, END_COLUMN_INDEX)
                        .mapToObj(j -> Point.of(i, j)))
                .collect(Collectors.toList());
    }

    private List<Piece> getPiecesByTeam(List<Point> points, Team team) {
        return points.stream()
                .map(this::findNodeByPoint)
                .filter(node -> hasPieceTeamByNode(node, team))
                .map(this::findPieceByNode)
                .toList();
    }

    public Map<Point, Piece> currentBoard() {
        return board.keySet().stream()
                .collect(Collectors.toMap(
                        Node::point,
                        board::get
                ));
    }
}
