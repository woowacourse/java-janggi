package janggi.repository;

import javax.sql.DataSource;
import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import janggi.domain.piece.PlacedPiece;
import janggi.domain.piece.camp.CampType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class PieceRepository {

    private final DataSource dataSource;

    public PieceRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public long save(PlacedPiece placedPiece) {
        return insert("INSERT INTO pieces(game_room_id, camp, piece_type, row_position, col_position) VALUES(?, ?, ?, ?, ?)",
                placedPiece.getGameRoomId(),
                placedPiece.getCampType().name(),
                placedPiece.getPieceRule().name(),
                placedPiece.getRowPosition(),
                placedPiece.getColPosition());
    }

    private long insert(String sql, Object... params) {
        try {
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            return -1L;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Position, Piece> findByGameRoomId(long gameRoomId) {
        return executeQuery("SELECT * FROM pieces WHERE game_room_id = ?",
                rs -> {
                    Map<Position, Piece> pieces = new HashMap<>();
                    while (rs.next()) {
                        Position position = new Position(rs.getInt("row_position"), rs.getInt("col_position"));
                        Piece piece = new Piece(PieceRule.valueOf(rs.getString("piece_type")), CampType.valueOf(rs.getString("camp")));
                        pieces.put(position, piece);
                    }
                    return pieces;
                },
                gameRoomId
        );
    }

    public PlacedPiece findByGameIdAndPosition(long gameRoomId, int row, int column) {
        return executeQuery("SELECT * FROM pieces WHERE game_room_id = ? AND row_position = ? AND col_position = ?",
                rs -> {
                    if (rs.next()) {
                        return new PlacedPiece(
                                rs.getLong("piece_id"),
                                rs.getLong("game_room_id"),
                                CampType.valueOf(rs.getString("camp")),
                                PieceRule.valueOf(rs.getString("piece_type")),
                                rs.getInt("row_position"),
                                rs.getInt("col_position")
                        );
                    }
                    throw new RuntimeException(String.format("%d번 게임방 내 %행%d열 기물을 찾을 수 없습니다", gameRoomId, row, column));
                },
                gameRoomId, row, column
        );
    }

    public void update(PlacedPiece placedPiece) {
        update("UPDATE pieces SET game_room_id = ?, camp = ?, piece_type = ?, row_position = ?, col_position = ? WHERE piece_id = ?",
                placedPiece.getGameRoomId(), placedPiece.getCampType().name(), placedPiece.getPieceRule().name()
                , placedPiece.getRowPosition(), placedPiece.getColPosition(), placedPiece.getPlacedPieceId());
    }

    public void delete(PlacedPiece placedPiece) {
        update("DELETE FROM pieces WHERE piece_id = ?",
                placedPiece.getPlacedPieceId());
    }

    private void update(String sql, Object... params) {
        try {
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T executeQuery(String sql, ResultSetMapper<T> mapper, Object... params) {
        try {
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            ResultSet rs = preparedStatement.executeQuery();
            return mapper.map(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
