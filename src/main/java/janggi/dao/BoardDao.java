package janggi.dao;

import janggi.board.Board;
import janggi.board.Pieces;
import janggi.dao.connection.MysqlConnection;
import janggi.piece.Team;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BoardDao {
    private final MysqlConnection DbConnection;

    public BoardDao(MysqlConnection databaseConnection) {
        this.DbConnection = databaseConnection;
    }

    public String addBoard(final Board board) {
        final var query = "INSERT INTO board (board_id, turn) VALUES (?, ?)";
        String boardId = UUID.randomUUID().toString();
        try (final var connection = DbConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, boardId);
            preparedStatement.setString(2, board.getTurn().toString());
            preparedStatement.executeUpdate();
            return boardId;
        } catch (final SQLException e) {
            throw new RuntimeException("장기판 생성에 실패했습니다.");
        }
    }

    public void updateBoardTurn(final String boardId, Team team) {
        final var query = "UPDATE board SET turn = (?) WHERE board_id = (?)";
        try (final var connection = DbConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, team.toString());
            preparedStatement.setString(2, boardId);
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

    public int countBoard() {
        final var query = "SELECT count(board_id) as board_count FROM board";
        try (final var connection = DbConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("board_count");
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException("장기판 조회에 실패했습니다.");
        }
        return 0;
    }

    public Board find(Pieces pieces) {
        final var query = "SELECT turn FROM board";
        try (final var connection = DbConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String turn = resultSet.getString("turn");
                return new Board(pieces, Team.fromString(turn));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException("장기판 조회에 실패했습니다.");
        }
        return null;
    }
}
