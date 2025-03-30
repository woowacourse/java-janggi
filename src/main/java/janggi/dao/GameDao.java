package janggi.dao;

import janggi.game.MovePieceCommand;
import janggi.setting.GameState;
import janggi.setting.PieceAssignType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GameDao {

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

    public int addNewGame(String title, PieceAssignType choAssignType, PieceAssignType hanAssignType) {
        String query = "INSERT INTO games (gameTitle, choAssignType, hanAssignType, gameState) VALUES(?, ?, ?, ?)";
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, choAssignType.toString());
            preparedStatement.setString(3, hanAssignType.toString());
            preparedStatement.setString(4, GameState.PLAY.toString());
            preparedStatement.executeUpdate();
            ResultSet queryResult = preparedStatement.getGeneratedKeys();
            queryResult.next();
            return queryResult.getInt(1);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMovePieceCommand(int gameId, MovePieceCommand movePieceCommand) {
        String query =
                "INSERT INTO movePieceRecords (gameId, targetPieceXPostion, targetPieceYPostion, destinationXPostion, destinationYPostion) "
                        + "VALUES(?, ?, ?, ?, ?)";
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, gameId);
            preparedStatement.setInt(2, movePieceCommand.getTargetPiecePosition().x());
            preparedStatement.setInt(3, movePieceCommand.getTargetPiecePosition().y());
            preparedStatement.setInt(4, movePieceCommand.getDestination().x());
            preparedStatement.setInt(5, movePieceCommand.getDestination().y());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
