package domain.piece;

import domain.PieceProvider;
import domain.position.Position;
import domain.Team;
import domain.strategy.PawnStrategy;

import java.util.List;

public class Pawn extends MoveablePiece {

    public Pawn(Team team) {
        super(team, new PawnStrategy());
    }

    @Override
    public boolean canMove(Position from, Position to, PieceProvider pieceProvider) {
        List<Position> moveCandidates = moveStrategy.getMoveCandidates(from, team, pieceProvider);
        boolean isTargetPositionBlank = pieceProvider.isBlank(to);

        for (Position candidatePosition : moveCandidates) {
            if (candidatePosition.equals(to) && isTargetPositionBlank) {
                return true;
            }
        }
        return false;
    }
}
