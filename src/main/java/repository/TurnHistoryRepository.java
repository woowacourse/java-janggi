package repository;

import domain.country.CountryType;
import dto.TurnHistory;
import java.sql.Connection;
import java.util.List;

public interface TurnHistoryRepository {
    List<TurnHistory> findTurnHistoriesByGameInfoId(int gameInfoId, Connection connection);

    int saveTurnHistory(int gameInfoId, CountryType countryType, Connection connection);

    void deleteAllTurnHistoriesByGameInfoId(int gameInfoId, Connection connection);
}
