package domain;

import dao.JanggiGameDao;
import dao.PieceDao;
import dao.PlayerDao;
import domain.game.JanggiGame;
import domain.game.dto.JanggiGameDto;
import domain.piece.Board;
import domain.piece.Piece;
import domain.piece.strategy.HorseElephantSetupStrategy;
import domain.player.Player;
import domain.player.Players;
import domain.player.Username;
import domain.player.Usernames;
import domain.position.Position;
import domain.turn.GameState;
import domain.turn.Turn;
import domain.turn.TurnState;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JanggiManager {

    private final PieceDao pieceDao;
    private final JanggiGameDao janggiGameDao;
    private final PlayerDao playerDao;

    public JanggiManager() {
        pieceDao = new PieceDao();
        janggiGameDao = new JanggiGameDao();
        playerDao = new PlayerDao();
    }

    public List<JanggiGameDto> findInProgressGames(Connection connection) {
        List<Long> inProgressGameIds = janggiGameDao.findInProgressGameIds(connection);

        return inProgressGameIds.stream()
                .map(gameId -> getInProgressGameInfo(gameId, connection))
                .toList();
    }

    public long saveNewGame(Players players, HorseElephantSetupStrategy choStrategy,
                            HorseElephantSetupStrategy hanStrategy, Connection connection) {
        JanggiGame janggiGame = JanggiGame.start(players, choStrategy, hanStrategy);

        long gameId = janggiGameDao.saveJanggiGame(janggiGame.getTurnState(), janggiGame.getGameState(),
                connection);

        savePlayers(players, gameId, connection);

        Map<Position, Piece> pieces = janggiGame.getAlivePieces();
        pieceDao.savePieces(pieces, gameId, connection);

        return gameId;
    }

    public void undo(Long gameId, Connection connection) {
        JanggiGame janggiGame = findJanggiGameById(gameId, connection);
        janggiGame.undo();
        updateJanggiGameStatus(gameId, janggiGame, connection);
    }

    public void movePiece(Long gameId, Position from, Position to, Connection connection) {
        JanggiGame janggiGame = findJanggiGameById(gameId, connection);
        janggiGame.movePiece(from, to);

        pieceDao.removePiece(gameId, to, connection);
        pieceDao.updatePiecePosition(gameId, from, to, connection);

        updateJanggiGameStatus(gameId, janggiGame, connection);
    }

    public Map<Position, Piece> getGamePieces(Long gameId, Connection connection) {
        return findJanggiGameById(gameId, connection).getAlivePieces();
    }

    public boolean isInProgress(Long gameId, Connection connection) {
        return findJanggiGameById(gameId, connection).isInProgress();
    }

    public Player getCurrentPlayer(Long gameId, Connection connection) {
        return findJanggiGameById(gameId, connection).getCurrentPlayer();
    }

    public Player findWinner(Long gameId, Connection connection) {
        return findJanggiGameById(gameId, connection).findWinner();
    }

    public boolean isFinishedByCheckmate(Long gameId, Connection connection) {
        return findJanggiGameById(gameId, connection).isFinishedByCheckmate();
    }

    public Map<String, Double> calculatePlayerScore(Long gameId, Connection connection) {
        return findJanggiGameById(gameId, connection).calculatePlayerScore();
    }

    private void updateJanggiGameStatus(Long gameId, JanggiGame janggiGame, Connection connection) {
        GameState gameState = janggiGame.getGameState();
        TurnState turnState = janggiGame.getTurnState();
        janggiGameDao.updateGameState(gameId, gameState, connection);
        janggiGameDao.updateTurnState(gameId, turnState, connection);
    }

    private JanggiGame findJanggiGameById(Long gameId, Connection connection) {
        TurnState turnState = janggiGameDao.findTurnStateById(gameId, connection)
                .orElseThrow(() -> new IllegalStateException("해당하는 게임 턴 데이터가 존재하지 않습니다"));

        GameState gameState = janggiGameDao.findGameStateById(gameId, connection)
                .orElseThrow(() -> new IllegalStateException("해당하는 게임 종료 여부 데이터가 존재하지 않습니다"));

        List<Player> playersByGameId = playerDao.findPlayersByGameId(gameId, connection);
        Players players = toPlayers(playersByGameId);

        Map<Position, Piece> pieces = pieceDao.findBoardPiecesByGameId(gameId, connection);
        Board board = new Board(pieces);

        Turn turn = gameState.createTurn(board, turnState);
        return JanggiGame.from(players, turn);
    }

    private void savePlayers(Players players, Long gameId, Connection connection) {
        Arrays.stream(TeamType.values())
                .map(players::getTeamPlayer)
                .forEach(player -> playerDao.savePlayer(player, gameId, connection));
    }

    private Players toPlayers(List<Player> players) {
        Username choPlayerName = new Username(getTeamPlayer(players, TeamType.CHO).getName());
        Username hanPlayerName = new Username(getTeamPlayer(players, TeamType.HAN).getName());

        Usernames usernames = new Usernames(choPlayerName, hanPlayerName);
        return Players.createFrom(usernames, choPlayerName);
    }

    private JanggiGameDto getInProgressGameInfo(Long gameId, Connection connection) {
        List<Player> players = playerDao.findPlayersByGameId(gameId, connection);

        String choPlayerName = getTeamPlayer(players, TeamType.CHO).getName();
        String hanPlayerName = getTeamPlayer(players, TeamType.HAN).getName();

        return new JanggiGameDto(gameId, choPlayerName, hanPlayerName);
    }

    private Player getTeamPlayer(List<Player> players, TeamType teamType) {
        return players.stream()
                .filter(player -> player.getTeamType() == teamType)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("해당하는 팀의 플레이어 데이터가 존재하지 않습니다."));
    }
}
