package janggi.repository;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.domain.team.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameRepository {
    private final Connection conn;

    public GameRepository(Connection conn) {
        this.conn = conn;
    }

    public int createGame(Map<Position, Piece> board) {
        int gameId = insertGame();
        savePieces(gameId, board);
        return gameId;
    }

    public Optional<GameInfo> findGame() {
        String sql = "SELECT * FROM game";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                return Optional.empty();
            }
            return Optional.of(new GameInfo(
                    rs.getInt("id"),
                    rs.getString("current_turn"),
                    rs.getString("winner")));
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 게임 조회 실패", e);
        }
    }

    public List<PieceInfo> findPieces(int gameId) {
        String sql = "SELECT type, team, piece_row, piece_col FROM piece WHERE game_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);

            ResultSet rs = pstmt.executeQuery();
            List<PieceInfo> pieces = new ArrayList<>();
            while (rs.next()) {
                pieces.add(new PieceInfo(
                        rs.getString("type"),
                        rs.getString("team"),
                        rs.getInt("piece_row"),
                        rs.getInt("piece_col")));
            }
            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 기물 정보 조회 실패", e);
        }
    }

    public void movePiece(int gameId, Position from, Position to) {
        deletePieceAt(gameId, to);
        updatePiecePosition(gameId, from, to);
    }

    public void updateTurn(int gameId, Team nextTurn) {
        String sql = "UPDATE game SET current_turn = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nextTurn.name());
            pstmt.setInt(2, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 현재 차례 업데이트 실패", e);
        }
    }

    public void updateWinner(int gameId, Team winner) {
        String sql = "UPDATE game SET winner = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, winner.name());
            pstmt.setInt(2, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 승자 업데이트 실패", e);
        }
    }

    public void deleteGame(int gameId) {
        String sql = "DELETE FROM game WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 게임 삭제 실패", e);
        }
    }


    private int insertGame() {
        String sql = "INSERT INTO game (current_turn) VALUES(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, Team.FIRST_TURN.name());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 새로운 게임 추가 실패", e);
        }
    }

    private void savePieces(int gameId, Map<Position, Piece> board) {
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

    private void deletePieceAt(int gameId, Position to) {
        String deleteSql = "DELETE FROM piece WHERE game_id = ? AND piece_row = ? AND piece_col = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
            pstmt.setInt(1, gameId);
            pstmt.setInt(2, to.getRowValue());
            pstmt.setInt(3, to.getColumnValue());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 도착 좌표 기물 삭제 실패", e);
        }
    }

    private void updatePiecePosition(int gameId, Position from, Position to) {
        String updateSql = "UPDATE piece SET piece_row = ?, piece_col = ? WHERE game_id = ? AND piece_row = ? AND piece_col = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            pstmt.setInt(1, to.getRowValue());
            pstmt.setInt(2, to.getColumnValue());
            pstmt.setInt(3, gameId);
            pstmt.setInt(4, from.getRowValue());
            pstmt.setInt(5, from.getColumnValue());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 기물 이동 업데이트 실패", e);
        }
    }

    private void addPieceBatch(PreparedStatement pstmt, int gameId, Position position, Piece piece) throws
            SQLException {
        if (piece.isEmptyPiece()) {
            return;
        }
        pstmt.setInt(1, gameId);
        pstmt.setString(2, piece.getType().name());
        pstmt.setString(3, piece.getTeam().name());
        pstmt.setInt(4, position.getRowValue());
        pstmt.setInt(5, position.getColumnValue());
        pstmt.addBatch();
    }
}
