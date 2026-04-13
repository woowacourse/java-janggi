package application;

import application.port.GameSessionRepository;
import application.port.StoredGameSession;
import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.piece.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("GameSessionService 클래스 테스트")
class GameSessionServiceTest {

    @Test
    @DisplayName("진행 중인 게임이 없으면 새 세션을 시작한다")
    void loadOrStartStartsNewSession() {
        FakeGameSessionRepository repository = new FakeGameSessionRepository();
        GameSessionService gameSessionService = new GameSessionService(repository, new GameReplayer());

        GameSession gameSession = gameSessionService.loadOrStart();

        assertThat(gameSession.id()).isEqualTo(1L);
        assertThat(gameSession.game().getTurn().getTeam()).isEqualTo(Team.HAN);
        assertThat(repository.isNewSessionCreated()).isTrue();
    }

    @Test
    @DisplayName("진행 중인 게임이 있으면 명령 이력을 재생해 복구한다")
    void loadOrStartRestoresInProgressSession() {
        FakeGameSessionRepository repository = new FakeGameSessionRepository();
        repository.prepareStoredGame(3L, List.of("1", "1", "e6 e5"));
        GameSessionService gameSessionService = new GameSessionService(repository, new GameReplayer());

        GameSession gameSession = gameSessionService.loadOrStart();

        assertThat(gameSession.id()).isEqualTo(3L);
        assertThat(gameSession.game().getTurn().getTeam()).isEqualTo(Team.HAN);
        assertThat(gameSession.game().getBoard().isEmpty(new Position(Column.E, Row.SIX))).isTrue();
        assertThat(gameSession.game().getBoard()
                .findPieceByPosition(new Position(Column.E, Row.FIVE))).isPresent();
    }

    @Test
    @DisplayName("유효한 명령을 실행하면 명령 이력을 저장한다")
    void executeStoresSuccessfulCommand() {
        FakeGameSessionRepository repository = new FakeGameSessionRepository();
        GameSessionService gameSessionService = new GameSessionService(repository, new GameReplayer());
        GameSession gameSession = gameSessionService.loadOrStart();

        gameSessionService.execute(gameSession, "1");

        assertThat(gameSession.game().getTurn().getTeam()).isEqualTo(Team.CHO);
        assertThat(repository.savedCommands()).containsExactly(new SavedCommand(1L, "1"));
    }

    @Test
    @DisplayName("유효하지 않은 명령은 저장하지 않는다")
    void executeDoesNotStoreInvalidCommand() {
        FakeGameSessionRepository repository = new FakeGameSessionRepository();
        GameSessionService gameSessionService = new GameSessionService(repository, new GameReplayer());
        GameSession gameSession = gameSessionService.loadOrStart();

        assertThatThrownBy(() -> gameSessionService.execute(gameSession, "5"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThat(repository.savedCommands()).isEmpty();
    }

    private static class FakeGameSessionRepository implements GameSessionRepository {
        private Optional<StoredGameSession> storedGameSession = Optional.empty();
        private final List<SavedCommand> savedCommands = new ArrayList<>();
        private boolean newSessionCreated;

        @Override
        public Optional<StoredGameSession> findInProgress() {
            return storedGameSession;
        }

        @Override
        public long create() {
            newSessionCreated = true;
            return 1L;
        }

        @Override
        public void appendCommand(long gameSessionId, String rawCommand) {
            savedCommands.add(new SavedCommand(gameSessionId, rawCommand));
        }

        void prepareStoredGame(long id, List<String> rawCommands) {
            storedGameSession = Optional.of(new StoredGameSession(id, rawCommands));
        }

        boolean isNewSessionCreated() {
            return newSessionCreated;
        }

        List<SavedCommand> savedCommands() {
            return List.copyOf(savedCommands);
        }
    }

    private record SavedCommand(long gameSessionId, String rawCommand) {
    }
}
