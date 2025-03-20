package domain;

import java.util.ArrayList;
import java.util.List;

public abstract class LimitedChessPiece extends JanggiChessPiece {
    private final List<Directions> directions;

    protected LimitedChessPiece(ChessPosition position, ChessTeam team, List<Directions> directions) {
        super(position, team);
        this.directions = directions;
    }

    @Override
    protected List<Path> getCoordinatePaths() {
        List<Path> result = new ArrayList<>();
        for (Directions direction : directions) {
            if (direction.canApplyFrom(getPosition())) {
                result.add(direction.getPathFrom(getPosition()));
            }
        }
        return result;
    }

    @Override
    protected abstract List<ChessPosition> getCoordinateDestinations(List<Path> coordinates, ChessPiecePositions positions);

    @Override
    public ChessPieceType getChessPieceType() {
        return null;
    }
}
