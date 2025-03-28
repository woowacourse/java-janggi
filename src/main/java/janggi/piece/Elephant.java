package janggi.piece;

import janggi.board.JanggiScore;
import janggi.board.VisibleBoard;
import janggi.coordinate.Path;
import janggi.coordinate.JanggiPosition;
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
    private static final JanggiScore KILL_JANGGI_SCORE = new JanggiScore(3);

    public Elephant(final Country country) {
        super(country);
    }

    @Override
    public JanggiScore plusScore(final JanggiScore janggiScore) {
        return KILL_JANGGI_SCORE.plus(janggiScore);
    }

    @Override
    protected boolean canMove(final JanggiPosition now, final JanggiPosition destination, final VisibleBoard visibleBoard) {
        if (now.calculateDistance(destination) != ELEPHANT_DISTANCE) {
            return false;
        }

        final List<JanggiPosition> absolutePath = findPathByDestination(now, destination);
        absolutePath.removeLast();

        return absolutePath.stream()
                .filter(position -> visibleBoard.existPieceByPosition(position))
                .findAny()
                .isEmpty();
    }

    private List<JanggiPosition> findPathByDestination(final JanggiPosition now, final JanggiPosition destination) {
        return RELATIVE_POSITIONS.stream()
                .filter(path -> path.equalsDestination(now, destination))
                .findFirst()
                .orElseThrow(IllegalAccessError::new)
                .calculateAbsolutePath(now);
    }

}
