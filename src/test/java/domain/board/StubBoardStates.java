package domain.board;

import static domain.Position.INITIAL_POSITION;
import static domain.Position.X_MAXIMUM_POSITION;
import static domain.Position.Y_MAXIMUM_POSITION;

import domain.Position;
import domain.state.EmptyState;
import domain.state.State;
import java.util.HashMap;
import java.util.Map;

public class StubBoardStates {
    private final Map<Position, State> boardStates = new HashMap<>();

    public StubBoardStates() {
        initializeEmptyState(boardStates);
    }

    private void initializeEmptyState(Map<Position, State> boardStates) {
        for (int y = INITIAL_POSITION; y <= Y_MAXIMUM_POSITION; y++) {
            for (int x = INITIAL_POSITION; x <= X_MAXIMUM_POSITION; x++) {
                Position position = new Position(x, y);
                boardStates.put(position, new EmptyState());
            }
        }
    }

    public void put(Position position, State state) {
        boardStates.put(position, state);
    }

    public BoardStates create() {
        return new BoardStates(boardStates);
    }
}
