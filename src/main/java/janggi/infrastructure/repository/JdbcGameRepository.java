package janggi.infrastructure.repository;

import janggi.domain.Game;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.PieceType;
import janggi.domain.player.Name;
import janggi.domain.player.Players;
import janggi.domain.repository.GameRepository;
import janggi.domain.space.Position;
import janggi.dto.GameDto;
import janggi.infrastructure.dao.GameDao;
import janggi.infrastructure.dao.PieceDao;
import janggi.infrastructure.dao.dto.GameEntity;
import janggi.infrastructure.dao.dto.PieceEntity;
import janggi.infrastructure.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JdbcGameRepository implements GameRepository {
    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public JdbcGameRepository(GameDao gameDao, PieceDao pieceDao) {
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    @Override
    public Long save(Game game) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                String choName = game.getPlayerNameBySide(Side.CHO).name();
                String hanName = game.getPlayerNameBySide(Side.HAN).name();
                String currentTurn = game.getCurrentSide().name();

                Long gameId = gameDao.insertGame(connection, choName, hanName, currentTurn);
                pieceDao.insertPieces(connection, gameId, game.getBoard());

                connection.commit();
                return gameId;
            } catch (Exception e) {
                connection.rollback();
                throw new RuntimeException("새 게임 저장 중 롤백 발생", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 커넥션 오류", e);
        }
    }

    @Override
    public void update(Long id, Game game) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                gameDao.updateGame(connection, id, game.getCurrentSide().name(), game.isPlaying());
                pieceDao.deleteAllByGameId(connection, id);
                pieceDao.insertPieces(connection, id, game.getBoard());

                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw new RuntimeException("게임 갱신 중 롤백 발생", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 커넥션 오류", e);
        }
    }

    @Override
    public Optional<Game> findById(Long gameId) {
        return gameDao.findById(gameId)
                .map(gameEntity -> restoreGame(gameEntity, pieceDao.findAllByGameId(gameId)));
    }

    @Override
    public List<GameDto> findAllGames() {
        return gameDao.findAll().stream()
                .map(entity -> new GameDto(entity.id(), entity.choPlayerName(), entity.hanPlayerName(), entity.currentTurn()))
                .toList();
    }

    private Game restoreGame(GameEntity gameEntity, List<PieceEntity> pieceEntities) {
        Board board = restoreBoard(pieceEntities);
        Players players = restorePlayers(gameEntity);
        return new Game(board, players);
    }

    private Board restoreBoard(List<PieceEntity> pieceEntities) {
        Map<Position, Piece> boardMap = pieceEntities.stream()
                .collect(Collectors.toMap(
                        entity -> Position.of(entity.x(), entity.y()),
                        entity -> PieceFactory.create(
                                PieceType.valueOf(entity.pieceType()),
                                Side.valueOf(entity.side())
                        )
                ));
        return new Board(boardMap);
    }

    private Players restorePlayers(GameEntity gameEntity) {
        Name choName = new Name(gameEntity.choPlayerName());
        Name hanName = new Name(gameEntity.hanPlayerName());
        Side currentTurn = Side.valueOf(gameEntity.currentTurn());

        return Players.createRestored(choName, hanName, currentTurn);
    }
}
