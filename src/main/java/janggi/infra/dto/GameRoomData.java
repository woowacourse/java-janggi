package janggi.infra.dto;

import janggi.domain.JanggiGame;

public record GameRoomData(
        String currentTurn,
        String winner,
        double choScore,
        double hanScore
) {
    public static GameRoomData from(JanggiGame game) {
        if (game.isFinished()) {
            return new GameRoomData(
                    game.getTeam().name(),
                    game.getWinner().name(),
                    game.getChoScore(),
                    game.getHanScore()
            );
        }
        return new GameRoomData(
                game.getTeam().name(),
                null,
                game.getChoScore(),
                game.getHanScore()
        );
    }
}
