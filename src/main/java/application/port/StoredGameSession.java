package application.port;

import java.util.List;

public record StoredGameSession(long id, List<String> rawCommands) {

    public StoredGameSession {
        rawCommands = List.copyOf(rawCommands);
    }
}
