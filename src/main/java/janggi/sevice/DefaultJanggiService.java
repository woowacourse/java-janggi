package janggi.sevice;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.state.GameContext;
import janggi.domain.strategy.ArrangementStrategy;
import janggi.domain.strategy.BoardAssembler;
import janggi.domain.strategy.DBArrangementStrategy;
import janggi.domain.strategy.IntersectionInitializer;
import janggi.repository.dao.GameDao;
import janggi.repository.dao.PieceDao;
import janggi.repository.entity.GameEntity;
import janggi.repository.entity.PieceEntity;
import janggi.repository.util.TransactionManager;
import janggi.sevice.dto.GameInformation;
import janggi.sevice.mapper.PieceEntityMapper;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class DefaultJanggiService implements JanggiService {

    private final TransactionManager transactionManager;
    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public DefaultJanggiService(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        this.gameDao = new GameDao(transactionManager);
        this.pieceDao = new PieceDao(transactionManager);
    }

    @Override
    public List<Long> findActiveGameIds() {
        return executeInTransaction(() -> {
            List<GameEntity> activeGameEntities = gameDao.findActiveGames();
            return activeGameEntities.stream()
                    .map(GameEntity::getId)
                    .toList();
        });
    }

    @Override
    public GameInformation loadGameInformation(Long gameId, IntersectionInitializer intersectionInitializer) {
        return executeInTransaction(() -> {
            Optional<GameEntity> game = gameDao.findById(gameId);
            if (game.isEmpty()) {
                throw new IllegalStateException("존재하지 않는 게임입니다");
            }
            GameEntity gameEntity = game.get();
            List<PieceEntity> pieceEntities = pieceDao.findByGameId(gameId);

            Map<Location, Piece> locationMap = PieceEntityMapper.toLocationMap(pieceEntities);
            ArrangementStrategy arrangementStrategy = new DBArrangementStrategy(locationMap);
            Board board = Board.create(BoardAssembler.of(List.of(arrangementStrategy), intersectionInitializer));

            return new GameInformation(gameEntity.getId(), board, Side.valueOf(gameEntity.getTurn()));
        });
    }

    @Override
    public GameInformation createGame(List<ArrangementStrategy> strategies,
                                      IntersectionInitializer intersectionInitializer) {
        return executeInTransaction(() -> {
            Long gameId = gameDao.insert(new GameEntity(Side.CHO.name(), true));

            Board board = Board.create(BoardAssembler.of(strategies, intersectionInitializer));

            List<List<Piece>> board2DArray = board.to2DArray();
            PieceEntityMapper.toEntities(gameId, board2DArray)
                    .forEach(pieceDao::insert);

            return new GameInformation(gameId, board, Side.CHO);
        });
    }

    @Override
    public void movePiece(GameInformation gameInformation, Location from, Location to, GameContext gameContext) {
        executeInTransaction(() -> {
            Board board = gameInformation.board();
            Piece removedPiece = board.move(from, to);
            gameContext.update(removedPiece);
            //TODO 이동 정보 저장
        });
    }

    @Override
    public void endGame(Long gameId) {
        executeInTransaction(() -> {
            //TODO 게임 상태 변경 로직
        });
    }

    private <T> T executeInTransaction(Supplier<T> action) {
        if (transactionManager.isNotActive()) {
            transactionManager.begin();
        }
        try {
            T result = action.get();
            transactionManager.commit();
            return result;
        } catch (RuntimeException e) {
            transactionManager.rollback();
            throw e;
        } finally {
            transactionManager.close();
        }
    }

    private void executeInTransaction(Runnable action) {
        if (transactionManager.isNotActive()) {
            transactionManager.begin();
        }
        try {
            action.run();
            transactionManager.commit();
        } catch (RuntimeException e) {
            transactionManager.rollback();
            throw e;
        } finally {
            transactionManager.close();
        }
    }
}
