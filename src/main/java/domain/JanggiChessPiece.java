package domain;

import java.util.List;

public abstract class JanggiChessPiece implements ChessPiece {

    private final ChessPosition position;
    private final ChessTeam team;

    protected JanggiChessPiece(ChessPosition position, ChessTeam team) {
        this.team = team;
        this.position = position;
    }

    @Override
    public List<ChessPosition> getDestinations() {
        List<Path> coordinates = getCoordinatePaths();
        return getCoordinateDestinations(coordinates);
    }

    abstract protected List<Path> getCoordinatePaths();

    abstract protected List<ChessPosition> getCoordinateDestinations(List<Path> coordinates);

    @Override
    public ChessPosition getPosition() {
        return position;
    }
}
