package domain.piece;

import domain.PieceProvider;
import domain.position.Position;
import domain.Team;
import domain.strategy.CannonStrategy;

import java.util.List;

public class Cannon extends MoveablePiece {

    public Cannon(Team team) {
        super(team, new CannonStrategy());
    }

    @Override
    public boolean canMove(Position from, Position to, PieceProvider pieceProvider) {
        List<Position> moveCandidates = moveStrategy.getMoveCandidates(from, team, pieceProvider);
        for (Position candidatePosition : moveCandidates) {
            if (candidatePosition.equals(to)) {
                return true;
            }
        }
        return false;
    }
}
