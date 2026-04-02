package model.piece;

import model.Team;
import model.coordinate.PalacePositions;
import model.coordinate.Position;
import model.piece.strategy.reach.GuardKingReach;
import model.piece.strategy.reach.ReachStrategy;

import java.util.List;

public class General extends Piece {

    private static final ReachStrategy DEFAULT = new GuardKingReach();

    public General(Team team) {
        super(team, PieceType.GENERAL);
    }

    @Override
    public List<Position> extractPath(Position current, Position next) {
        return List.of();
    }

    @Override
    protected ReachStrategy determineReachStrategy(Position current, Position next) {
        if (isMovableOnPalace(current, next)) {
            return DEFAULT;
        }
        return (r, c) -> false;
    }

    private static boolean isMovableOnPalace(Position current, Position next) {
        return isMovementInPalace(current, next) && notOnPalaceDiagonal(current, next);
    }

    private static boolean isMovementInPalace(Position current, Position next) {
        return PalacePositions.inPalace(current) && PalacePositions.inPalace(next);
    }

    private static boolean notOnPalaceDiagonal(Position current, Position next) {
        return PalacePositions.onPalaceDiagonal(current) || PalacePositions.onPalaceDiagonal(next);
    }
}
