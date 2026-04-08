package dto;

import domain.board.Intersection;

public record MoveCommand(Type type, Intersection selectedToMove) {

    public static MoveCommand from(String rawMoveCommand) {
        if (rawMoveCommand.equalsIgnoreCase("exit")) {
            // TODO 이것도 null 넣어서 뱉는 거 호출부에서는 방어적으로 사용하는데, 애초에 null 뱉는 방식이 문제긴 한 듯
            return new MoveCommand(Type.EXIT, null);
        }

        Intersection parsed = Intersection.parse(rawMoveCommand);
        return new MoveCommand(Type.MOVE, parsed);
    }

    public boolean isExit() {
        return type == Type.EXIT;
    }

    enum Type {
        EXIT, MOVE
    }
}
