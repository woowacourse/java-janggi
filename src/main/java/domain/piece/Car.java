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
        if (moveCandidates.contains(to)) {
            validateTarget(pieceProvider.getPiece(to));
            return true;
        }
        return false;
    }
}
