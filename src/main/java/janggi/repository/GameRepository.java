package janggi.repository;

import janggi.db.DBConnector;
import janggi.db.GameDao;
import janggi.db.PieceDao;
import janggi.domain.GameContext;
import janggi.domain.board.Board;
import janggi.domain.team.TurnManager;
import java.sql.Connection;
import java.sql.SQLException;

public class GameRepository {
    private final DBConnector dbConnector;

    public GameRepository(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public void saveGame(GameContext gameContext) {
        try (Connection connection = dbConnector.getConnection()) {
            PieceDao.deletePiecesTable(connection);
            GameDao.deleteGameTable(connection);
            GameDao.insertCurrentTurn(connection, gameContext);
            PieceDao.insertPiece(connection, gameContext);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류", e);
        }
    }

    public GameContext loadPreviousGame() {
        try (Connection connection = dbConnector.getConnection()) {
            TurnManager turnManager = new TurnManager(GameDao.selectCurrentTurn(connection));
            Board board = new Board(PieceDao.selectPieceMap(connection));
            return new GameContext(turnManager, board);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류", e);
        }
    }

    public void deleteGame() {
        try (Connection connection = dbConnector.getConnection()) {
            PieceDao.deletePiecesTable(connection);
            GameDao.deleteGameTable(connection);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류", e);
        }
    }

    public boolean hasGameData() {
        try (Connection connection = dbConnector.getConnection()) {
            return GameDao.hasGameData(connection);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류", e);
        }
    }
}
