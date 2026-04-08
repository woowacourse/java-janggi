package persistence;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import domain.board.Formation;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JdbcGameRepositoryTest {
    private JdbcGameRepository gameRepository;

    @BeforeEach
    void setUp() {
        String url = "jdbc:h2:mem:janggi-test-" + UUID.randomUUID() + ";MODE=MySQL;DB_CLOSE_DELAY=-1";
        ConnectionFactory connectionFactory = new ConnectionFactory(url, "sa", "");
        new SchemaInitializer(connectionFactory).initialize();
        gameRepository = new JdbcGameRepository(connectionFactory);
    }

    @Test
    void 진행_중인_게임_목록을_조회할_수_있다() {
        long firstGameId = gameRepository.createGame("초초", "한한", Formation.LEFT_ELEPHANT, Formation.RIGHT_ELEPHANT);
        long secondGameId = gameRepository.createGame("갑돌", "갑순", Formation.OUTER_ELEPHANT, Formation.INNER_ELEPHANT);
        gameRepository.saveMove(firstGameId, 1, Position.of(0, 3), Position.of(0, 4));
        gameRepository.saveMove(secondGameId, 1, Position.of(8, 3), Position.of(8, 4));
        gameRepository.saveMove(secondGameId, 2, Position.of(8, 6), Position.of(8, 5));

        List<SavedGameSummary> savedGames = gameRepository.findInProgressGames();

        assertThat(savedGames).hasSize(2);
        assertThat(savedGames).extracting(SavedGameSummary::id).contains(firstGameId, secondGameId);
        assertThat(savedGames).extracting(SavedGameSummary::choPlayerName).contains("초초", "갑돌");
        assertThat(savedGames).extracting(SavedGameSummary::hanPlayerName).contains("한한", "갑순");
    }

    @Test
    void 진행_중인_게임을_id로_선택해_불러올_수_있다() {
        long gameId = gameRepository.createGame("초초", "한한", Formation.LEFT_ELEPHANT, Formation.RIGHT_ELEPHANT);
        gameRepository.saveMove(gameId, 1, Position.of(0, 3), Position.of(0, 4));
        gameRepository.saveMove(gameId, 2, Position.of(0, 6), Position.of(0, 5));

        SavedGame savedGame = gameRepository.findInProgressById(gameId).orElseThrow();

        assertThat(savedGame.id()).isEqualTo(gameId);
        assertThat(savedGame.choPlayerName()).isEqualTo("초초");
        assertThat(savedGame.hanPlayerName()).isEqualTo("한한");
        assertThat(savedGame.moves()).hasSize(2);
    }

    @Test
    void 종료된_게임은_이어하기_대상에서_제외된다() {
        long gameId = gameRepository.createGame("초초", "한한", Formation.LEFT_ELEPHANT, Formation.RIGHT_ELEPHANT);
        gameRepository.finishGame(gameId);

        List<SavedGameSummary> savedGames = gameRepository.findInProgressGames();
        assertThat(savedGames).isEmpty();

        assertThat(gameRepository.findInProgressById(gameId)).isEmpty();
    }
}
