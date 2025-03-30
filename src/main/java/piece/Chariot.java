package piece;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import board.Board;
import board.Position;
import piece.movement.PalaceMovement;

public class Chariot extends Piece {

    public Chariot(final Team team) {
        super(team, PieceType.CHARIOT);
    }

    @Override
    public Set<Position> getMovablePositions(final Position position, final Board board) {
        Set<Position> movablePositions = new HashSet<>();
        if (position.isPalacePosition() && PalaceMovement.hasDiagonalDirectionPosition(position)) {
            movablePositions.addAll(findMovablePositionInPalace(position, board));
        }
        for (Direction straightDirection : Direction.getStraightDirection()) {
            movablePositions.addAll(findMovablePositionInDirection(position, straightDirection, board));
        }
        return movablePositions;
    }

    private Set<Position> findMovablePositionInPalace(final Position position, final Board board) {
        Set<Position> movablePositionsInDiagonalDirections = new HashSet<>();
        List<Direction> diagonalDirections = PalaceMovement.getMatchedDiagonalDirections(position);
        for (Direction direction : diagonalDirections) {
            movablePositionsInDiagonalDirections.addAll(
                    findMovablePositionInDirection(position, direction, board)
                            .stream()
                            .filter(Position::isPalacePosition)
                            .collect(Collectors.toSet()));
        }
        return movablePositionsInDiagonalDirections;
    }

    private Set<Position> findMovablePositionInDirection(
            final Position position, final Direction direction, final Board board
    ) {
        Set<Position> movablePositionsInDirection = new HashSet<>();
        Position movePosition = position.moveByDirection(direction);
        while (!isBlockedPosition(board, movePosition)) {
            movablePositionsInDirection.add(movePosition);
            if (board.isExists(movePosition)) {
                break;
            }
            movePosition = movePosition.moveByDirection(direction);
        }
        return movablePositionsInDirection;
    }

    @Override
    public PieceType getType() {
        return this.pieceType;
    }

    private boolean isBlockedPosition(final Board board, final Position position) {
        return position.isInValidPosition() || board.isSameTeamPosition(team, position);
    }

}
