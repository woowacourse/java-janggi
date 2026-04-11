package repository;

import domain.board.Board;
import domain.board.Position;
import domain.game.JanggiGame;
import domain.piece.Piece;
import domain.state.GameState;
import dto.JanggiGameDto;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;

public class JdbcJanggiGameRepository implements JanggiGameRepository {

    private final DataSource dataSource;
    private final JdbcGameDao gameDao;
    private final JdbcPieceDao pieceDao;

    public JdbcJanggiGameRepository(
            final DataSource dataSource,
            final JdbcGameDao gameDao,
            final JdbcPieceDao pieceDao
    ) {
        this.dataSource = dataSource;
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    @Override
    public Long save(final JanggiGame janggiGame) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Long gameId = gameDao.save(connection, janggiGame);
                pieceDao.saveAll(connection, gameId, janggiGame.getPieces());
                connection.commit();
                return gameId;
            } catch (SQLException exception) {
                connection.rollback();
                throw exception;
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("게임을 저장하는 중 오류가 발생했습니다.");
        }
    }

    @Override
    public List<JanggiGameDto> findAll() {
        try (Connection connection = dataSource.getConnection()) {
            return gameDao.findAll(connection);
        } catch (SQLException exception) {
            throw new IllegalStateException("게임 목록을 불러오는 중 오류가 발생했습니다.");
        }
    }

    @Override
    public Optional<JanggiGame> findById(final Long gameId) {
        try (Connection connection = dataSource.getConnection()) {
            Optional<GameState> gameState = gameDao.findGameState(connection, gameId);
            if (gameState.isEmpty()) {
                return Optional.empty();
            }

            Map<Position, Piece> pieces = pieceDao.findByGameId(connection, gameId);
            Board board = Board.init(pieces);
            return Optional.of(JanggiGame.of(board, gameState.get()));
        } catch (SQLException exception) {
            throw new IllegalStateException("게임을 불러오는 중 오류가 발생했습니다. gameId=" + gameId);
        }
    }

    @Override
    public void update(final Long gameId, final JanggiGame janggiGame) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                gameDao.update(connection, gameId, janggiGame);
                pieceDao.deleteByGameId(connection, gameId);
                pieceDao.saveAll(connection, gameId, janggiGame.getPieces());
                connection.commit();
            } catch (SQLException exception) {
                connection.rollback();
                throw exception;
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("게임을 업데이트하는 중 오류가 발생했습니다. gameId=" + gameId);
        }
    }
}
