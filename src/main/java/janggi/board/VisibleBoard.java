package janggi.board;

import janggi.coordinate.Position;
import janggi.piece.Country;
import java.util.List;

public interface VisibleBoard {
    boolean existPieceByPosition(final Position existPosition);

    boolean isCannonByPosition(final Position position);

    boolean containsCannonByPositions(final List<Position> positions);

    boolean equalsTeamTypeByPosition(final Position position, final Country country);

    int calculatePieceCountByPositions(final List<Position> positions);
}
