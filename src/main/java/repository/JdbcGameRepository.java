package repository;

import data.GameDao;
import data.GameEntity;
import data.JanggiMapper;
import data.PieceDao;
import data.PieceEntity;
import domain.Game;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcGameRepository implements GameRepository {
    private final GameDao gameDao;
    private final PieceDao pieceDao;
    private final JanggiMapper janggiMapper;
    private final String url;
    private final String username;
    private final String password;

    public JdbcGameRepository(GameDao gameDao, PieceDao pieceDao, JanggiMapper janggiMapper,
                              String url, String username, String password) {
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
        this.janggiMapper = janggiMapper;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public void save(Game game) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(false);

            GameEntity persistedGameEntity;
            GameEntity gameEntity = janggiMapper.toGameDto(game);

            if (game.id() == null) {
                persistedGameEntity = gameDao.insert(conn, gameEntity);
                game.assignId(persistedGameEntity.id());
            } else {
                persistedGameEntity = gameDao.update(conn, gameEntity);
                pieceDao.deleteByGameId(conn, persistedGameEntity.id());
            }

            List<PieceEntity> pieceEntities = janggiMapper.toPieceDtos(game.board(), persistedGameEntity.id());
            pieceDao.insertAll(conn, persistedGameEntity.id(), pieceEntities);

            conn.commit();
        } catch (Exception e) {
            rollback(conn);
            throw new IllegalArgumentException("[ERROR] 게임 저장 중 오류가 발생했습니다.", e);
        } finally {
            close(conn);
        }
    }

    @Override
    public Optional<Game> findById(Long id) {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            Optional<GameEntity> gameDto = gameDao.findById(conn, id);
            if (gameDto.isEmpty()) {
                return Optional.empty();
            }

            List<PieceEntity> pieceEntities = pieceDao.findByGameId(conn, id);
            return Optional.of(
                    janggiMapper.toDomain(gameDto.get(), janggiMapper.toBoard(pieceEntities))
            );
        } catch (SQLException e) {
            throw new IllegalArgumentException("[ERROR] 게임 조회 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(false);

            gameDao.deleteById(conn, id);

            conn.commit();
        } catch (Exception e) {
            rollback(conn);
            throw new IllegalArgumentException("[ERROR] 게임 삭제 중 오류가 발생했습니다. id=" + id, e);
        } finally {
            close(conn);
        }
    }

    private void rollback(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            conn.rollback();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 트랜잭션 롤백 중 오류가 발생했습니다.", e);
        }
    }

    private void close(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            conn.close();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 커넥션 종료 중 오류가 발생했습니다.", e);
        }
    }
}
