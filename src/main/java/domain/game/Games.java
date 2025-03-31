package domain.game;

public class Games {
    private int gameId;
    private String gameStatus;
    private int currentTurn;
    private int bluePlayerId;
    private int redPlayerId;

    public Games(int gameId, String gameStatus, int currentTurn, int bluePlayerId, int redPlayerId) {
        this.gameId = gameId;
        this.gameStatus = gameStatus;
        this.currentTurn = currentTurn;
        this.bluePlayerId = bluePlayerId;
        this.redPlayerId = redPlayerId;
    }

    public int getGameId() {
        return gameId;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public int getBluePlayerId() {
        return bluePlayerId;
    }

    public int getRedPlayerId() {
        return redPlayerId;
    }
    
}
