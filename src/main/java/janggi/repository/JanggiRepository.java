package janggi.repository;

import janggi.dao.BoardDao;
import janggi.dao.JanggiDao;
import janggi.dao.PieceDao;
import janggi.domain.GameStatus;
import janggi.domain.JanggiGame;
import janggi.domain.Player;
import janggi.domain.Team;
import janggi.entity.BoardEntity;
import janggi.entity.JanggiEntity;
import janggi.entity.PieceEntity;
import java.util.List;
import java.util.Optional;

public class JanggiRepository {

    private final JanggiDao janggiDao;
    private final BoardDao boardDao;
    private final PieceDao pieceDao;

    public JanggiRepository(final JanggiDao janggiDao, final BoardDao boardDao, final PieceDao pieceDao) {
        this.janggiDao = janggiDao;
        this.boardDao = boardDao;
        this.pieceDao = pieceDao;
    }

    public void save(final JanggiGame janggiGame) {
        JanggiEntity janggiEntity = JanggiEntity.from(janggiGame);
        Optional<Long> janggiIdOptional = janggiDao.findJanggiIdByRedAndGreenPlayerNameAndGameStatus(
                janggiGame.getRedPlayer().getName(),
                janggiGame.getGreenPlayer().getName(),
                GameStatus.CONTINUE.name());
        if (janggiIdOptional.isPresent()) {
            janggiDao.save(janggiEntity.addJanggiId(janggiIdOptional.get()));
            return;
        }
        janggiDao.save(janggiEntity);
    }

    public boolean existsByRedAndGreenPlayerNameAndGameStatus(final String redPlayerName,
                                                              final String greenPlayerName,
                                                              final GameStatus gameStatus) {
        return janggiDao.existsByRedAndGreenPlayerNameAndGameStatus(redPlayerName,
                greenPlayerName,
                gameStatus.name());
    }

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
        boardDao.save(BoardEntity.from(janggiEntity.janggiId()));
        Optional<Long> janggiIdOptional = boardDao.findByJanggiId(janggiEntity.janggiId());
        if (janggiIdOptional.isEmpty()) {
            return Optional.empty();
        }
        List<PieceEntity> boardEntities = findAllAlive(janggiIdOptional.get());

        return Optional.of(new JanggiGame(PieceEntity.convertToBoard(boardEntities),
                redPlayer,
                greenPlayer,
                Team.convert(janggiEntity.gameTurn()),
                GameStatus.convert(janggiEntity.gameStatus())));
    }

    public Optional<Long> findJanggiIdByRedAndGreenPlayerNameAndGameStatus(final String redPlayerName,
                                                                           final String greenPlayerName,
                                                                           final GameStatus gameStatus) {
        return janggiDao.findJanggiIdByRedAndGreenPlayerNameAndGameStatus(redPlayerName,
                greenPlayerName,
                gameStatus.name());
    }

    private List<PieceEntity> findAllAlive(long janggiId) {
        return pieceDao.findAllByBoardIdAndIsAlive(janggiId, true);
    }
}
