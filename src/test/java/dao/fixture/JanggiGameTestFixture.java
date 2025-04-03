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
import java.util.Map;

public class JanggiGameTestFixture {

    public static long saveNewJanggiGame(Connection connection) {
        JanggiGameDao janggiGameDao = new JanggiGameDao();
        PlayerDao playerDao = new PlayerDao();
        return saveNewGame(janggiGameDao, playerDao, connection);
    }

    public static long saveNewJanggiGame(TurnState turnState, GameState gameState, Connection connection) {
        JanggiGameDao janggiGameDao = new JanggiGameDao();
        PlayerDao playerDao = new PlayerDao();
        return saveNewGame(janggiGameDao, playerDao, turnState, gameState, connection);
    }

    public static long saveBoardPieces(Long gameId, Map<Position, Piece> pieces, Connection connection) {
        PieceDao pieceDao = new PieceDao();
        pieceDao.savePieces(pieces, gameId, connection);
        return gameId;
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
