package domain.piece;

import domain.PieceMovement;
import domain.PiecePath;
import domain.PieceType;
import domain.board.Board;
import domain.board.Direction;
import domain.board.Node;
import java.util.ArrayList;
import java.util.List;

public class Ma implements Piece {

    private static final List<PieceMovement> PIECE_MOVEMENTS = List.of(
            new PieceMovement(
                    List.of(new PiecePath(List.of(Direction.UP))),
                    new PiecePath(List.of(Direction.UP, Direction.UP, Direction.LEFT))
            ),
            new PieceMovement(
                    List.of(new PiecePath(List.of(Direction.UP))),
                    new PiecePath(List.of(Direction.UP, Direction.UP, Direction.RIGHT))
            ),
            new PieceMovement(
                    List.of(new PiecePath(List.of(Direction.RIGHT))),
                    new PiecePath(List.of(Direction.RIGHT, Direction.RIGHT, Direction.UP))
            ),
            new PieceMovement(
                    List.of(new PiecePath(List.of(Direction.RIGHT))),
                    new PiecePath(List.of(Direction.RIGHT, Direction.RIGHT, Direction.DOWN))
            ),
            new PieceMovement(
                    List.of(new PiecePath(List.of(Direction.DOWN))),
                    new PiecePath(List.of(Direction.DOWN, Direction.DOWN, Direction.RIGHT))
            ),
            new PieceMovement(
                    List.of(new PiecePath(List.of(Direction.DOWN))),
                    new PiecePath(List.of(Direction.DOWN, Direction.DOWN, Direction.LEFT))
            ),
            new PieceMovement(
                    List.of(new PiecePath(List.of(Direction.LEFT))),
                    new PiecePath(List.of(Direction.LEFT, Direction.LEFT, Direction.DOWN))
            ),
            new PieceMovement(
                    List.of(new PiecePath(List.of(Direction.LEFT))),
                    new PiecePath(List.of(Direction.LEFT, Direction.LEFT, Direction.UP))
            )
    );
    private final Team team;

    public Ma(Team team) {
        this.team = team;
    }

    @Override
    public boolean canMove(Team team, Node source, Node destination, Board board) {
        return findMovableNodes(team, source, board).contains(destination);
    }

    private List<Node> findMovableNodes(Team team, Node startNode, Board board) {
        List<Node> candidates = new ArrayList<>();
        for (PieceMovement pathToDestination : PIECE_MOVEMENTS) {
            checkAndAddCandidate(team, startNode,
                    pathToDestination.obstaclePaths(), pathToDestination.destinationPath(),
                    candidates, board);
        }
        return candidates;
    }

    private void checkAndAddCandidate(Team team, Node startNode,
                                      List<PiecePath> obstaclePaths, PiecePath destinationPath,
                                      List<Node> candidates, final Board board) {
        if (!startNode.existsFollowingNode(destinationPath.directions())) {
            return;
        }
        Node destinationNode = startNode.followingNode(destinationPath.directions());

        List<Node> obstacleNodes = new ArrayList<>();
        for (PiecePath obstaclePath : obstaclePaths) {
            if (!startNode.existsFollowingNode(obstaclePath.directions())) {
                continue;
            }
            Node obstacle = startNode.followingNode(obstaclePath.directions());

            obstacleNodes.add(obstacle);
        }

        for (Node obstacleNode : obstacleNodes) {
            if (board.existsPiece(obstacleNode)) {
                return;
            }
        }
        if (board.existsPieceByTeam(destinationNode, team)) {
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
