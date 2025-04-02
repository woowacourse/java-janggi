package domain.dao;

import domain.dto.HistoryDto;

import java.util.List;

public interface MoveHistoryDao {

    void addHistory(final int gameId, final int originId, final int destinationId);

    void deleteAll();

    List<HistoryDto> getAllHistory(final int gameId);
}
