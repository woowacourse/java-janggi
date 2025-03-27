package domain.dao;

import domain.janggiboard.customstrategy.BoardArrangementStrategy;

public interface JanggiGameDao {

    void addGame(final BoardArrangementStrategy strategyOfCho, final BoardArrangementStrategy strategyOfHan);
    int getGame();
    BoardArrangementStrategy findChoStrategyById(int gameId);
    BoardArrangementStrategy findHanStrategyById(int gameId);
    void deleteAll();
}
