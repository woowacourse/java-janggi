package domain.piece;

import domain.Country;
import domain.Direction;
import domain.Position;
import domain.state.State;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Piece {
    private static final String CAN_NOT_MOVE_TO_POSITION = "[ERROR] 해당 경로로 기물을 이동시킬 수 없습니다.";

    protected final PieceInfo pieceInfo;

    public Piece(PieceInfo pieceInfo) {
        this.pieceInfo = pieceInfo;
    }

    public List<Position> path(Position from, Position to) {
        List<Position> path = new ArrayList<>();
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
        validateDirections(directions);
        return directions;
    }

    abstract void validateDirections(List<Direction> directions);

    public boolean canMove(Map<Position, State> pathStates) {
        List<State> states = pathStates.values().stream()
                .toList();
        validateToState(states.getFirst(), states.getLast());
        // from, to State 제외한 Position 검사
        for (int index = 1; index < states.size() - 1; index++) {
            State state = states.get(index);
            if (!state.isEmpty()) {
                throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
            }
        }
        return true;
    }

    void validateToState(State fromState, State toState) {
        if (!toState.isEmpty()) {
            if (fromState.getPieceCountry() == toState.getPieceCountry()) {
                throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
            }
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
