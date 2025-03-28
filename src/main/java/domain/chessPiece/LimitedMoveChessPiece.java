package domain.chessPiece;

import domain.direction.Directions;
import domain.path.Path;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import java.util.ArrayList;
import java.util.List;

public abstract class LimitedMoveChessPiece extends JanggiChessPiece {
    private final List<Directions> directions;

    protected LimitedMoveChessPiece(ChessTeam team, List<Directions> directions) {
        super(team);
        this.directions = directions;
    }

    protected LimitedMoveChessPiece(final ChessPosition position, final ChessTeam team,
                                 final List<Directions> directions) {
        super(position, team);
        this.directions = directions;
    }

    @Override
    public final List<Path> getCoordinatePaths(ChessPosition startPosition) {
        List<Path> result = new ArrayList<>();
        for (Directions direction : directions) {
            if (direction.canApplyFrom(startPosition) && canMove(startPosition, direction)) {
                result.add(direction.getPathFrom(startPosition));
            }
        }
        return result;
    }

    protected abstract boolean canMove(final ChessPosition position, final Directions directions);
}
