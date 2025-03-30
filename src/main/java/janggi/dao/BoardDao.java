package janggi.dao;

import janggi.board.Board;
import janggi.dao.connection.MysqlConnection;
import java.sql.SQLException;

public class BoardDao {
    private final MysqlConnection DbConnection;

    public BoardDao(MysqlConnection databaseConnection) {
        this.DbConnection = databaseConnection;
    }

    public void addBoard(final Board board) {
        final var query = "INSERT INTO board (turn) VALUES (?)";
        try (final var connection = DbConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, board.getTurn().toString());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("장기판 생성에 실패했습니다.");
        }
    }
}
