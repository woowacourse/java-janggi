package domain.strategy;

import domain.Position;
import java.util.ArrayList;
import java.util.List;

public class GeneralMoveStrategy extends MoveStrategy {

    private final static int[] DR = {0, 1, 0, -1};
    private final static int[] DC = {1, 0, -1, 0};

    private List<Position> destinations;

    private GeneralMoveStrategy(Position position) {
        super(position);
        this.destinations = setupDestinations();
    }

    @Override
    public void updateRoute() {
        this.destinations = setupDestinations();
    }

    public static GeneralMoveStrategy of(Position position) {
        return new GeneralMoveStrategy(position);
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

    @Override
    public boolean isMoveAble(Position targetPosition) {
        return destinations.contains(targetPosition);
    }

    @Override
    public boolean hasPieceOnPath(Position destination, List<Position> piecePositions) {
        return false;
    }
}
