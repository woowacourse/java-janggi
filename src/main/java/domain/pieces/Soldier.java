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

public class Soldier extends Piece {

    private static final List<MovingFunction> hanMovement = List.of(
            Piece::south, Piece::west, Piece::east
    );
    private static final List<MovingFunction> choMovement = List.of(
            Piece::north, Piece::west, Piece::east
    );

    public Soldier(Camp camp) {
        super(camp);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardChecker boardChecker) {

        Set<Position> destination = new HashSet<>();
        if (this.isSameCamp(Camp.HAN)) {
            for (MovingFunction movement : hanMovement) {
                move(from, movement).ifPresent(destination::add);
            }
        }
        if (this.isSameCamp(Camp.CHO)) {
            for (MovingFunction movement : choMovement) {
                move(from, movement).ifPresent(destination::add);
            }
        }
        return destination.contains(to);
    }

    private Optional<Position> move(Position position, MovingFunction movement) {
        return movement.move(position);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.SOLDIER;
    }
}
