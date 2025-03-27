package domain.dao;

import domain.janggiboard.customstrategy.BoardArrangementStrategy;

public interface JanggiGameDao {

    void addGame(final BoardArrangementStrategy strategyOfCho, final BoardArrangementStrategy strategyOfHan);
    String getGame();
    BoardArrangementStrategy findChoStrategyById(String gameId);
    BoardArrangementStrategy findHanStrategyById(String gameId);
    void deleteAll();
}
