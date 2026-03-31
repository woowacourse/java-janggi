package janggi.application;

import janggi.application.dto.GameSnapshot;
import janggi.application.dto.GameSummary;
import janggi.domain.Board;
import janggi.domain.JanggiGame;
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

    private GameStatus gameStatus(GameSnapshot gameSnapshot) {
        if (gameSnapshot.finished()) {
            return new FinishedGame(gameSnapshot.winner());
        }
        if (gameSnapshot.currentTurn() == Team.HAN) {
            return new HanTurn();
        }
        return new ChoTurn();
    }
}
