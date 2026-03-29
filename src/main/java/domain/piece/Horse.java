package domain.piece;

import domain.Position;
import domain.Team;
import domain.strategy.HorseStrategy;
import domain.strategy.MoveStrategy;

import java.util.List;

public class Horse extends Piece {

    private final MoveStrategy moveStrategy;

    public Horse(Team team) {
        super(team);
        this.moveStrategy = new HorseStrategy();
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
