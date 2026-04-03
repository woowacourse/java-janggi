package janggi.repository;

import janggi.config.DBConnection;
import janggi.entity.BoardEntity;
import janggi.global.EntityMapper;
import java.util.Optional;

public class BoardRepositoryImpl implements BoardRepository {

    private static final String TABLE_NAME = "boards";
    private final DBConnection dbConnection;

    public BoardRepositoryImpl(final DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public long save(final BoardEntity boardEntity) {
        final String sql = String.format("INSERT INTO %s (name) VALUES ('%s')",
            TABLE_NAME, boardEntity.name());
        return dbConnection.executeUpdate(sql).getFirst();
    }

    @Override
    public boolean existsById(final long id) {
        return findById(id).isPresent();
    }

    @Override
    public Optional<BoardEntity> findById(final long targetId) {
        final String sql = String.format("SELECT id, name FROM %s WHERE id = %d",
            TABLE_NAME, targetId);
        final EntityMapper<BoardEntity> mapper = resultSet -> {
            long id = resultSet.getLong(1);
            String name = resultSet.getString(2);
            return BoardEntity.from(id, name);

        };

        return dbConnection.executeSelect(sql, mapper);
    }

    @Override
    public boolean deleteById(final long id) {
        final String sql = String.format("DELETE FROM %s WHERE id = %d", TABLE_NAME, id);
        return dbConnection.executeDelete(sql);
    }
}
