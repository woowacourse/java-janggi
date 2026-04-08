package repository.move_record;

import domain.Side;

public record MoveRecord(
    int sourceX,
    int sourceY,
    int targetX,
    int targetY,
    Side turn
) {

}
