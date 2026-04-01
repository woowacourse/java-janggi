package domain.piece;

import domain.PieceProvider;
import domain.position.Position;
import domain.Team;
import domain.strategy.CarStrategy;
import domain.strategy.Strategy;

import java.util.List;

public class Car extends Piece {
    private final Strategy moveStrategy;

    public Car(Team team) {
        super(team);
        this.moveStrategy = new CarStrategy();
    }

    @Override
    public boolean canMove(Position from, Position to, PieceProvider pieceProvider) {
        List<Position> moveCandidates = moveStrategy.getMoveCandidates(from, pieceProvider);

        for (Position candidatePosition : moveCandidates) {
            if (candidatePosition.equals(to)) {
                return true;
            }
        }
        return false;
    }
}
