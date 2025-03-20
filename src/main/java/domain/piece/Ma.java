package domain.piece;

import static domain.board.Direction.DOWN;
import static domain.board.Direction.LEFT;
import static domain.board.Direction.RIGHT;
import static domain.board.Direction.UP;

import domain.PieceMovement;
import domain.PiecePath;
import domain.board.Board;
import domain.board.Node;
import java.util.ArrayList;
import java.util.List;

public class Ma implements Piece {

    private static final List<PieceMovement> PIECE_MOVEMENTS = List.of(
            new PieceMovement(
                    List.of(new PiecePath(List.of(UP))), new PiecePath(List.of(UP, UP, LEFT))
            ),
            new PieceMovement(
                    List.of(new PiecePath(List.of(UP))), new PiecePath(List.of(UP, UP, RIGHT))
            ),
            new PieceMovement(
                    List.of(new PiecePath(List.of(RIGHT))), new PiecePath(List.of(RIGHT, RIGHT, UP))
            ),
            new PieceMovement(
                    List.of(new PiecePath(List.of(RIGHT))), new PiecePath(List.of(RIGHT, RIGHT, DOWN))
            ),
            new PieceMovement(
                    List.of(new PiecePath(List.of(DOWN))), new PiecePath(List.of(DOWN, DOWN, RIGHT))
            ),
            new PieceMovement(
                    List.of(new PiecePath(List.of(DOWN))), new PiecePath(List.of(DOWN, DOWN, LEFT))
            ),
            new PieceMovement(
                    List.of(new PiecePath(List.of(LEFT))), new PiecePath(List.of(LEFT, LEFT, DOWN))
            ),
            new PieceMovement(
                    List.of(new PiecePath(List.of(LEFT))), new PiecePath(List.of(LEFT, LEFT, UP))
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
        for (PieceMovement pieceMovement : PIECE_MOVEMENTS) {
            checkObstaclesAndAddCandidate(sourceNode,
                    pieceMovement.obstaclePaths(), pieceMovement.destinationPath(),
                    candidates, board);
        }
        return candidates;
    }

    private void checkObstaclesAndAddCandidate(Node sourceNode,
                                               List<PiecePath> obstaclePaths, PiecePath destinationPath,
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
