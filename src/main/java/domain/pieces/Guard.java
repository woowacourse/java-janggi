package domain.pieces;

import domain.Camp;
import domain.ExistBoard;
import domain.Position;
import java.util.HashSet;
import java.util.Set;

public class Guard extends Piece {

    public Guard(Camp camp) {
        super(camp);
    }

    @Override
    public boolean canMove(Position from, Position to, ExistBoard existBoard) {

        Set<Position> destination = new HashSet<>();

        destination.add(move_L(from));
        destination.add(move_R(from));
        destination.add(move_D(from));
        destination.add(move_U(from));
        destination.add(move_RU(from));
        destination.add(move_RD(from));
        destination.add(move_LU(from));
        destination.add(move_LD(from));

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

    private Position move_RU(Position position) {
        try {
            return rightUpDiagonal(position);
        } catch (IllegalArgumentException e) {
            return position;
        }
    }

    private Position move_RD(Position position) {
        try {
            return rightDownDiagonal(position);
        } catch (IllegalArgumentException e) {
            return position;
        }
    }

    private Position move_LU(Position position) {
        try {
            return leftUpDiagonal(position);
        } catch (IllegalArgumentException e) {
            return position;
        }
    }

    private Position move_LD(Position position) {
        try {
            return leftDownDiagonal(position);
        } catch (IllegalArgumentException e) {
            return position;
        }
    }
}
