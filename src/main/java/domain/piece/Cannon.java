package domain.piece;

import domain.country.CountryType;
import domain.state.State;
import java.util.List;

public class Cannon extends MoveStraightPiece {
    private static final String INVALID_JUMP_PIECE_COUNT = "[ERROR] 포는 하나의 기물만 뛰어 넘을 수 있습니다.";
    private static final String CANNOT_JUMP_CANNON = "[ERROR] 포는 포를 뛰어 넘을 수 없습니다.";
    private static final String CANNOT_KILL_CANNON = "[ERROR] 포는 포를 잡을 수 없습니다.";

    private static final int CANNON_JUMP_PIECE_COUNT = 1;

    public Cannon(CountryType countryType) {
        super(new PieceInfo(PieceType.CANNON, countryType));
    }

    @Override
    void validateToStateWithFromState(State fromState, State toState) {
        super.validateToStateWithFromState(fromState, toState);
        if (fromState.getPieceType() == toState.getPieceType()) {
            throw new IllegalArgumentException(CANNOT_KILL_CANNON);
        }
    }

    @Override
    void validatePath(List<State> states) {
        int pieceCount = 0;
        for (int index = 1; index < states.size() - 1; index++) {
            State state = states.get(index);
            validatePathState(state);
            pieceCount = adjustPieceCount(state, pieceCount);
        }
        validatePieceCount(pieceCount);
    }

    @Override
    void validatePathState(State state) {
        if (state.isEmpty()) {
            return;
        }
        if (state.getPieceType() == PieceType.CANNON) {
            throw new IllegalArgumentException(CANNOT_JUMP_CANNON);
        }
    }

    private int adjustPieceCount(State state, int pieceCount) {
        if (!state.isEmpty()) {
            return ++pieceCount;
        }
        return pieceCount;
    }

    private void validatePieceCount(int pieceCount) {
        if (pieceCount != CANNON_JUMP_PIECE_COUNT) {
            throw new IllegalArgumentException(INVALID_JUMP_PIECE_COUNT);
        }
    }
}
