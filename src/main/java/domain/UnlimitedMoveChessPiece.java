package domain;

import java.util.ArrayList;
import java.util.List;

public abstract class UnlimitedMoveChessPiece extends JanggiChessPiece {
    private final List<Direction> directions;

    protected UnlimitedMoveChessPiece(ChessPosition position, ChessTeam team, List<Direction> directions) {
        super(position, team);
        this.directions = directions;
    }

    @Override
    protected List<Path> getCoordinatePaths() {
        final List<Path> paths = new ArrayList<>();
        for (Direction direction : directions) {
            List<ChessPosition> boundaryPositions = getBoundaryPositions(direction);
            if (!boundaryPositions.isEmpty()) {
                paths.add(new Path(boundaryPositions));
            }
        }
        return paths;
    }

    private List<ChessPosition> getBoundaryPositions(Direction direction) {
        final List<ChessPosition> chessPositions = new ArrayList<>();
        for (int distance = 1; distance <= 9; distance++) {
            final int nextRow = getPosition().row() + direction.dr * distance;
            final int nextColumn = getPosition().column() + direction.dc * distance;
            if (ChessPosition.isValid(nextRow, nextColumn)) {
                chessPositions.add(new ChessPosition(nextRow, nextColumn));
            }
        }
        return chessPositions;
    }

    @Override
    protected abstract List<ChessPosition> getCoordinateDestinations(
            List<Path> coordinates, ChessPiecePositions chessPiecePositions
    );
}
