package repository;

import domain.board.Board;
import domain.board.Intersection;
import domain.game.JanggiGame;
import domain.game.Side;
import domain.piece.AlivePieces;
import domain.piece.Piece;
import domain.piece.PieceType;
import dto.GameSummary;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.sql.DataSource;

public final class JanggiGameRepository {

    private static final Path SCHEMA_PATH = Path.of("sql/ddl.sql");
    private static final String QUERY_DELIMITER = ";";

    private final DataSource dataSource;

    public JanggiGameRepository(DataSource dataSource) throws IOException {
        this.dataSource = dataSource;
        executeDDL(dataSource);
    }

    private void executeDDL(DataSource dataSource) throws IOException {
        String[] queries = Files.readString(SCHEMA_PATH)
                .trim()
                .split(QUERY_DELIMITER);

        try (
                Connection conn = dataSource.getConnection();
                Statement statement = conn.createStatement()
        ) {
            for (String query : queries) {
                statement.addBatch(query);
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public long save(JanggiGame janggiGame) {
        String sql = "INSERT INTO game (current_turn) VALUES (?)";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setString(1, janggiGame.currentTurn().name());
            pstmt.executeUpdate();
            // TODO: ResultSet 자원도 닫아야 됨을 인지해서 일단 중첩 try-with-resources로 대응했지만, 중첩없이 해결 가능한 지 고민 필요.
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long gameId = rs.getLong(1);
                    insertPieces(conn, janggiGame, gameId);

                    return gameId;
                }
            }

            throw new SQLException("게임 생성에 실패했습니다.");
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void updateGameStatus(JanggiGame janggiGame, Long gameId) {
        try (Connection conn = dataSource.getConnection()) {
            try {
                conn.setAutoCommit(false);
                updateCurrentTurn(conn, janggiGame, gameId);
                syncPieces(conn, janggiGame, gameId);
                conn.commit();
            } catch (IllegalStateException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private void updateCurrentTurn(Connection conn, JanggiGame janggiGame, long gameId) {
        String sql = ""
                + "UPDATE game "
                + "SET current_turn = ? "
                + "WHERE game_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, janggiGame.currentTurn().name());
            pstmt.setLong(2, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public JanggiGame findById(long gameId) {
        String sql = ""
                + "SELECT * "
                + "FROM game "
                + "WHERE game_id = ?";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setLong(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Side currentTurn = Side.from(rs.getString("current_turn"));
                    Map<Intersection, Piece> pieces = findPieces(conn, gameId);

                    return new JanggiGame(
                            new Board(new AlivePieces(pieces)),
                            currentTurn
                    );
                }
            }

            throw new IllegalStateException("게임을 찾을 수 없습니다.");
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<GameSummary> findAll() {
        String sql = ""
                + "SELECT game_id, created_at, current_turn "
                + "FROM game ";

        List<GameSummary> gameSummaries = new ArrayList<>();
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    gameSummaries.add(new GameSummary(
                            rs.getLong("game_id"),
                            rs.getDate("created_at"),
                            rs.getString("current_turn")
                    ));
                }
            }

            return List.copyOf(gameSummaries);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private void insertPieces(Connection conn, JanggiGame janggiGame, long gameId) {
        String sql = ""
                + "INSERT INTO piece (game_id, position_row, position_file, piece_type, side) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            Map<Intersection, Piece> map = janggiGame.toMap();

            pstmt.setLong(1, gameId);
            for (Entry<Intersection, Piece> entry : map.entrySet()) {
                Intersection intersection = entry.getKey();
                Piece piece = entry.getValue();

                pstmt.setInt(2, intersection.row());
                pstmt.setInt(3, intersection.file());
                pstmt.setString(4, piece.getType().name());
                pstmt.setString(5, piece.getSide().name());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    // TODO: 트랜잭션 테스트에서의 stubbing을 위해 일단 접근 제어자를 default로 변경함. 캡슐화 지키면서도 stbbing할 방법 찾기.
    void syncPieces(Connection conn, JanggiGame janggiGame, long gameId) {
        String sqlForDelete = ""
                + "DELETE FROM piece "
                + "WHERE game_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlForDelete)) {
            pstmt.setLong(1, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        insertPieces(conn, janggiGame, gameId);
    }

    private Map<Intersection, Piece> findPieces(Connection conn, long gameId) {
        String sql = ""
                + "SELECT position_row, position_file, piece_type, side "
                + "FROM piece "
                + "WHERE piece.game_id = ?";

        Map<Intersection, Piece> pieces = new HashMap<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Intersection intersection = new Intersection(rs.getInt("position_row"), rs.getInt("position_file"));
                    Piece piece = Piece.of(
                            PieceType.from(rs.getString("piece_type")),
                            Side.from(rs.getString("side"))
                    );
                    pieces.put(intersection, piece);
                }
            }

            return pieces;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void clear() {
        final String DELETE_FORMAT = "DELETE FROM %s";
        final List<String> tables = List.of("piece", "game");

        try (
                Connection connection = dataSource.getConnection();
                Statement stmt = connection.createStatement()
        ) {
            for (String table : tables) {
                stmt.addBatch(DELETE_FORMAT.formatted(table));
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
