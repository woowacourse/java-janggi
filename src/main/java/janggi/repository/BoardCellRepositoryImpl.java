package janggi.repository;

import janggi.config.DBConnection;
import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.team.TeamType;
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
    public boolean existsByPosition(final Position position) {
        final String sql = String.format(
            "SELECT id, \"row\", \"column\", piece_type, team, board_id FROM %s WHERE \"row\" = %d AND \"column\" = %d",
            TABLE_NAME, position.getRow(),
            position.getColumn());
        final EntityMapper<BoardCellEntity> mapper = getBoardCellEntityEntityMapper();
        final BoardCellEntity boardCellEntity = dbConnection.executeSelect(sql, mapper);
        return boardCellEntity.id() != 0;
    }

    private static EntityMapper<BoardCellEntity> getBoardCellEntityEntityMapper() {
        return resultSet -> {
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
    }

    @Override
    public BoardCellEntity findById(final long targetId) {
        final String sql = String.format(
            "SELECT id, \"row\", \"column\", piece_type, team, board_id "
                + "FROM %s WHERE id = %d", TABLE_NAME, targetId);
        final EntityMapper<BoardCellEntity> mapper = getBoardCellEntityEntityMapper();
        return dbConnection.executeSelect(sql, mapper);
    }

    @Override
    public long updateByPosition(final Position position, final Piece piece) {
        final String sql = String.format("UPDATE %s SET piece_type = '%s', team = '%s' WHERE \"row\" = %d AND \"column\" = %d",
            TABLE_NAME, piece.getPieceType(), piece.getTeamType(), position.getRow(), position.getColumn());

        return dbConnection.executeUpdate(sql);
    }
}
