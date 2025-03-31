package domain.piece;

import domain.Position;
import domain.movestrategy.RangeMoveStrategy;
import domain.movestrategy.RangeMoveStrategyChangeable;
import domain.player.Player;
import java.util.List;

public class Cannon extends Piece implements RangeMoveStrategyChangeable {

    private RangeMoveStrategy moveStrategy;

    public Cannon(Player player, RangeMoveStrategy moveStrategy, int point) {
        super(player, point);
        this.moveStrategy = moveStrategy;
    }

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {
        return moveStrategy.calculatePath(startPosition, targetPosition);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CANNON;
    }

    @Override
    public void changeStrategy(RangeMoveStrategy rangeMoveStrategy) {
        this.moveStrategy = rangeMoveStrategy;
    }
}
