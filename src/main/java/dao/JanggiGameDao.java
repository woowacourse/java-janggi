package dao;

import database.MysqlConnectionManager;
import dto.GameStatus;
import dto.PieceDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.board.Country;
import model.pieces.PieceType;

public class JanggiGameDao {
    private final MysqlConnectionManager manager;

    public JanggiGameDao(MysqlConnectionManager manager) {
        this.manager = manager;
    }

    public int createGame(Country turn, String roomName) {
        String sql = "INSERT INTO janggi_game (turn, room_name) VALUES (?, ?)";
        try (Connection conn = manager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, turn.name());
            pstmt.setString(2, roomName);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new RuntimeException("[ERROR] 게임 ID 생성에 실패했습니다.");
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결 중 오류가 발생하여 게임을 생성할 수 없습니다: ", e);
        }
    }

    public void saveGame(int gameId, Country turn, List<PieceDto> pieces) {
        String updateTurnSql = "UPDATE janggi_game SET turn = ? WHERE id = ?";
        String deletePiecesSql = "DELETE FROM piece WHERE janggi_game_id = ?";
        String insertPieceSql = "INSERT INTO piece (janggi_game_id, row_pos, col_pos, country, type) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = manager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(updateTurnSql)) {
                pstmt.setString(1, turn.name());
                pstmt.setInt(2, gameId);
                pstmt.executeUpdate();
            }

            try (PreparedStatement pstmt = conn.prepareStatement(deletePiecesSql)) {
                pstmt.setInt(1, gameId);
                pstmt.executeUpdate();
            }

            try (PreparedStatement pstmt = conn.prepareStatement(insertPieceSql)) {
                for (PieceDto piece : pieces) {
                    pstmt.setInt(1, gameId);
                    pstmt.setInt(2, piece.row());
                    pstmt.setInt(3, piece.column());
                    pstmt.setString(4, piece.country().name());
                    pstmt.setString(5, piece.pieceType().name());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 저장 중 DB 오류가 발생했습니다: ", e);
        }
    }

    public int countByRoomName(String roomName) {
        String sql = "SELECT COUNT(*) FROM janggi_game WHERE room_name = ?";
        try (Connection conn = manager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, roomName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 이름별 방의 개수를 가져오던 중 DB 오류가 발생했습니다: ", e);
        }
    }

    public List<String> getRoomNameList() {
        List<String> roomNameList = new ArrayList<>();
        String sql = "SELECT room_name FROM janggi_game";

        try (Connection conn = manager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String roomName = rs.getString("room_name");
                roomNameList.add(roomName);
            }

            return roomNameList;
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임방 이름을 가져오는 중 DB 오류가 발생했습니다: ", e);
        }
    }

    public GameStatus findStatusByRoomName(String roomName) {
        String sql = "SELECT id, turn, room_name FROM janggi_game WHERE room_name = ?";
        try (Connection conn = manager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, roomName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new GameStatus(
                        rs.getInt("id"),
                        Country.fromCountry(rs.getString("turn")),
                        rs.getString("room_name")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 정보를 불러오는 중 DB 오류가 발생했습니다: ", e);
        }
    }

    public List<PieceDto> loadPiecesByGameId(int gameId) {
        List<PieceDto> pieces = new ArrayList<>();
        String sql = "SELECT * FROM piece WHERE janggi_game_id = ?";

        try (Connection conn = manager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pieces.add(new PieceDto(
                        rs.getInt("row_pos"),
                        rs.getInt("col_pos"),
                        Country.fromCountry(rs.getString("country")),
                        PieceType.fromType(rs.getString("type"))
                ));
            }
            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임을 불러오는 중 DB 오류가 발생했습니다: ", e);
        }
    }
}
