package model.piece;

import model.Team;
import model.coordinate.PalacePositions;
import model.coordinate.Position;
import model.piece.strategy.reach.GuardKingReach;
import model.piece.strategy.reach.ReachStrategy;

import java.util.List;

public class General extends Piece {

    public General(Team team) {
        super(team, PieceType.GENERAL);
    }

    @Override
    public List<Position> extractPath(Position current, Position next) {
        return List.of();
    }

    @Override
    protected ReachStrategy determineReachStrategy(Position current, Position next) {
        return new GuardKingReach(current, next);
    }
}
