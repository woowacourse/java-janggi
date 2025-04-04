package domain.janggiPiece;

import domain.direction.Direction;
import domain.hurdlePolicy.HurdlePolicy;
import domain.hurdlePolicy.UnpassableHurdlePolicy;
import domain.path.Path;
import domain.position.JanggiPosition;
import domain.type.JanggiTeam;

import java.util.ArrayList;
import java.util.List;

public class Guard extends JanggiChessPiece {
    private final HurdlePolicy hurdlePolicy = new UnpassableHurdlePolicy();

    public Guard(final JanggiTeam team) {
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

    @Override
    public HurdlePolicy getHurdlePolicy() {
        return hurdlePolicy;
    }

    @Override
    public Piece getChessPieceType() {
        return Piece.GUARD;
    }
}
