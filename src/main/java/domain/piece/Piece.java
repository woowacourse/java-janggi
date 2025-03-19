package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import java.util.List;

public interface Piece {
    List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate, JanggiBoard janggiBoard);

    Country getTeam();

    boolean isPho();
}
