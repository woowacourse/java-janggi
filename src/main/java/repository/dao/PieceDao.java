package repository.dao;

import java.sql.SQLException;
import java.util.List;
import repository.entity.PieceEntity;

public interface PieceDao {
    void save(PieceEntity entity) throws SQLException;

    void saveAll(List<PieceEntity> entities) throws SQLException;

    PieceEntity find(int targetRow, int targetColumn) throws SQLException;

    List<PieceEntity> findAll() throws SQLException;

    void update(int targetRow, int targetColumn, int newRow, int newColumn) throws SQLException;

    void delete(int targetRow, int targetColumn) throws SQLException;
}
