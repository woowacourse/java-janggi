package janggi.piece;

import janggi.movement.Direction;
import janggi.movement.Movement;
import janggi.position.Position;

import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Chariot implements Piece {

    private final Movement movement = Movement.CHARIOT;
    private final Position position;

    public Chariot(Position position) {
        validatePositionRange(position);
        this.position = position;
    }

    @Override
    public void move() {

    }

    @Override
    public List<Position> checkPossibleMoves() {
//        List<Position> possibleMoves = new ArrayList<>();
//        for (Direction direction : movement.getDirections()) {
//          List<Position> possiblePositions = direction.plusOffsetToAllDirections(position);
//          for (Position piecePosition : possiblePositions) {
//              Optional<Position> okayPosition = checkOutOfBoundsPosition(piecePosition);
//              possibleMoves.add(okayPosition.get());
//          }
//        }
//        return possibleMoves;
        return movement.getDirections()
                .stream()
                .map(direction ->
                        {
                            List<Position> positions = direction.testPlusOffset(position);
                            List<Optional<Position>> optionalPositions = new ArrayList<>();
                            for (Position position : positions) {
                               Optional<Position> optionalPosition = makePositionWithOptional(position);
                               optionalPositions.add(optionalPosition);
                            }
                            return optionalPositions;
                        }
                )
                .flatMap(List::stream)
                .flatMap(Optional::stream)
                .toList();
    }
}
