package repository.move_record.dto;

import domain.piece.Side;

public record MoveRecord(
    int sourceX,
    int sourceY,
    int targetX,
    int targetY,
    Side turn
) {

}
