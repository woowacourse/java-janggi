package dao.fixture;

import dao.JanggiGameDao;
import domain.TeamType;
import domain.player.Players;
import domain.player.Usernames;
import domain.turn.GameState;
import domain.turn.TurnState;
import java.sql.Connection;
import java.sql.SQLException;

public class JanggiGameTestFixture {

    public static long saveNewJanggiGame(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        JanggiGameDao janggiGameDao = new JanggiGameDao(connection);
        return saveNewGame(janggiGameDao);
    }

    private static long saveNewGame(JanggiGameDao janggiGameDao) {
        String choPlayerName = "테스트1";
        String hanPlayerName = "테스트2";
        Usernames usernames = new Usernames(choPlayerName, hanPlayerName);
        Players players = Players.createFrom(usernames, choPlayerName);
        TurnState turnState = new TurnState(false, TeamType.CHO);
        GameState gameState = GameState.IN_PROGRESS;

        return janggiGameDao.saveJanggiGame(players, turnState, gameState);
    }
}
