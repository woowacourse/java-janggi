package domain.pieces;

import domain.BoardChecker;
import domain.Camp;
import domain.MovingFunction;
import domain.PieceType;
import domain.Position;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class General extends Piece {

    public General(Camp camp) {
        super(camp);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardChecker boardChecker) {
        Set<Position> destination = new HashSet<>(moveStraight(from));
        if (from.isPalaceDiagonalPosition()) {
            destination.addAll(moveDiagonal(from));
        }
        return destination.contains(to);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.GENERAL;
    }

    private Set<Position> moveStraight(Position from) {
        Set<Position> destination = new HashSet<>();
        for (MovingFunction movement : MOVEMENTS) {
            move(from, movement).filter(Position::isInPalace).ifPresent(destination::add);
        }
        return destination;
    }

    private Set<Position> moveDiagonal(Position from) {
        Set<Position> destination = new HashSet<>();
        for (MovingFunction movement : DIAGONAL_MOVEMENTS) {
            move(from, movement).filter(Position::isPalaceDiagonalPosition)
                    .ifPresent(destination::add);
        }
        return destination;
    }

    private Optional<Position> move(Position position, MovingFunction movement) {
        return movement.move(position);
    }
}
