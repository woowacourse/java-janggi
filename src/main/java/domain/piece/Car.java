package domain.piece;

import domain.PieceProvider;
import domain.position.Position;
import domain.Team;
import domain.strategy.CarStrategy;

import java.util.List;

public class Car extends MoveablePiece {

    public Car(Team team) {
        super(team, new CarStrategy());
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
