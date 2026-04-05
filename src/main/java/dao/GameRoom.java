package dao;

import domain.player.Team;
import infra.db.DbConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GameRoom {
    private static final String INITIAL_STATUS = "PROGRESS";
    private static final String INSERT_GAME_SQL = "INSERT INTO game(current_turn, status) VALUES(?, ?)";

    public long createGame() {
        try (Connection connection = DbConnectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_GAME_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, Team.CHO.name());
            statement.setString(2, INITIAL_STATUS);
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }
            throw new IllegalStateException("게임 생성 키를 조회하지 못했습니다.");
        } catch (SQLException e) {
            throw new IllegalStateException("게임 생성에 실패했습니다.", e);
        }
    }
}
