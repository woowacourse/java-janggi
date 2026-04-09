package janggi.infrastructure;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.game.Players;
import janggi.domain.game.Side;
import janggi.domain.game.Turn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.repository.JanggiRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcJanggiRepository implements JanggiRepository {
    @Override
    public Long save(Players players) {
        String sql = "INSERT INTO game (cho_player, han_player, current_turn) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // 자동으로 생성된 키 반환

            // 데이터 바인딩
            pstmt.setString(1, players.getChoPlayerName());
            pstmt.setString(2, players.getHanPlayerName());
            pstmt.setString(3, players.getTurn().getSide().name());

            // SQL 명령 -> DB 서버
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 저장 실패: " + e.getMessage());
        }
        throw new RuntimeException("[ERROR] ID 생성 실패");
    }

    @Override
    public void updateGameStatus(Long gameId, Board board, Turn turn) {
        // 현재 턴 정보 업데이트
        String updateTurnSql = "UPDATE game SET current_turn = ? WHERE id = ?";
        // 기존 보드 상태 삭제
        String deleteBoardSql = "DELETE FROM board_state WHERE game_id = ?";
        // 현재 보드 상태 삽입
        String insertBoardSql = "INSERT INTO board_state (game_id, row_index, col_index, piece_type, side, piece_number) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // 트랜잭션 시작

            try {
                // 턴 업데이트
                try (PreparedStatement pstmt = conn.prepareStatement(updateTurnSql)) {
                    pstmt.setString(1, turn.getSide().name());
                    pstmt.setLong(2, gameId);
                    pstmt.executeUpdate();
                }

                // 기존 보드 데이터 삭제
                try (PreparedStatement pstmt = conn.prepareStatement(deleteBoardSql)) {
                    pstmt.setLong(1, gameId);
                    pstmt.executeUpdate();
                }

                // 새로운 보드 데이터 삽입
                try (PreparedStatement pstmt = conn.prepareStatement(insertBoardSql)) {
                    for (Map.Entry<Position, Piece> entry : board.getPiecePosition().entrySet()) {
                        Position position = entry.getKey();
                        Piece piece = entry.getValue();

                        if (piece.isEmpty()) {
                            continue;
                        }

                        pstmt.setLong(1, gameId);
                        pstmt.setInt(2, position.row());
                        pstmt.setInt(3, position.column());
                        pstmt.setString(4, piece.getPieceType().name());
                        pstmt.setString(5, piece.getSide().name());
                        pstmt.setString(6, piece.getPieceNumber());
                        pstmt.addBatch();
                    }
                    pstmt.executeBatch();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 상태 업데이트 실패: " + e.getMessage());
        }
    }

    @Override
    public Optional<Long> findInProgressGameId() {
        String sql = "SELECT id FROM game WHERE is_finished = FALSE ORDER BY created_at DESC LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) { // 조회 메서드

            if (rs.next()) {
                return Optional.of(rs.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 진행 중인 게임 조회 실패: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Long> findAllInProgressGameIds() {
        String sql = "SELECT id FROM game WHERE is_finished = FALSE ORDER BY created_at DESC";
        List<Long> ids = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // 데이터가 없을 때까지 반복해서 리스트에 추가
            while (rs.next()) {
                ids.add(rs.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 목록 조회 실패: " + e.getMessage());
        }
        return ids;
    }

    @Override
    public Board findBoardById(Long gameId) {
        String sql = "SELECT row_index, col_index, piece_type, side, piece_number FROM board_state WHERE game_id = ?";
        Map<Position, Piece> piecePosition = new HashMap<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Position position = new Position(rs.getInt("row_index"), rs.getInt("col_index"));

                    // DB 문자열 -> Enum 변환
                    PieceType type = PieceType.valueOf(rs.getString("piece_type"));
                    Side side = Side.valueOf(rs.getString("side"));
                    String pieceNumber = rs.getString("piece_number");

                    // Piece 생성
                    piecePosition.put(position, new Piece(side, type, pieceNumber));
                }
            }
            return Board.from(piecePosition);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 보드 복구 실패: " + e.getMessage());
        }
    }

    @Override
    public Players findPlayersById(Long gameId) {
        String sql = "SELECT cho_player, han_player FROM game WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String choName = rs.getString("cho_player");
                    String hanName = rs.getString("han_player");
                    return Players.of(choName, hanName);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 플레이어 조회 실패: " + e.getMessage());
        }
        throw new RuntimeException("[ERROR] 해당 ID의 게임을 찾을 수 없습니다.");
    }

    @Override
    public Turn findTurnById(Long gameId) {
        String sql = "SELECT current_turn FROM game WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Side side = Side.valueOf(rs.getString("current_turn"));
                    return new Turn(side);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 턴 조회 실패: " + e.getMessage());
        }
        throw new RuntimeException("[ERROR] 턴 정보를 찾을 수 없습니다.");
    }

    @Override
    public void finishGame(Long gameId) {
        String sql = "UPDATE game SET is_finished = TRUE WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 종료 처리 실패: " + e.getMessage());
        }
    }
}
