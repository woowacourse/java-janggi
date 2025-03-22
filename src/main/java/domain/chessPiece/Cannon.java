package domain.chessPiece;

import domain.direction.Direction;
import domain.path.Path;
import domain.position.ChessPiecePositions;
import domain.position.ChessPosition;
import domain.type.ChessPieceType;
import domain.type.ChessTeam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static domain.direction.Direction.*;
import static domain.direction.Direction.RIGHT;

public class Cannon extends UnlimitedMoveChessPiece {
    private static final List<Direction> directions = List.of(UP, DOWN, LEFT, RIGHT);

    public Cannon(final ChessTeam team) {
        super(team, directions);
    }

    public static Map<ChessPosition, ChessPiece> initPieces() {
        return Map.of(
                new ChessPosition(2, 1), new Cannon(ChessTeam.RED),
                new ChessPosition(2, 7), new Cannon(ChessTeam.RED),
                new ChessPosition(7, 1), new Cannon(ChessTeam.BLUE),
                new ChessPosition(7, 7), new Cannon(ChessTeam.BLUE)
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

    private List<ChessPosition> getOverHurdlePaths(Path path, ChessPiecePositions positions) {
        List<ChessPosition> pathPositions = path.getPath();
        for (int i = 0; i < pathPositions.size(); i++) {
            ChessPosition currentPosition = pathPositions.get(i);
            if (isHurdle(currentPosition, positions)) {
                return pathPositions.subList(i+1, pathPositions.size());
            }
            if (isWall(currentPosition, positions)) {
                return List.of();
            }
        }
        return List.of();
    }

    private boolean isHurdle(ChessPosition targetPosition, ChessPiecePositions positions) {
        if (!positions.existChessPieceByPosition(targetPosition)) {
            return false;
        }
        ChessPiece other = positions.getChessPieceByPosition(targetPosition);
        return !isWall(targetPosition, positions) && getTeam() != other.getTeam();
    }

    private boolean isWall(ChessPosition targetPosition, ChessPiecePositions positions) {
        if (!positions.existChessPieceByPosition(targetPosition)) {
            return false;
        }
        ChessPiece other = positions.getChessPieceByPosition(targetPosition);
        return other.getChessPieceType() == ChessPieceType.CANNON;
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

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.CANNON;
    }
}
