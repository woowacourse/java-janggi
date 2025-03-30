package janggi.dao;

import janggi.game.MovePieceCommand;
import janggi.setting.CampType;
import janggi.value.Position;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovePieceCommandDao {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            return null;
        }
    }

    public void addMovePieceCommand(int gameId, MovePieceCommand movePieceCommand) {
        String query =
                "INSERT INTO movePieceRecords "
                        + "(gameId, campType, targetPieceXPostion, targetPieceYPostion, destinationXPostion, destinationYPostion) "
                        + "VALUES(?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, gameId);
            preparedStatement.setString(2, movePieceCommand.getCampType().toString());
            preparedStatement.setInt(3, movePieceCommand.getTargetPiecePosition().x());
            preparedStatement.setInt(4, movePieceCommand.getTargetPiecePosition().y());
            preparedStatement.setInt(5, movePieceCommand.getDestination().x());
            preparedStatement.setInt(6, movePieceCommand.getDestination().y());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MovePieceCommand> finaAllMovePieceCommand(int gameId) {
        String query = "select * from movePieceRecords WHERE movePieceRecords.gameId = ? ORDER BY movePieceRecords.created_at ASC;";
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<MovePieceCommand> queryResult = new ArrayList<>();
            while (resultSet.next()) {
                MovePieceCommand command = new MovePieceCommand(
                        CampType.valueOf(resultSet.getString("campType")),
                        new Position(resultSet.getInt("targetPieceXPostion"), resultSet.getInt("targetPieceYPostion")),
                        new Position(resultSet.getInt("destinationXPostion"), resultSet.getInt("destinationYPostion"))
                );
                queryResult.add(command);
            }
            return Collections.unmodifiableList(queryResult);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
