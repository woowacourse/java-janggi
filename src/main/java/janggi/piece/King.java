package janggi.piece;

import janggi.movement.Movement;
import janggi.position.Position;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class King implements Piece {

    private final Movement movement = Movement.KING;
    private final Position position;

    public King(Position position) {
        validatePositionRange(position);
        this.position = position;
    }

    // todo 2
    @Override
    public Map<Position, Boolean> isAlreadyLocatedWithinOneSpaceOrAtThatSpace(Piece selectedPiece) {
        return null;
/*        return possiblePositions.stream()
                    .collect(Collectors.toMap(
                            possiblePosition -> possiblePosition,  // 키: 해당 Position
                            possiblePosition -> isOneSpaceAway(this.position, possiblePosition)
                                    || isAtThatSpace(this.position, possiblePosition) // 값: 한 칸 차이 여부
                    ));*/
    }

    @Override
    public boolean isOneSpaceAway(Position piecePosition, Position possiblePosition) {
        return false;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean canMoveFirstStep(Piece seletedPiece) {
        return false;
    }

    @Override
    public boolean isAtThatSpace(Position piecePosition, Position possiblePosition) {
        return Piece.super.isAtThatSpace(piecePosition, possiblePosition);
    }

    /*@Override
    public boolean isOneSpaceAway(Position piecePosition, Position possiblePosition, Piece selectedPiece) {
*//*        // 상, 마인 경우 상=-2, 마=-1을 뺀 위치를 고려해야 한다!! 처음 직선 1칸에서의 위치를 고려해야 하므로.
        // 차...
        // 병졸...
        int dx = Math.abs(piecePosition.getX() - possiblePosition.getX());
        int dy = Math.abs(piecePosition.getY() - possiblePosition.getY());
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);*//*
        return false;
    }
*/
    @Override
    public void move() {

    }

    @Override
    public List<Position> checkPossibleMoves() {
        return movement.getDirections()
                .stream()
                .map(direction ->
                        {
                            Position position = direction.plusOffsetToPosition(this.position);
                            return makePositionWithOptional(position);
                        }
                )
                .flatMap(Optional::stream)
                .toList();
    }
}
