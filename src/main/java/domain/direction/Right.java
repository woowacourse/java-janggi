package domain.direction;

import domain.board.Intersection;

public class Right implements Direction {

    @Override
    public Intersection moveForward(Intersection currentIntersection, MoveAmount moveAmount) {
        return new Intersection(
                currentIntersection.row(),
                currentIntersection.file() + moveAmount.amount()
        );
    }

    @Override
    public Intersection moveLeft(Intersection currentIntersection, MoveAmount moveAmount) {
        return new Intersection(
                currentIntersection.row() - moveAmount.amount(),
                currentIntersection.file()
        );
    }

    @Override
    public Intersection moveRight(Intersection currentIntersection, MoveAmount moveAmount) {
        return new Intersection(
                currentIntersection.row() + moveAmount.amount(),
                currentIntersection.file()
        );
    }

    @Override
    public Intersection moveForwardLeft(Intersection currentIntersection, MoveAmount moveAmount) {
        return new Intersection(
                currentIntersection.row() - moveAmount.amount(),
                currentIntersection.file() + moveAmount.amount()
        );
    }

    @Override
    public Intersection moveForwardRight(Intersection currentIntersection, MoveAmount moveAmount) {
        return new Intersection(
                currentIntersection.row() + moveAmount.amount(),
                currentIntersection.file() + moveAmount.amount()
        );
    }
}
