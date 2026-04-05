package repository;

import dto.PieceDto;
import dto.PieceSnapshot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JanggiJdbcRepository implements JanggiRepository {

    private static final String COLUMN = "column";
    private static final String ROW = "row";
    private static final String PIECE_TYPE = "piece_type";
    private static final String TEAM = "team";

    @Override
    public void createTable(Connection connection) throws SQLException {
        connection.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS board (
                                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                                    game_id INTEGER NOT NULL,
                                    column INTEGER NOT NULL,
                                    row INTEGER NOT NULL,
                                    piece_type TEXT NOT NULL,
                                    team TEXT NOT NULL,
                                    FOREIGN KEY (game_id) REFERENCES game(id)
                )
                """).executeUpdate();
    }

    public List<PieceDto> findPiecesByGameId(Connection connection, int gameId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT column, row, piece_type, team FROM board WHERE game_id = ?");
        stmt.setInt(1, gameId);
        ResultSet rs = stmt.executeQuery();
        List<PieceDto> pieces = new ArrayList<>();
        while (rs.next()) {
            pieces.add(new PieceDto(
                    rs.getInt(COLUMN),
                    rs.getInt(ROW),
                    rs.getString(PIECE_TYPE),
                    rs.getString(TEAM)
            ));
        }
        return pieces;
    }

    @Override
    public void save(Connection connection, int gameId, List<PieceSnapshot> pieceSnapshots) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("""
                INSERT INTO board (game_id, column, row, piece_type, team)
                VALUES (?, ?, ?, ?, ?)
                """);
        for (PieceSnapshot snapshot : pieceSnapshots) {
            stmt.setInt(1, gameId);
            stmt.setInt(2, snapshot.column());
            stmt.setInt(3, snapshot.row());
            stmt.setString(4, snapshot.pieceType());
            stmt.setString(5, snapshot.team());
            stmt.addBatch();
        }
        stmt.executeBatch();
    }
}
