package domain.piece;

import domain.Position;
import domain.Team;
import domain.strategy.MoveStrategy;
import domain.strategy.PalaceStrategy;

import java.util.List;

public class King extends Piece {

    private final MoveStrategy moveStrategy;

    public King(Team team) {
        super(team);
        this.moveStrategy = new PalaceStrategy();
    }

    @Override
    public boolean canMove(Position currentPosition, Position targetPosition, PieceProvider pieceProvider) {
        List<Position> moveCandidates = moveStrategy.getMoveCandidates(currentPosition, pieceProvider);
        boolean isTargetPositionBlank = pieceProvider.isBlank(targetPosition);

        for (Position candidatePosition : moveCandidates) {
            if (candidatePosition.equals(targetPosition) && isTargetPositionBlank) {
                return true;
            }
        }
        return false;
    }
}
