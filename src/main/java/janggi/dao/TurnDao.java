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

    public void updateCurrentTurn(Color color) {
        DBConnection dbConnection = new DBConnection();
        try (Connection janggiConnection = dbConnection.getJanggiConnection()) {
            PreparedStatement statement = janggiConnection.prepareStatement("UPDATE turn set currentTeamColor = ?");
            statement.setString(1, color.name());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Color findCurrentTurn() {
        DBConnection dbConnection = new DBConnection();
        try (Connection janggiConnection = dbConnection.getJanggiConnection()) {
            ResultSet resultSet = janggiConnection.prepareStatement("SELECT * FROM turn").executeQuery();
            resultSet.next();
            return Color.from(resultSet.getString("currentTeamColor"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
