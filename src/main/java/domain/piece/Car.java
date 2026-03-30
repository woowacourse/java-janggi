package domain.piece;

import domain.position.Position;
import domain.Team;
import domain.strategy.CarStrategy;
import domain.strategy.MoveStrategy;

import java.util.List;

public class Car extends Piece {
    private final MoveStrategy moveStrategy;

    public Car(Team team) {
        super(team);
        this.moveStrategy = new CarStrategy();
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
