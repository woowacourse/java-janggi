package application.port;

import java.util.Optional;

public interface GameSessionRepository {

    Optional<StoredGameSession> findInProgress();

    long create();

    void appendCommand(long gameSessionId, String rawCommand);
}
