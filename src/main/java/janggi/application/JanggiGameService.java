package janggi.application;

import janggi.application.dto.GameSnapshot;
import janggi.application.dto.GameSummary;
import janggi.domain.Board;
import janggi.domain.JanggiGame;
import janggi.domain.Point;
import janggi.domain.status.ChoTurn;
import janggi.domain.status.FinishedGame;
import janggi.domain.status.GameStatus;
import janggi.domain.status.HanTurn;
import janggi.domain.status.Team;
import java.util.List;

public class JanggiGameService {

    private final GameRepository gameRepository;
    private final InitialBoardProvider initialBoardProvider;

    public JanggiGameService(GameRepository gameRepository, InitialBoardProvider initialBoardProvider) {
        this.gameRepository = gameRepository;
        this.initialBoardProvider = initialBoardProvider;
    }

    public List<GameSummary> findAllGames() {
        return gameRepository.findAll();
    }

    public JanggiGame startNewGame() {
        Board board = new Board();
        board.init(initialBoardProvider.load());
        return new JanggiGame(board);
    }

    public JanggiGame loadGame(Long gameId) {
        GameSnapshot gameSnapshot = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));
        Board board = new Board();
        board.init(gameSnapshot.positions());
        return new JanggiGame(board, gameStatus(gameSnapshot));
    }

    public void play(Long gameId, Point from, Point to) {
        JanggiGame janggiGame = loadGame(gameId);
        janggiGame.play(from, to);
        gameRepository.update(toSnapshot(gameId, janggiGame));
    }

    public Long createGame() {
        JanggiGame janggiGame = startNewGame();
        return gameRepository.save(toSnapshot(null, janggiGame));
    }

    private GameStatus gameStatus(GameSnapshot gameSnapshot) {
        if (gameSnapshot.finished()) {
            return new FinishedGame(gameSnapshot.winner());
        }
        if (gameSnapshot.currentTurn() == Team.HAN) {
            return new HanTurn();
        }
        return new ChoTurn();
    }

    private GameSnapshot toSnapshot(Long gameId, JanggiGame janggiGame) {
        return new GameSnapshot(
                gameId,
                janggiGame.currentTurn(),
                janggiGame.isFinished(),
                winner(janggiGame),
                janggiGame.boardStatus()
        );
    }

    private Team winner(JanggiGame janggiGame) {
        if (!janggiGame.isFinished()) {
            return null;
        }
        return janggiGame.getWinner();
    }
}
