package domain.piece;

import domain.Position;
import domain.Team;
import domain.strategy.CannonStrategy;

import java.util.List;

public class Cannon extends Piece {

    private final CannonStrategy cannonStrategy;

    public Cannon(Team team) {
        super(team);
        cannonStrategy = new CannonStrategy();
    }

    public boolean canMove(Position currentPosition, Position targetPosition, PieceProvider pieceProvider) {
        List<Position> moveCandidates = cannonStrategy.getMoveCandidates(currentPosition, pieceProvider);
        for (Position candidatePosition : moveCandidates) {
            if (candidatePosition.equals(targetPosition)) {
                return true;
            }
        }
        return false;
    }
}
