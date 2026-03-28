package domain.piece;

import domain.Position;
import domain.Team;
import domain.strategy.CarStrategy;
import domain.strategy.MoveStrategy;

import java.util.List;

public class Chariot extends Piece {
    private final MoveStrategy moveStrategy;

    public Chariot(Team team) {
        super(team);
        this.moveStrategy = new CarStrategy();
    }

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
