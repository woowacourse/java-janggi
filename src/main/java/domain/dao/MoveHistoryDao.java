package domain.dao;

import java.util.List;

public interface MoveHistoryDao {

    void addHistory(final String gameId, final String originId, final String destinationId);
    void deleteAll();
    List<List<String>> getAllHistory(final String gameId);
}
