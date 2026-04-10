package db.repository;

import static org.assertj.core.api.Assertions.assertThat;

import db.connector.Connector;
import db.persistence.Persisted;
import db.util.AssertDbConnector;
import db.util.DataAccessException;
import db.util.TestDatabaseConnector;
import db.util.TestDatabaseUtil;
import domain.board.Board;
import domain.board.Intersection;
import domain.game.JanggiGame;
import domain.game.Side;
import domain.movement.Move;
import domain.piece.AlivePieces;
import domain.piece.General;
import domain.piece.Piece;
import domain.piece.Soldier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import org.assertj.db.type.AssertDbConnection;
import org.assertj.db.type.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GameRepositoryTest {

    private final Connector connector = new TestDatabaseConnector();
    private Connection connection;
    private AssertDbConnection assertConnection;
    private GameRepository gameRepository;

    @BeforeEach
    void setUpDatabase() {
        TestDatabaseUtil.setUpDatabase();

        connection = connector.getConnection();
        assertConnection = AssertDbConnector.getAssertDbConnection();
        gameRepository = new GameRepository(connector, new PieceRepository());
    }

    @Nested
    class 게임을_저장한다 {

        @Test
        void 게임_데이터가_저장된다() {
            // given
            Board emptyBoard = new Board(new AlivePieces(Map.of()));
            JanggiGame game = new JanggiGame(emptyBoard, Side.CHO);

            // when
            Persisted<JanggiGame> persisted = gameRepository.save(game);

            // then
            Request gameRow = assertConnection.request("""
                            SELECT current_turn
                            FROM game
                            WHERE id = ?
                            """
                    )
                    .parameters(persisted.id())
                    .build();

            String expectedSide = game.getCurrentTurn()
                    .name();
            assertThat(gameRow)
                    .hasNumberOfRows(1)
                    .row(0)
                    .hasValues(expectedSide);
        }

        @Test
        void 게임의_기물_데이터도_같이_저장된다() {
            // given
            Map<Intersection, Piece> pieces = Map.of(
                    new Intersection(3, 3), new Soldier(Side.CHO),
                    new Intersection(4, 4), new Soldier(Side.HAN),
                    new Intersection(5, 5), new General(Side.CHO),
                    new Intersection(6, 6), new General(Side.HAN)
            );
            JanggiGame game = new JanggiGame(new Board(new AlivePieces(pieces)), Side.CHO);

            // when
            Persisted<JanggiGame> persisted = gameRepository.save(game);

            // then
            Request pieceCount = assertConnection.request("""
                            SELECT COUNT(*)
                            FROM piece
                            WHERE game_id = ?
                            """
                    )
                    .parameters(persisted.id())
                    .build();

            assertThat(pieceCount)
                    .row(0)
                    .hasValues(pieces.size());
        }
    }

    @Test
    void 저장된_모든_게임을_조회한다() {
        // given
        int firstId = insertGame(Side.CHO);
        int secondId = insertGame(Side.HAN);

        // when
        List<Integer> gameIds = gameRepository.findAllIds();

        // then
        assertThat(gameIds).containsExactlyInAnyOrder(firstId, secondId);
    }

    @Nested
    class PK를_기반으로_게임을_조회한다 {

        @Test
        void 게임에_대한_정보를_조회한다() {
            // given
            Side currentTurn = Side.CHO;
            int savedGameId = insertGame(currentTurn);

            // when
            Persisted<JanggiGame> foundGame = gameRepository.findById(savedGameId);

            // then
            JanggiGame game = foundGame.data();

            assertThat(foundGame.id()).isEqualTo(savedGameId);
            assertThat(game.getCurrentTurn()).isEqualTo(currentTurn);
        }

        @Test
        void 기물에_대한_정보도_같이_조회한다() {
            // given
            Map<Intersection, Piece> pieces = Map.of(
                    new Intersection(3, 3), new Soldier(Side.CHO),
                    new Intersection(4, 4), new Soldier(Side.HAN),
                    new Intersection(5, 5), new General(Side.CHO),
                    new Intersection(6, 6), new General(Side.HAN)
            );
            JanggiGame game = new JanggiGame(new Board(new AlivePieces(pieces)), Side.CHO);
            Persisted<JanggiGame> persistedGame = gameRepository.save(game);

            // when
            Persisted<JanggiGame> found = gameRepository.findById(persistedGame.id());

            // then
            JanggiGame foundGame = found.data();
            assertThat(foundGame.getPieces()).containsExactlyInAnyOrderEntriesOf(pieces);
        }
    }

    @Nested
    class 게임_정보를_수정한다 {

        private static final Intersection START_INTERSECTION = new Intersection(9, 5);
        private static final Intersection DESTINATION = new Intersection(9, 4);
        private final Board board = new Board(new AlivePieces(Map.of(
                new Intersection(2, 5), new General(Side.HAN),
                START_INTERSECTION, new General(Side.CHO))
        ));

        @Test
        void 게임에_대한_정보를_수정한다() {
            // given
            JanggiGame game = new JanggiGame(board, Side.CHO);
            Persisted<JanggiGame> persistedGame = gameRepository.save(game);

            Move latestMove = new Move(START_INTERSECTION, DESTINATION);
            game.movePiece(latestMove);

            // when
            gameRepository.update(persistedGame, latestMove);

            // then
            Request findCurrentSide = assertConnection.request("""
                            SELECT current_turn
                            FROM game
                            WHERE id = ?
                            """
                    ).parameters(persistedGame.id())
                    .build();

            String expectedSide = game.getCurrentTurn()
                    .name();
            assertThat(findCurrentSide)
                    .hasNumberOfRows(1)
                    .row(0)
                    .hasValues(expectedSide);
        }

        @Test
        void 기물에_대한_정보도_수정한다() {
            // given
            JanggiGame game = new JanggiGame(board, Side.CHO);
            Persisted<JanggiGame> persistedGame = gameRepository.save(game);

            int rowOfFrom = START_INTERSECTION.getRow();
            int fileOfFrom = START_INTERSECTION.getFile();
            Move latestMove = new Move(START_INTERSECTION, DESTINATION);
            game.movePiece(latestMove);

            // when
            gameRepository.update(persistedGame, latestMove);

            // then
            Request findCurrentSide = assertConnection.request("""
                            SELECT count(*)
                            FROM piece
                            WHERE game_id = ? AND `row` = ? AND file = ?
                            """
                    ).parameters(persistedGame.id(), rowOfFrom, fileOfFrom)
                    .build();

            assertThat(findCurrentSide)
                    .hasNumberOfRows(1)
                    .row(0)
                    .hasValues(0);
        }
    }

    @Test
    void 게임_정보를_제거한다() {
        // given
        int savedGameId = insertGame(Side.CHO);

        // when
        gameRepository.deleteById(savedGameId);

        // then
        Request gameCount = assertConnection.request("""
                        SELECT COUNT(*)
                        FROM game
                        WHERE id = ?
                        """
                )
                .parameters(savedGameId)
                .build();

        assertThat(gameCount)
                .row(0)
                .hasValues(0);
    }

    private int insertGame(Side currentTurn) {
        String sql = "INSERT INTO game (current_turn) VALUES (?)";
        try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
}
