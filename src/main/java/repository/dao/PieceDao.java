package repository.dao;

import java.sql.SQLException;
import java.util.List;
import repository.entity.PieceEntity;

public interface PieceDao {
    Long save(PieceEntity entity) throws SQLException;

    List<Long> saveAll(List<PieceEntity> entities) throws SQLException;

    PieceEntity find(int targetRow, int targetColumn) throws SQLException;

    List<PieceEntity> findAll() throws SQLException;

    void update(int targetRow, int targetColumn, int newRow, int newColumn) throws SQLException;

    void delete(int targetRow, int targetColumn) throws SQLException;
}
