package dao;

import domain.piece.character.PieceType;
import domain.piece.character.Team;
import domain.point.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {

    private boolean executeSql(Connection connection, String sql, List<Object> params) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(sql + ": 실행에 실패했습니다.");
        }
    }

    public boolean insert(Connection connection, PieceEntity piece) {
        String sql = """
                INSERT INTO piece (row_index, column_index, piece_type_name, team_name, game_room_name)
                VALUES (?, ?, ?, ?, ?);
                """;
        return executeSql(connection, sql,
                List.of(piece.rowIndex(), piece.columnIndex(),
                        piece.pieceType().name(), piece.team().name(), piece.gameRoomName()));
    }

    public void insertAll(Connection connection, List<PieceEntity> pieceEntities) {
        pieceEntities.forEach(pieceEntity -> insert(connection, pieceEntity));
    }

    public boolean updatePointByGameRoomNameAndPoint(Connection connection,
                                                     String gameRoomName,
                                                     Point oldPoint, Point newPoint) {
        String sql = """
                UPDATE piece
                SET row_index = ?, column_index = ?
                WHERE row_index = ? && column_index = ? && game_room_name = ?
                """;
        return executeSql(connection, sql,
                List.of(newPoint.row(), newPoint.column(), oldPoint.row(), oldPoint.column(), gameRoomName));
    }

    public boolean deleteByGameRoomNameAndPoint(Connection connection, String gameRoomName, Point point) {
        String sql = """
                DELETE FROM piece
                WHERE game_room_name = ? && row_index = ? && column_index = ?
                """;
        return executeSql(connection, sql,
                List.of(gameRoomName, point.row(), point.column()));
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
}
