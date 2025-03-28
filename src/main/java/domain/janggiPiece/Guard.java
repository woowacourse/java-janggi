package domain.janggiPiece;

import domain.direction.Direction;
import domain.hurdlePolicy.HurdlePolicy;
import domain.hurdlePolicy.UnpassableHurdlePolicy;
import domain.path.Path;
import domain.position.JanggiPosition;
import domain.position.JanggiPositionFactory;
import domain.type.JanggiPieceType;
import domain.type.JanggiTeam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Guard extends JanggiChessPiece {
    private final HurdlePolicy hurdlePolicy = new UnpassableHurdlePolicy();

    public Guard(final JanggiTeam team) {
        super(team);
    }

    public static Map<JanggiPosition, JanggiChessPiece> initPieces() {
        return Map.of(
                JanggiPositionFactory.of(0, 3), new Guard(JanggiTeam.RED),
                JanggiPositionFactory.of(0, 5), new Guard(JanggiTeam.RED),
                JanggiPositionFactory.of(9, 3), new Guard(JanggiTeam.BLUE),
                JanggiPositionFactory.of(9, 5), new Guard(JanggiTeam.BLUE)
        );
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

    @Override
    public HurdlePolicy getHurdlePolicy() {
        return hurdlePolicy;
    }

    @Override
    public JanggiPieceType getChessPieceType() {
        return JanggiPieceType.GUARD;
    }
}
