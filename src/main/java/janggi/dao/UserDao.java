package janggi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class UserDao {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    public Connection getConnection() {
        // 드라이버 연결
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void addUser(final User user) {
        final var query = "INSERT INTO user VALUES(?, ?)";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.userId());
            preparedStatement.setString(2, user.name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findByUserId(final String userId) {
        final var query = "SELECT * FROM user WHERE user_id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userId);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getString("user_id"),
                        resultSet.getString("name")
                );
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public User updateUsername(final String userId, final String name) {
        // 1. Update 쿼리 실행
        final var updateQuery = "UPDATE user SET name = ? WHERE user_id = ?";
        try (final var connection = getConnection();
             final var updateStatement = connection.prepareStatement(updateQuery)) {

            updateStatement.setString(1, name); // 이름을 'mint'로 설정
            updateStatement.setString(2, userId); // 매개변수로 받은 userId 사용

            int rowsAffected = updateStatement.executeUpdate(); // executeUpdate() 사용

            if (rowsAffected > 0) {
                // 2. 업데이트된 레코드 조회
                final var selectQuery = "SELECT user_id, name FROM user WHERE user_id = ?";
                try (final var selectStatement = connection.prepareStatement(selectQuery)) {
                    selectStatement.setString(1, userId);

                    final var resultSet = selectStatement.executeQuery();
                    if (resultSet.next()) {
                        return new User(
                                resultSet.getString("user_id"),
                                resultSet.getString("name")
                        );
                    }
                }
            }
        } catch (final SQLException e) {
            System.err.println("SQL 에러: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return null; // 업데이트 실패 또는 레코드를 찾지 못한 경우
    }

    public User deleteUser(final String userId) {
        final var selectQuery = "SELECT user_id, name FROM user WHERE user_id = ?";
        User userToDelete = null;
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, userId);
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userToDelete = new User(
                        resultSet.getString("user_id"),
                        resultSet.getString("name")
                );
            }
            // 사용자 삭제
            final var deleteQuery = "DELETE FROM user WHERE user_id = ?";
            try (final var deleteStatement = connection.prepareStatement(deleteQuery)) {
                deleteStatement.setString(1, userId);
                int rowsAffected = deleteStatement.executeUpdate(); // executeQuery() 대신 executeUpdate() 사용

                if (rowsAffected > 0) {
                    return userToDelete; // 삭제된 사용자 정보 반환
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}

