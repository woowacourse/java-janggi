package domain.movestrategy;

import domain.board.Board;
import domain.board.Position;
import domain.board.Direction;
import domain.piece.Team;
import java.util.List;

public class SoldierMoveStrategy implements MoveStrategy {

    private static final List<Direction> CHO_PATHS = List.of(
            Direction.RIGHT, Direction.UP, Direction.LEFT
    );

    private static final List<Direction> HAN_PATHS = List.of(
            Direction.RIGHT, Direction.DOWN, Direction.LEFT
    );

    @Override
    public List<Position> getMovablePositions(final Board board, final Position from) {
        Team team = board.getTeam(from);
        List<Direction> paths = getPathsBy(team);

        return paths.stream()
                .map(from::move)
                .filter(Position::isInsideBoard)
                .filter(position -> board.isEmptyOrOpposite(from, position))
                .toList();
    }

    private List<Direction> getPathsBy(final Team team) {
        if (team == Team.HAN) {
            return HAN_PATHS;
        }
        return CHO_PATHS;
    }
}
