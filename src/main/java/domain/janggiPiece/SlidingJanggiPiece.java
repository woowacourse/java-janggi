package domain.janggiPiece;

import domain.direction.Direction;
import domain.path.Path;
import domain.position.JanggiPosition;
import domain.type.JanggiTeam;

import java.util.ArrayList;
import java.util.List;

public abstract class SlidingJanggiPiece extends JanggiChessPiece {
    protected SlidingJanggiPiece(JanggiTeam team) {
        super(team);
    }

    @Override
    public final List<Path> getCoordinatePaths(JanggiPosition startPosition) {
        final List<Path> paths = new ArrayList<>();
        // 궁성 내부에 존재하면, 대각선 방향에 대해서는 궁성 내에서만 움직일 수 있다.
        for (Direction direction : startPosition.getLinkedRoadDirections()) {
            List<JanggiPosition> boundaryPositions = getBoundaryPositions(startPosition, direction);
            if (!boundaryPositions.isEmpty()) {
                paths.add(new Path(boundaryPositions));
            }
        }
        return paths;
    }

    // 궁성 외부인 경우에는 아무렇게나 움직이면 됨 (어차피 대각선 방향 안 들어옴)
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
}
