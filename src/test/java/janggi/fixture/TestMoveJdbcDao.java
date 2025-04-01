package janggi.fixture;

import janggi.dao.MoveDao;
import janggi.dto.MoveDto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestMoveJdbcDao implements MoveDao {
    private static final String SERVER = "localhost:13307"; // MySQL 서버 주소
    private static final String DATABASE = "janggiTest"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    private final Connection connection;

    public TestMoveJdbcDao() {
        this.connection = getConnection();
    }

    private Connection getConnection() {
        try {
            final Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            return connection;
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<MoveDto> selectAllHistory(final int gameId) {
        final String selectAllQuery = "SELECT start_row, start_column, end_row, end_column FROM testMoveHistory WHERE game_id = ?";
        final List<MoveDto> moveDtos = new ArrayList<>();
        try (final var preparedStatement = connection.prepareStatement(selectAllQuery)) {
            preparedStatement.setInt(1, gameId);
            final ResultSet historyResult = preparedStatement.executeQuery();
            while (historyResult.next()) {
                moveDtos.add(makeHistory(historyResult));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return moveDtos;
    }

    @Override
    public void saveHistory(final MoveDto moveDto, final int gameId) {
        final String historySaveQuery = "INSERT INTO testMoveHistory(game_id, start_row, start_column, end_row, end_column) VALUES (?,?,?,?,?)";
        try (final var preparedStatement = connection.prepareStatement(historySaveQuery)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.setString(2, moveDto.getStartRow());
            preparedStatement.setString(3, moveDto.getStartColumn());
            preparedStatement.setString(4, moveDto.getEndRow());
            preparedStatement.setString(5, moveDto.getEndColumn());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private MoveDto makeHistory(final ResultSet historyResult) throws SQLException {
        final String startRow = historyResult.getString("start_row");
        final String startColumn = historyResult.getString("start_column");
        final String endRow = historyResult.getString("end_row");
        final String endColumn = historyResult.getString("end_column");
        return MoveDto.of(startRow, startColumn, endRow, endColumn);
    }

    public void rollBack() throws SQLException {
        connection.rollback();
        connection.close();
    }
}
