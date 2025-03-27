package domain.piece;

import domain.JanggiBoard;
import domain.JanggiCoordinate;

public interface CastleDiagonalMove {
    void validateCastleDiagonalMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to);

    void validateDiagonalReachAble(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to);
}
