package domain.chessPiece;

import domain.direction.Direction;
import domain.path.Path;
import domain.position.ChessPosition;
import domain.type.ChessTeam;

import java.util.ArrayList;
import java.util.List;

public abstract class UnlimitedMoveChessPiece extends JanggiChessPiece {
    private final List<Direction> directions;

    protected UnlimitedMoveChessPiece(ChessTeam team, List<Direction> directions) {
        super(team);
        this.directions = directions;
    }

    @Override
    protected final List<Path> getCoordinatePaths(ChessPosition startPosition) {
        final List<Path> paths = new ArrayList<>();
        for (Direction direction : directions) {
            List<ChessPosition> boundaryPositions = getBoundaryPositions(startPosition, direction);
            if (!boundaryPositions.isEmpty()) {
                paths.add(new Path(boundaryPositions));
            }
        }
        return paths;
    }

    private List<ChessPosition> getBoundaryPositions(ChessPosition startPosition, Direction direction) {
        final List<ChessPosition> chessPositions = new ArrayList<>();
        for (int distance = 1; distance <= 9; distance++) {
            final int nextRow = startPosition.row() + direction.dr * distance;
            final int nextColumn = startPosition.column() + direction.dc * distance;
            if (ChessPosition.isValid(nextRow, nextColumn)) {
                chessPositions.add(new ChessPosition(nextRow, nextColumn));
            }
        }
        return chessPositions;
    }
}
