package model.piece;

import model.coordinate.Position;
import model.game.Team;
import model.piece.strategy.GuardKingReach;
import model.piece.strategy.ReachStrategy;

import java.util.List;

public class General extends Piece {

    public General(Team team) {
        super(team, PieceType.GENERAL);
    }

    @Override
    public List<Position> extractPath(Position currentExclusive, Position nextExclusive) {
        return List.of();
    }

    @Override
    protected ReachStrategy determineReachStrategy(Position current, Position next) {
        return new GuardKingReach(current, next);
    }
}
