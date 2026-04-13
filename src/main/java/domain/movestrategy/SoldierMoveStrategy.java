package domain.movestrategy;

import domain.board.Board;
import domain.board.Direction;
import domain.board.Position;
import domain.piece.Team;
import java.util.List;
import java.util.stream.Stream;

public class SoldierMoveStrategy implements MoveStrategy {

    private static final List<Direction> CHO_PATHS = List.of(
            Direction.RIGHT, Direction.UP, Direction.LEFT
    );

    private static final List<Direction> HAN_PATHS = List.of(
            Direction.RIGHT, Direction.DOWN, Direction.LEFT
    );

    private static final List<Direction> CHO_PALACE_DIAGONALS = List.of(
            Direction.LEFT_UP, Direction.RIGHT_UP
    );

    private static final List<Direction> HAN_PALACE_DIAGONALS = List.of(
            Direction.LEFT_DOWN, Direction.RIGHT_DOWN
    );

    @Override
    public List<Position> getMovablePositions(final Board board, final Position from) {
        Team team = board.getTeam(from);

        Stream<Direction> directions = getPathsBy(team).stream();

        Stream<Direction> diagonals = getPalaceDiagonalPathsBy(team).stream()
                .filter(direction -> from.isDiagonalConnected(from.move(direction)));

        return Stream.concat(directions, diagonals)
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

    private List<Direction> getPalaceDiagonalPathsBy(final Team team) {
        if (team == Team.HAN) {
            return HAN_PALACE_DIAGONALS;
        }
        return CHO_PALACE_DIAGONALS;
    }
}
