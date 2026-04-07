package dto;

import domain.board.Position;

public record MoveResult(
        Position from,
        Position to,
        boolean isGeneralCaught
) {

    public static MoveResult of(Position from, Position to, boolean isGeneralCaught) {
        return new MoveResult(from, to, isGeneralCaught);
    }
}
