package domain.pieces;

import domain.BoardChecker;
import domain.Camp;
import domain.MovingFunction;
import domain.PieceType;
import domain.Position;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Guard extends Piece {

    public Guard(Camp camp) {
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

    private Set<Position> moveDiagonal(Position from) {
        Set<Position> destination = new HashSet<>();
        for (MovingFunction movement : getDiagonalMovement()) {
            move(from, movement)
                    .filter(Position::isPalaceDiagonalPosition)
                    .ifPresent(destination::add);
        }
        return destination;
    }

    private Set<Position> moveStraight(Position from) {
        Set<Position> destination = new HashSet<>();
        for (MovingFunction movement : getMovements()) {
            move(from, movement)
                    .filter(Position::isInPalace)
                    .ifPresent(destination::add);
        }
        return destination;
    }

    private List<MovingFunction> getDiagonalMovement() {
        return List.of(Piece::northEast, Piece::southEast, Piece::northWest,
                Piece::southWest);
    }

    private List<MovingFunction> getMovements() {
        return List.of(Piece::north, Piece::south,
                Piece::west, Piece::east);
    }

    private Optional<Position> move(Position position, MovingFunction movement) {
        return movement.move(position);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.GUARD;
    }
}
