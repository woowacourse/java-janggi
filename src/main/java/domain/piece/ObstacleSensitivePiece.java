package domain.piece;

import domain.board.PieceVisibleBoard;
import domain.piece.character.Team;
import domain.point.Movement;
import domain.point.Path;
import domain.point.Point;
import java.util.List;

public abstract class ObstacleSensitivePiece extends Piece {

    public ObstacleSensitivePiece(Team team) {
        super(team);
    }

    @Override
    protected List<Point> findMovablePoints(final Point source, final PieceVisibleBoard board) {
        return movements().stream()
                .filter(movement -> board.canMoveByPath(source, movement.destinationPath())
                        && !board.matchTeam(board.getPointMovedByPath(source, movement.destinationPath()), team())
                        && !blockedByObstacle(source, movement.obstaclePaths(), board))
                .map(movement -> board.getPointMovedByPath(source, movement.destinationPath()))
                .toList();
    }

    private boolean blockedByObstacle(final Point point, final List<Path> obstaclePaths,
                                      final PieceVisibleBoard board) {
        return obstaclePaths.stream()
                .filter(path -> board.canMoveByPath(point, path))
                .map(path -> board.getPointMovedByPath(point, path))
                .anyMatch(board::existsPiece);
    }

    abstract List<Movement> movements();
}
