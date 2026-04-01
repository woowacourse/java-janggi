package domain.movement.direction;

import domain.movement.Vector;

public interface Direction {

    Vector toForward();

    Vector toBackword();

    Vector toLeft();

    Vector toRight();

    Vector toForwardLeft();

    Vector toForwardRight();

    Direction reverse();
}
