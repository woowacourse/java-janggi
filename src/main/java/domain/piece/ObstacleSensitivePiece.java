package domain.piece;

import domain.Team;
import domain.board.PieceVisibleBoard;
import domain.point.Movement;
import domain.point.Point;
import java.util.List;

public abstract class ObstacleSensitivePiece extends Piece {

    public ObstacleSensitivePiece(Team team) {
        super(team);
    }

    @Override
    public List<Point> findMovablePoints(final Point source, final PieceVisibleBoard board) {
        return movements().stream()
                .filter(movement -> board.canMoveByPath(source, movement.destinationPath())
                        && !board.matchTeam(board.getPointMovedByPath(source, movement.destinationPath()), team())
                        && !blockedByObstacle(source, movement, board))
                .map(movement -> board.getPointMovedByPath(source, movement.destinationPath()))
                .toList();
    }

    private boolean blockedByObstacle(final Point point, final Movement movement, final PieceVisibleBoard board) {
        return movement.obstaclePaths().stream()
                .filter(path -> board.canMoveByPath(point, path))
                .map(path -> board.getPointMovedByPath(point, path))
                .anyMatch(board::existsPiece);
    }

    abstract List<Movement> movements();
}
