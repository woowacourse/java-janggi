package mapper;

import domain.point.Point;
import dto.InputMoveDto;
import dto.Move;

public class MoveMapper {

    public static Move toMove(InputMoveDto inputMoveDto) {
        Point from = new Point(inputMoveDto.getFrom().getY(), inputMoveDto.getFrom().getX());
        Point to = new Point(inputMoveDto.getTo().getY(), inputMoveDto.getTo().getX());
        return new Move(from, to);
    }

}
