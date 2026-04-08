package repository;

import domain.country.CountryType;
import domain.piece.PieceInfos;
import dto.PositionHistory;
import java.sql.Connection;
import java.util.List;

public interface PositionHistoryRepository {
    List<PositionHistory> findPositionHistoriesByGameInfoId(int gameInfoId, Connection connection);

    void savePositionHistory(PieceInfos pieceInfos, int gameInfoId, CountryType turn, Connection connection);

    void deletePositionHistoriesByGameInfoId(int gameInfoId, Connection connection);
}
