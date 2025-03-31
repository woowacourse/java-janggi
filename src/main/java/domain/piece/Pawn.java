package domain.piece;

import domain.Position;
import domain.movestrategy.FixedMoveStrategy;
import domain.movestrategy.FixedMoveStrategyChangeable;
import domain.player.Player;
import java.util.List;

public class Pawn extends Piece implements FixedMoveStrategyChangeable {

    private FixedMoveStrategy moveStrategy;

    public Pawn(Player player, FixedMoveStrategy moveStrategy, int point) {
        super(player, point);
        this.moveStrategy = moveStrategy;
    }

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {
        return moveStrategy.calculatePath(startPosition, targetPosition, player);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    @Override
    public void changeStrategy(FixedMoveStrategy fixedMoveStrategy) {
        this.moveStrategy = fixedMoveStrategy;
    }
}
