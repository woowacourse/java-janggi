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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.sql.DataSource;
import support.DataAccessException;

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
            throw new DataAccessException(e);
        }
    }

    public long save(Connection conn, JanggiGame janggiGame) {
        String sql = "INSERT INTO game (current_turn) VALUES (?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, janggiGame.currentTurn().name());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long gameId = rs.getLong(1);
                    insertPieces(conn, janggiGame, gameId);

                    return gameId;
                }
            }

            throw new SQLException("게임 생성에 실패했습니다.");
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void updateGameStatus(Connection conn, JanggiGame janggiGame,
                                 Intersection from, Intersection to, long gameId) {
        updateCurrentTurn(conn, janggiGame, gameId);
        deleteCapturedPiece(conn, to, gameId);
        movePiece(conn, from, to, gameId);
    }

    private void deleteCapturedPiece(Connection conn, Intersection to, long gameId) {
        String sql = ""
                + "DELETE FROM piece "
                + "WHERE game_id = ? AND position_row = ? AND position_file = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            pstmt.setInt(2, to.row());
            pstmt.setInt(3, to.file());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    private void movePiece(Connection conn, Intersection from, Intersection to, long gameId) {
        String sql = ""
                + "UPDATE piece "
                + "SET position_row = ?, position_file = ? "
                + "WHERE game_id = ? AND position_row = ? AND position_file = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, to.row());
            pstmt.setInt(2, to.file());
            pstmt.setLong(3, gameId);
            pstmt.setInt(4, from.row());
            pstmt.setInt(5, from.file());
            pstmt.execute();
        } catch (SQLException e) {
            throw new DataAccessException(e);
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
            throw new DataAccessException(e);
        }
    }

    public JanggiGame findById(Connection conn, long gameId) {
        String sql = ""
                + "SELECT * "
                + "FROM game "
                + "WHERE game_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Side currentTurn = Side.valueOf(rs.getString("current_turn"));
                    Map<Intersection, Piece> pieces = findPieces(conn, gameId);

                    return JanggiGame.load(
                            new Board(new AlivePieces(pieces)),
                            currentTurn
                    );
                }
            }

            throw new IllegalStateException("게임을 찾을 수 없습니다.");
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public List<GameSummary> findAll(Connection conn) {
        String sql = ""
                + "SELECT game_id, created_at, current_turn "
                + "FROM game ";

        List<GameSummary> gameSummaries = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    gameSummaries.add(new GameSummary(
                            rs.getLong("game_id"),
                            rs.getObject("created_at", LocalDateTime.class),
                            rs.getString("current_turn")
                    ));
                }
            }

            return List.copyOf(gameSummaries);
        } catch (SQLException e) {
            throw new DataAccessException(e);
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
            throw new DataAccessException(e);
        }
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
                            Side.valueOf(rs.getString("side"))
                    );
                    pieces.put(intersection, piece);
                }
            }

            return pieces;
        } catch (SQLException e) {
            throw new DataAccessException(e);
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
            throw new DataAccessException(e);
        }
    }
}
