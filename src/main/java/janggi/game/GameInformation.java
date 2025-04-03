package janggi.game;

import janggi.rule.GameState;
import janggi.rule.PieceAssignType;
import java.util.Objects;

public record GameInformation(int gameId, String gameTitle, PieceAssignType choAssignType,
                              PieceAssignType hanAssignType, GameState gameState) {

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

}
