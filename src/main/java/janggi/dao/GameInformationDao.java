package janggi.dao;

import janggi.db.DBConnector;
import janggi.game.GameInformation;
import janggi.rule.GameState;
import janggi.rule.PieceAssignType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameInformationDao {

    private static final String GAME_ID_COLUMN = "game_id";
    private static final String GAME_TITLE_COLUMN = "title";
    private static final String CHO_ASSIGN_COLUMN = "cho_assign_type";
    private static final String HAN_ASSIGN_COLUMN = "han_assign_type";
    private static final String GAME_STATE_COLUMN = "game_state";

    private final DBConnector DBConnectorImpl;

    public GameInformationDao(DBConnector DBConnectorImpl) {
        this.DBConnectorImpl = DBConnectorImpl;
    }

    public GameInformation addNew(String title, PieceAssignType choAssignType, PieceAssignType hanAssignType) {
        String query = "INSERT INTO games (title, cho_assign_type, han_assign_type, game_state) VALUES(?, ?, ?, ?)";
        try (
                Connection connection = DBConnectorImpl.getConnection();
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, title);
            statement.setString(2, choAssignType.toString());
            statement.setString(3, hanAssignType.toString());
            statement.setString(4, GameState.PLAY.toString());
            statement.executeUpdate();
            ResultSet queryResult = statement.getGeneratedKeys();
            queryResult.next();
            int gameId = queryResult.getInt(1);
            return new GameInformation(gameId, title, choAssignType, hanAssignType, GameState.PLAY);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public GameInformation findById(int gameId) {
        String query = "SELECT * FROM games WHERE games.game_id = ?";
        try (
                Connection connection = DBConnectorImpl.getConnection();
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, gameId);
            ResultSet resultSet = statement.executeQuery();
            return parseGameInformation(resultSet).getFirst();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<GameInformation> findAllInPlaying() {
        String query = "select * from games where games.game_state = ?";
        try (
                Connection connection = DBConnectorImpl.getConnection();
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, GameState.PLAY.toString());
            ResultSet resultSet = statement.executeQuery();
            return parseGameInformation(resultSet);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateGameStateToEnd(int gameId) {
        String query = "UPDATE games SET game_state = ? WHERE game_id = ?;";
        try (
                Connection connection = DBConnectorImpl.getConnection();
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, GameState.END.toString());
            statement.setInt(2, gameId);
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<GameInformation> parseGameInformation(ResultSet resultSet) {
        List<GameInformation> informations = new ArrayList<>();
        try {
            while (resultSet.next()) {
                GameInformation gameInformation = new GameInformation(
                        resultSet.getInt(GAME_ID_COLUMN),
                        resultSet.getString(GAME_TITLE_COLUMN),
                        PieceAssignType.valueOf(resultSet.getString(CHO_ASSIGN_COLUMN)),
                        PieceAssignType.valueOf(resultSet.getString(HAN_ASSIGN_COLUMN)),
                        GameState.valueOf(resultSet.getString(GAME_STATE_COLUMN))
                );
                informations.add(gameInformation);
            }
            return Collections.unmodifiableList(informations);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
