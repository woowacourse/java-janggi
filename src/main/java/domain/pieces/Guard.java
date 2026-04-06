package domain.pieces;

import domain.Camp;
import domain.BoardChecker;
import domain.MovingFunction;
import domain.PieceType;
import domain.Position;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Guard extends Piece {

    public Guard(Camp camp) {
        super(camp);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardChecker existBoard) {

        Set<Position> destination = new HashSet<>();

        move(from, this::north).ifPresent(destination::add);
        move(from, this::south).ifPresent(destination::add);
        move(from, this::west).ifPresent(destination::add);
        move(from, this::east).ifPresent(destination::add);
        move(from, this::northEast).ifPresent(destination::add);
        move(from, this::southEast).ifPresent(destination::add);
        move(from, this::northWest).ifPresent(destination::add);
        move(from, this::southWest).ifPresent(destination::add);

        return destination.contains(to);
    }

    private Optional<Position> move(Position position, MovingFunction movement) {
        return movement.move(position);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.GUARD;
    }
}
