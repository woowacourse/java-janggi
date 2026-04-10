package repository;

import domain.board.BoardPiece;
import domain.game.Game;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcGameRepository implements GameRepository {
    private final String url;
    private final GameMapper gameMapper;
    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public JdbcGameRepository(String url) {
        this.url = url;
        this.gameMapper = new GameMapper();
        this.gameDao = new GameDao();
        this.pieceDao = new PieceDao();
        initialize();
    }

    @Override
    public void save(Game game) {
        try (Connection connection = DriverManager.getConnection(url)) {
            connection.setAutoCommit(false);

            try {
                long gameId = gameDao.upsert(connection, game.currentTurn(), game.isFinished());
                pieceDao.replace(connection, gameId, gameMapper.toBoardPieces(game));
                connection.commit();
            } catch (SQLException exception) {
                connection.rollback();
                throw new IllegalStateException("[ERROR] 게임 저장에 실패했습니다.");
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] DB 연결에 실패했습니다.");
        }
    }


    @Override
    public Optional<Game> findInProgressGame() {
        try (Connection connection = DriverManager.getConnection(url)) {
            Optional<GameData> foundGame = gameDao.findInProgressLastGame(connection);

            if (foundGame.isEmpty()) {
                return Optional.empty();
            }

            GameData gameData = foundGame.get();
            List<BoardPiece> boardPieces = pieceDao.findByGameId(connection, gameData.id());

            return Optional.of(gameMapper.toGame(
                    gameData.currentTurn(),
                    gameData.finished(),
                    boardPieces
            ));
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] 게임 조회에 실패했습니다.");
        }
    }

    private void initialize() {
        try (Connection connection = DriverManager.getConnection(url)) {
            gameDao.createTable(connection);
            pieceDao.createTable(connection);
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] 테이블 생성에 실패했습니다.");
        }
    }
}
