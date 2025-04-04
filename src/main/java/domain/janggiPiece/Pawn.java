package domain.janggiPiece;

import domain.direction.Direction;
import domain.hurdlePolicy.HurdlePolicy;
import domain.hurdlePolicy.UnpassableHurdlePolicy;
import domain.path.Path;
import domain.position.JanggiPosition;
import domain.type.JanggiTeam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Pawn extends JanggiChessPiece {

    private static final Map<JanggiTeam, List<Direction>> DIRECTIONS = Map.of(
            JanggiTeam.RED, List.of(
                    Direction.LEFT,
                    Direction.RIGHT,
                    Direction.DOWN,
                    Direction.LEFT_DOWN,
                    Direction.RIGHT_DOWN
            ),
            JanggiTeam.BLUE, List.of(
                    Direction.LEFT,
                    Direction.RIGHT,
                    Direction.UP,
                    Direction.LEFT_UP,
                    Direction.RIGHT_UP
            )
    );
    private final HurdlePolicy hurdlePolicy = new UnpassableHurdlePolicy();

    public Pawn(JanggiTeam janggiTeam) {
        super(janggiTeam);
    }

    @Override
    public List<Path> getCoordinatePaths(JanggiPosition startPosition) {
        List<Direction> directions = DIRECTIONS.get(getTeam());
        final List<Path> result = new ArrayList<>();
        for (Direction direction : startPosition.getLinkedRoadDirections()) {
            if (directions.contains(direction)) {
                List<JanggiPosition> path = List.of(startPosition.move(direction));
                result.add(new Path(path));
            }
        }
        return result;
    }

    @Override
    public HurdlePolicy getHurdlePolicy() {
        return hurdlePolicy;
    }

    @Override
    public Piece getChessPieceType() {
        return Piece.PAWN;
    }
}
