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
    public JanggiGame loadGame(long gameId) {
        return TransactionContext.query(() -> {
            List<PieceSnapshot> pieceSnapshots = janggiGameDao.findAllPieces(gameId);
            GameStateData gameStateData = janggiGameStateDao.findByGameId(gameId);
            return JanggiGameRestorer.restore(pieceSnapshots, gameStateData);
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


//    @Override
//    public void saveMove(long gameId, Position from, Position to, JanggiGame game) {
//        TransactionContext.run(() -> {
//            long moveId = pieceMoveDao.insertMove(gameId, from.row(), from.col(), to.row(), to.col());
//            pieceMoveDao.insertMovePieces(moveId, game.allFactors());
//        });
//    }
//
//
//    @Override
//    public Optional<Long> findLatestUnfinishedGameId() {
//        try (Connection conn = dataSource.getConnection()) {
//            return janggiGameStateDao.findLatestUnfinishedGameId();
//        } catch (SQLException e) {
//            throw new JanggiDataException("미완료 게임 조회 실패", e);
//        }
//    }
//
//    @Override
//    public int findCurrentMoveOrder(long gameId) {
//        try (Connection conn = dataSource.getConnection()) {
//            return pieceMoveDao.findCurrentMoveOrder(gameId);
//        } catch (SQLException e) {
//            throw new JanggiDataException("행마 순서 조회 실패", e);
//        }
//    }
//
//    private void executeSaveMove(Connection conn, long gameId, int moveOrder,
//                                 Position from, Position to, JanggiGame game) throws SQLException {
//        try {
//            long moveId = pieceMoveDao.insertMove(gameId, moveOrder, from.row(), from.col(), to.row(), to.col());
//            pieceMoveDao.insertMovePieces(moveId, game.allFactors());
//
//            GameStatus status = game.getGameStatus();
//            janggiGameStateDao.update(gameId,
//                    GameStatusMapper.toCurrentTurn(status),
//                    status.isFinished(),
//                    GameStatusMapper.toWinner(status));
//
//            conn.commit();
//        } catch (SQLException e) {
//            conn.rollback();
//            throw e;
//        }
//    }
}