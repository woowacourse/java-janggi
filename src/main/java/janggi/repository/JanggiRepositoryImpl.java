package janggi.repository;

import janggi.dao.BoardDao;
import janggi.dao.JanggiDao;
import janggi.domain.GameStatus;
import janggi.domain.JanggiGame;
import janggi.domain.Player;
import janggi.domain.Team;
import janggi.entity.BoardEntity;
import janggi.entity.JanggiEntity;
import java.util.List;
import java.util.Optional;

public class JanggiRepositoryImpl implements JanggiRepository {

    private final JanggiDao janggiDao;
    private final BoardDao boardDao;

    public JanggiRepositoryImpl(final JanggiDao janggiDao, final BoardDao boardDao) {
        this.janggiDao = janggiDao;
        this.boardDao = boardDao;
    }

    @Override
    public void save(final JanggiGame janggiGame, final GameStatus gameStatus) {
        JanggiEntity janggiEntity = JanggiEntity.from(janggiGame, 0);
        janggiDao.save(janggiEntity, gameStatus);
    }

    @Override
    public boolean existsByRedAndGreenPlayerNameAndGameStatus(final String redPlayerName,
                                                              final String greenPlayerName,
                                                              final GameStatus gameStatus) {
        return janggiDao.existsByRedAndGreenPlayerNameAndGameStatus(redPlayerName,
                greenPlayerName,
                gameStatus.name());
    }

    @Override
    public Optional<JanggiGame> findByRedAndGreenPlayerNameAndGameStatus(final String redPlayerName,
                                                                         final String greenPlayerName,
                                                                         final GameStatus gameStatus) {
        Optional<JanggiEntity> janggiEntityOptional = janggiDao.findByRedAndGreenPlayerNameAndGameStatus(
                redPlayerName,
                greenPlayerName,
                gameStatus.name());
        if (janggiEntityOptional.isEmpty()) {
            return Optional.empty();
        }
        JanggiEntity janggiEntity = janggiEntityOptional.get();
        Player redPlayer = new Player(janggiEntity.redPlayerName(), Team.RED, janggiEntity.redScore());
        Player greenPlayer = new Player(janggiEntity.greenPlayerName(), Team.GREEN, janggiEntity.greenScore());
        List<BoardEntity> boardEntities = findByJanggiIdAndIsAlive(janggiEntity.janggiId());

        return Optional.of(new JanggiGame(BoardEntity.convertToBoard(boardEntities),
                redPlayer,
                greenPlayer,
                Team.convert(janggiEntity.gameTurn()),
                GameStatus.convert(janggiEntity.gameStatus())));
    }

    @Override
    public Optional<Long> findJanggiIdByRedAndGreenPlayerNameAndGameStatus(final String redPlayerName,
                                                                           final String greenPlayerName,
                                                                           final GameStatus gameStatus) {
        Optional<JanggiEntity> janggiEntityOptional = janggiDao.findByRedAndGreenPlayerNameAndGameStatus(
                redPlayerName, greenPlayerName, gameStatus.name());
        if (janggiEntityOptional.isEmpty()) {
            return Optional.empty();
        }
        JanggiEntity janggiEntity = janggiEntityOptional.get();
        return Optional.of(janggiEntity.janggiId());
    }

    private List<BoardEntity> findByJanggiIdAndIsAlive(long janggiId) {
        return boardDao.findAllByJanggiIdAndIsAlive(janggiId, true);
    }
}
