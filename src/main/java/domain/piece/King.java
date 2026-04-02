package domain.piece;

import domain.PieceProvider;
import domain.position.Position;
import domain.Team;
import domain.strategy.Strategy;
import domain.strategy.PalaceStrategy;

import java.util.List;

public class King extends Piece {

    public King(Team team) {
        super(team, new PalaceStrategy());
    }

    @Override
    public boolean canMove(Position from, Position to, PieceProvider pieceProvider) {
        List<Position> moveCandidates = moveStrategy.getMoveCandidates(from, pieceProvider);
        boolean isTargetPositionBlank = pieceProvider.isBlank(to);
        for (Position candidatePosition : moveCandidates) {
            if (candidatePosition.equals(to) && isTargetPositionBlank) {
                return true;
            }
        }
        return false;
    }
}
