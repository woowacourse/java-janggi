package domain.piece;

import domain.PieceProvider;
import domain.position.Position;
import domain.Team;
import domain.strategy.ElephantStrategy;
import domain.strategy.Strategy;

import java.util.List;

public class Elephant extends Piece {

    public Elephant(Team team) {
        super(team, new ElephantStrategy());
    }

    @Override
    public boolean canMove(Position from, Position to, Team team, PieceProvider pieceProvider) {
        List<Position> moveCandidates = moveStrategy.getMoveCandidates(from, pieceProvider);
        for (Position candidatePosition : moveCandidates) {
            if (candidatePosition.equals(to)) {
                return true;
            }
        }
        return false;
    }
}
