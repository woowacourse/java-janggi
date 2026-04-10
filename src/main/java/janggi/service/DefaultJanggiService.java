package janggi.service;

import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.board.Location;
import janggi.domain.piece.Piece;
import janggi.domain.strategy.BoardAssembler;
import janggi.domain.strategy.arrangement.ArrangementStrategy;
import janggi.domain.strategy.arrangement.DBArrangementStrategy;
import janggi.domain.strategy.intersection.IntersectionInitializer;
import janggi.repository.dao.GameDao;
import janggi.repository.dao.PieceDao;
import janggi.repository.entity.GameEntity;
import janggi.repository.entity.PieceEntity;
import janggi.repository.util.transaction.TransactionManager;
import janggi.service.dto.GameInformation;
import janggi.service.mapper.PieceEntityMapper;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        return transactionManager.executeInTransaction(() -> {
            List<GameEntity> activeGameEntities = gameDao.findActiveGames();
            return activeGameEntities.stream()
                    .map(GameEntity::getId)
                    .toList();
        });
    }

    @Override
    public GameInformation loadGameInformation(Long gameId, IntersectionInitializer intersectionInitializer) {
        return transactionManager.executeInTransaction(() -> {
            Optional<GameEntity> game = gameDao.findById(gameId);
            if (game.isEmpty()) {
                throw new IllegalStateException("존재하지 않는 게임입니다");
            }
            GameEntity gameEntity = game.get();
            List<PieceEntity> pieceEntities = pieceDao.findByGameId(gameId);

            Map<Location, Piece> locationMap = PieceEntityMapper.toLocationMap(pieceEntities);
            ArrangementStrategy arrangementStrategy = new DBArrangementStrategy(locationMap);
            Board board = Board.create(BoardAssembler.of(List.of(arrangementStrategy), intersectionInitializer));

            return GameInformation.of(gameEntity.getId(), board, Side.valueOf(gameEntity.getTurn()));
        });
    }

    @Override
    public GameInformation createGame(List<ArrangementStrategy> strategies,
                                      IntersectionInitializer intersectionInitializer) {
        return transactionManager.executeInTransaction(() -> {
            Long gameId = gameDao.insert(new GameEntity(Side.CHO.name(), true));

            Board board = Board.create(BoardAssembler.of(strategies, intersectionInitializer));

            List<List<Piece>> board2DArray = board.to2DArray();
            PieceEntityMapper.toEntities(gameId, board2DArray)
                    .forEach(pieceDao::insert);

            return GameInformation.of(gameId, board, Side.CHO);
        });
    }

    @Override
    public void movePiece(GameInformation gameInformation, Location from, Location to) {
        transactionManager.executeInTransaction(() -> {
            Long gameId = gameInformation.getGameId();
            Board board = gameInformation.getBoard();
            Piece removedPiece = board.move(from, to);

            Optional<PieceEntity> fromPiece = pieceDao.findByGameIdAndLocation(gameId, from);
            Optional<PieceEntity> toPiece = pieceDao.findByGameIdAndLocation(gameId, to);
            fromPiece.ifPresent(pieceEntity -> pieceDao.updatePosition(pieceEntity.getId(), to));
            toPiece.ifPresent(pieceEntity -> pieceDao.deleteById(pieceEntity.getId()));

            gameInformation.update(removedPiece);
            if (gameInformation.isInProgress()) {
                gameDao.updateTurn(gameId, gameInformation.getCurrentSide().name());
            }
        });
    }

    @Override
    public void endGame(Long gameId) {
        transactionManager.executeInTransaction(
                () -> gameDao.updateIsActive(gameId, false)
        );
    }
}
