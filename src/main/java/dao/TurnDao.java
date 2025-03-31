package dao;

import dto.TurnDto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TurnDao {

    private static final String URL = "jdbc:mysql://localhost:13306/janggi?useSSL=false&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public void saveTurnCountry(TurnDto turnDto) {
        final var insertSQL = "INSERT INTO turn (country_name) VALUES (?)";
        deleteAll();
        try (var connection = getConnection()) {

            try (var preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setString(1, turnDto.country());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 저장 중 오류 발생", e);
        }
    }

    public void deleteAll() {
        final var deleteSQL = "DELETE FROM turn";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("DB 삭제 중 오류 발생", e);
        }

    }

    public TurnDto loadTurnCountry() {
        final var selectSQL = "SELECT country_name FROM turn LIMIT 1";

        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(selectSQL);
             final var resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                String country = resultSet.getString("country_name");
                return new TurnDto(country);
            } else {
                throw new RuntimeException("저장된 게임 정보가 없습니다.");
            }

        } catch (final SQLException e) {
            throw new RuntimeException("DB에서 턴 정보를 불러오는 중 오류 발생", e);
        }
    }


}




