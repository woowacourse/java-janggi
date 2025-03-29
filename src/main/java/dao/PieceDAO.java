package dao;

import domain.piece.character.PieceType;
import domain.piece.character.Team;
import domain.point.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {

    public boolean insert(Connection connection, PieceEntity piece) {
        String sql = """
                INSERT INTO piece (row_index, column_index, piece_type_name, team_name, game_room_name)
                VALUES (?, ?, ?, ?, ?);
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, piece.rowIndex());
            preparedStatement.setInt(2, piece.columnIndex());
            preparedStatement.setString(3, piece.pieceType().name());
            preparedStatement.setString(4, piece.team().name());
            preparedStatement.setString(5, piece.gameRoomName());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            throw new RuntimeException(sql + ": 실행에 실패했습니다.");
        }
    }

    public List<PieceEntity> findByGameRoomName(Connection connection, String gameRoomName) {
        String sql = """               
                SELECT row_index, column_index, piece_type_name, team_name, game_room_name
                FROM piece p
                WHERE p.game_room_name = ?;
                """;
        List<PieceEntity> pieces = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, gameRoomName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    pieces.add(new PieceEntity(
                            null,
                            resultSet.getInt("row_index"),
                            resultSet.getInt("column_index"),
                            PieceType.valueOf(resultSet.getString("piece_type_name")),
                            Team.valueOf(resultSet.getString("team_name")),
                            resultSet.getString("game_room_name")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(sql + ": 실행에 실패했습니다.");
        }
        return pieces;
    }

    public boolean updatePointByGameRoomNameAndPoint(Connection connection,
                                                     String gameRoomName,
                                                     Point oldPoint, Point newPoint) {
        String sql = """
                UPDATE piece 
                SET row_index = ?, column_index = ? 
                WHERE row_index = ? && column_index = ? && game_room_name = ?
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, newPoint.row());
            preparedStatement.setInt(2, newPoint.column());
            preparedStatement.setLong(3, oldPoint.row());
            preparedStatement.setLong(4, oldPoint.column());
            preparedStatement.setString(5, gameRoomName);

            int rowAffected = preparedStatement.executeUpdate();
            return rowAffected == 1;
        } catch (SQLException e) {
            throw new RuntimeException(sql + ": 실행에 실패했습니다.");
        }
    }

    public boolean deleteByGameRoomNameAndPoint(Connection connection, String gameRoomName, Point point) {
        String sql = """
                DELETE FROM piece 
                WHERE game_room_name = ? && row_index = ? && column_index = ?
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, gameRoomName);
            preparedStatement.setInt(2, point.row());
            preparedStatement.setInt(3, point.column());

            int rowAffected = preparedStatement.executeUpdate();
            return rowAffected == 1;
        } catch (SQLException e) {
            throw new RuntimeException(sql + ": 실행에 실패했습니다.");
        }
    }

    public void insertAll(Connection connection, List<PieceEntity> pieceEntities) {
        pieceEntities.forEach(pieceEntity -> insert(connection, pieceEntity));
    }
}
