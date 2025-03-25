package domain.piece;

import static domain.board.Path.DOWN_DOWN_LEFT_PATH;
import static domain.board.Path.DOWN_DOWN_RIGHT_PATH;
import static domain.board.Path.DOWN_PATH;
import static domain.board.Path.LEFT_LEFT_DOWN_PATH;
import static domain.board.Path.LEFT_LEFT_UP_PATH;
import static domain.board.Path.LEFT_PATH;
import static domain.board.Path.RIGHT_PATH;
import static domain.board.Path.RIGHT_RIGHT_DOWN_PATH;
import static domain.board.Path.RIGHT_RIGHT_UP_PATH;
import static domain.board.Path.UP_PATH;
import static domain.board.Path.UP_UP_LEFT_PATH;
import static domain.board.Path.UP_UP_RIGHT_PATH;

import domain.board.Board;
import domain.board.Movement;
import domain.board.Point;
import java.util.ArrayList;
import java.util.List;

public class Ma implements PatternMovable {

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
        for (Movement movement : movements()) {
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
    public List<Movement> movements() {
        return List.of(
                new Movement(List.of(UP_PATH), UP_UP_LEFT_PATH),
                new Movement(List.of(UP_PATH), UP_UP_RIGHT_PATH),
                new Movement(List.of(RIGHT_PATH), RIGHT_RIGHT_UP_PATH),
                new Movement(List.of(RIGHT_PATH), RIGHT_RIGHT_DOWN_PATH),
                new Movement(List.of(DOWN_PATH), DOWN_DOWN_RIGHT_PATH),
                new Movement(List.of(DOWN_PATH), DOWN_DOWN_LEFT_PATH),
                new Movement(List.of(LEFT_PATH), LEFT_LEFT_DOWN_PATH),
                new Movement(List.of(LEFT_PATH), LEFT_LEFT_UP_PATH)
        );
    }

    @Override
    public PieceType type() {
        return PieceType.MA;
    }

    @Override
    public Team team() {
        return this.team;
    }

    @Override
    public int score() {
        return 5;
    }
}
