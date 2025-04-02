package janggi.dao;

import janggi.db.DBConnector;
import janggi.game.MovePieceCommand;
import janggi.rule.CampType;
import janggi.value.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovePieceCommandDao {

    private static final String CAMP_TYPE_COLUMN = "camp_type";
    private static final String TARGET_X_POSITION_COLUMN = "target_piece_x_position";
    private static final String TARGET_Y_POSITION_COLUMN = "target_piece_y_position";
    private static final String DESTINATION_X_POSITION_COLUMN = "destination_x_position";
    private static final String DESTINATION_Y_POSITION_COLUMN = "destination_y_position";

    private final DBConnector DBConnectorImpl;

    public MovePieceCommandDao(DBConnector DBConnectorImpl) {
        this.DBConnectorImpl = DBConnectorImpl;
    }

    public void addMovePieceCommand(int gameId, MovePieceCommand movePieceCommand) {
        String query = "INSERT INTO move_piece_records "
                + "(game_id, camp_type, target_piece_x_position, target_piece_y_position, destination_x_position, destination_y_position) "
                + "VALUES(?, ?, ?, ?, ?, ?)";
        try (
                Connection connection = DBConnectorImpl.getConnection();
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
        String query = "select * from move_piece_records WHERE move_piece_records.game_id = ? ORDER BY move_piece_records.created_at ASC;";
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
                CampType campType = CampType.valueOf(resultSet.getString(CAMP_TYPE_COLUMN));
                Position targetPiecePosition = new Position(
                        resultSet.getInt(TARGET_X_POSITION_COLUMN),
                        resultSet.getInt(TARGET_Y_POSITION_COLUMN));
                Position destination = new Position(
                        resultSet.getInt(DESTINATION_X_POSITION_COLUMN),
                        resultSet.getInt(DESTINATION_Y_POSITION_COLUMN));
                MovePieceCommand command = new MovePieceCommand(campType, targetPiecePosition, destination);
                commands.add(command);
            }
            return Collections.unmodifiableList(commands);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
