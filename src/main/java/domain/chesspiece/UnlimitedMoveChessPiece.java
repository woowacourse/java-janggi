package domain.chesspiece;

import domain.direction.Direction;
import domain.hurdlePolicy.HurdlePolicy;
import domain.path.Path;
import domain.position.ChessPosition;
import domain.type.ChessTeam;

import java.util.ArrayList;
import java.util.List;

public abstract class UnlimitedMoveChessPiece extends JanggiChessPiece {
    private final List<Direction> directions;


    protected UnlimitedMoveChessPiece(final ChessTeam team, final ChessPosition position,
                                      final List<Direction> directions, final HurdlePolicy hurdlePolicy) {
        super(position, team, hurdlePolicy);
        this.directions = directions;
    }

    @Override
    public final List<Path> calculateCoordinatePaths(ChessPosition startPosition) {
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
        ChessPosition currentPosition = startPosition;
        while (canMove(direction, currentPosition)) {
            currentPosition = currentPosition.move(direction);
            chessPositions.add(currentPosition);
        }
        return chessPositions;
    }

    private static boolean canMove(final Direction direction, final ChessPosition currentPosition) {
        if (direction.isDiagonal()) {
            return currentPosition.canMove(direction) && currentPosition.canCastleMove(direction);
        }
        return currentPosition.canMove(direction);
    }
}
