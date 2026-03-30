package domain.pieces;

import domain.Camp;
import domain.ExistBoard;
import domain.PieceType;
import domain.Position;
import java.util.HashSet;
import java.util.Set;

public class Soldier extends Piece {

    private final PieceType pieceType = PieceType.SOLDIER;

    public Soldier(Camp camp) {
        super(camp);
    }

    @Override
    public boolean canMove(Position from, Position to, ExistBoard existBoard) {

        Set<Position> destination = new HashSet<>();

        destination.add(move_L(from));
        destination.add(move_R(from));

        if (this.isSameCamp(Camp.HAN)) {
            destination.add(move_D(from));
        }

        if (this.isSameCamp(Camp.CHO)) {
            destination.add(move_U(from));
        }

        return destination.contains(to);
    }

    private Position move_U(Position position) {
        try {
            return up(position);
        } catch (IllegalArgumentException e) {
            return position;
        }
    }

    private Position move_L(Position position) {
        try {
            return left(position);
        } catch (IllegalArgumentException e) {
            return position;
        }
    }

    private Position move_R(Position position) {
        try {
            return right(position);
        } catch (IllegalArgumentException e) {
            return position;
        }
    }

    private Position move_D(Position position) {
        try {
            return down(position);
        } catch (IllegalArgumentException e) {
            return position;
        }
    }

    @Override
    public PieceType getPieceType() {
        return this.pieceType;
    }
}
