package janggi.game;

import janggi.rule.GameState;
import janggi.rule.PieceAssignType;

public final class GameInformation {

    private final int gameId;
    private final String gameTitle;
    private final PieceAssignType choAssignType;
    private final PieceAssignType hanAssignType;
    private final GameState gameState;

    public GameInformation(
            int gameId,
            String gameTitle,
            PieceAssignType choAssignType,
            PieceAssignType hanAssignType,
            GameState gameState
    ) {
        this.gameId = gameId;
        this.gameTitle = gameTitle;
        this.choAssignType = choAssignType;
        this.hanAssignType = hanAssignType;
        this.gameState = gameState;
    }

    public int getGameId() {
        return gameId;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public PieceAssignType getChoAssignType() {
        return choAssignType;
    }

    public PieceAssignType getHanAssignType() {
        return hanAssignType;
    }

    public GameState getGameState() {
        return gameState;
    }
}
