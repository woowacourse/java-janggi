package infrastructure.dao;

import application.persistence.DbConnector;
import infrastructure.entity.BoardEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDao {

    private final DbConnector dbConnector;

    public BoardDao(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public void save(List<BoardEntity> boardEntities) {
        String query = "INSERT INTO board (piece_name, x, y, country) VALUES(?, ?, ?, ?)";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (BoardEntity boardEntity : boardEntities) {
                preparedStatement.setString(1, boardEntity.getPieceName());
                preparedStatement.setInt(2, boardEntity.getX());
                preparedStatement.setInt(3, boardEntity.getY());
                preparedStatement.setString(4, boardEntity.getCountry());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BoardEntity> findAll() {
        String query = "SELECT * FROM board";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<BoardEntity> boardEntities = new ArrayList<>();
            while (resultSet.next()) {
                boardEntities.add(new BoardEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("piece_name"),
                        resultSet.getInt("x"),
                        resultSet.getInt("y"),
                        resultSet.getString("country")
                ));
            }
            return boardEntities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() {
        String query = "DELETE FROM board";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
