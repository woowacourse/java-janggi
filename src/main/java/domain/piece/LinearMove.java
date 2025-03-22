package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;

public interface LinearMove {
    void validateLinearMove(JanggiBoard janggiBoard, JanggiCoordinate from, JanggiCoordinate to);
}
