package domain.piece;

import domain.Destinations;
import domain.Position;
import domain.Side;
import domain.board.BoardReader;
import domain.strategy.MovementStrategy;
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
