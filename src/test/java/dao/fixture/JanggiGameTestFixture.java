package dao.fixture;

import dao.JanggiGameDao;
import dao.PieceDao;
import dao.PlayerDao;
import domain.TeamType;
import domain.piece.Piece;
import domain.player.Players;
import domain.player.Username;
import domain.player.Usernames;
import domain.position.Position;
import domain.turn.GameState;
import domain.turn.TurnState;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class JanggiGameTestFixture {

    public static long saveNewJanggiGame(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        JanggiGameDao janggiGameDao = new JanggiGameDao(connection);
        PlayerDao playerDao = new PlayerDao(connection);
        return saveNewGame(janggiGameDao, playerDao);
    }

    public static long saveNewJanggiGame(Connection connection, TurnState turnState, GameState gameState)
            throws SQLException {
        connection.setAutoCommit(false);
        JanggiGameDao janggiGameDao = new JanggiGameDao(connection);
        PlayerDao playerDao = new PlayerDao(connection);
        return saveNewGame(janggiGameDao, playerDao, turnState, gameState);
    }

    public static long saveBoardPieces(Connection connection, Long gameId, Map<Position, Piece> pieces) {
        PieceDao pieceDao = new PieceDao(connection);
        return pieceDao.savePieces(pieces, gameId);
    }

    private static long saveNewGame(JanggiGameDao janggiGameDao, PlayerDao playerDao) {
        TurnState turnState = new TurnState(false, TeamType.CHO);
        GameState gameState = GameState.IN_PROGRESS;
        return saveNewGame(janggiGameDao, playerDao, turnState, gameState);
    }

    private static long saveNewGame(JanggiGameDao janggiGameDao, PlayerDao playerDao, TurnState turnState,
                                    GameState gameState) {
        Username choPlayerName = new Username("테스트1");
        Username hanPlayerName = new Username("테스트2");
        Usernames usernames = new Usernames(choPlayerName, hanPlayerName);
        Players players = Players.createFrom(usernames, choPlayerName);

        long gameId = janggiGameDao.saveJanggiGame(turnState, gameState);
        playerDao.savePlayer(players.getTeamPlayer(TeamType.CHO), gameId);
        playerDao.savePlayer(players.getTeamPlayer(TeamType.HAN), gameId);
        return gameId;
    }
}
