package domain.piece;

import domain.JanggiBoard;
import domain.JanggiCoordinate;

public interface InDiagonalCastleMove {
    void validateCastleDiagonalMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to);

    void validateDiagonalReachAble(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to);
}
