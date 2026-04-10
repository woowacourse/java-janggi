package data;

import domain.piece.Camp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardDao {
    public Long insertBoard(Connection connection, boolean gameInProgress, String turn) {
        String sql = "INSERT INTO boards (`game_in_progress`, `turn`) VALUES (?, ?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setBoolean(1, gameInProgress);
            preparedStatement.setString(2, turn);

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
                throw new IllegalArgumentException("ID를 조회할 수 없습니다.");
            }

        } catch (SQLException e) {
            throw new IllegalStateException("데이터 삽입에 실패했습니다.", e);
        }
    }

    public Optional<BoardDto> getBoard(Connection connection, Long boardId) {
        String sql = "SELECT `id`, `game_in_progress`, `turn` FROM boards WHERE `id` = (?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setLong(1, boardId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                Long id = resultSet.getLong("id");
                boolean gameInProgress = resultSet.getBoolean("game_in_progress");
                Camp turn = Camp.valueOf(resultSet.getString("turn"));

                return Optional.of(new BoardDto(id, gameInProgress, turn));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("데이터 조회에 실패했습니다.", e);
        }

    }

    public List<BoardDto> getAllBoards(Connection connection) {
        String sql = "SELECT `id`, `game_in_progress`, `turn` FROM boards WHERE `game_in_progress` = true ORDER BY `id`";
        List<BoardDto> boards = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                boards.add(new BoardDto(
                        resultSet.getLong("id"),
                        resultSet.getBoolean("game_in_progress"),
                        Camp.valueOf(resultSet.getString("turn"))
                ));
            }
            return boards;
        } catch (SQLException e) {
            throw new IllegalStateException("데이터 조회에 실패했습니다.", e);
        }
    }


    public void deleteBoard(Connection connection, Long boardId) {
        String sql = "DELETE FROM boards WHERE `id`=(?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setLong(1, boardId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("데이터 삭제에 실패했습니다.", e);
        }
    }

    public void updateBoard(Connection connection, Long boardId, boolean gameInProgress, String turn) {
        String sql = "UPDATE boards SET `game_in_progress` = (?), `turn` = (?) WHERE `id` = (?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setBoolean(1, gameInProgress);
            preparedStatement.setString(2, turn);
            preparedStatement.setLong(3, boardId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("데이터 수정에 실패했습니다.", e);
        }
    }
}
