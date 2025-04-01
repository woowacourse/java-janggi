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
import queue.DelayedQuery;
import queue.MessageQueue;

public class PieceDao {

    public void insert(PieceDto piece) {
        String sql = """
                INSERT INTO piece (row_index, column_index, piece_type, team, game_room)
                VALUES (?, ?, ?, ?, ?);
                """;
        addToMessageQueue(sql, List.of(piece.rowIndex(), piece.columnIndex(),
                piece.pieceType().name(), piece.team().name(), piece.gameRoomName()));
    }

    public void insertAll(List<PieceDto> pieceEntities) {
        pieceEntities.forEach(this::insert);
    }

    public void updatePointByGameRoomNameAndPoint(String gameRoomName,
                                                  Point oldPoint, Point newPoint) {
        String sql = """
                UPDATE piece
                SET row_index = ?, column_index = ?
                WHERE row_index = ? AND column_index = ? AND game_room = ?
                """;
        addToMessageQueue(sql,
                List.of(newPoint.row(), newPoint.column(), oldPoint.row(), oldPoint.column(), gameRoomName));
    }

    public void deleteByGameRoomNameAndPoint(String gameRoomName, Point point) {
        String sql = """
                DELETE FROM piece
                WHERE game_room = ? AND row_index = ? AND column_index = ?
                """;
        addToMessageQueue(sql, List.of(gameRoomName, point.row(), point.column()));
    }

    public List<PieceDto> findByGameRoomName(Connection connection, String gameRoomName) {
        String sql = """               
                SELECT row_index, column_index, piece_type, team, game_room
                FROM piece p
                WHERE p.game_room = ?;
                """;
        List<PieceDto> pieces = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, gameRoomName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    pieces.add(new PieceDto(
                            null,
                            resultSet.getInt("row_index"),
                            resultSet.getInt("column_index"),
                            PieceType.valueOf(resultSet.getString("piece_type")),
                            Team.valueOf(resultSet.getString("team")),
                            resultSet.getString("game_room")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 조회에 실패했습니다");
        }
        return pieces;
    }

    private void addToMessageQueue(String sql, List<Object> params) {
        MessageQueue.addLast(new DelayedQuery(sql, params));
    }
}
