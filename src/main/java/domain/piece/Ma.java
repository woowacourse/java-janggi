package domain.piece;

import static domain.board.Direction.DOWN;
import static domain.board.Direction.LEFT;
import static domain.board.Direction.RIGHT;
import static domain.board.Direction.UP;

import domain.Directions;
import domain.PiecePath;
import domain.board.Board;
import domain.board.Node;
import java.util.ArrayList;
import java.util.List;

public class Ma implements Piece {

    private static final List<PiecePath> PIECE_PATHS = List.of(
            new PiecePath(
                    List.of(new Directions(List.of(UP))), new Directions(List.of(UP, UP, LEFT))
            ),
            new PiecePath(
                    List.of(new Directions(List.of(UP))), new Directions(List.of(UP, UP, RIGHT))
            ),
            new PiecePath(
                    List.of(new Directions(List.of(RIGHT))), new Directions(List.of(RIGHT, RIGHT, UP))
            ),
            new PiecePath(
                    List.of(new Directions(List.of(RIGHT))), new Directions(List.of(RIGHT, RIGHT, DOWN))
            ),
            new PiecePath(
                    List.of(new Directions(List.of(DOWN))), new Directions(List.of(DOWN, DOWN, RIGHT))
            ),
            new PiecePath(
                    List.of(new Directions(List.of(DOWN))), new Directions(List.of(DOWN, DOWN, LEFT))
            ),
            new PiecePath(
                    List.of(new Directions(List.of(LEFT))), new Directions(List.of(LEFT, LEFT, DOWN))
            ),
            new PiecePath(
                    List.of(new Directions(List.of(LEFT))), new Directions(List.of(LEFT, LEFT, UP))
            )
    );

    private final Team team;

    public Ma(Team team) {
        this.team = team;
    }

    @Override
    public boolean canMove(Node source, Node destination, Board board) {
        return findMovableNodes(source, board).contains(destination);
    }

    private List<Node> findMovableNodes(Node sourceNode, Board board) {
        List<Node> candidates = new ArrayList<>();
        for (PiecePath piecePath : PIECE_PATHS) {
            checkObstaclesAndAddCandidate(sourceNode,
                    piecePath.obstaclePaths(), piecePath.destinationPath(),
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
        return PieceType.MA;
    }

    @Override
    public boolean hasTeam(Team team) {
        return this.team == team;
    }
}
