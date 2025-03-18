package domain.piece;

import domain.JanggiCoordinate;
import java.util.List;

public interface Piece {
    List<JanggiCoordinate> availableMovePositions();
}
