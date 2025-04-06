package dao.piece;

import dao.converter.PieceDto;
import domain.piece.character.PieceType;
import domain.piece.character.Team;
import domain.point.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

public class PieceDaoImpl implements PieceDao {

    public boolean insert(Connection connection, PieceDto piece) {
        String sql = """
                INSERT INTO piece (row_index, column_index, piece_type, team, game_room_name)
                VALUES (?, ?, ?, ?, ?);
                """;
        int rowAffected = executeQuery(connection, sql,
                List.of(piece.rowIndex(), piece.columnIndex(),
                        piece.pieceType().name(), piece.team().name(), piece.gameRoomName()));
        return rowAffected == 1;
    }

    public boolean insertAll(Connection connection, List<PieceDto> pieceDtos) {
        boolean isSuccess = true;
        for (PieceDto pieceDto : pieceDtos) {
            isSuccess &= insert(connection, pieceDto);
        }
        return isSuccess;
    }

    public boolean updatePointByGameRoomNameAndPoint(Connection connection,
                                                     String gameRoomName, Point oldPoint, Point newPoint) {
        String sql = """
                UPDATE piece
                SET row_index = ?, column_index = ?
                WHERE row_index = ? AND column_index = ? AND game_room_name = ?;
                """;
        int rowAffected = executeQuery(connection, sql,
                List.of(newPoint.row(), newPoint.column(), oldPoint.row(), oldPoint.column(), gameRoomName));
        return rowAffected == 1;
    }

    public boolean deleteByGameRoomNameAndPoint(Connection connection,
                                                String gameRoomName, Point point) {
        String sql = """
                DELETE FROM piece
                WHERE game_room_name = ? AND row_index = ? AND column_index = ?;
                """;
        int rowAffected = executeQuery(connection, sql, List.of(gameRoomName, point.row(), point.column()));
        return rowAffected == 1;
    }

    public List<PieceDto> findByGameRoomName(Connection connection, String gameRoomName) {
        String sql = """               
                SELECT row_index, column_index, piece_type, team, game_room_name
                FROM piece p
                WHERE p.game_room_name = ?;
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
                            resultSet.getString("game_room_name")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 조회에 실패했습니다. : " + e.getMessage());
        }
        return pieces;
    }

    private int executeQuery(Connection connection, String sql, List<Object> params) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
            int rowAffected = preparedStatement.executeUpdate();
            return rowAffected;
        } catch (SQLSyntaxErrorException e) {
            throw new IllegalArgumentException("[ERROR]: 잘못된 형식의 쿼리문입니다. " + sql);
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] DB 연결이 끊어졌습니다.");
        }
    }
}
