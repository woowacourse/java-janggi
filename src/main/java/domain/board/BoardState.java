package domain.board;

import java.util.List;

public class BoardState {

    private final List<IntersectionState> boardState;

    public BoardState(List<IntersectionState> boardState) {
        this.boardState = boardState;
    }

    public List<IntersectionState> getBoardState() {
        return List.copyOf(boardState);
    }

}
