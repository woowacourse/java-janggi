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
import util.ConnectionFactory;

public class JanggiGameTestFixture {

    public static long saveNewJanggiGame(ConnectionFactory factory) throws SQLException {
        try (Connection connection = factory.getConnection()) {
            connection.setAutoCommit(false);
            JanggiGameDao janggiGameDao = new JanggiGameDao();
            PlayerDao playerDao = new PlayerDao();
            long gameId = saveNewGame(janggiGameDao, playerDao, connection);
            connection.commit();
            return gameId;
        }
    }

    public static long saveNewJanggiGame(ConnectionFactory factory, TurnState turnState, GameState gameState)
            throws SQLException {
        try (Connection connection = factory.getConnection()) {
            connection.setAutoCommit(false);
            JanggiGameDao janggiGameDao = new JanggiGameDao();
            PlayerDao playerDao = new PlayerDao();
            long gameId = saveNewGame(janggiGameDao, playerDao, turnState, gameState, connection);
            connection.commit();
            return gameId;
        }
    }

    public static long saveBoardPieces(ConnectionFactory factory, Long gameId, Map<Position, Piece> pieces)
            throws SQLException {
        try (Connection connection = factory.getConnection()) {
            PieceDao pieceDao = new PieceDao();
            int rows = pieceDao.savePieces(pieces, gameId, connection);
            connection.commit();
            return gameId;
        }
    }

    private static long saveNewGame(JanggiGameDao janggiGameDao, PlayerDao playerDao, Connection connection) {
        TurnState turnState = new TurnState(false, TeamType.CHO);
        GameState gameState = GameState.IN_PROGRESS;
        return saveNewGame(janggiGameDao, playerDao, turnState, gameState, connection);
    }

    private static long saveNewGame(JanggiGameDao janggiGameDao, PlayerDao playerDao, TurnState turnState,
                                    GameState gameState, Connection connection) {
        Username choPlayerName = new Username("테스트1");
        Username hanPlayerName = new Username("테스트2");
        Usernames usernames = new Usernames(choPlayerName, hanPlayerName);
        Players players = Players.createFrom(usernames, choPlayerName);

        long gameId = janggiGameDao.saveJanggiGame(turnState, gameState, connection);
        playerDao.savePlayer(players.getTeamPlayer(TeamType.CHO), gameId, connection);
        playerDao.savePlayer(players.getTeamPlayer(TeamType.HAN), gameId, connection);
        return gameId;
    }
}
