package domain.pieces;

import domain.BoardChecker;
import domain.Camp;
import domain.MovingFunction;
import domain.PieceType;
import domain.Position;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Soldier extends Piece {

    public Soldier(Camp camp) {
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

    private Set<Position> moveStraight(Position from) {
        Set<Position> destination = new HashSet<>();
        for (MovingFunction movement : getMovements()) {
            move(from, movement).ifPresent(destination::add);
        }
        return destination;
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


    private List<MovingFunction> getDiagonalMovement() {
        List<MovingFunction> movements = new ArrayList<>();
        if (this.isSameCamp(Camp.HAN)) {
            movements = List.of(Piece::southWest, Piece::southEast);
        }
        if (this.isSameCamp(Camp.CHO)) {
            movements = List.of(Piece::northWest, Piece::northEast);
        }
        return movements;
    }

    private List<MovingFunction> getMovements() {
        List<MovingFunction> movements = new ArrayList<>();
        if (this.isSameCamp(Camp.HAN)) {
            movements = List.of(
                    Piece::south, Piece::west, Piece::east
            );
        }
        if (this.isSameCamp(Camp.CHO)) {
            movements = List.of(
                    Piece::north, Piece::west, Piece::east
            );
        }
        return movements;
    }

    private Optional<Position> move(Position position, MovingFunction movement) {
        return movement.move(position);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.SOLDIER;
    }
}
