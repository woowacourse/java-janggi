package domain.piece;

import domain.PieceProvider;
import domain.position.Position;
import domain.Team;
import domain.strategy.PalaceStrategy;

import java.util.List;

public class King extends MoveablePiece {

    public King(Team team) {
        super(team, new PalaceStrategy());
    }

    @Override
    public boolean canMove(Position from, Position to, PieceProvider pieceProvider) {
        List<Position> moveCandidates = moveStrategy.getMoveCandidates(from, team, pieceProvider);
        if (moveCandidates.contains(to)) {
            validateTarget(pieceProvider.getPiece(to));
            return true;
        }
        return false;
    }
}
