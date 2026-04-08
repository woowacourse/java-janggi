package janggi.infrastructure.repository;

import janggi.config.DBConnection;
import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.infrastructure.entity.BoardCellEntity;
import janggi.infrastructure.entity.EntityMapper;
import java.util.List;
import java.util.Optional;

public class BoardCellRepositoryImpl implements BoardCellRepository {

    private static final String TABLE_NAME = "board_cells";
    private static final EntityMapper<BoardCellEntity> ENTITY_MAPPER = resultSet -> {
        long id = resultSet.getInt(1);
        int row = resultSet.getInt(2);
        int column = resultSet.getInt(3);
        String pieceType = resultSet.getString(4);
        String team = resultSet.getString(5);
        int gameId = resultSet.getInt(6);
        return new BoardCellEntity(id, row, column, pieceType, team, gameId);
    };

    private final DBConnection dbConnection;

    public BoardCellRepositoryImpl(final DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public long save(final BoardCellEntity boardCellEntity) {
        final String sql = String.format(
            "INSERT INTO %s (row_pos, column_pos, piece_type, team, game_id) VALUES (?, ?, ?, ?, ?)",
            TABLE_NAME);
        return dbConnection.executeUpdate(sql, boardCellEntity.row(), boardCellEntity.column(),
            boardCellEntity.pieceType(), boardCellEntity.team(), boardCellEntity.gameId());
    }

    @Override
    public List<Long> saveAll(final List<BoardCellEntity> boardCellEntities) {
        final String sql = String.format(
            "INSERT INTO %s (row_pos, column_pos, piece_type, team, game_id) VALUES (?, ?, ?, ?, ?)",
            TABLE_NAME);
        final List<Object[]> parametersList = boardCellEntities.stream()
            .map(entity -> new Object[]{
                entity.row(),
                entity.column(),
                entity.pieceType(),
                entity.team(),
                entity.gameId()
            }).toList();
        return dbConnection.executeBatchUpdate(sql, parametersList);
    }

    @Override
    public Optional<BoardCellEntity> findByPositionAndGameId(final Position position, final long gameId) {
        final String sql = String.format(
            "SELECT id, row_pos, column_pos, piece_type, team, game_id "
                + "FROM %s WHERE row_pos = ? AND column_pos = ? AND game_id = ?", TABLE_NAME);

        return dbConnection.executeSelect(sql, ENTITY_MAPPER, position.getRow(),
            position.getColumn(), gameId);
    }

    @Override
    public List<BoardCellEntity> findAllByGameId(final long gameId) {
        final String sql = String.format(
            "SELECT id, row_pos, column_pos, piece_type, team, game_id FROM %s WHERE game_id = ?",
            TABLE_NAME);

        return dbConnection.executeSelectAll(sql, ENTITY_MAPPER, gameId);
    }

    @Override
    public long upsertByPositionAndGameId(final Position position, final long gameId, final Piece piece) {
        if (findByPositionAndGameId(position, gameId).isEmpty()) {
            return save(BoardCellEntity.from(gameId, position, piece));
        }
        final String sql = String.format(
            "UPDATE %s SET piece_type = ?, team = ? WHERE row_pos = ? AND column_pos = ? AND game_id = ?",
            TABLE_NAME);

        return dbConnection.executeUpdate(sql, piece.getPieceType().name(),
            piece.getTeamType().name(),
            position.getRow(), position.getColumn(), gameId);
    }

    @Override
    public void deleteByPositionAndGameId(final Position position, final long gameId) {
        final String sql = String.format(
            "DELETE FROM %s WHERE row_pos = ? AND column_pos = ? AND game_id = ?",
            TABLE_NAME);
        dbConnection.executeDelete(sql, position.getRow(), position.getColumn(), gameId);
    }
}
