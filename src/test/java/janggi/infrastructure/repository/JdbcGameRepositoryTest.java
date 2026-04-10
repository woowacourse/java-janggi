package janggi.infrastructure.repository;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Game;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.Formation;
import janggi.domain.piece.Piece;
import janggi.domain.player.Name;
import janggi.domain.player.Player;
import janggi.domain.player.Players;
import janggi.domain.space.Position;
import janggi.infrastructure.dao.GameDao;
import janggi.infrastructure.dao.MoveHistoryDao;
import janggi.infrastructure.db.ConnectionContext;
import janggi.infrastructure.db.DatabaseConnection;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JdbcGameRepositoryTest {
    private JdbcGameRepository gameRepository;

    @BeforeAll
    static void beforeAll() throws Exception {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             InputStream inputStream = JdbcGameRepositoryTest.class.getClassLoader().getResourceAsStream("schema.sql")) {

            if (inputStream == null) {
                throw new IllegalStateException("schema.sql 파일을 찾을 수 없습니다.");
            }

            String schemaSql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            statement.execute(schemaSql);
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        GameDao gameDao = new GameDao();
        MoveHistoryDao moveHistoryDao = new MoveHistoryDao();
        gameRepository = new JdbcGameRepository(gameDao, moveHistoryDao);

        Connection connection = DatabaseConnection.getConnection();
        ConnectionContext.set(connection);

        try (Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM move_history");
            statement.execute("DELETE FROM game");
        }
    }

    @AfterEach
    void tearDown() throws Exception {
        Connection connection = ConnectionContext.get();
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        ConnectionContext.clear();
    }

    @Test
    void 게임_전체_상태를_저장하고_다시_조회하여_완벽히_복원한다() {
        // given
        Name choName = new Name("고래");
        Name hanName = new Name("제이콥");
        Formation choFormation = Formation.LEFT_ELEPHANT;
        Formation hanFormation = Formation.RIGHT_ELEPHANT;

        Player choPlayer = new Player(choName, Side.CHO, choFormation);
        Player hanPlayer = new Player(hanName, Side.HAN, hanFormation);
        Players players = new Players(choPlayer, hanPlayer);

        Board board = BoardFactory.create(choFormation, hanFormation);
        Game originalGame = Game.startNew(board, players);

        Long savedId = gameRepository.save(originalGame);

        Position source = Position.of(0, 3);
        Position target = Position.of(0, 4);
        originalGame.move(source, target);

        // when
        gameRepository.update(savedId, originalGame);

        Game restoredGame = gameRepository.findById(savedId)
                .orElseThrow(() -> new IllegalArgumentException("게임을 찾을 수 없습니다."));

        // then: 플레이어 및 턴 정보 검증
        assertThat(restoredGame.getCurrentSide()).isEqualTo(Side.HAN);
        assertThat(restoredGame.getPlayerNameBySide(Side.CHO)).isEqualTo(choName);
        assertThat(restoredGame.getPlayerNameBySide(Side.HAN)).isEqualTo(hanName);
        assertThat(restoredGame.getPlayerFormationBySide(Side.CHO)).isEqualTo(choFormation);

        // then: 보드 상태 검증
        Map<Position, Piece> restoredBoard = new HashMap<>();
        restoredGame.forEachPiece(restoredBoard::put);

        assertThat(restoredBoard).hasSize(32);
        assertThat(restoredBoard.get(target).getType().name()).isEqualTo("SOLDIER");
        assertThat(restoredBoard.containsKey(source)).isFalse();
    }
}
