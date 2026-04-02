package janggi.repository;

import janggi.config.DBConnection;
import janggi.entity.BoardCellEntity;
import janggi.global.EntityMapper;

public class BoardCellRepositoryImpl implements BoardCellRepository {

    private static final String TABLE_NAME = "board_cells";

    private final DBConnection dbConnection;

    public BoardCellRepositoryImpl(final DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public long save(final BoardCellEntity boardCellEntity) {
        final String sql = String.format(
            "INSERT INTO %s (\"row\", \"column\", piece_type, team, board_id) VALUES (%d, %d, '%s', '%s', %d)",
            TABLE_NAME, boardCellEntity.row(), boardCellEntity.column(),
            boardCellEntity.piece_type(), boardCellEntity.team(), boardCellEntity.board_id());
        return dbConnection.executeUpdate(sql);
    }

    @Override
    public BoardCellEntity findById(final long targetId) {
        final String sql = String.format("SELECT id, \"row\", \"column\", piece_type, team, board_id "
            + "FROM %s WHERE id = %d", TABLE_NAME, targetId);
        final EntityMapper<BoardCellEntity> mapper = resultSet -> {
            int id = 0;
            int row = 0;
            int column = 0;
            String piece_type = "";
            String team = "";
            int board_id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt(1);
                row = resultSet.getInt(2);
                column = resultSet.getInt(3);
                piece_type = resultSet.getString(4);
                team = resultSet.getString(5);
                board_id = resultSet.getInt(6);
            }
            return new BoardCellEntity(id, row, column, piece_type, team, board_id);
        };
        return dbConnection.executeSelect(sql, mapper);
    }
}
