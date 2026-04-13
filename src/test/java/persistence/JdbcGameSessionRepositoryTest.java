package persistence;

import application.port.StoredGameSession;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JdbcGameSessionRepository 클래스 테스트")
class JdbcGameSessionRepositoryTest {
    @TempDir
    Path tempDirectory;

    @Test
    @DisplayName("세션을 생성하면 진행 중 게임으로 조회할 수 있다")
    void createSession() {
        JdbcGameSessionRepository repository = repository();

        long id = repository.create();

        assertThat(repository.findInProgress())
                .contains(new StoredGameSession(id, List.of()));
    }

    @Test
    @DisplayName("명령을 추가하면 입력 순서대로 저장한다")
    void appendCommand() {
        JdbcGameSessionRepository repository = repository();
        long id = repository.create();

        repository.appendCommand(id, "1");
        repository.appendCommand(id, "4");
        repository.appendCommand(id, "e6 e5");

        Optional<StoredGameSession> storedGameSession = repository.findInProgress();

        assertThat(storedGameSession).contains(new StoredGameSession(id, List.of("1", "4", "e6 e5")));
    }

    @Test
    @DisplayName("파일 DB에 저장한 데이터는 저장소를 다시 만들어도 복구된다")
    void restoreAfterRestart() {
        JdbcGameSessionRepository firstRepository = repository();
        long id = firstRepository.create();
        firstRepository.appendCommand(id, "1");
        firstRepository.appendCommand(id, "1");

        JdbcGameSessionRepository secondRepository = repository();

        assertThat(secondRepository.findInProgress())
                .contains(new StoredGameSession(id, List.of("1", "1")));
    }

    @Test
    @DisplayName("완료 처리한 세션은 진행 중 게임 조회에서 제외한다")
    void finishSession() {
        JdbcGameSessionRepository repository = repository();
        long id = repository.create();

        repository.finish(id);

        assertThat(repository.findInProgress()).isEmpty();
    }

    @Test
    @DisplayName("포기 처리한 세션은 진행 중 게임 조회에서 제외한다")
    void abandonSession() {
        JdbcGameSessionRepository repository = repository();
        long id = repository.create();

        repository.abandon(id);

        assertThat(repository.findInProgress()).isEmpty();
    }

    private JdbcGameSessionRepository repository() {
        return new JdbcGameSessionRepository(connectionProvider());
    }

    private ConnectionProvider connectionProvider() {
        return new ConnectionProvider(databaseUrl(), "sa", "");
    }

    private String databaseUrl() {
        return "jdbc:h2:file:" + tempDirectory.resolve("janggi");
    }
}
