package domain.piece;

import domain.Board;
import domain.Color;
import domain.Direction;
import domain.Position;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Chariot extends Piece {

    public Chariot(final Position position, final Color color, final Board board) {
        super(position, color, board);
    }

    @Override
    public Set<Position> getMovablePositions() {
        return Direction.getStraightDirection().stream()
                .flatMap(direction -> calculateMovablePositionInDirection(direction, position).stream())
                .collect(Collectors.toSet());
    }

    private Set<Position> calculateMovablePositionInDirection(final Direction direction, final Position position) {
        if (!position.canMove(direction)) {
            return Set.of();
        }
        Position nextPosition = position.move(direction);
        if (board.isSameTeam(this, nextPosition)) {
            return Set.of();
        }
        if (board.isExists(nextPosition)) {
            return Set.of(nextPosition);
        }
        return Stream.concat(
                Stream.of(nextPosition),
                calculateMovablePositionInDirection(direction, nextPosition).stream()
        ).collect(Collectors.toSet());
    }

    @Override
    public String getDisplayName() {
        return "차";
    }
}
