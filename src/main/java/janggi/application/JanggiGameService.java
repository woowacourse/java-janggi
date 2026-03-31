package janggi.application;

import janggi.application.dto.GameSummary;
import janggi.domain.Board;
import janggi.domain.JanggiGame;
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
}
