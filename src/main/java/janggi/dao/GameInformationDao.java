package janggi.dao;

import janggi.game.GameInformation;
import janggi.setting.GameState;
import janggi.setting.PieceAssignType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameInformationDao {

    private static final String GAME_ID_FIELD = "gameId";
    private static final String GAME_TITLE_FIELD = "gameTitle";
    private static final String CHO_ASSIGN_FIELD = "choAssignType";
    private static final String HAN_ASSIGN_FIELD = "hanAssignType";
    private static final String GAME_STATE_FIELD = "gameState";

    private final DatabaseConnector databaseConnector;

    public GameInformationDao(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public int addNew(String title, PieceAssignType choAssignType, PieceAssignType hanAssignType) {
        String query = "INSERT INTO games (gameTitle, choAssignType, hanAssignType, gameState) VALUES(?, ?, ?, ?)";
        try (
                Connection connection = databaseConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, title);
            statement.setString(2, choAssignType.toString());
            statement.setString(3, hanAssignType.toString());
            statement.setString(4, GameState.PLAY.toString());
            statement.executeUpdate();
            ResultSet queryResult = statement.getGeneratedKeys();
            queryResult.next();
            return queryResult.getInt(1);
        } catch (final SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public List<GameInformation> findAllInPlaying() {
        String query = "select * from games where games.gameState = ?";
        try (
                Connection connection = databaseConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, GameState.PLAY.toString());
            ResultSet resultSet = statement.executeQuery();
            return parseGameInformation(resultSet);
        } catch (final SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void updateGameStateToEnd(int gameId) {
        String query = "UPDATE games SET gameState = ? WHERE gameId = ?;";
        try (
                Connection connection = databaseConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, GameState.END.toString());
            statement.setInt(2, gameId);
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private List<GameInformation> parseGameInformation(ResultSet resultSet) {
        List<GameInformation> informations = new ArrayList<>();
        try {
            while (resultSet.next()) {
                GameInformation gameInformation = new GameInformation(
                        resultSet.getInt(GAME_ID_FIELD),
                        resultSet.getString(GAME_TITLE_FIELD),
                        PieceAssignType.valueOf(resultSet.getString(CHO_ASSIGN_FIELD)),
                        PieceAssignType.valueOf(resultSet.getString(HAN_ASSIGN_FIELD)),
                        GameState.valueOf(resultSet.getString(GAME_STATE_FIELD))
                );
                informations.add(gameInformation);
            }
            return Collections.unmodifiableList(informations);
        } catch (final SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
