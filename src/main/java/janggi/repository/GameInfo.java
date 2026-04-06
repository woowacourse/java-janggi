package janggi.repository;

public class GameInfo {

    private final int gameId;
    private final String currentTeam;
    private final String winner;

    public GameInfo(int gameId, String currentTeam, String winner) {
        this.gameId = gameId;
        this.currentTeam = currentTeam;
        this.winner = winner;
    }

    public int getGameId() {
        return gameId;
    }

    public String getCurrentTeam() {
        return currentTeam;
    }

    public String getWinner() {
        return winner;
    }
}
