package data;

import domain.piece.Camp;

import java.sql.*;
import java.util.Optional;

public class BoardDao {
    public Long insertBoard(Connection connection, String turn) {
        String sql = "INSERT INTO boards (`turn`) VALUES (?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, turn);

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
        String sql = "SELECT FROM boards WHERE `id`=(?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setLong(1, boardId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                Long id = resultSet.getLong("id");
                boolean gameInProgress = resultSet.getBoolean("gameInProgress");
                Camp turn = Camp.valueOf(resultSet.getString("camp"));

                return Optional.of(new BoardDto(id, gameInProgress, turn));
            }
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

    public void updateTurn(Connection connection, Long boardId, String turn) {
        String sql = "UPDATE boards SET `turn` = (?) WHERE `id` = (?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, turn);
            preparedStatement.setLong(2, boardId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("데이터 수정에 실패했습니다.", e);
        }
    }
}
