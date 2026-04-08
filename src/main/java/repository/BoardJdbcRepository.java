package repository;

import dto.PieceDto;
import dto.PieceSnapshot;
import exception.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BoardJdbcRepository implements BoardRepository {

    private static final String COLUMN = "column";
    private static final String ROW = "row";
    private static final String PIECE_TYPE = "piece_type";
    private static final String TEAM = "team";

    private static final String CREATE_TABLE_FAIL_MESSAGE = "board 테이블 생성에 실패했습니다.";
    private static final String FIND_PIECES_FAIL_MESSAGE = "기물 목록 조회에 실패했습니다.";
    private static final String SAVE_FAIL_MESSAGE = "기물 저장에 실패했습니다.";
    private static final String UPDATE_FROM_FAIL_MESSAGE = "출발 위치 기물 초기화에 실패했습니다.";
    private static final String FIND_PIECE_FAIL_MESSAGE = "위치로 기물 조회에 실패했습니다.";
    private static final String UPDATE_TO_FAIL_MESSAGE = "도착 위치 기물 업데이트에 실패했습니다.";

    @Override
    public void createTable(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS board (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        game_id INTEGER NOT NULL,
                        column INTEGER NOT NULL,
                        row INTEGER NOT NULL,
                        piece_type TEXT NOT NULL,
                        team TEXT NOT NULL,
                        FOREIGN KEY (game_id) REFERENCES game(id)
                    )
                    """);
        } catch (SQLException e) {
            throw new DataAccessException(CREATE_TABLE_FAIL_MESSAGE, e);
        }
    }

    @Override
    public List<PieceDto> findPiecesByGameId(Connection connection, int gameId) {
        try {
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
        } catch (SQLException e) {
            throw new DataAccessException(FIND_PIECES_FAIL_MESSAGE, e);
        }
    }

    @Override
    public void save(Connection connection, int gameId, List<PieceSnapshot> pieceSnapshots) {
        try {
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
        } catch (SQLException e) {
            throw new DataAccessException(SAVE_FAIL_MESSAGE, e);
        }
    }

    @Override
    public void updateFrom(Connection connection, int gameId, List<Integer> from) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE board SET piece_type = 'EMPTY', team = 'NONE' WHERE game_id = ? AND column = ? AND row = ?");
            stmt.setInt(1, gameId);
            stmt.setInt(2, from.get(0));
            stmt.setInt(3, from.get(1));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(UPDATE_FROM_FAIL_MESSAGE, e);
        }
    }

    @Override
    public PieceDto findPieceByPosition(Connection connection, int gameId, List<Integer> from) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT piece_type, team FROM board WHERE game_id = ? AND column = ? AND row = ?");
            stmt.setInt(1, gameId);
            stmt.setInt(2, from.get(0));
            stmt.setInt(3, from.get(1));
            ResultSet rs = stmt.executeQuery();
            return new PieceDto(from.get(0), from.get(1), rs.getString(PIECE_TYPE), rs.getString(TEAM));
        } catch (SQLException e) {
            throw new DataAccessException(FIND_PIECE_FAIL_MESSAGE, e);
        }
    }

    @Override
    public void updateTo(Connection connection, int gameId, List<Integer> to, String pieceType, String team) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE board SET piece_type = ?, team = ? WHERE game_id = ? AND column = ? AND row = ?");
            stmt.setString(1, pieceType);
            stmt.setString(2, team);
            stmt.setInt(3, gameId);
            stmt.setInt(4, to.get(0));
            stmt.setInt(5, to.get(1));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(UPDATE_TO_FAIL_MESSAGE, e);
        }
    }
}
