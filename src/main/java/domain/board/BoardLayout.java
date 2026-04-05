package domain.board;

import java.util.List;

public record BoardLayout(
        int baseY,
        int generalY,
        int cannonY,
        int soldierY,
        List<Integer> formationX
) {
}
