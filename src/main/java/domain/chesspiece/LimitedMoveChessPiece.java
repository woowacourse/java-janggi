package domain.chesspiece;

import domain.direction.Directions;
import domain.hurdlePolicy.HurdlePolicy;
import domain.path.Path;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import java.util.ArrayList;
import java.util.List;

public abstract class LimitedMoveChessPiece extends JanggiChessPiece {
    private final List<Directions> directions;

    protected LimitedMoveChessPiece(final ChessPosition position, final ChessTeam team,
                                    final List<Directions> directions, final HurdlePolicy hurdlePolicy) {
        super(position, team, hurdlePolicy);
        this.directions = directions;
    }

    @Override
    public final List<Path> calculateCoordinatePaths(ChessPosition startPosition) {
        List<Path> result = new ArrayList<>();
        for (Directions direction : directions) {
            if (direction.canApplyFrom(startPosition) && canMoveInCastle(startPosition, direction)) {
                result.add(direction.getPathFrom(startPosition));
            }
        }
        return result;
    }

    protected abstract boolean canMoveInCastle(final ChessPosition position, final Directions directions);
}
