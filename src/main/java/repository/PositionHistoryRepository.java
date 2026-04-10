package repository;

import domain.piece.PieceInfos;
import dto.PositionState;
import java.sql.Connection;
import java.util.List;

public interface PositionHistoryRepository {
    List<PositionState> findPositionHistoriesByTurnHistoryId(int turnHistoryId, Connection connection);

    void savePositionHistory(PieceInfos pieceInfos, int gameInfoId, Connection connection);

    void deletePositionHistoriesByGameInfoId(int gameInfoId, Connection connection);
}
