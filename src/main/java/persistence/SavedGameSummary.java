package persistence;

public record SavedGameSummary(
        long id,
        String choPlayerName,
        String hanPlayerName,
        int moveCount
) {
}
