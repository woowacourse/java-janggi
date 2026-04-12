package service.dto;

import domain.board.JanggiBoard;
import domain.intersection.Intersection;
import domain.piece.Team;
import domain.point.Point;

public record Moved(
        Intersection origin,
        Intersection destination,
        Team currentTurn
) {

    public static Moved of(JanggiBoard board, Point start, Point end) {
        Intersection origin = board.findIntersection(start);
        Intersection destination = board.findIntersection(end);
        return new Moved(origin, destination, board.getCurrentTurn());
    }

}
