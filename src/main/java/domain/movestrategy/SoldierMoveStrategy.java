package domain.movestrategy;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Delta;
import domain.piece.Team;
import java.util.List;

public class SoldierMoveStrategy implements MoveStrategy {

    private static final List<Delta> CHO_PATHS = List.of(
            Delta.RIGHT, Delta.UP, Delta.LEFT
    );

    private static final List<Delta> HAN_PATHS = List.of(
            Delta.RIGHT, Delta.DOWN, Delta.LEFT
    );

    @Override
    public List<Position> getMovablePositions(final Board board, final Position from) {
        Team team = board.getTeam(from);
        List<Delta> paths = getDeltasBy(team);

        return paths.stream()
                .map(from::move)
                .filter(Position::isInside)
                .filter(position -> board.isEmptyOrOpposite(from, position))
                .toList();
    }

    private List<Delta> getDeltasBy(final Team team) {
        if (team == Team.HAN) {
            return HAN_PATHS;
        }
        return CHO_PATHS;
    }
}
