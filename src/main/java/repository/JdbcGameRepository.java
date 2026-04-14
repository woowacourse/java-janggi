package repository;

import data.GameDao;
import data.GameEntity;
import data.JanggiMapper;
import data.BoardDao;
import data.BoardEntity;
import domain.Game;
import domain.position.Position;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcGameRepository implements GameRepository {
    private final GameDao gameDao;
    private final BoardDao boardDao;
    private final JanggiMapper janggiMapper;
    private final String url;
    private final String username;
    private final String password;

    public JdbcGameRepository(GameDao gameDao, BoardDao boardDao, JanggiMapper janggiMapper,
                              String url, String username, String password) {
        this.gameDao = gameDao;
        this.boardDao = boardDao;
        this.janggiMapper = janggiMapper;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public void save(Game game) {
        if (game.id() != null) {
            throw new IllegalArgumentException("[ERROR] 이미 저장된 게임입니다.");
        }

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(false);

            GameEntity gameEntity = janggiMapper.toGameDto(game);
            GameEntity persistedGameEntity = gameDao.insert(conn, gameEntity);
            game.assignId(persistedGameEntity.id());

            List<BoardEntity> pieceEntities = janggiMapper.toPieceDtos(game.board(), persistedGameEntity.id());
            boardDao.insertAll(conn, persistedGameEntity.id(), pieceEntities);

            conn.commit();
        } catch (Exception e) {
            rollback(conn);
            throw new IllegalArgumentException("[ERROR] 게임 저장 중 오류가 발생했습니다.", e);
        } finally {
            close(conn);
        }
    }

    @Override
    public void updateTurn(Game game, Position from, Position to) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(false);

            GameEntity gameEntity = janggiMapper.toGameDto(game);
            gameDao.update(conn, gameEntity);
            boardDao.deleteByPosition(conn, game.id(), to);
            boardDao.updatePosition(conn, game.id(), from, to);

            conn.commit();
        } catch (Exception e) {
            rollback(conn);
            throw new IllegalArgumentException("[ERROR] 턴 저장 중 오류가 발생했습니다.", e);
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

            List<BoardEntity> pieceEntities = boardDao.findByGameId(conn, id);
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
