package janggi.dao;

import janggi.game.MovePieceCommand;
import janggi.setting.CampType;
import janggi.value.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovePieceCommandDao {

    private static final String CAMP_TYPE_FIELD = "campType";
    private static final String TARGET_X_POSITION_FIELD = "targetPieceXPosition";
    private static final String TARGET_Y_POSITION_FIELD = "targetPieceYPosition";
    private static final String DESTINATION_X_POSITION_FIELD = "destinationXPosition";
    private static final String DESTINATION_Y_POSITION_FIELD = "destinationYPosition";

    private final DatabaseConnector databaseConnector;

    public MovePieceCommandDao(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public void addMovePieceCommand(int gameId, MovePieceCommand movePieceCommand) {
        String query = "INSERT INTO movePieceRecords "
                + "(gameId, campType, targetPieceXPosition, targetPieceYPosition, destinationXPosition, destinationYPosition) "
                + "VALUES(?, ?, ?, ?, ?, ?)";
        try (
                Connection connection = databaseConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, gameId);
            statement.setString(2, movePieceCommand.getCampType().toString());
            statement.setInt(3, movePieceCommand.getTargetPiecePosition().x());
            statement.setInt(4, movePieceCommand.getTargetPiecePosition().y());
            statement.setInt(5, movePieceCommand.getDestination().x());
            statement.setInt(6, movePieceCommand.getDestination().y());
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MovePieceCommand> finaAllMovePieceCommand(int gameId) {
        String query = "select * from movePieceRecords WHERE movePieceRecords.gameId = ? ORDER BY movePieceRecords.created_at ASC;";
        try (
                Connection connection = databaseConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, gameId);
            ResultSet resultSet = statement.executeQuery();
            return parseMovePieceCommands(resultSet);
        } catch (final SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private List<MovePieceCommand> parseMovePieceCommands(ResultSet resultSet) {
        List<MovePieceCommand> commands = new ArrayList<>();
        try {
            while (resultSet.next()) {
                CampType campType = CampType.valueOf(resultSet.getString(CAMP_TYPE_FIELD));
                Position targetPiecePosition = new Position(
                        resultSet.getInt(TARGET_X_POSITION_FIELD),
                        resultSet.getInt(TARGET_Y_POSITION_FIELD));
                Position destination = new Position(
                        resultSet.getInt(DESTINATION_X_POSITION_FIELD),
                        resultSet.getInt(DESTINATION_Y_POSITION_FIELD));
                MovePieceCommand command = new MovePieceCommand(campType, targetPiecePosition, destination);
                commands.add(command);
            }
            return Collections.unmodifiableList(commands);
        } catch (final SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
