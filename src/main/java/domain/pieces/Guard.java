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

    private static final List<MovingFunction> movements = List.of(
            Piece::north, Piece::south, Piece::west, Piece::east,
            Piece::northEast, Piece::southEast, Piece::northWest, Piece::southWest
    );
    public Guard(Camp camp) {
        super(camp);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardChecker boardChecker) {

        Set<Position> destination = new HashSet<>();

        for (MovingFunction movement : movements) {
            move(from, movement).ifPresent(destination::add);
        }
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
