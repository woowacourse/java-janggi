package janggi.piece;

import janggi.board.VisibleBoard;
import janggi.coordinate.Path;
import janggi.coordinate.Position;
import janggi.coordinate.RelativePosition;
import java.util.List;

public class Elephant extends Piece {

    private static final double ELEPHANT_DISTANCE = Math.sqrt(13);
    private static final List<Path> RELATIVE_POSITIONS = List.of(
            new Path(List.of(RelativePosition.TOP, RelativePosition.TOP_LEFT_DIAGONAL,
                    RelativePosition.TOP_LEFT_DIAGONAL)),
            new Path(List.of(RelativePosition.TOP, RelativePosition.TOP_RIGHT_DIAGONAL,
                    RelativePosition.TOP_RIGHT_DIAGONAL)),
            new Path(List.of(RelativePosition.BOTTOM, RelativePosition.BOTTOM_LEFT_DIAGONAL,
                    RelativePosition.BOTTOM_LEFT_DIAGONAL)),
            new Path(List.of(RelativePosition.BOTTOM, RelativePosition.BOTTOM_RIGHT_DIAGONAL,
                    RelativePosition.BOTTOM_RIGHT_DIAGONAL)),
            new Path(List.of(RelativePosition.LEFT, RelativePosition.TOP_LEFT_DIAGONAL,
                    RelativePosition.TOP_LEFT_DIAGONAL)),
            new Path(List.of(RelativePosition.LEFT, RelativePosition.BOTTOM_LEFT_DIAGONAL,
                    RelativePosition.BOTTOM_LEFT_DIAGONAL)),
            new Path(List.of(RelativePosition.RIGHT, RelativePosition.TOP_RIGHT_DIAGONAL,
                    RelativePosition.TOP_RIGHT_DIAGONAL)),
            new Path(List.of(RelativePosition.RIGHT, RelativePosition.BOTTOM_RIGHT_DIAGONAL,
                    RelativePosition.BOTTOM_RIGHT_DIAGONAL))
    );

    public Elephant(final Country country) {
        super(country);
    }

    @Override
    protected boolean canMove(final Position now, final Position destination, final VisibleBoard visibleBoard) {
        if (now.calculateDistance(destination) != ELEPHANT_DISTANCE) {
            return false;
        }

        final List<Position> absolutePath = findPathByDestination(now, destination);
        absolutePath.removeLast();

        return absolutePath.stream()
                .filter(position -> visibleBoard.existPieceByPosition(position))
                .findAny()
                .isEmpty();
    }

    private List<Position> findPathByDestination(final Position now, final Position destination) {
        return RELATIVE_POSITIONS.stream()
                .filter(path -> path.equalsDestination(now, destination))
                .findFirst()
                .orElseThrow(IllegalAccessError::new)
                .calculateAbsolutePath(now);
    }

    @Override
    public boolean isCannon() {
        return false;
    }
}
