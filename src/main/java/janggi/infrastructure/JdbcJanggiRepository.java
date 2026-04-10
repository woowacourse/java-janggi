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
    // SQL 쿼리
    private static final String INSERT_GAME_SQL = "INSERT INTO game (cho_player, han_player, current_turn) VALUES (?, ?, ?)";
    private static final String UPDATE_TURN_SQL = "UPDATE game SET current_turn = ? WHERE id = ?";
    private static final String DELETE_BOARD_SQL = "DELETE FROM board_state WHERE game_id = ?";
    private static final String INSERT_BOARD_SQL = "INSERT INTO board_state (game_id, row_index, col_index, piece_type, side, piece_number) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_IN_PROGRESS_ID_SQL = "SELECT id FROM game WHERE is_finished = FALSE ORDER BY created_at DESC LIMIT 1";
    private static final String SELECT_ALL_IN_PROGRESS_IDS_SQL = "SELECT id FROM game WHERE is_finished = FALSE ORDER BY created_at DESC";
    private static final String SELECT_BOARD_BY_ID_SQL = "SELECT row_index, col_index, piece_type, side, piece_number FROM board_state WHERE game_id = ?";
    private static final String SELECT_PLAYERS_BY_ID_SQL = "SELECT cho_player, han_player, current_turn FROM game WHERE id = ?";
    private static final String SELECT_TURN_BY_ID_SQL = "SELECT current_turn FROM game WHERE id = ?";
    private static final String UPDATE_FINISH_GAME_SQL = "UPDATE game SET is_finished = TRUE WHERE id = ?";

    // 컬럼명
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CHO_PLAYER = "cho_player";
    private static final String COLUMN_HAN_PLAYER = "han_player";
    private static final String COLUMN_CURRENT_TURN = "current_turn";
    private static final String COLUMN_ROW_INDEX = "row_index";
    private static final String COLUMN_COL_INDEX = "col_index";
    private static final String COLUMN_PIECE_TYPE = "piece_type";
    private static final String COLUMN_SIDE = "side";
    private static final String COLUMN_PIECE_NUMBER = "piece_number";

    // 에러 메시지
    private static final String ERROR_SAVE = "[ERROR] DB 저장 실패: ";
    private static final String ERROR_ID_GENERATION = "[ERROR] ID 생성 실패";
    private static final String ERROR_UPDATE_STATUS = "[ERROR] 게임 상태 업데이트 실패: ";
    private static final String ERROR_FIND_IN_PROGRESS = "[ERROR] 진행 중인 게임 조회 실패: ";
    private static final String ERROR_FIND_ALL_IN_PROGRESS = "[ERROR] 게임 목록 조회 실패: ";
    private static final String ERROR_FIND_BOARD = "[ERROR] 보드 복구 실패: ";
    private static final String ERROR_FIND_PLAYERS = "[ERROR] 플레이어 조회 실패: ";
    private static final String ERROR_NOT_FOUND_GAME = "[ERROR] 해당 ID의 게임을 찾을 수 없습니다.";
    private static final String ERROR_FIND_TURN = "[ERROR] 턴 조회 실패: ";
    private static final String ERROR_FIND_TURN_NOT_FOUND = "[ERROR] 턴 정보를 찾을 수 없습니다.";
    private static final String ERROR_FINISH_GAME = "[ERROR] 게임 종료 처리 실패: ";

    @Override
    public Long save(Players players) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_GAME_SQL,
                     Statement.RETURN_GENERATED_KEYS)) { // 자동으로 생성된 키 반환

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
            throw new RuntimeException(ERROR_SAVE + e.getMessage());
        }
        throw new RuntimeException(ERROR_ID_GENERATION);
    }

    @Override
    public void updateGameStatus(Long gameId, Board board, Turn turn) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // 트랜잭션 시작

            try {
                // 현재 턴 정보 업데이트
                try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_TURN_SQL)) {
                    pstmt.setString(1, turn.getSide().name());
                    pstmt.setLong(2, gameId);
                    pstmt.executeUpdate();
                }

                // 기존 보드 상태 삭제
                try (PreparedStatement pstmt = conn.prepareStatement(DELETE_BOARD_SQL)) {
                    pstmt.setLong(1, gameId);
                    pstmt.executeUpdate();
                }

                // 새로운 보드 데이터 삽입
                try (PreparedStatement pstmt = conn.prepareStatement(INSERT_BOARD_SQL)) {
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
            throw new RuntimeException(ERROR_UPDATE_STATUS + e.getMessage());
        }
    }

    @Override
    public Optional<Long> findInProgressGameId() {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_IN_PROGRESS_ID_SQL);
             ResultSet rs = pstmt.executeQuery()) { // 조회 메서드

            if (rs.next()) {
                return Optional.of(rs.getLong(COLUMN_ID));
            }
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_FIND_IN_PROGRESS + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Long> findAllInProgressGameIds() {
        List<Long> ids = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_IN_PROGRESS_IDS_SQL);
             ResultSet rs = pstmt.executeQuery()) {

            // 데이터가 없을 때까지 반복해서 리스트에 추가
            while (rs.next()) {
                ids.add(rs.getLong(COLUMN_ID));
            }
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_FIND_ALL_IN_PROGRESS + e.getMessage());
        }
        return ids;
    }

    @Override
    public Board findBoardById(Long gameId) {
        Map<Position, Piece> piecePosition = new HashMap<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BOARD_BY_ID_SQL)) {

            pstmt.setLong(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Position position = new Position(rs.getInt(COLUMN_ROW_INDEX), rs.getInt(COLUMN_COL_INDEX));

                    // DB 문자열 -> Enum 변환
                    PieceType type = PieceType.valueOf(rs.getString(COLUMN_PIECE_TYPE));
                    Side side = Side.valueOf(rs.getString(COLUMN_SIDE));
                    String pieceNumber = rs.getString(COLUMN_PIECE_NUMBER);

                    // Piece 생성
                    piecePosition.put(position, new Piece(side, type, pieceNumber));
                }
            }
            return Board.from(piecePosition);
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_FIND_BOARD + e.getMessage());
        }
    }

    @Override
    public Players findPlayersById(Long gameId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_PLAYERS_BY_ID_SQL)) {

            pstmt.setLong(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String choName = rs.getString(COLUMN_CHO_PLAYER);
                    String hanName = rs.getString(COLUMN_HAN_PLAYER);
                    Side currentTurn = Side.valueOf(rs.getString(COLUMN_CURRENT_TURN));
                    return Players.fromSavedStatus(choName, hanName, currentTurn);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_FIND_PLAYERS + e.getMessage());
        }
        throw new RuntimeException(ERROR_NOT_FOUND_GAME);
    }

    @Override
    public Turn findTurnById(Long gameId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_TURN_BY_ID_SQL)) {

            pstmt.setLong(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Side side = Side.valueOf(rs.getString(COLUMN_CURRENT_TURN));
                    return new Turn(side);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_FIND_TURN + e.getMessage());
        }
        throw new RuntimeException(ERROR_FIND_TURN_NOT_FOUND);
    }

    @Override
    public void finishGame(Long gameId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_FINISH_GAME_SQL)) {

            pstmt.setLong(1, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_FINISH_GAME + e.getMessage());
        }
    }
}
