package domain.chessPiece;

import domain.position.ChessPiecePositions;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import domain.path.Path;

import java.util.List;

public abstract class JanggiChessPiece implements ChessPiece {

    private final ChessPosition position;
    private final ChessTeam team;

    protected JanggiChessPiece(ChessPosition position, ChessTeam team) {
        this.team = team;
        this.position = position;
    }

    @Override
    public List<ChessPosition> getDestinations(ChessPiecePositions positions) {
        List<Path> coordinates = getCoordinatePaths();
        return getCoordinateDestinations(coordinates, positions);
    }

    protected abstract List<Path> getCoordinatePaths();

    protected abstract List<ChessPosition> getCoordinateDestinations(List<Path> coordinates, ChessPiecePositions positions);

    @Override
    public ChessPosition getPosition() {
        return position;
    }

    @Override
    public ChessTeam getTeam() {
        return team;
    }
}
