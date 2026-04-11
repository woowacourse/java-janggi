package repository;

import domain.country.CountryType;
import dto.TurnHistory;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TurnHistoryTestRepository implements TurnHistoryRepository {
    private final Map<Integer, List<TurnHistory>> turnHistories = new HashMap<>();
    private int idSequence = 0;

    @Override
    public List<TurnHistory> findTurnHistoriesByGameInfoId(int gameInfoId, Connection connection) {
        return List.copyOf(turnHistories.get(gameInfoId));
    }

    @Override
    public int saveTurnHistory(int gameInfoId, CountryType countryType, Connection connection) {
        if (!turnHistories.containsKey(gameInfoId)) {
            turnHistories.put(gameInfoId, new ArrayList<>());
        }
        idSequence += 1;
        List<TurnHistory> histories = turnHistories.get(gameInfoId);
        histories.add(new TurnHistory(idSequence, countryType.name()));
        return idSequence;
    }

    @Override
    public void deleteAllTurnHistoriesByGameInfoId(int gameInfoId, Connection connection) {
        turnHistories.remove(gameInfoId);
    }
}
