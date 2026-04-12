package repository;

import domain.Position;
import domain.piece.PieceInfo;
import dto.PositionState;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionStateTestRepository implements PositionStateRepository {
    private final Map<Integer, List<PositionState>> positionStates = new HashMap<>();

    @Override
    public List<PositionState> findAllPositionStatesByGameInfoId(int gameInfoId, Connection connection) {
        return List.copyOf(positionStates.get(gameInfoId));
    }

    @Override
    public PositionState findPositionStateByPosition(Position position, int gameInfoId, Connection connection) {
        return findAllPositionStatesByGameInfoId(gameInfoId, connection).stream()
                .filter(positionState -> positionState.x() == position.x() && positionState.y() == position.y())
                .findAny()
                .orElseThrow(() -> new IllegalStateException(""));
    }

    @Override
    public void savePositionState(Position position, PieceInfo pieceInfo, int gameInfoId, Connection connection) {
        if (!positionStates.containsKey(gameInfoId)) {
            positionStates.put(gameInfoId, new ArrayList<>());
        }
        List<PositionState> states = positionStates.get(gameInfoId);
        states.add(new PositionState(position.x(), position.y(), pieceInfo.pieceType().name(),
                pieceInfo.countryType().name()));
    }

    @Override
    public void updatePositionState(Position position, PieceInfo pieceInfo, int gameInfoId, Connection connection) {
        deletePositionStateByPosition(position, gameInfoId, connection);
        savePositionState(position, pieceInfo, gameInfoId, connection);
    }

    @Override
    public void deletePositionStateByPosition(Position position, int gameInfoId, Connection connection) {
        if (!positionStates.containsKey(gameInfoId)) {
            return;
        }
        List<PositionState> states = positionStates.get(gameInfoId);
        states.removeIf(positionState -> positionState.x() == position.x() && positionState.y() == position.y());
    }

    @Override
    public void deleteAllPositionStatesByGameInfoId(int gameInfoId, Connection connection) {
        positionStates.remove(gameInfoId);
    }
}
