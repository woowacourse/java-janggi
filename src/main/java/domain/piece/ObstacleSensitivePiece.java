package domain.piece;

import domain.board.Board;
import domain.board.Movement;
import domain.board.Point;
import java.util.ArrayList;
import java.util.List;

public abstract class ObstacleSensitivePiece extends Piece {

    public ObstacleSensitivePiece(Team team) {
        super(team);
    }

    @Override
    public List<Point> findMovablePoints(final Point source, final Board board) {
        List<Point> candidates = new ArrayList<>();
        for (Movement movement : movements()) {
            if (!blockedByObstacle(source, movement, board)) {
                continue;
            }
            candidates.add(board.getPointMovedByPath(source, movement.destinationPath()));
        }
        return candidates;
    }

    private boolean blockedByObstacle(final Point point, final Movement movement, final Board board) {
        if (!board.canMoveByPath(point, movement.destinationPath())) {
            return false;
        }

        Point destinationPoint = board.getPointMovedByPath(point, movement.destinationPath());
        if (board.matchTeam(destinationPoint, team())) {
            return false;
        }

        return movement.obstaclePaths().stream()
                .filter(path -> board.canMoveByPath(point, path))
                .map(path -> board.getPointMovedByPath(point, path))
                .noneMatch(board::existsPiece);
    }

    abstract List<Movement> movements();
}
