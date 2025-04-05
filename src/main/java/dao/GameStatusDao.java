package dao;

import database.ConnectionManager;
import domain.board.GameStatus;
import domain.piece.Country;

public class GameStatusDao {

    private final ConnectionManager manager;

    public GameStatusDao(ConnectionManager manager) {
        this.manager = manager;
    }

    public void saveTurn(Country country, int turnCount) {
        String sql = "REPLACE INTO game_status (id, current_turn, turn_count) VALUES (1, ?, ?)";

        try (var conn = manager.getConnection();
             var pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, country.name());
            pstmt.setInt(2, turnCount);
            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new IllegalArgumentException("현재 턴 저장 중 오류가 발생했습니다.", e);
        }
    }

    public GameStatus loadTurn() {
        String sql = "SELECT current_turn, turn_count FROM game_status WHERE id = 1";

        try (var conn = manager.getConnection();
             var pstmt = conn.prepareStatement(sql);
             var rs = pstmt.executeQuery()) {

            if (rs.next()) {
                Country country = Country.valueOf(rs.getString("current_turn"));
                int turnCount = rs.getInt("turn_count");
                return new GameStatus(country, turnCount);
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("게임 상태를 불러오는 데 실패했습니다.", e);
        }

        return null;
    }
}
