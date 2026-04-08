package mapper;

import domain.game.MoveCommand;
import domain.point.Point;
import dto.InputMoveDto;

public class MoveMapper {
    private MoveMapper() {
    }

    public static MoveCommand toMove(InputMoveDto inputMoveDto) {
        Point from = new Point(inputMoveDto.getFrom().getY(), inputMoveDto.getFrom().getX());
        Point to = new Point(inputMoveDto.getTo().getY(), inputMoveDto.getTo().getX());
        return new MoveCommand(from, to);
    }
}
