package application;

import domain.game.JanggiGame;

public record GameStartResult(
        JanggiGame game,
        boolean resumed
) {

    public static GameStartResult resumed(JanggiGame game) {
        return new GameStartResult(game, true);
    }

    public static GameStartResult started(JanggiGame game) {
        return new GameStartResult(game, false);
    }
}
