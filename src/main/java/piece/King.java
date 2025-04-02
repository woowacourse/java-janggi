package piece;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import board.Board;
import board.Position;

public class King extends Piece {

    public King(final Team team) {
        super(team, PieceType.KING);
    }

    @Override
    protected Set<Position> getMovablePositions(final Position startPosition, final Board board) {
        if (!startPosition.isPalacePosition()) {
            throw new IllegalStateException("궁은 궁성 영역 밖에 존재할 수 없습니다.");
        }
        Set<Position> movablePositions = new HashSet<>();
        if (startPosition.hasDiagonalDirectionInPosition()) {
            movablePositions.addAll(findMovablePositions(Direction.getDiagonalDirection(), startPosition, board));
        }
        movablePositions.addAll(findMovablePositions(Direction.getStraightDirection(), startPosition, board));
        return movablePositions;
    }

    private Set<Position> findMovablePositions(
            final List<Direction> directions, final Position startPosition, final Board board
    ) {
        return directions.stream()
                .map(startPosition::moveByDirection)
                .filter(movedPosition -> isMovable(movedPosition, board))
                .collect(Collectors.toSet());
    }

    @Override
    public PieceType getType() {
        return this.pieceType;
    }

    private boolean isMovable(final Position position, final Board board) {
        return position.isPalacePosition() && !board.isSameTeamPosition(this.team, position);
    }

}
