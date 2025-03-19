package domain.piece;

import domain.PieceType;
import domain.board.Board;
import domain.board.Direction;
import domain.board.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Byeong implements Piece {

    private static final List<Direction> choByeongDirections = List.of(Direction.LEFT, Direction.UP_LEFT, Direction.UP, Direction.UP_RIGHT, Direction.RIGHT);
    private static final List<Direction> hanByeongDirections = List.of(Direction.LEFT, Direction.DOWN_LEFT, Direction.DOWN, Direction.DOWN_RIGHT, Direction.RIGHT);
    private static final Map<Team, List<Direction>> directionsByTeam = Map.ofEntries(
            Map.entry(Team.CHO, choByeongDirections),
            Map.entry(Team.HAN, hanByeongDirections)
    );

    private final Team team;

    public Byeong(Team team) {
        this.team = team;
    }

    @Override
    public boolean canMove(Team team, Node source, Node destination, Board board) {
        return findMovableNodes(team, source, board).contains(destination);
    }

    private List<Node> findMovableNodes(Team team, Node currentNode, Board board) {
        List<Node> candidates = new ArrayList<>();
        currentNode.edges().stream()
                .filter(edge -> directionsByTeam.get(team).contains(edge.direction()))
                .filter(edge -> !board.existsPieceByTeam(edge.nextNode(), team) || !board.existsPiece(edge.nextNode()))
                .forEach(edge -> candidates.add(edge.nextNode()));

        return candidates;
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
