package domain.piece;

import domain.Directions;
import domain.Movement;
import domain.board.Board;
import domain.board.FixedMovePattern;
import domain.board.Node;
import java.util.ArrayList;
import java.util.List;

public class Sang implements Piece {

    private final Team team;

    public Sang(Team team) {
        this.team = team;
    }

    @Override
    public boolean canMove(Node source, Node destination, Board board) {
        return findMovableNodes(source, board).contains(destination);
    }

    private List<Node> findMovableNodes(Node sourceNode, Board board) {
        List<Node> candidates = new ArrayList<>();
        for (Movement movement : FixedMovePattern.SANG_MOVEMENTS.movements()) {
            checkObstaclesAndAddCandidate(sourceNode,
                    movement.obstaclePaths(), movement.destinationPath(),
                    candidates, board);
        }
        return candidates;
    }

    private void checkObstaclesAndAddCandidate(Node sourceNode,
                                               List<Directions> obstaclePaths, Directions destinationPath,
                                               List<Node> candidates, final Board board) {
        if (!sourceNode.canMoveByPath(destinationPath)) {
            return;
        }

        Node destinationNode = sourceNode.moveByPath(destinationPath);
        if (board.hasPieceTeamByNode(destinationNode, this.team)) {
            return;
        }

        List<Node> obstacleNodes = obstaclePaths.stream()
                .filter(sourceNode::canMoveByPath)
                .map(sourceNode::moveByPath)
                .toList();

        if (obstacleNodes.stream().anyMatch(board::existsPieceByNode)) {
            return;
        }
        candidates.add(destinationNode);
    }

    @Override
    public PieceType type() {
        return PieceType.SANG;
    }

    @Override
    public boolean hasTeam(Team team) {
        return this.team == team;
    }
}
