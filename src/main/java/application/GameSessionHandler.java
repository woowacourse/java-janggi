package application;

import domain.game.Game;
import domain.game.Position;
import domain.game.Side;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.player.Name;
import domain.player.Players;
import domain.score.ScorePolicy;
import java.util.List;
import persistence.JdbcGameRepository;
import persistence.SavedGame;
import persistence.SavedGameSummary;

public class GameSessionHandler {
    private final JdbcGameRepository gameRepository;

    public GameSessionHandler(JdbcGameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameSession initializeNewSession(Name choName, Name hanName, Formation choFormation, Formation hanFormation) {
        Players players = Players.createInitial(choName, hanName);
        Board board = BoardFactory.create(choFormation, hanFormation);
        Game game = new Game(board, players, new ScorePolicy());
        long gameId = gameRepository.createGame(
                choName.name(),
                hanName.name(),
                choFormation,
                hanFormation,
                game.getBoard(),
                game.getCurrentSide(),
                0
        );
        return new GameSession(gameId, game, 0);
    }

    public List<SavedGameSummary> findInProgressGames() {
        return gameRepository.findInProgressGames();
    }

    public GameSession restoreSession(long gameId) {
        SavedGame savedGame = gameRepository.findInProgressById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("선택한 게임을 불러올 수 없습니다. 다시 시도하세요."));

        Players players = restorePlayers(savedGame);
        Game game = new Game(savedGame.board(), players, new ScorePolicy());
        return new GameSession(savedGame.id(), game, savedGame.moveCount());
    }

    public void move(GameSession session, Position source, Position target) {
        Game game = session.game();
        game.selectSource(source).validateDestinations(target);
        game.move(source, target);
        int nextTurn = session.nextMoveCount();
        gameRepository.updateGameState(session.gameId(), game.getBoard(), game.getCurrentSide(), nextTurn);
        session.updateMoveCount(nextTurn);
    }

    public void finish(GameSession session) {
        gameRepository.finishGame(session.gameId());
    }

    private Players restorePlayers(SavedGame savedGame) {
        Players players = Players.createInitial(
                new Name(savedGame.choPlayerName()),
                new Name(savedGame.hanPlayerName())
        );
        if (savedGame.currentSide() == Side.HAN) {
            players.switchPlayer();
        }
        return players;
    }
}
