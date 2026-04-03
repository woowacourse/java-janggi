package janggi.controller;

import janggi.domain.board.HorseElephantPosition;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.Game;
import janggi.domain.position.Position;
import janggi.repository.GameRepository;
import java.util.Map;

public class JanggiService {

    private final GameRepository gameRepository;

    public JanggiService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Long makeGame(Map<Dynasty, HorseElephantPosition> horseElephantPositions) {
        Game game = Game.initGame(horseElephantPositions);
        return gameRepository.save(game);
    }

    public Game findGame(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("게임이 존재하지 않습니다."));
    }

    public void movePiece(Long gameId, Position from, Position to) {
        Game game = findGame(gameId);
        game.movePiece(from, to);

        gameRepository.update(gameId, game);
    }

}
