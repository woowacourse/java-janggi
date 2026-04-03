package domain.direction;

import domain.board.Intersection;

public class Down implements Direction {

    @Override
    public Intersection moveForward(Intersection currentIntersection, MoveAmount moveAmount) {
        return new Intersection(
                currentIntersection.row() + moveAmount.amount(),
                currentIntersection.file()
        );
    }

    @Override
    public Intersection moveLeft(Intersection currentIntersection, MoveAmount moveAmount) {
        return new Intersection(
                currentIntersection.row(),
                currentIntersection.file() + moveAmount.amount()
        );
    }

    @Override
    public Intersection moveRight(Intersection currentIntersection, MoveAmount moveAmount) {
        return new Intersection(
                currentIntersection.row(),
                currentIntersection.file() - moveAmount.amount()
        );
    }

    @Override
    public Intersection moveForwardLeft(Intersection currentIntersection, MoveAmount moveAmount) {
        return new Intersection(
                currentIntersection.row() + moveAmount.amount(),
                currentIntersection.file() + moveAmount.amount()
        );
    }

    @Override
    public Intersection moveForwardRight(Intersection currentIntersection, MoveAmount moveAmount) {
        return new Intersection(
                currentIntersection.row() + moveAmount.amount(),
                currentIntersection.file() - moveAmount.amount()
        );
    }

    @Override
    public Direction left() {
        return new Right();
    }

    @Override
    public Direction right() {
        return new Left();
    }

    @Override
    public Direction reverse() {
        return new Up();
    }
}
