package janggi.service;

import java.util.Optional;

import janggi.domain.board.coordinate.Point;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.Game;
import janggi.domain.game.GameSession;
import janggi.domain.side.Side;
import janggi.repository.GameRepository;

public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Optional<Integer> findActiveGameId() {
        return gameRepository.findActiveGameId();
    }

    public GameSession continueGame(int gameId) {
        Game game = gameRepository.load(gameId);
        return new GameSession(game, gameId);
    }

    public GameSession createGame(BoardSetUp choBoardSetUp, BoardSetUp hanBoardSetUp) {
        Game game = Game.createGame(choBoardSetUp, hanBoardSetUp);
        int gameId = gameRepository.save(game);
        return new GameSession(game, gameId);
    }

    public Side move(GameSession session, Point from, Point to) {
        Side winner = session.game().move(from, to);
        gameRepository.update(session.gameId(), session.game());

        if (!winner.equals(Side.NONE)) {
            gameRepository.finish(session.gameId(), winner);
        }

        return winner;
    }

    public void finish(int gameId) {
        gameRepository.finish(gameId, Side.NONE);
    }
}
