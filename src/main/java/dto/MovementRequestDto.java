package dto;

import domain.board.Point;
import java.util.List;

public class MovementRequestDto {
    private final List<Integer> startPoint;
    private final List<Integer> arrivalPoint;

    public MovementRequestDto(final List<Integer> startPoint, final List<Integer> arrivalPoint) {
        this.startPoint = startPoint;
        this.arrivalPoint = arrivalPoint;
    }

    public Point getOriginPoint() {
        return new Point(startPoint.getFirst(),
                startPoint.getLast());
    }

    public Point getArrivalPoint() {
        return new Point(arrivalPoint.getFirst(),
                arrivalPoint.getLast());
    }
}
