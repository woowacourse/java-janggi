package janggi.repository;

import janggi.config.DBConnection;
import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.entity.BoardCellEntity;
import janggi.global.EntityMapper;
import java.util.List;
import java.util.Optional;

public class BoardCellRepositoryImpl implements BoardCellRepository {

    private static final String TABLE_NAME = "board_cells";

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
            boardCellEntity.piece_type(), boardCellEntity.team(), boardCellEntity.game_id());
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
                entity.piece_type(),
                entity.team(),
                entity.game_id()
            }).toList();
        return dbConnection.executeBatchUpdate(sql, parametersList);
    }

    @Override
    public boolean existsByPosition(final Position position) {
        final String sql = String.format(
            "SELECT id, row_pos, column_pos, piece_type, team, game_id FROM %s WHERE row_pos = ? AND column_pos = ?",
            TABLE_NAME);
        final EntityMapper<BoardCellEntity> mapper = getBoardCellEntityEntityMapper();
        final Optional<BoardCellEntity> boardCellEntity = dbConnection.executeSelect(sql, mapper,
            position.getRow(), position.getColumn());
        return boardCellEntity.isPresent();
    }

    @Override
    public Optional<BoardCellEntity> findByPosition(Position position) {
        final String sql = String.format(
            "SELECT id, row_pos, column_pos, piece_type, team, game_id "
                + "FROM %s WHERE row_pos = ? AND column_pos = ?", TABLE_NAME);
        final EntityMapper<BoardCellEntity> mapper = getBoardCellEntityEntityMapper();

        return dbConnection.executeSelect(sql, mapper, position.getRow(), position.getColumn());
    }

    @Override
    public List<BoardCellEntity> findAllByGameId(final long gameId) {
        final String sql = String.format(
            "SELECT id, row_pos, column_pos, piece_type, team, game_id FROM %s WHERE game_id = %d",
            TABLE_NAME, gameId);
        final EntityMapper<BoardCellEntity> mapper = getBoardCellEntityEntityMapper();

        return dbConnection.executeSelectAll(sql, mapper);
    }

    private static EntityMapper<BoardCellEntity> getBoardCellEntityEntityMapper() {
        return resultSet -> {
            long id = resultSet.getInt(1);
            int row = resultSet.getInt(2);
            int column = resultSet.getInt(3);
            String piece_type = resultSet.getString(4);
            String team = resultSet.getString(5);
            int board_id = resultSet.getInt(6);
            return new BoardCellEntity(id, row, column, piece_type, team, board_id);
        };
    }

    @Override
    public long upsertByPosition(final long gameId, final Position position, final Piece piece) {
        if (!existsByPosition(position)) {
            return save(BoardCellEntity.from(gameId, position, piece));
        }
        final String sql = String.format(
            "UPDATE %s SET piece_type = ?, team = ? WHERE row_pos = ? AND column_pos = ?",
            TABLE_NAME);

        return dbConnection.executeUpdate(sql, piece.getPieceType().name(),
            piece.getTeamType().name(),
            position.getRow(), position.getColumn());
    }

    @Override
    public void deleteByPosition(final long gameId, final Position position) {
        final String sql = String.format(
            "DELETE FROM %s WHERE game_id = ? AND row_pos = ? AND column_pos = ?",
            TABLE_NAME);
        dbConnection.executeDelete(sql, gameId, position.getRow(), position.getColumn());
    }
}
