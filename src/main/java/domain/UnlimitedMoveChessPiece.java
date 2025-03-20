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
            final List<ChessPosition> chessPositions = new ArrayList<>();
            for (int distance = 1; distance <= 9; distance++) {
                final int nextRowDistance = direction.dr * distance;
                final int nextColumnDistance = direction.dc * distance;
                final int nextRow = getPosition().row() + nextRowDistance;
                final int nextColumn = getPosition().column() + nextColumnDistance;
                if (!ChessPosition.isValid(nextRow, nextColumn)) {
                    continue;
                }
                chessPositions.add(getPosition().move(nextRowDistance, nextColumnDistance));
            }
            if (!chessPositions.isEmpty()) {
                paths.add(new Path(chessPositions));
            }

        }
        return paths;
    }

    @Override
    protected abstract List<ChessPosition> getCoordinateDestinations(List<Path> coordinates, ChessPiecePositions chessPiecePositions);
}
