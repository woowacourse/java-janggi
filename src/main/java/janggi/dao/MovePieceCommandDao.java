package janggi.dao;

import janggi.db.DBConnector;
import janggi.game.MovePieceCommand;
import janggi.rule.CampType;
import janggi.value.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovePieceCommandDao {

    private static final String COMMAND_ID_COLUMN = "command_id";
    private static final String CAMP_TYPE_COLUMN = "camp_type";
    private static final String TARGET_X_POSITION_COLUMN = "target_piece_x_position";
    private static final String TARGET_Y_POSITION_COLUMN = "target_piece_y_position";
    private static final String DESTINATION_X_POSITION_COLUMN = "destination_x_position";
    private static final String DESTINATION_Y_POSITION_COLUMN = "destination_y_position";

    private final DBConnector DBConnectorImpl;

    public MovePieceCommandDao(DBConnector DBConnectorImpl) {
        this.DBConnectorImpl = DBConnectorImpl;
    }

    public MovePieceCommand addNew(int gameId, CampType campType, Position tagetPiecePosition, Position destination) {
        String query = "INSERT INTO move_piece_commands "
                + "(game_id, camp_type, target_piece_x_position, target_piece_y_position, destination_x_position, destination_y_position) "
                + "VALUES(?, ?, ?, ?, ?, ?)";
        try (
                Connection connection = DBConnectorImpl.getConnection();
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setInt(1, gameId);
            statement.setString(2, campType.toString());
            statement.setInt(3, tagetPiecePosition.x());
            statement.setInt(4, tagetPiecePosition.y());
            statement.setInt(5, destination.x());
            statement.setInt(6, destination.y());
            statement.executeUpdate();
            ResultSet queryResult = statement.getGeneratedKeys();
            queryResult.next();
            int commandId = queryResult.getInt(1);
            return new MovePieceCommand(commandId, campType, tagetPiecePosition, destination);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MovePieceCommand> findAllInGameId(int gameId) {
        String query = "select * from move_piece_commands WHERE move_piece_commands.game_id = ? ORDER BY move_piece_commands.created_at ASC;";
        try (
                Connection connection = DBConnectorImpl.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, gameId);
            ResultSet resultSet = statement.executeQuery();
            return parseMovePieceCommands(resultSet);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<MovePieceCommand> parseMovePieceCommands(ResultSet resultSet) {
        List<MovePieceCommand> commands = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int commandId = resultSet.getInt(COMMAND_ID_COLUMN);
                CampType campType = CampType.valueOf(resultSet.getString(CAMP_TYPE_COLUMN));
                Position targetPiecePosition = new Position(
                        resultSet.getInt(TARGET_X_POSITION_COLUMN),
                        resultSet.getInt(TARGET_Y_POSITION_COLUMN));
                Position destination = new Position(
                        resultSet.getInt(DESTINATION_X_POSITION_COLUMN),
                        resultSet.getInt(DESTINATION_Y_POSITION_COLUMN));
                MovePieceCommand command = new MovePieceCommand(commandId, campType, targetPiecePosition, destination);
                commands.add(command);
            }
            return Collections.unmodifiableList(commands);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
