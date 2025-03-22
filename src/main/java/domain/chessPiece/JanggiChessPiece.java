package domain.chessPiece;

import domain.position.ChessPiecePositions;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import domain.path.Path;

import java.util.List;

public abstract class JanggiChessPiece implements ChessPiece {

    private final ChessTeam team;

    protected JanggiChessPiece(ChessTeam team) {
        this.team = team;
    }

    @Override
    public List<ChessPosition> getDestinations(ChessPosition startPosition, ChessPiecePositions positions) {
        List<Path> coordinates = getCoordinatePaths(startPosition);
        return getCoordinateDestinations(coordinates, positions);
    }

    protected abstract List<Path> getCoordinatePaths(ChessPosition startPosition);

    protected abstract List<ChessPosition> getCoordinateDestinations(List<Path> coordinates, ChessPiecePositions positions);

    @Override
    public ChessTeam getTeam() {
        return team;
    }
}
