package janggi.repository;

import janggi.JanggiGame2;
import janggi.domain.board.BoardFactory;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;
import janggi.domain.team.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JdbcGameDao implements GameDao {

    private final Connection conn;

    public JdbcGameDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<GameInfo2> findAllGames() {
        String sql = "SELECT id, updated_at, han_score, cho_score, current_turn, winner FROM game ORDER BY updated_at DESC";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            List<GameInfo2> games = new ArrayList<>();
            while (rs.next()) {
                games.add(new GameInfo2(
                        rs.getLong("id"),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getDouble("han_score"),
                        rs.getDouble("cho_score"),
                        Team.valueOf(rs.getString("current_turn")),
                        Team.valueOf(rs.getString("winner"))
                ));
            }
            return games;
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 게임 목록 조회 실패", e);
        }
    }

    @Override
    public long createGame(JanggiGame2 game) {
        long gameId = insertGame(game);
        savePieces(gameId, game.getBoard());
        return gameId;
    }

    @Override
    public void updateGame(long gameId, JanggiGame2 game) {
        updateGameInfo(gameId, game);
        deletePieces(gameId);
        savePieces(gameId, game.getBoard());
    }

    @Override
    public JanggiGame2 getById(long gameId) {
        Team currentTeam = findCurrentTeam(gameId);
        Map<Position, Piece> pieces = findPieces(gameId);

        return new JanggiGame2(BoardFactory.restore(pieces), currentTeam);
    }

    private long insertGame(JanggiGame2 game) {
        String sql = "INSERT INTO game (current_turn, winner, han_score, cho_score) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, game.getCurrentTeam().name());
            pstmt.setString(2, game.getWinner().name());
            pstmt.setDouble(3, game.getScore().getHanScore());
            pstmt.setDouble(4, game.getScore().getChoScore());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 새로운 게임 추가 실패", e);
        }
    }

    private void savePieces(long gameId, Map<Position, Piece> board) {
        String sql = "INSERT INTO piece (game_id, type, team, piece_row, piece_col) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Map.Entry<Position, Piece> entry : board.entrySet()) {
                addPieceBatch(pstmt, gameId, entry.getKey(), entry.getValue());
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 기물 저장 실패", e);
        }
    }

    private void addPieceBatch(PreparedStatement pstmt, long gameId, Position position, Piece piece) throws SQLException {
        if (piece.isEmptyPiece()) {
            return;
        }
        pstmt.setLong(1, gameId);
        pstmt.setString(2, piece.getType().name());
        pstmt.setString(3, piece.getTeam().name());
        pstmt.setInt(4, position.getRowValue());
        pstmt.setInt(5, position.getColumnValue());
        pstmt.addBatch();
    }

    private void updateGameInfo(long gameId, JanggiGame2 game) {
        String sql = "UPDATE game SET current_turn = ?, winner = ?, han_score = ?, cho_score = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, game.getCurrentTeam().name());
            pstmt.setString(2, game.getWinner().name());
            pstmt.setDouble(3, game.getScore().getHanScore());
            pstmt.setDouble(4, game.getScore().getChoScore());
            pstmt.setLong(5, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 게임 정보 업데이트 실패", e);
        }
    }

    private void deletePieces(long gameId) {
        String sql = "DELETE FROM piece WHERE game_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 기물 삭제 실패", e);
        }
    }

    private Team findCurrentTeam(long gameId) {
        String sql = "SELECT current_turn FROM game WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                throw new RuntimeException("[ERROR] 해당 게임이 존재하지 않습니다.");
            }
            return Team.valueOf(rs.getString("current_turn"));
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 게임 조회 실패", e);
        }
    }

    private Map<Position, Piece> findPieces(long gameId) {
        String sql = "SELECT type, team, piece_row, piece_col FROM piece WHERE game_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            ResultSet rs = pstmt.executeQuery();
            Map<Position, Piece> pieces = new LinkedHashMap<>();
            while (rs.next()) {
                Position position = Position.of(rs.getInt("piece_row"), rs.getInt("piece_col"));
                Team team = Team.valueOf(rs.getString("team"));
                Piece piece = PieceType.valueOf(rs.getString("type")).createPiece(team);
                pieces.put(position, piece);
            }
            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 기물 조회 실패", e);
        }
    }
}
