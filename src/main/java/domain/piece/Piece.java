package domain.piece;

import domain.Country;
import domain.Direction;
import domain.Path;
import domain.Position;
import domain.state.State;
import java.util.List;
import java.util.Map;

public abstract class Piece {
    private static final String NOT_EMPTY_PATH = "[ERROR] 이동 경로에 다른 기물이 존재해 이동시킬 수 없습니다.";
    private static final String CANNOT_MOVE_SAME_COUNTRY_POSITION = "[ERROR] 같은 진영의 기물이 있는 위치로 이동시킬 수 없습니다.";

    protected final PieceInfo pieceInfo;

    public Piece(PieceInfo pieceInfo) {
        this.pieceInfo = pieceInfo;
    }

    public Path path(Position from, Position to) {
        Path path = new Path();
        Position position = from;
        path.add(position);
        for (Direction direction : findDirections(from, to)) {
            position = position.nextPosition(direction);
            path.add(position);
        }
        return path;
    }

    public List<Direction> findDirections(Position from, Position to) {
        List<Integer> distances = from.calculateDistance(to);
        int x = distances.get(0);
        int y = distances.get(1);

        List<Direction> directions = Direction.findDirections(x, y);
        validateDirections(directions, from, to);
        return directions;
    }

    abstract void validateDirections(List<Direction> directions, Position from, Position to);

    public void validateMove(Map<Position, State> pathStates) {
        List<State> states = pathStates.values().stream()
                .toList();
        validateToState(states.getFirst(), states.getLast());
        // from, to State 제외한 Position 검사
        validatePath(states);
    }

    void validateToState(State fromState, State toState) {
        if (toState.isEmpty()) {
            return;
        }
        validateToStateWithFromState(fromState, toState);
    }

    void validateToStateWithFromState(State fromState, State toState) {
        if (fromState.getPieceCountry() == toState.getPieceCountry()) {
            throw new IllegalArgumentException(CANNOT_MOVE_SAME_COUNTRY_POSITION);
        }
    }

    void validatePath(List<State> states) {
        for (int index = 1; index < states.size() - 1; index++) {
            State state = states.get(index);
            validatePathState(state);
        }
    }

    void validatePathState(State state) {
        if (!state.isEmpty()) {
            throw new IllegalArgumentException(NOT_EMPTY_PATH);
        }
    }

    public PieceInfo getPieceInfo() {
        return pieceInfo;
    }

    public PieceType getPieceType() {
        return pieceInfo.pieceType();
    }

    public Country getPieceCountry() {
        return pieceInfo.country();
    }
}
