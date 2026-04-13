package service;

import domain.Game;
import domain.board.Board;
import domain.player.Players;
import repository.GameRepository;

public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game startNewGame(Players players, Board board) {
        Game game = new Game(players, board);
        gameRepository.save(game);
        return game;
    }

    public Game loadGame(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));
    }

    public void saveProgress(Game game) {
        gameRepository.save(game);
    }

    public void deleteFinishedGame(Game game) {
        if (!game.isGameOver()) {
            return;
        }
        gameRepository.deleteById(game.id());
    }
}
