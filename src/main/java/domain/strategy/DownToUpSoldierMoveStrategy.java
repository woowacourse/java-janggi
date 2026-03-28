package domain.strategy;

import domain.Position;
import java.util.ArrayList;
import java.util.List;

public class DownToUpSoldierMoveStrategy extends MoveStrategy {

    private final static int[] DR = {0, -1, 0};
    private final static int[] DC = {1, 0, -1};

    private final List<Position> destinations;

    private DownToUpSoldierMoveStrategy(Position position) {
        super(position);
        this.destinations = setupDestinations();
    }

    public static DownToUpSoldierMoveStrategy of(Position position) {
        return new DownToUpSoldierMoveStrategy(position);
    }

    @Override
    public boolean isMoveAble(Position targetPosition) {
        return destinations.contains(targetPosition);
    }

    @Override
    public boolean hasPieceOnPath(Position destination, List<Position> piecePositions) {
        return false;
    }

    private List<Position> setupDestinations() {
        List<Position> positions = new ArrayList<>();

        for (int i = 0; i < DR.length; i++) {
            int dr = position.row() + DR[i];
            int dc = position.col() + DC[i];
            positions.add(Position.of(dr, dc));
        }

        return positions;
    }
}
