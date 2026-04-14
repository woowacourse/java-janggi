package service;

import domain.Game;
import domain.board.Board;
import domain.player.Players;
import domain.position.Position;
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

    public Game playTurn(Long gameId, Position from, Position to) {
        Game game = loadGame(gameId);
        game.playOneTurn(from, to);
        gameRepository.save(game);

        if (game.isGameOver()) {
            gameRepository.deleteById(game.id());
        }

        return game;
    }

}
