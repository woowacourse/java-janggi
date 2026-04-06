package domain.pieces;

import domain.BoardChecker;
import domain.Camp;
import domain.MovingFunction;
import domain.PieceType;
import domain.Position;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Soldier extends Piece {

    public Soldier(Camp camp) {
        super(camp);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardChecker boardChecker) {

        Set<Position> destination = new HashSet<>();

        moveSoldier(from, this::west).ifPresent(destination::add);
        moveSoldier(from, this::east).ifPresent(destination::add);

        if (this.isSameCamp(Camp.HAN)) {
            moveSoldier(from, this::south).ifPresent(destination::add);
        }
        if (this.isSameCamp(Camp.CHO)) {
            moveSoldier(from, this::north).ifPresent(destination::add);
        }

        return destination.contains(to);
    }

    private Optional<Position> moveSoldier(Position position, MovingFunction movement) {
        return movement.move(position);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.SOLDIER;
    }
}
