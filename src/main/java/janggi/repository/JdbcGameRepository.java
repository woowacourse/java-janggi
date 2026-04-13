package janggi.repository;

import janggi.db.ConnectionFactory;
import janggi.db.DatabaseException;
import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.game.JanggiGame;
import janggi.domain.game.MoveResult;
import janggi.domain.piece.Name;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.Team;
import janggi.repository.dao.GameDao;
import janggi.repository.dao.PieceDao;
import janggi.repository.data.GameData;
import janggi.repository.data.PieceData;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcGameRepository implements GameRepository {
    private final ConnectionFactory connectionFactory;
    private final GameDao gameDao;
    private final PieceDao pieceDao;
    private final PieceFactory pieceFactory;

    public JdbcGameRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        this.gameDao = new GameDao();
        this.pieceDao = new PieceDao();
        this.pieceFactory = new PieceFactory();
    }

    @Override
    public long saveNewGame(JanggiGame janggiGame) {
        try (Connection connection = connectionFactory.create()) {
            return saveNewGameWithTransaction(connection, janggiGame);
        } catch (SQLException e) {
            throw new DatabaseException("새 게임 저장에 실패했습니다.", e);
        }
    }

    @Override
    public Optional<SavedGame> findPlayingGame() {
        try (Connection connection = connectionFactory.create()) {
            Optional<GameData> savedGame = gameDao.findPlayingGame(connection);
            if (savedGame.isEmpty()) {
                return Optional.empty();
            }

            long savedGameId = savedGame.get().id();
            Board board = findBoard(connection, savedGameId);
            Team currentTurnTeam = Team.valueOf(savedGame.get().turn());
            JanggiGame janggiGame = JanggiGame.restore(board, currentTurnTeam);
            return Optional.of(new SavedGame(savedGameId, janggiGame));
        } catch (SQLException e) {
            throw new DatabaseException("진행 중인 게임 조회에 실패했습니다.", e);
        }
    }

    @Override
    public void applyMoveResult(long savedGameId, MoveResult moveResult) {
        try (Connection connection = connectionFactory.create()) {
            applyMoveResultWithTransaction(connection, savedGameId, moveResult);
        } catch (SQLException e) {
            throw new DatabaseException("수 반영 저장에 실패했습니다.", e);
        }
    }

    private long saveNewGameWithTransaction(Connection connection, JanggiGame janggiGame) throws SQLException {
        try {
            connection.setAutoCommit(false);

            long savedGameId = gameDao.save(connection, createNewGameData(janggiGame));
            pieceDao.saveAll(connection, createPieceData(savedGameId, janggiGame));
            connection.commit();
            return savedGameId;
        } catch (SQLException e) {
            rollback(connection);
            throw e;
        }
    }

    private void applyMoveResultWithTransaction(Connection connection, long savedGameId, MoveResult moveResult)
            throws SQLException {
        try {
            connection.setAutoCommit(false);
            gameDao.update(connection, createUpdatedGameData(savedGameId, moveResult));
            if (moveResult.captured()) {
                pieceDao.deleteOn(connection, savedGameId, moveResult.to());
            }
            pieceDao.move(connection, savedGameId, moveResult.from(), moveResult.to());
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw e;
        }
    }

    private Board findBoard(Connection connection, long savedGameId) throws SQLException {
        Map<Position, Piece> board = new LinkedHashMap<>();
        List<PieceData> piecesData = pieceDao.findByGameId(connection, savedGameId);

        for (PieceData pieceData : piecesData) {
            Position position = new Position(pieceData.column(), pieceData.row());
            Piece piece = pieceFactory.create(Name.valueOf(pieceData.name()), Team.valueOf(pieceData.team()));
            board.put(position, piece);
        }
        return new Board(board);
    }

    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DatabaseException("트랜잭션 롤백에 실패했습니다.", e);
        }
    }

    private GameData createNewGameData(JanggiGame janggiGame) {
        return new GameData(null, janggiGame.currentTurnTeam().name(), findStatus(janggiGame));
    }

    private GameData createUpdatedGameData(long savedGameId, MoveResult moveResult) {
        return new GameData(savedGameId, moveResult.currentTurnTeam().name(), moveResult.gameStatus().name());
    }

    private List<PieceData> createPieceData(long savedGameId, JanggiGame janggiGame) {
        return janggiGame.getBoard().getBoard().entrySet().stream()
                .map(entry -> createPieceData(savedGameId, entry.getKey(), entry.getValue()))
                .toList();
    }

    private PieceData createPieceData(long savedGameId, Position piecePosition, Piece piece) {
        return new PieceData(
                savedGameId,
                piecePosition.y(),
                piecePosition.x(),
                piece.getName().name(),
                piece.getTeam().name()
        );
    }

    private String findStatus(JanggiGame janggiGame) {
        if (janggiGame.isPlaying()) {
            return "PLAYING";
        }
        return "FINISHED";
    }
}
