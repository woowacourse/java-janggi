package database.dao;

import java.sql.*;

public class JdbcBoardDao implements BoardDao{

    private static final String INSERT_BOARD_QUERY = """
            insert into board () values ();
            """;

    @Override
    public Long save(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOARD_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }



}
