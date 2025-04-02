package janggi.dao;

import janggi.exception.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class BoardDao {

    private final Connection connection;

    public BoardDao(Connection connection) {
        this.connection = connection;
    }

    public Long create(BoardVO board) {
        String sql = "INSERT INTO board (team_code, current_camp, winner_camp) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, board.teamCode());
            statement.setString(2, board.currentCamp());
            statement.setString(3, board.winnerCamp());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
                throw new SQLException("생성된 board의 ID를 가져올 수 없습니다.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Board를 생성하는데 실패했습니다.", e);
        }
    }

    public Optional<BoardVO> findById(Long boardId) {
        String sql = "SELECT * FROM board WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, boardId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapToBoardVO(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException("ID로 Board를 조회하는데 실패했습니다.", e);
        }
    }

    public List<Long> findAllBoardIds() {
        String sql = "SELECT id FROM board ORDER BY id DESC";
        List<Long> boardIds = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                boardIds.add(resultSet.getLong("id"));
            }
            return boardIds;
        } catch (SQLException e) {
            throw new DataAccessException("모든 Board ID를 조회하는 데 실패했습니다.", e);
        }
    }

    public void updateCurrentCamp(Long boardId, String currentCamp) {
        String sql = "UPDATE board SET current_camp = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, currentCamp);
            statement.setLong(2, boardId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("현재 차례를 업데이트하는데 실패했습니다.", e);
        }
    }

    private BoardVO mapToBoardVO(ResultSet resultSet) throws SQLException {
        return new BoardVO(
                resultSet.getLong("id"),
                resultSet.getString("team_code"),
                resultSet.getString("current_camp"),
                resultSet.getString("winner_camp")
        );
    }
}
