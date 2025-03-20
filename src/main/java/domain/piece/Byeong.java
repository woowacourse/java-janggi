package domain.piece;

import domain.board.Board;
import domain.board.Direction;
import domain.board.Edge;
import domain.board.Node;
import java.util.List;
import java.util.Map;

public class Byeong implements Piece {

    private static final List<Direction> choByeongDirections = List.of(Direction.LEFT, Direction.UP_LEFT, Direction.UP,
            Direction.UP_RIGHT, Direction.RIGHT);
    private static final List<Direction> hanByeongDirections = List.of(Direction.LEFT, Direction.DOWN_LEFT,
            Direction.DOWN, Direction.DOWN_RIGHT, Direction.RIGHT);
    private static final Map<Team, List<Direction>> directionsByTeam = Map.ofEntries(
            Map.entry(Team.CHO, choByeongDirections),
            Map.entry(Team.HAN, hanByeongDirections)
    );

    private final Team team;

    public Byeong(Team team) {
        this.team = team;
    }

    @Override
    public boolean canMove(Node source, Node destination, Board board) {
        return findMovableNodes(source, board).contains(destination);
    }

    private List<Node> findMovableNodes(Node currentNode, Board board) {
        return currentNode.edges().stream()
                .filter(edge -> directionsByTeam.get(this.team).contains(edge.direction()))
                .filter(edge -> !board.hasPieceTeamByNode(edge.nextNode(), this.team)
                        || !board.existsPieceByNode(edge.nextNode()))
                .map(Edge::nextNode)
                .toList();
    }

    @Override
    public PieceType type() {
        return PieceType.BYEONG;
    }

    @Override
    public boolean hasTeam(Team team) {
        return this.team == team;
    }
}
