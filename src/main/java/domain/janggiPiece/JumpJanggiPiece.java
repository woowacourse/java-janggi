package domain.janggiPiece;

import domain.direction.JanggiDirections;
import domain.path.JanggiPath;
import domain.position.JanggiPosition;
import domain.type.JanggiTeam;

import java.util.ArrayList;
import java.util.List;

public abstract class JumpJanggiPiece extends JanggiChessPiece {
    private final List<JanggiDirections> directions;

    protected JumpJanggiPiece(JanggiTeam team, List<JanggiDirections> directions) {
        super(team);
        this.directions = directions;
    }

    @Override
    public final List<JanggiPath> getCoordinatePaths(JanggiPosition startPosition) {
        List<JanggiPath> result = new ArrayList<>();
        for (JanggiDirections direction : directions) {
            if (direction.canApplyFrom(startPosition)) {
                result.add(direction.getPathFrom(startPosition));
            }
        }
        return result;
    }
}
