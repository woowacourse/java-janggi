package domain.piece;

import domain.Country;
import domain.Direction;
import domain.Position;
import domain.state.State;
import java.util.List;
import java.util.Map;

public class Cannon extends Piece {
    private static final String ONLY_MOVE_STRAIGHT = "[ERROR] 포는 직선으로만 이동 가능합니다.";
    private static final String FIXED_DIRECTION = "[ERROR] 포는 하나의 방향으로만 이동 가능합니다.";
    private static final String CAN_NOT_MOVE_TO_POSITION = "[ERROR] 해당 경로로 기물을 이동시킬 수 없습니다.";

    private static final int CANNON_JUMP_PIECE_COUNT = 1;

    public Cannon(Country country) {
        super(new PieceInfo(PieceType.CANNON, country));
    }

    @Override
    public void validateDirections(List<Direction> directions) {
        Direction oneSide = directions.getFirst();
        boolean allSameDirection = directions.stream()
                .allMatch(direction -> direction.equals(oneSide));
        if (!allSameDirection) {
            throw new IllegalArgumentException(FIXED_DIRECTION);
        }
        // 궁성 영역 생각하지 않음
        if (oneSide.isDiagonal()) {
            throw new IllegalArgumentException(ONLY_MOVE_STRAIGHT);
        }
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
                    throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
                }
                pieceCount++;
            }
        }
        if (pieceCount != CANNON_JUMP_PIECE_COUNT) {
            throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
        }
        return true;
    }

    @Override
    protected void validateToState(State fromState, State toState) {
        if (!toState.isEmpty()) {
            if (fromState.getPieceCountry() == toState.getPieceCountry()) {
                throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
            }
            if (fromState.getPieceType() == toState.getPieceType()) {
                throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
            }
        }
    }
}
