package domain;

import java.util.ArrayList;
import java.util.List;

import static domain.Direction.*;
import static domain.Direction.RIGHT;

public class Cannon extends UnlimitedMoveChessPiece {
    private static final List<Direction> directions = List.of(UP, DOWN, LEFT, RIGHT);

    public Cannon(ChessPosition position, final ChessTeam team) {
        super(position, team, directions);
    }

    public static List<Cannon> initPieces() {
        return List.of(
                new Cannon(new ChessPosition(2, 1), ChessTeam.RED),
                new Cannon(new ChessPosition(2, 7), ChessTeam.RED),
                new Cannon(new ChessPosition(7, 1), ChessTeam.BLUE),
                new Cannon(new ChessPosition(7, 7), ChessTeam.BLUE)
        );
    }

    @Override
    protected List<ChessPosition> getCoordinateDestinations(
            final List<Path> coordinates,
            final ChessPiecePositions positions
    ) {
        final List<ChessPosition> destinations = new ArrayList<>();
        for (Path path : coordinates) {
            List<ChessPosition> overHurdlePaths = getOverHurdlePaths(path, positions);
            destinations.addAll(getOverHurdleDestinations(overHurdlePaths, positions));
        }
        return destinations;
    }

    private List<ChessPosition> getOverHurdleDestinations(
            List<ChessPosition> overHurdlePaths,
            ChessPiecePositions positions
    ) {
        List<ChessPosition> result = new ArrayList<>();
        for (ChessPosition currentPosition : overHurdlePaths) {
            if (canMoveTo(currentPosition, positions)) {
                result.add(currentPosition);
            }
            if (positions.existChessPieceByPosition(currentPosition)) {
                return result;
            }
        }
        return result;
    }

    private boolean canMoveTo(ChessPosition targetPosition, ChessPiecePositions positions) {
        return !positions.existChessPieceByPosition(targetPosition) || isHurdle(targetPosition, positions);
    }

    private List<ChessPosition> getOverHurdlePaths(Path path, ChessPiecePositions positions) {
        List<ChessPosition> pathPositions = path.getPath();
        for (int i = 0; i < pathPositions.size(); i++) {
            ChessPosition currentPosition = pathPositions.get(i);
            if (isHurdle(currentPosition, positions)) {
                return pathPositions.subList(i+1, pathPositions.size());
            }
        }
        return List.of();
    }

    private boolean isHurdle(ChessPosition targetPosition, ChessPiecePositions positions) {
        if (!positions.existChessPieceByPosition(targetPosition)) {
            return false;
        }
        ChessPiece other = positions.getChessPieceByPosition(targetPosition);
        return getChessPieceType() != other.getChessPieceType() && getTeam() != other.getTeam();
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.CANNON;
    }

}
