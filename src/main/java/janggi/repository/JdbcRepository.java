package janggi.repository;

import janggi.dao.GameDao;
import janggi.dao.GameJdbcDao;
import janggi.dao.MoveDao;
import janggi.dao.MoveJdbcDao;
import janggi.dto.MoveDto;
import java.util.List;

public class JdbcRepository implements Repository {

    private final GameDao gameDao;
    private final MoveDao moveDao;

    public JdbcRepository() {
        this.gameDao = new GameJdbcDao();
        this.moveDao = new MoveJdbcDao();
    }

    @Override
    public void setGameFinished(final int id) {
        gameDao.setGameFinished(id);
    }

    @Override
    public void saveInitialGame(final int setupOption) {
        gameDao.saveInitialGame(setupOption);
    }

    @Override
    public int findRecentOngoingGameId() {
        return gameDao.findRecentNotFinishedGameId();
    }

    @Override
    public boolean isExistOngoingGame() {
        return gameDao.existNotFinishedGame();
    }

    @Override
    public List<Integer> findOngoingGameIds() {
        return gameDao.findNotFinishedGameIds();
    }

    @Override
    public int findGameSetup(final int id) {
        return gameDao.findGameSetup(id);
    }

    @Override
    public List<MoveDto> selectAllHistory(final int id) {
        return moveDao.selectAllHistory(id);
    }

    @Override
    public void saveHistory(final MoveDto moveDto, final int id) {
        moveDao.saveHistory(moveDto, id);
    }
}
