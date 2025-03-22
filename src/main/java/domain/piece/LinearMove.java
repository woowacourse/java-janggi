package domain.piece;

import domain.JanggiBoard;
import domain.JanggiCoordinate;

public interface LinearMove {
    void validateLinearMove(JanggiBoard janggiBoard, JanggiCoordinate from, JanggiCoordinate to);
}
