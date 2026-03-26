package domain.direction;

import domain.board.Intersection;

public interface Direction {

    Intersection moveForward(Intersection currentIntersection, MoveAmount moveAmount);

    Intersection moveLeft(Intersection currentIntersection, MoveAmount moveAmount);

    Intersection moveRight(Intersection currentIntersection, MoveAmount moveAmount);

    Intersection moveForwardLeft(Intersection currentIntersection, MoveAmount moveAmount);

    Intersection moveForwardRight(Intersection currentIntersection, MoveAmount moveAmount);
}
