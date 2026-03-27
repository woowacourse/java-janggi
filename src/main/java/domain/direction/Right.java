package domain.direction;

import domain.board.Intersection;

public class Right implements Direction {

    @Override
    public Intersection moveForward(Intersection currentIntersection, MoveAmount moveAmount) {
        return new Intersection(
                currentIntersection.getRow(),
                currentIntersection.getFile() + moveAmount.amount()
        );
    }

    @Override
    public Intersection moveLeft(Intersection currentIntersection, MoveAmount moveAmount) {
        return new Intersection(
                currentIntersection.getRow() - moveAmount.amount(),
                currentIntersection.getFile()
        );
    }

    @Override
    public Intersection moveRight(Intersection currentIntersection, MoveAmount moveAmount) {
        return new Intersection(
                currentIntersection.getRow() + moveAmount.amount(),
                currentIntersection.getFile()
        );
    }

    @Override
    public Intersection moveForwardLeft(Intersection currentIntersection, MoveAmount moveAmount) {
        return new Intersection(
                currentIntersection.getRow() - moveAmount.amount(),
                currentIntersection.getFile() + moveAmount.amount()
        );
    }

    @Override
    public Intersection moveForwardRight(Intersection currentIntersection, MoveAmount moveAmount) {
        return new Intersection(
                currentIntersection.getRow() + moveAmount.amount(),
                currentIntersection.getFile() + moveAmount.amount()
        );
    }

    @Override
    public Direction reverse() {
        return new Left();
    }
}
