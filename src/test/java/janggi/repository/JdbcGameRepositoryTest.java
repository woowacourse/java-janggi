package janggi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.db.ConnectionFactory;
import janggi.db.SchemaInitializer;
import janggi.domain.board.Board;
import janggi.domain.board.BoardInitializer;
import janggi.domain.board.Position;
import janggi.domain.game.JanggiGame;
import janggi.domain.game.MoveResult;
import janggi.domain.piece.ChariotPiece;
import janggi.domain.piece.Piece;
import janggi.domain.piece.SoldierPiece;
import janggi.domain.piece.Team;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JdbcGameRepositoryTest {
    private static final Path TEST_DATABASE_PATH = Path.of("build/test-db/janggi-test.db");
    private static final String TEST_DATABASE_URL = "jdbc:sqlite:build/test-db/janggi-test.db";

    private ConnectionFactory connectionFactory;
    private JdbcGameRepository jdbcGameRepository;

    @BeforeEach
    void setUp() throws Exception {
        clearTestDatabase();
        connectionFactory = new ConnectionFactory(TEST_DATABASE_URL);
        SchemaInitializer schemaInitializer = new SchemaInitializer(connectionFactory);
        schemaInitializer.initialize();
        jdbcGameRepository = new JdbcGameRepository(connectionFactory);
    }

    @Test
    @DisplayName("새 게임을 저장하면 게임 상태와 기물 배치가 DB에 저장된다.")
    void testSaveNewGame() throws Exception {
        Board board = BoardInitializer.initializeBoard(1, 1);
        JanggiGame janggiGame = JanggiGame.start(board);

        long savedGameId = jdbcGameRepository.saveNewGame(janggiGame);

        assertThat(savedGameId).isPositive();
        assertThat(countGames()).isEqualTo(1);
        try (Connection connection = connectionFactory.create()) {
            PreparedStatement pieceCountStatement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM pieces WHERE game_id = ?"
            );
            pieceCountStatement.setLong(1, savedGameId);
            ResultSet pieceCountResult = pieceCountStatement.executeQuery();
            pieceCountResult.next();
            assertThat(pieceCountResult.getInt(1)).isEqualTo(32);
        }
    }

    @Test
    @DisplayName("진행 중인 게임을 조회하면 마지막 상태로 복원된다.")
    void testFindPlayingGame() {
        Board board = BoardInitializer.initializeBoard(1, 1);
        JanggiGame janggiGame = JanggiGame.start(board);
        long savedGameId = jdbcGameRepository.saveNewGame(janggiGame);

        Position startPiecePosition = new Position(1, 4);
        Position endPiecePosition = new Position(1, 5);
        MoveResult moveResult = janggiGame.move(startPiecePosition, endPiecePosition);
        jdbcGameRepository.applyMoveResult(savedGameId, moveResult);

        Optional<SavedGame> savedGame = jdbcGameRepository.findPlayingGame();

        assertThat(savedGame).isPresent();
        assertThat(savedGame.get().janggiGame().getBoard().hasPieceAt(startPiecePosition)).isFalse();
        assertThat(savedGame.get().janggiGame().getBoard().hasPieceAt(endPiecePosition)).isTrue();
        assertThat(savedGame.get().janggiGame().currentTurnTeam()).isEqualTo(Team.CHO);
    }

    @Test
    @DisplayName("기물을 잡으면 잡힌 기물은 삭제되고 이동한 기물 위치로 저장된다.")
    void testApplyMoveResultWhenCapturePiece() throws Exception {
        Board board = createBoard(
                new Position(1, 1), new ChariotPiece(Team.HAN),
                new Position(1, 3), new SoldierPiece(Team.CHO)
        );
        JanggiGame janggiGame = JanggiGame.start(board);
        long savedGameId = jdbcGameRepository.saveNewGame(janggiGame);

        MoveResult moveResult = janggiGame.move(new Position(1, 1), new Position(1, 3));
        jdbcGameRepository.applyMoveResult(savedGameId, moveResult);

        Optional<SavedGame> savedGame = jdbcGameRepository.findPlayingGame();

        assertThat(savedGame).isPresent();
        assertThat(savedGame.get().janggiGame().getBoard().hasPieceAt(new Position(1, 1))).isFalse();
        assertThat(savedGame.get().janggiGame().getBoard().hasPieceAt(new Position(1, 3))).isTrue();
        assertThat(countPieces(savedGameId)).isEqualTo(1);
    }

    private int countGames() throws Exception {
        String sql = "SELECT COUNT(*) FROM games";

        try (Connection connection = connectionFactory.create();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    private int countPieces(long savedGameId) throws Exception {
        String sql = "SELECT COUNT(*) FROM pieces WHERE game_id = ?";

        try (Connection connection = connectionFactory.create();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, savedGameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1);
            }
        }
    }

    private Board createBoard(Position firstPosition, Piece firstPiece,
                              Position secondPosition, Piece secondPiece) {
        Map<Position, Piece> board = new LinkedHashMap<>();
        board.put(firstPosition, firstPiece);
        board.put(secondPosition, secondPiece);
        return new Board(board);
    }

    private void clearTestDatabase() throws Exception {
        Files.createDirectories(TEST_DATABASE_PATH.getParent());
        Files.deleteIfExists(TEST_DATABASE_PATH);
    }
}
