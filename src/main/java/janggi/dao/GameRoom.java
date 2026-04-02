package janggi.dao;

import janggi.db.SQLManager;
import janggi.dto.GameDto;
import janggi.dto.GameResponseDto;
import janggi.dto.TurnDto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class GameRoom {
    private final SQLManager sqlManager;

    public GameRoom(SQLManager sqlManager) {
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
            e.printStackTrace();
        }
    }

    public int insertGame(Connection connection, GameDto gameDto) {
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
            e.printStackTrace();
        }

        return generatedId;
    }

    public List<GameResponseDto> findAllGames() {
        List<GameResponseDto> gameInfos = new ArrayList<>();
        String sql = "SELECT * FROM GameRoom";

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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public void removeGame(Connection connection, int id) {
        String sql = "DELETE FROM GameRoom WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
