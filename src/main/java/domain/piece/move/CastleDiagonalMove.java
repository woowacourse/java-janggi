package domain.piece.move;

import domain.JanggiBoard;
import domain.JanggiCoordinate;

public interface CastleDiagonalMove {
    void validateCastleDiagonalMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to);

    void validateDiagonalReachable(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to);
}
