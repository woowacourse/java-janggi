package domain.movement;

import domain.board.Intersection;

public record Move(
        Intersection from,
        Intersection to
) {
}
