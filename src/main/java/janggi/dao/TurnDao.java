package janggi.dao;

import janggi.db.DBConnection;
import janggi.model.Board;
import janggi.model.Color;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import janggi.model.piece.Cannon;
import janggi.model.piece.Chariot;
import janggi.model.piece.Elephant;
import janggi.model.piece.Guard;
import janggi.model.piece.Horse;
import janggi.model.piece.King;
import janggi.model.piece.Piece;
import janggi.model.piece.Soldier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map.Entry;

public class TurnDao {
    public static final String TEAM_COLOR = "currentTeamColor";
    private final DBConnection dbConnection;

    public TurnDao(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void updateCurrentTurn(Color color) {
        dbConnection.executeUpdate("DELETE FROM turn");
        dbConnection.executeUpdate("INSERT INTO turn VALUES (?)", color.name());
    }

    public Color findCurrentTurn() {
        try (PreparedStatement statement = dbConnection.generatePreparedStatement("SELECT * FROM turn");
             ResultSet resultSet = statement.executeQuery()
        ) {
            resultSet.next();
            return Color.from(resultSet.getString(TEAM_COLOR));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
