package domain.janggiPiece;

import domain.direction.Direction;
import domain.path.JanggiPath;
import domain.position.JanggiPosition;
import domain.type.JanggiTeam;

import java.util.ArrayList;
import java.util.List;

public abstract class StepJanggiPiece extends JanggiChessPiece {
    private final List<Direction> directions;

    protected StepJanggiPiece(JanggiTeam team, List<Direction> directions) {
        super(team);
        this.directions = directions;
    }

    @Override
    public List<JanggiPath> getCoordinatePaths(JanggiPosition startPosition) {
        final List<JanggiPath> result = new ArrayList<>();
        for (Direction direction : startPosition.getLinkedRoadDirections()) {
            if (directions.contains(direction)) {
                List<JanggiPosition> path = List.of(startPosition.move(direction));
                result.add(new JanggiPath(path));
            }
        }
        return result;
    }
}
