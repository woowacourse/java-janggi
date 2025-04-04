package domain.janggiPiece;

import domain.direction.Direction;
import domain.hurdlePolicy.CannonHurdlePolicy;
import domain.hurdlePolicy.HurdlePolicy;
import domain.path.Path;
import domain.position.JanggiPosition;
import domain.type.JanggiTeam;

import java.util.ArrayList;
import java.util.List;

public class Cannon extends JanggiChessPiece {
    private final HurdlePolicy hurdlePolicy = new CannonHurdlePolicy();

    public Cannon(final JanggiTeam team) {
        super(team);
    }

    @Override
    public final List<Path> getCoordinatePaths(JanggiPosition startPosition) {
        final List<Path> paths = new ArrayList<>();
        for (Direction direction : startPosition.getLinkedRoadDirections()) {
            List<JanggiPosition> boundaryPositions = getBoundaryPositions(startPosition, direction);
            if (!boundaryPositions.isEmpty()) {
                paths.add(new Path(boundaryPositions));
            }
        }
        return paths;
    }

    private List<JanggiPosition> getBoundaryPositions(JanggiPosition startPosition, Direction direction) {
        final List<JanggiPosition> chessPositions = new ArrayList<>();
        JanggiPosition currentPosition = startPosition;
        while (canSlide(currentPosition, direction)) {
            currentPosition = currentPosition.move(direction);
            chessPositions.add(currentPosition);
        }
        return chessPositions;
    }

    private boolean canSlide(JanggiPosition startPosition, Direction direction) {
        if (!direction.isDiagonal()) {
            return startPosition.canMove(direction);
        }
        if (!startPosition.canMove(direction)) {
            return false;
        }
        JanggiPosition nextPosition = startPosition.move(direction);
        return nextPosition.isCastle();
    }

    @Override
    public HurdlePolicy getHurdlePolicy() {
        return hurdlePolicy;
    }

    @Override
    public Piece getChessPieceType() {
        return Piece.CANNON;
    }
}
