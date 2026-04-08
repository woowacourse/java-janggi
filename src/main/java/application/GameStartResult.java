package application;

public record GameStartResult(
        GameSession session,
        boolean resumed
) {

    public static GameStartResult resumed(GameSession session) {
        return new GameStartResult(session, true);
    }

    public static GameStartResult started(GameSession session) {
        return new GameStartResult(session, false);
    }
}
