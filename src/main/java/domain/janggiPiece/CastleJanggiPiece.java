package domain.janggiPiece;

import domain.direction.Direction;
import domain.path.Path;
import domain.position.JanggiPosition;
import domain.type.JanggiTeam;

import java.util.ArrayList;
import java.util.List;

public abstract class CastleJanggiPiece extends JanggiChessPiece {
    protected CastleJanggiPiece(JanggiTeam team) {
        super(team);
    }

    @Override
    public List<Path> getCoordinatePaths(JanggiPosition startPosition) {
        List<Path> result = new ArrayList<>();
        for (Direction direction : startPosition.getLinkedRoadDirections()) {
            JanggiPosition nextPosition = startPosition.move(direction);
            if (nextPosition.isCastle()) {
                result.add(new Path(List.of(nextPosition)));
            }
        }
        return result;
    }
}
