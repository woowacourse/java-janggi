package repository;

import domain.Position;
import domain.piece.PieceInfo;
import domain.piece.PieceInfos;
import dto.PositionState;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionHistoryTestRepository implements PositionHistoryRepository {
    private final Map<Integer, List<PositionState>> positionHistories = new HashMap<>();

    @Override
    public List<PositionState> findPositionHistoriesByTurnHistoryId(int turnHistoryId, Connection connection) {
        return List.copyOf(positionHistories.get(turnHistoryId));
    }

    @Override
    public void savePositionHistory(PieceInfos pieceInfos, int turnHistoryId, Connection connection) {
        if (!positionHistories.containsKey(turnHistoryId)) {
            positionHistories.put(turnHistoryId, new ArrayList<>());
        }
        List<PositionState> states = positionHistories.get(turnHistoryId);
        addPositionState(states, pieceInfos);
    }

    private void addPositionState(List<PositionState> states, PieceInfos pieceInfos) {
        for (Position position : pieceInfos.getKeys()) {
            PieceInfo pieceInfo = pieceInfos.get(position);
            PositionState positionState = new PositionState(position.x(), position.y(), pieceInfo.pieceType().name(),
                    pieceInfo.countryType().name());
            states.add(positionState);
        }
    }

    @Override
    public void deletePositionHistoriesByTurnHistoryId(int turnHistoryId, Connection connection) {
        positionHistories.remove(turnHistoryId);
    }
}
