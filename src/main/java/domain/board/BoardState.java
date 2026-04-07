package domain.board;

import java.util.List;

public class BoardState {
    private final List<IntersectionState> boardState;

    public BoardState(List<IntersectionState> boardState) {
        this.boardState = List.copyOf(boardState);
    }

    public List<IntersectionState> getBoardState() {
        return boardState;
    }
}
