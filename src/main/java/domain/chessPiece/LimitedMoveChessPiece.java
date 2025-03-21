package domain.chessPiece;

import domain.direction.Directions;
import domain.path.Path;
import domain.position.ChessPiecePositions;
import domain.position.ChessPosition;
import domain.type.ChessPieceType;
import domain.type.ChessTeam;

import java.util.ArrayList;
import java.util.List;

public abstract class LimitedMoveChessPiece extends JanggiChessPiece {
    private final List<Directions> directions;

    protected LimitedMoveChessPiece(ChessPosition position, ChessTeam team, List<Directions> directions) {
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
    public List<ChessPosition> getCoordinateDestinations(List<Path> coordinates, ChessPiecePositions positions) {
        return coordinates.stream()
                .filter(path -> !existChessPiece(positions, path))
                .map(Path::getDestination)
                .filter(destination -> isAbleToCatch(destination, positions))
                .toList();
    }

    private boolean existChessPiece(final ChessPiecePositions positions, final Path path) {
        List<ChessPosition> pathPositions = path.getPath();
        for (int i = 0; i < pathPositions.size() - 1; i++) {
            ChessPosition currentPosition = pathPositions.get(i);
            if (positions.existChessPieceByPosition(currentPosition)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAbleToCatch(ChessPosition targetPosition, ChessPiecePositions positions) {
        if (!positions.existChessPieceByPosition(targetPosition)) {
            return true;
        }
        ChessPiece targetPiece = positions.getChessPieceByPosition(targetPosition);
        return targetPiece.getTeam() != getTeam();
    }

    @Override
    abstract public ChessPieceType getChessPieceType();
}
