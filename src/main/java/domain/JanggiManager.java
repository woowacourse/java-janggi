package domain;

import dao.JanggiGameDao;
import dao.PieceDao;
import dao.PlayerDao;
import domain.game.JanggiGame;
import domain.game.dto.JanggiGameResponseDto;
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

    public JanggiManager(Connection connection) {
        this.pieceDao = new PieceDao(connection);
        this.janggiGameDao = new JanggiGameDao(connection);
        this.playerDao = new PlayerDao(connection);
    }

    public List<JanggiGameResponseDto> findInProgressGames() {
        List<Long> inProgressGameIds = janggiGameDao.findInProgressGameIds();

        return inProgressGameIds.stream()
                .map(this::getInProgressGameInfo)
                .toList();
    }

    public long saveNewGame(Players players, HorseElephantSetupStrategy choStrategy,
                            HorseElephantSetupStrategy hanStrategy) {
        JanggiGame janggiGame = JanggiGame.start(players, choStrategy, hanStrategy);

        long gameId = janggiGameDao.saveJanggiGame(janggiGame.getTurnState(), janggiGame.getGameState());

        savePlayers(players, gameId);

        Map<Position, Piece> pieces = janggiGame.getAlivePieces();
        pieceDao.savePieces(pieces, gameId);

        return gameId;
    }

    public void undo(Long gameId) {
        JanggiGame janggiGame = findJanggiGameById(gameId);
        janggiGame.undo();
        updateJanggiGameStatus(gameId, janggiGame);
    }

    public void movePiece(Long gameId, Position from, Position to) {
        JanggiGame janggiGame = findJanggiGameById(gameId);
        janggiGame.movePiece(from, to);

        pieceDao.removePiece(gameId, to);
        pieceDao.updatePiecePosition(gameId, from, to);

        updateJanggiGameStatus(gameId, janggiGame);
    }

    public Map<Position, Piece> getGamePieces(Long gameId) {
        return findJanggiGameById(gameId).getAlivePieces();
    }

    public boolean isInProgress(Long gameId) {
        return findJanggiGameById(gameId).isInProgress();
    }

    public Player getCurrentPlayer(Long gameId) {
        return findJanggiGameById(gameId).getCurrentPlayer();
    }

    public Player findWinner(Long gameId) {
        return findJanggiGameById(gameId).findWinner();
    }

    public boolean isFinishedByCheckmate(Long gameId) {
        return findJanggiGameById(gameId).isFinishedByCheckmate();
    }

    public Map<Player, Double> calculatePlayerScore(Long gameId) {
        return findJanggiGameById(gameId).calculatePlayerScore();
    }

    private void updateJanggiGameStatus(Long gameId, JanggiGame janggiGame) {
        GameState gameState = janggiGame.getGameState();
        TurnState turnState = janggiGame.getTurnState();
        janggiGameDao.updateGameState(gameId, gameState);
        janggiGameDao.updateTurnState(gameId, turnState);
    }

    private JanggiGame findJanggiGameById(Long gameId) {
        TurnState turnState = janggiGameDao.findTurnStateById(gameId)
                .orElseThrow(() -> new IllegalStateException("해당하는 게임 턴 데이터가 존재하지 않습니다"));

        GameState gameState = janggiGameDao.findGameStateById(gameId)
                .orElseThrow(() -> new IllegalStateException("해당하는 게임 종료 여부 데이터가 존재하지 않습니다"));

        List<Player> playersByGameId = playerDao.findPlayersByGameId(gameId);
        Players players = toPlayers(playersByGameId);

        Map<Position, Piece> pieces = pieceDao.findBoardPiecesByGameId(gameId);
        Board board = new Board(pieces);

        Turn turn = gameState.createTurn(board, turnState);
        return JanggiGame.from(players, turn);
    }

    private void savePlayers(Players players, Long gameId) {
        Arrays.stream(TeamType.values())
                .map(players::getTeamPlayer)
                .forEach(player -> playerDao.savePlayer(player, gameId));
    }

    private Players toPlayers(List<Player> players) {
        Username choPlayerName = new Username(getTeamPlayer(players, TeamType.CHO).getName());
        Username hanPlayerName = new Username(getTeamPlayer(players, TeamType.HAN).getName());

        Usernames usernames = new Usernames(choPlayerName, hanPlayerName);
        return Players.createFrom(usernames, choPlayerName);
    }

    private JanggiGameResponseDto getInProgressGameInfo(Long gameId) {
        List<Player> players = playerDao.findPlayersByGameId(gameId);

        Player choPlayer = getTeamPlayer(players, TeamType.CHO);
        Player hanPlayer = getTeamPlayer(players, TeamType.HAN);

        return new JanggiGameResponseDto(gameId, choPlayer.getName(), hanPlayer.getName());
    }

    private Player getTeamPlayer(List<Player> players, TeamType teamType) {
        return players.stream()
                .filter(player -> player.getTeamType() == teamType)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("해당하는 팀의 플레이어 데이터가 존재하지 않습니다."));
    }
}
