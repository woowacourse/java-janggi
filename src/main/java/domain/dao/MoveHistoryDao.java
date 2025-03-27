package domain.dao;

import java.util.List;

public interface MoveHistoryDao {

    void addHistory(final int gameId, final int originId, final int destinationId);
    void deleteAll();
    List<List<Integer>> getAllHistory(final int gameId);
}
