package repository;

import data.GameDao;
import data.GameDto;
import data.JanggiMapper;
import data.PieceDao;
import data.PieceDto;
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

            Long gameId;
            GameDto gameDto = janggiMapper.toGameDto(game);

            if (game.id() == null) {
                gameId = gameDao.insert(conn, gameDto);
                game.assignId(gameId);
            } else {
                gameId = game.id();
                gameDao.update(conn, gameDto);
                pieceDao.deleteByBoardId(conn, gameId);
            }

            List<PieceDto> pieceDtos = janggiMapper.toPieceDtos(game.board(), gameId);
            pieceDao.insertAll(conn, gameId, pieceDtos);

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
            Optional<GameDto> gameDto = gameDao.findById(conn, id);
            if (gameDto.isEmpty()) {
                return Optional.empty();
            }

            List<PieceDto> pieceDtos = pieceDao.findByBoardId(conn, id);
            return Optional.of(
                    janggiMapper.toDomain(gameDto.get(), janggiMapper.toBoard(pieceDtos))
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
