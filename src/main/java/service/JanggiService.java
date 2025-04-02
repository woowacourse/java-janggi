package service;

import dao.BoardDao;
import dao.BoardDaoImpl;
import dao.TurnDao;
import dao.TurnDaoImpl;
import db.DatabaseConnector;
import domain.board.Point;
import domain.piece.Piece;
import domain.piece.Team;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class JanggiService {

    private final DatabaseConnector databaseConnector;
    private final TurnDao turnDao;
    private final BoardDao boardDao;

    public JanggiService(final DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
        this.turnDao = new TurnDaoImpl(databaseConnector);
        this.boardDao = new BoardDaoImpl(databaseConnector);
    }

    public boolean hasSavedGame() {
        return boardDao.hasRecords();
    }

    public Map<Point, Piece> findBoard() {
        return boardDao.load();
    }

    public Team findTurn() {
        return turnDao.load();
    }

    public void saveAllData(final Map<Point, Piece> board, final Team turn) {
        try (Connection connection = databaseConnector.getConnection()) {
            connection.setAutoCommit(false);

            removeAllData();
            turnDao.save(connection, turn);
            for (Point point : board.keySet()) {
                boardDao.save(connection, point, board.get(point));
            }

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 작업이 실패했습니다. " + e.getMessage(), e);
        }
    }

    public void removeAllData() {
        try (Connection connection = databaseConnector.getConnection()) {
            connection.setAutoCommit(false);

            turnDao.remove(connection);
            boardDao.removeAll(connection);

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 작업이 실패했습니다. " + e.getMessage(), e);
        }
    }
}
