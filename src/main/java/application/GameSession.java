package application;

import domain.Game;

class GameSession {
    private final long gameId;
    private final Game game;
    private int moveCount;

    GameSession(long gameId, Game game, int moveCount) {
        this.gameId = gameId;
        this.game = game;
        this.moveCount = moveCount;
    }

    long gameId() {
        return gameId;
    }

    Game game() {
        return game;
    }

    int nextMoveCount() {
        return moveCount + 1;
    }

    void updateMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }
}
