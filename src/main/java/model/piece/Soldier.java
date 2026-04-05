package model.piece;

import model.coordinate.PalacePositions;
import model.coordinate.Position;
import model.game.Team;
import model.piece.strategy.PalaceSoldierReach;
import model.piece.strategy.ReachStrategy;
import model.piece.strategy.SoldierReach;

import java.util.List;

public class Soldier extends Piece {

    private static final int CHO_FORWARD = -1;
    private static final int HAN_FORWARD = 1;

    private final ReachStrategy defaultReach;
    private final ReachStrategy palaceReach;

    public Soldier(Team team) {
        super(team, PieceType.SOLDIER);
        int forward = resolveForward(team);
        this.defaultReach = new SoldierReach(forward);
        this.palaceReach = new PalaceSoldierReach(forward);
    }

    private static int resolveForward(Team team) {
        if (team == Team.CHO) {
            return CHO_FORWARD;
        }
        return HAN_FORWARD;
    }

    @Override
    public List<Position> extractPath(Position currentExclusive, Position nextExclusive) {
        return List.of();
    }

    @Override
    protected ReachStrategy determineReachStrategy(Position current, Position next) {
        if (PalacePositions.onPalaceDiagonal(current) && PalacePositions.onPalaceDiagonal(next)) {
            return palaceReach;
        }
        return defaultReach;
    }
}
