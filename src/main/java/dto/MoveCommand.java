package dto;

import domain.PieceType;
import domain.Position;

public record MoveCommand(
        PieceType pieceType,
        Position from,
        Position to
) {
    public static MoveCommand of(PieceType pieceType, Position from, Position to) {
        return new MoveCommand(pieceType, from, to);
    }
}
