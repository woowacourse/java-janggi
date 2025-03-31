package janggi.entity;

import janggi.domain.JanggiGame;
import janggi.domain.Player;

public record JanggiEntity(long janggiId,
                           String redPlayerName,
                           String greenPlayerName,
                           double redScore,
                           double greenScore,
                           String gameStatus,
                           String gameTurn) {

    public static JanggiEntity from(JanggiGame janggiGame, long janggiId) {
        Player redPlayer = janggiGame.getRedPlayer();
        Player greenPlayer = janggiGame.getGreenPlayer();

        return new JanggiEntity(janggiId,
                redPlayer.getName(),
                greenPlayer.getName(),
                redPlayer.getScore().value(),
                greenPlayer.getScore().value(),
                janggiGame.getGameStatus().name(),
                janggiGame.getTurn().name());
    }

    public static JanggiEntity from(JanggiGame janggiGame) {
        return from(janggiGame, 0);
    }

    public JanggiEntity addJanggiId(final Long janggiId) {
        return new JanggiEntity(janggiId, redPlayerName, greenPlayerName, redScore, greenScore, gameStatus, gameTurn);
    }
}
