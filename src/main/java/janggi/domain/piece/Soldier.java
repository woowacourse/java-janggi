package janggi.domain.piece;

import janggi.domain.space.Destinations;
import janggi.domain.space.Position;
import janggi.domain.Side;
import janggi.domain.board.BoardReader;
import janggi.domain.move.MovementStrategy;
import java.util.List;

public class Soldier extends Piece {
    public Soldier(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public Destinations findDestinations(Position current, BoardReader board) {
        Destinations baseDestinations = super.findDestinations(current, board);
        List<Position> forwardOnly = baseDestinations.getPositions().stream()
                .filter(target -> isForward(current, target))
                .toList();
        return new Destinations(forwardOnly);
    }

    private boolean isForward(Position current, Position target) {
        if (getSide() == Side.CHO) {
            return target.getY() >= current.getY();
        }
        return target.getY() <= current.getY();
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }
}
