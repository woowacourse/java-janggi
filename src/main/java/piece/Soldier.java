package piece;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import board.Board;
import board.Position;

public class Soldier extends Piece {

    public Soldier(final Team team) {
        super(team, PieceType.SOLDIER);
    }

    @Override
    protected Set<Position> getMovablePositions(final Position position, final Board board) {
        Set<Position> movablePositions = new HashSet<>();
        if (position.isPalacePosition()) {
            Set<Position> movablePositionsInPalace =
                    findMovablePositions(Direction.getDiagonalDirection(), position, board)
                            .stream()
                            .filter(Position::isPalacePosition)
                            .collect(Collectors.toSet());
            movablePositions.addAll(movablePositionsInPalace);
        }
        movablePositions.addAll(findMovablePositions(Direction.getStraightDirection(), position, board));
        return movablePositions;
    }

    private Set<Position> findMovablePositions(
            final List<Direction> directions, final Position position, final Board board
    ) {
        return directions.stream()
                .filter(direction -> !getUnmovableDirections().contains(direction))
                .map(position::moveByDirection)
                .filter(movePosition -> isMovable(movePosition, board))
                .collect(Collectors.toSet());
    }

    @Override
    public PieceType getType() {
        return this.pieceType;
    }

    private List<Direction> getUnmovableDirections() {
        if (team == Team.BLUE) {
            return List.of(Direction.BOTTOM, Direction.LEFT_BOTTOM, Direction.RIGHT_BOTTOM);
        }
        return List.of(Direction.TOP, Direction.LEFT_TOP, Direction.RIGHT_TOP);
    }

    private boolean isMovable(final Position position, final Board board) {
        return !board.isExists(position) || !board.isSameTeamPosition(this.team, position);
    }

    private boolean sameUnmovableDirectionMovePositions(final Position startPosition, final Position candiatePosition) {
        return getUnmovableDirections().stream()
                .map(startPosition::moveByDirection)
                .anyMatch(unmovableDirectionPosition -> unmovableDirectionPosition.equals(candiatePosition));
    }

}
