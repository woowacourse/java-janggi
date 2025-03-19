package janggi.piece;

import janggi.movement.Movement;
import janggi.position.Position;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Elephant implements Piece{

    private final Movement movement = Movement.ELEPHANT;
    private final Position position;

    public Elephant(Position position) {
        validatePositionRange(position);
        this.position = position;
    }

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

    @Override
    public Map<Position, Boolean> isAlreadyLocatedWithinOneSpaceOrAtThatSpace(List<Position> possiblePositions) {
        return Map.of();
    }

    @Override
    public boolean isOneSpaceAway(Position piecePosition, Position possiblePosition) { //piecePosition
        if (piecePosition.getX() == possiblePosition)
//        this.position.getX() + 1
        return false;
    }

    @Override
    public boolean isAtThatSpace(Position piecePosition, Position possiblePosition) {
        return Piece.super.isAtThatSpace(piecePosition, possiblePosition);
    }

//    public List<Position> removeUnMoveableFirstStep(Elephant selectPiece) {
//        List<Position> possibleMoves = selectPiece.checkPossibleMoves();
//        if (selectPiece.position.getX() + 1 == this.position.getX()) {
//            possibleMoves.stream()
//                    .filter(
//                            position -> {
//                                return !(position.getX() - selectPiece.position.getX() == 2);
//                            }
//
//                    )
//        }
//        return null;
//    }
}
