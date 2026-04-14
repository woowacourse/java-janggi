package repository;

import dao.JanggiGameDao;
import dao.JanggiGameStateDao;
import domain.JanggiGame;
import dto.GameStateData;
import dto.PieceSnapshot;
import factory.JanggiGameRestorer;
import infrastructure.TransactionContext;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import dto.UnfinishedGameInfo;

public class JdbcJanggiRepository implements JanggiRepository {

    private final JanggiGameDao janggiGameDao;
    private final JanggiGameStateDao janggiGameStateDao;

    private final JanggiGameRestorer JanggiGameRestorer = new JanggiGameRestorer();

    public JdbcJanggiRepository(JanggiGameDao janggiGameDao,
                                JanggiGameStateDao janggiGameStateDao) {
        this.janggiGameDao = janggiGameDao;
        this.janggiGameStateDao = janggiGameStateDao;
    }

    @Override
    public long createGame(JanggiGame game) {
        AtomicLong gameId = new AtomicLong(-1L);

        TransactionContext.run(() -> {
            long id = janggiGameDao.insertGame(game.pieceSnapshots());
            gameId.set(id);
            janggiGameStateDao.insert(gameId.get(), game.gameStatus(), game.isFinished());
        });

        return gameId.get();
    }

    @Override
    public void updateGame(long gameId, JanggiGame game) {
        TransactionContext.run(() -> {
            janggiGameDao.updateGame(gameId, game.pieceSnapshots());
            janggiGameStateDao.update(gameId, game.gameStatus(), game.isFinished());
        });
    }

    @Override
    public Optional<JanggiGame> loadGame(long gameId) {
        return TransactionContext.query(() -> {
            List<PieceSnapshot> pieceSnapshots = janggiGameDao.findAllPieces(gameId);
            Optional<GameStateData> gameStateData = janggiGameStateDao.findByGameId(gameId);
            return gameStateData.map(state -> JanggiGameRestorer.restore(pieceSnapshots, state));
        });
    }

    @Override
    public boolean hasUnfinishedGame() {
        return TransactionContext.query(() -> findLatestUnfinishedGameId().isPresent());
    }

    @Override
    public Optional<Long> findLatestUnfinishedGameId() {
        return TransactionContext.query(janggiGameDao::findLatestUnfinishedGameId);
    }

    @Override
    public List<UnfinishedGameInfo> findUnfinishedGameInfos() {
        return TransactionContext.query(janggiGameDao::findUnfinishedGameInfos);
    }
}
