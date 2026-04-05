package janggi.dao;

import janggi.db.SQLManager;
import janggi.dto.GameDto;
import janggi.dto.GameResponseDto;
import janggi.dto.TurnDto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameRoomDao {
    private static final String FAILED_TABLE_INIT_MESSAGE = "게임 방 테이블 생성에 실패하였습니다.";
    private static final String FAILED_GAME_ROOM_INSERT_MESSAGE = "게임 방 데이터 추가에 실패하였습니다.";
    private static final String FAILED_GAME_ROOM_GET_MESSAGE = "게임 방 정보를 가져오는데 실패하였습니다.";
    private static final String FAILED_GAME_ROOM_UPDATE_MESSAGE = "게임 방 데이터 업데이트에 실패하였습니다.";
    private static final String FAILED_GAME_ROOM_DELETE_MESSAGE = "게임 방 데이터 삭제에 실패하였습니다.";

    private final SQLManager sqlManager;

    public GameRoomDao(SQLManager sqlManager) {
        this.sqlManager = sqlManager;
    }

    public void initTable() {
        String sql =
        """
        CREATE TABLE IF NOT EXISTS GameRoom (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            created_at TEXT NOT NULL,
            updated_at TEXT NOT NULL,
            turn INTEGER NOT NULL,
            side TEXT NOT NULL
        )
        """;

        try (Connection conn = sqlManager.ensureConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            if (!conn.getAutoCommit()) conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TABLE_INIT_MESSAGE);
        }
    }

    public int insertGame(Connection connection, GameDto gameDto){
        int generatedId = -1;
        String sql = "INSERT INTO GameRoom (name, created_at, updated_at, turn, side) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, gameDto.name());
            pstmt.setString(2, gameDto.createdAt());
            pstmt.setString(3, gameDto.updatedAt());
            pstmt.setInt(4, gameDto.turn());
            pstmt.setString(5, gameDto.side());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_GAME_ROOM_INSERT_MESSAGE);
        }

        return generatedId;
    }

    public List<GameResponseDto> findAllGames() {
        List<GameResponseDto> gameInfos = new ArrayList<>();
        String sql = "SELECT id, name, created_at, updated_at, side, turn FROM GameRoom";
        try (Connection conn = sqlManager.ensureConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                gameInfos.add(new GameResponseDto(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("created_at"),
                        rs.getString("updated_at"),
                        rs.getString("side"),
                        rs.getInt("turn")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_GAME_ROOM_GET_MESSAGE);
        }
        return gameInfos;
    }

    public void updateGameTurn(Connection connection, int gameId, TurnDto turnDto) {
        String sql = "UPDATE GameRoom SET turn = ?, side = ?, updated_at = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, turnDto.turn());
            pstmt.setString(2, turnDto.side());
            pstmt.setString(3, java.time.LocalDateTime.now().toString());
            pstmt.setInt(4, gameId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_GAME_ROOM_UPDATE_MESSAGE);
        }
    }

    public void removeGame(Connection connection, int id) {
        String sql = "DELETE FROM GameRoom WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(FAILED_GAME_ROOM_DELETE_MESSAGE);
        }
    }
}
