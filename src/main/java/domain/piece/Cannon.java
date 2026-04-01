package domain.piece;

import domain.position.Position;
import domain.Team;
import domain.strategy.CannonStrategy;

import domain.strategy.MoveStrategy;
import java.util.List;

public class Cannon extends Piece {

    private final MoveStrategy moveStrategy;

    public Cannon(Team team) {
        super(team);
        moveStrategy = new CannonStrategy();
    }

    @Override
    public boolean canMove(Position currentPosition, Position targetPosition, PieceProvider pieceProvider) {
        List<Position> moveCandidates = moveStrategy.getMoveCandidates(currentPosition, pieceProvider);
        for (Position candidatePosition : moveCandidates) {
            if (candidatePosition.equals(targetPosition)) {
                return true;
            }
        }
        return false;
    }
}
