package janggi.domain.piece;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;

public class Knight implements Piece {

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Point start, Point end) {
        return false;
    }

//    private final Map<Point, List<Point>> paths = Map.of(
//            new Point(-2, 1), List.of(new Point(-1, 0)),
//            new Point(-2, -1), List.of(new Point(-1, 0)),
//            new Point(-1, 2), List.of(new Point(0, 1)),
//            new Point(1, 2), List.of(new Point(0, 1)),
//            new Point(2, 1), List.of(new Point(1, 0)),
//            new Point(2, -1), List.of(new Point(1, 0)),
//            new Point(1, -2), List.of(new Point(0, -1)),
//            new Point(-1, -2), List.of(new Point(0, -1))
//    );
//    private final Dynasty dynasty;
//
//    public Knight(Dynasty dynasty) {
//        this.dynasty = dynasty;
//    }
//
//    @Override
//    public boolean isMovable(JanggiBoard janggiBoard, Point start, Point end) {
//        Point interval = end.minus(start);
//        if (!paths.containsKey(interval)) {
//            return false;
//        }
//
//        List<Point> path = paths.get(interval);
//        for (Point point : path) {
//            if (janggiBoard.isExistPiece(point)) {
//                return false;
//            }
//        }
//        return !janggiBoard.isExistSameDynasty(end, dynasty);
//    }
//
//    @Override
//    public boolean isSameDynasty(Dynasty dynasty) {
//        return false;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        Knight knight = (Knight) o;
//        return dynasty == knight.dynasty;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hashCode(dynasty);
//    }
}
