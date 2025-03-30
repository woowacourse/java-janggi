package piece;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import board.Board;
import board.Position;
import piece.movement.PalaceMovement;

public class Soldier extends Piece {

    public Soldier(final Team team) {
        super(team, PieceType.SOLDIER);
    }

    @Override
    protected Set<Position> getMovablePositions(final Position position, final Board board) {
        if (position.isPalacePosition()) {
            return PalaceMovement.applyMovement(position)
                    .stream()
                    .filter(candidatePosition -> isMovable(candidatePosition, board)
                            && !sameUnmovableDirectionMovePositions(position, candidatePosition)
                    ).collect(Collectors.toSet());
        }
        return Direction.getStraightDirection().stream()
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
