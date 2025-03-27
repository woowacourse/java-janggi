package domain.dao;

import domain.janggiboard.customstrategy.BoardArrangementStrategy;
import java.util.HashMap;
import java.util.Map;

public class FakeJanggiGameDao implements JanggiGameDao {

    private class Strategies {
        private final BoardArrangementStrategy strategyOfCho;
        private final BoardArrangementStrategy strategyOfHan;

        public Strategies(BoardArrangementStrategy strategyOfCho, BoardArrangementStrategy strategyOfHan) {
            this.strategyOfCho = strategyOfCho;
            this.strategyOfHan = strategyOfHan;
        }
    }

    private final Map<Integer, Strategies> game;

    public FakeJanggiGameDao() {
        this.game = new HashMap<>();
    }

    @Override
    public void addGame(BoardArrangementStrategy strategyOfCho, BoardArrangementStrategy strategyOfHan) {
        game.put(1, new Strategies(strategyOfCho, strategyOfHan));
    }

    @Override
    public int getGame() {
        if (game.isEmpty()) {
            return -1;
        }
        return 1;
    }

    @Override
    public BoardArrangementStrategy findChoStrategyById(int gameId) {
        return game.get(1).strategyOfCho;
    }

    @Override
    public BoardArrangementStrategy findHanStrategyById(int gameId) {
        return game.get(1).strategyOfHan;
    }

    @Override
    public void deleteAll() {
        this.game.clear();
    }
}
