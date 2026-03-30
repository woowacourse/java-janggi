package dto;

import domain.Camp;
import domain.PieceType;
import domain.Position;

public record PositionStatusDto(
        Position position,
        PieceType pieceType,
        Camp camp
) {

}
