package dao;

import dto.PieceSnapshot;
import dto.UnfinishedGameInfo;
import infrastructure.TransactionContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JanggiGameDao {

    public long insertGame(List<PieceSnapshot> pieces) throws SQLException {
        long gameId = insert();
        insertPieces(gameId, pieces);
        return gameId;
    }

    public long updateGame(long gameId, List<PieceSnapshot> pieces) throws SQLException {
        insertPieces(gameId, pieces);
        return gameId;
    }

    private long insert() throws SQLException {
        String sql = "INSERT INTO game (created_at) VALUES (NOW())";

        try (PreparedStatement ps = TransactionContext.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                return keys.getLong(1);
            }
            throw new SQLException("game INSERT 후 id 획득 실패");
        }
    }

    private void insertPieces(long gameId, List<PieceSnapshot> pieces) throws SQLException {
        String sql = "INSERT INTO piece (game_id, pos_row, pos_col, piece_name, team) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = TransactionContext.getPreparedStatement(sql)) {
            for (PieceSnapshot piece : pieces) {
                ps.setLong(1, gameId);
                ps.setInt(2, piece.row());
                ps.setInt(3, piece.col());
                ps.setString(4, piece.name());
                ps.setString(5, piece.team());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public List<PieceSnapshot> findAllPieces(long gameId) throws SQLException {
        String sql = "SELECT pos_row, pos_col, piece_name, team FROM piece WHERE game_id = ?";

        try (PreparedStatement ps = TransactionContext.getPreparedStatement(sql)) {
            ps.setLong(1, gameId);

            ResultSet rs = ps.executeQuery();
            List<PieceSnapshot> pieces = new ArrayList<>();
            while (rs.next()) {
                pieces.add(PieceSnapshot.from(
                        rs.getInt("pos_row"),
                        rs.getInt("pos_col"),
                        rs.getString("piece_name"),
                        rs.getString("team")
                ));
            }
            return pieces;
        }
    }

    public Optional<Long> findLatestUnfinishedGameId() throws SQLException {
        String sql = "SELECT game_id FROM game_state WHERE is_finished = false ORDER BY last_played_at DESC LIMIT 1";

        try (PreparedStatement ps =  TransactionContext.getPreparedStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return Optional.of(rs.getLong("game_id"));
            }
            return Optional.empty();
        }
    }

    public List<UnfinishedGameInfo> findUnfinishedGameInfos() throws SQLException {
        String sql = "SELECT game_id, last_played_at FROM game_state WHERE is_finished = false ORDER BY last_played_at ASC";

        try (PreparedStatement ps = TransactionContext.getPreparedStatement(sql)){
            ResultSet rs = ps.executeQuery();
            List<UnfinishedGameInfo> unfinishedGames = new ArrayList<>();
            while (rs.next()) {
                unfinishedGames.add(UnfinishedGameInfo.from(
                        rs.getLong("game_id"),
                        rs.getTimestamp("last_played_at").toLocalDateTime()
                ));
            }
            return unfinishedGames;
        }
    }
}
