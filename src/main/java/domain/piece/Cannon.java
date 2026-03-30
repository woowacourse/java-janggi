package domain.piece;

import domain.Country;
import domain.Position;
import domain.state.State;
import java.util.List;
import java.util.Map;

public class Cannon extends MoveStraightPiece {
    private static final String INVALID_JUMP_PIECE_COUNT = "[ERROR] 포는 하나의 기물만 뛰어 넘을 수 있습니다.";
    private static final String CANNOT_JUMP_CANNON = "[ERROR] 포는 포를 뛰어 넘을 수 없습니다.";
    private static final String CANNOT_KILL_CANNON = "[ERROR] 포는 포를 잡을 수 없습니다.";
    private static final String CANNOT_MOVE_SAME_COUNTRY_POSITION = "[ERROR] 같은 진영의 기물이 있는 위치로 이동시킬 수 없습니다.";

    private static final int CANNON_JUMP_PIECE_COUNT = 1;

    public Cannon(Country country) {
        super(new PieceInfo(PieceType.CANNON, country));
    }

    @Override
    public boolean canMove(Map<Position, State> pathStates) {
        List<State> states = pathStates.values().stream()
                .toList();
        State fromState = states.getFirst();
        validateToState(fromState, states.getLast());
        int pieceCount = 0;
        for (int index = 1; index < states.size() - 1; index++) {
            State state = states.get(index);
            if (!state.isEmpty()) {
                if (state.getPieceType() == fromState.getPieceType()) {
                    throw new IllegalArgumentException(CANNOT_JUMP_CANNON);
                }
                pieceCount++;
            }
        }
        if (pieceCount != CANNON_JUMP_PIECE_COUNT) {
            throw new IllegalArgumentException(INVALID_JUMP_PIECE_COUNT);
        }
        return true;
    }

    @Override
    protected void validateToState(State fromState, State toState) {
        if (!toState.isEmpty()) {
            if (fromState.getPieceCountry() == toState.getPieceCountry()) {
                throw new IllegalArgumentException(CANNOT_MOVE_SAME_COUNTRY_POSITION);
            }
            if (fromState.getPieceType() == toState.getPieceType()) {
                throw new IllegalArgumentException(CANNOT_KILL_CANNON);
            }
        }
    }
}
