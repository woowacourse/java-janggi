package domain.piece;

import domain.board.Board;
import domain.board.JumpingMovements;
import domain.board.Movement;
import domain.board.Point;
import java.util.ArrayList;
import java.util.List;

public class Ma implements Piece {

    private final Team team;

    public Ma(Team team) {
        this.team = team;
    }

    @Override
    public boolean canMove(final Point source, final Point destination, final Board board) {
        return findMovablePoints(source, board).contains(destination);
    }

    private List<Point> findMovablePoints(final Point source, final Board board) {
        List<Point> candidates = new ArrayList<>();
        for (Movement movement : JumpingMovements.MA.movements()) {
            if (!canMove(source, movement, board)) {
                continue;
            }
            candidates.add(board.getPointMovedByPath(source, movement.destinationPath()));
        }
        return candidates;
    }

    private boolean canMove(final Point point, final Movement movement, final Board board) {
        if (!board.canMoveByPath(point, movement.destinationPath())) {
            return false;
        }

        Point destinationPoint = board.getPointMovedByPath(point, movement.destinationPath());
        if (board.matchTeam(destinationPoint, this.team)) {
            return false;
        }

        List<Point> obstaclePoints = movement.obstaclePaths().stream()
                .filter(path -> board.canMoveByPath(point, path))
                .map(path -> board.getPointMovedByPath(point, path))
                .toList();
        return obstaclePoints.stream().noneMatch(board::existsPiece);
    }

    @Override
    public PieceType type() {
        return PieceType.MA;
    }

    @Override
    public Team team() {
        return this.team;
    }
}
