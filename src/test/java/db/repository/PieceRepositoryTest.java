package db.repository;

import static org.assertj.core.api.Assertions.assertThat;

import db.util.AssertDbConnector;
import db.connector.Connector;
import db.util.TestDatabaseConnector;
import db.util.DataAccessException;
import db.util.TestDatabaseUtil;
import domain.board.Intersection;
import domain.game.Side;
import domain.movement.Move;
import domain.piece.Chariot;
import domain.piece.Piece;
import domain.piece.Soldier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import org.assertj.db.type.AssertDbConnection;
import org.assertj.db.type.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PieceRepositoryTest {

    private final Connector connector = new TestDatabaseConnector();
    private Connection connection;
    private AssertDbConnection assertConnection;
    private int gameId;

    @BeforeEach
    void setUpDatabase() {
        TestDatabaseUtil.setUpDatabase();

        connection = connector.getConnection();
        assertConnection = AssertDbConnector.getAssertDbConnection();

        gameId = generateGameKey();
    }

    @Test
    void 기물_정보를_저장한다() throws SQLException {
        // given
        PieceRepository pieceRepository = new PieceRepository();

        Map<Intersection, Piece> pieces = Map.of(
                new Intersection(3, 4), new Soldier(Side.CHO)
        );

        // when
        pieceRepository.save(pieces, gameId, connection);

        // then
        Request findPieces = assertConnection.request("""
                        SELECT `row`, file, side, type
                        FROM piece
                        WHERE game_id = ?
                        """
                ).parameters(gameId)
                .build();

        assertThat(findPieces)
                .hasNumberOfRows(1)
                .row(0)
                .hasValues(3, 4, "CHO", "Soldier");
    }

    @Test
    void 게임의_PK를_이용해_기물_정보를_조회한다() throws SQLException {
        // given
        Intersection intersection = new Intersection(3, 4);
        Piece piece = new Soldier(Side.CHO);
        Map<Intersection, Piece> expected = Map.of(intersection, piece);

        insertPiece(gameId, intersection, piece, connection);

        PieceRepository pieceRepository = new PieceRepository();

        // when
        Map<Intersection, Piece> pieces = pieceRepository.findByGameId(gameId, connection);

        // then
        assertThat(pieces).containsExactlyEntriesOf(expected);
    }

    @Nested
    class 기물의_위치_정보를_수정한다 {

        @Test
        void 위치_정보를_수정한다() throws SQLException {
            // given
            Intersection from = new Intersection(3, 4);
            Intersection to = new Intersection(5, 6);
            Piece pieceAtFrom = new Soldier(Side.CHO);

            insertPiece(gameId, from, pieceAtFrom, connection);

            PieceRepository pieceRepository = new PieceRepository();

            // when
            pieceRepository.update(new Move(from, to), gameId, connection);

            // then
            Request findPieces = assertConnection.request("""
                            SELECT `row`, file
                            FROM piece
                            WHERE piece.game_id = ?
                            """
                    ).parameters(gameId)
                    .build();

            assertThat(findPieces)
                    .hasNumberOfRows(1)
                    .row(0)
                    .hasValues(to.getRow(), to.getFile());
        }

        @Test
        void 기존에_도착지에_있던_기물_정보는_제거된다() throws SQLException {
            // given
            Intersection from = new Intersection(3, 4);
            Intersection to = new Intersection(5, 6);
            Piece pieceAtFrom = new Soldier(Side.CHO);
            Piece pieceAtTo = new Chariot(Side.HAN);

            insertPiece(gameId, from, pieceAtFrom, connection);
            insertPiece(gameId, to, pieceAtTo, connection);

            PieceRepository pieceRepository = new PieceRepository();

            // when
            pieceRepository.update(new Move(from, to), gameId, connection);

            // then
            Request findPieces = assertConnection.request("""
                            SELECT side, type
                            FROM piece
                            WHERE piece.game_id = ? AND `row` = ? AND file = ?
                            """
                    ).parameters(gameId, to.getRow(), to.getFile())
                    .build();

            String expectedSide = pieceAtFrom.getSide()
                    .name();
            String expectedType = pieceAtFrom.getClass()
                    .getSimpleName();

            assertThat(findPieces)
                    .hasNumberOfRows(1)
                    .row(0)
                    .hasValues(expectedSide, expectedType);
        }
    }

    @Test
    void 게임의_PK를_이용해_기물_정보를_제거한다() throws SQLException {
        // given
        Intersection intersection = new Intersection(3, 4);
        Piece piece = new Soldier(Side.CHO);

        insertPiece(gameId, intersection, piece, connection);

        PieceRepository pieceRepository = new PieceRepository();

        // when
        pieceRepository.delete(gameId, connection);

        // then
        Request piecesCount = assertConnection.request("""
                        SELECT COUNT(*)
                        FROM piece
                        WHERE game_id = ?
                        """
                ).parameters(gameId)
                .build();
        assertThat(piecesCount)
                .row(0)
                .hasValues(0);
    }

    private int generateGameKey() {
        String insertGame = "INSERT INTO game (current_turn) VALUES (?)";
        try (var statement = connection.prepareStatement(insertGame, Statement.RETURN_GENERATED_KEYS)) {
            Side currentTurn = Side.CHO;
            statement.setString(1, currentTurn.name());
            statement.executeUpdate();

            return findKey(statement);
        } catch (SQLException exception) {
            throw new DataAccessException("게임 생성을 실패했습니다.");
        }
    }

    private int findKey(PreparedStatement statement) throws SQLException {
        try (ResultSet keys = statement.getGeneratedKeys()) {
            keys.next();

            return keys.getInt(1);
        }
    }

    private void insertPiece(
            int gameId,
            Intersection intersection,
            Piece piece,
            Connection connection
    ) {
        String insertPiece = "INSERT INTO piece (game_id, `row`, file, side, type)"
                + " VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertPiece)) {
            statement.setInt(1, gameId);
            statement.setInt(2, intersection.getRow());
            statement.setInt(3, intersection.getFile());

            Side side = piece.getSide();
            statement.setString(4, side.name());
            String type = piece.getClass()
                    .getSimpleName();
            statement.setString(5, type);

            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException(exception);
        }
    }
}
