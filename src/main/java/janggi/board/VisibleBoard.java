package janggi.board;

import janggi.coordinate.JanggiPosition;
import janggi.piece.Country;
import java.util.List;

public interface VisibleBoard {
    boolean existPieceByPosition(final JanggiPosition existJanggiPosition);

    boolean isCannonByPosition(final JanggiPosition janggiPosition);

    boolean containsCannonByPositions(final List<JanggiPosition> janggiPositions);

    boolean equalsTeamTypeByPosition(final JanggiPosition janggiPosition, final Country country);

    int calculatePieceCountByPositions(final List<JanggiPosition> janggiPositions);
}
