package dao;

import config.JdbcUtils;
import dto.TurnDto;
import java.sql.SQLException;

public class TurnDao {

    public void saveTurnCountry(TurnDto turnDto) {
        final var insertSQL = "INSERT INTO turn (country_name) VALUES (?)";

        try (var connection = JdbcUtils.getConnection();
             var preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, turnDto.country());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("DB 저장 중 오류 발생", e);
        }
    }

    public void deleteAll() {
        final var deleteSQL = "DELETE FROM turn";
        try (var connection = JdbcUtils.getConnection();
             var preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB 삭제 중 오류 발생", e);
        }
    }

    public TurnDto loadTurnCountry() {
        final var selectSQL = "SELECT country_name FROM turn LIMIT 1";

        try (var connection = JdbcUtils.getConnection();
             var preparedStatement = connection.prepareStatement(selectSQL);
             var resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return new TurnDto(resultSet.getString("country_name"));
            } else {
                throw new RuntimeException("저장된 게임 정보가 없습니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB에서 턴 정보를 불러오는 중 오류 발생", e);
        }
    }
}
