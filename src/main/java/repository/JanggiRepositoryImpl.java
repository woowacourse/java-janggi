package repository;

import config.db.DatabaseConfig;
import model.game.Janggi;

import java.sql.Connection;
import java.sql.SQLException;

public class JanggiRepositoryImpl implements JanggiRepository {

    private final Connection connection;

    public JanggiRepositoryImpl() {
        try (Connection connection = DatabaseConfig.getConnection()) {
            this.connection = connection;
        } catch (SQLException ex) {
            throw new RuntimeException("데이터베이스 연결 불가");
        }
    }

    @Override
    public Long saveGame(Janggi janggi) {
        return 0L;
    }

    @Override
    public void updateGame(Long gameId, Janggi janggi) {

    }
}
