package domain.board;

import java.util.List;

public record BoardState(
        List<IntersectionState> intersectionStates
) {

    public BoardState {
        intersectionStates = List.copyOf(intersectionStates);
    }

}
