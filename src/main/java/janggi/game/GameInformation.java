package janggi.game;

import janggi.rule.GameState;
import janggi.rule.PieceAssignType;
import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        GameInformation that = (GameInformation) object;
        return gameId == that.gameId && Objects.equals(gameTitle, that.gameTitle)
                && choAssignType == that.choAssignType && hanAssignType == that.hanAssignType
                && gameState == that.gameState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, gameTitle, choAssignType, hanAssignType, gameState);
    }
}
