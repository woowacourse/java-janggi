package janggi.dao;

import janggi.board.Board;
import janggi.dao.connection.MysqlConnection;
import janggi.piece.Team;
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

    public void updateBoardTurn(final int boardId, Team team) {
        final var query = "UPDATE board SET turn = (?) WHERE board_id = (?)";
        try (final var connection = DbConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, team.toString());
            preparedStatement.setString(2, String.valueOf(boardId));
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("순서 변경에 실패했습니다.");
        }
    }

    public void deleteAllBoards() {
        final var query = "DELETE from board";
        try (final var connection = DbConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("장기판 삭제에 실패했습니다.");
        }
    }
}
