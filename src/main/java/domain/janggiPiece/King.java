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

public class King extends JanggiChessPiece {
    private final HurdlePolicy hurdlePolicy = new UnpassableHurdlePolicy();

    public King(final JanggiTeam team) {
        super(team);
    }

    public static Map<JanggiPosition, JanggiChessPiece> initPieces() {
        return Map.of(
                JanggiPositionFactory.of(1, 4), new King(JanggiTeam.RED),
                JanggiPositionFactory.of(8, 4), new King(JanggiTeam.BLUE)
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
        return JanggiPieceType.KING;
    }
}
