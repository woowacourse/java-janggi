package janggi.service;

import java.util.Optional;

import janggi.domain.board.coordinate.Point;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.Game;
import janggi.domain.game.GameResult;
import janggi.domain.game.GameSession;
import janggi.domain.side.Side;
import janggi.repository.JdbcGameRepository;

public class GameService {

    private final JdbcGameRepository jdbcGameRepository;

    public GameService(JdbcGameRepository jdbcGameRepository) {
        this.jdbcGameRepository = jdbcGameRepository;
    }

    public Optional<Integer> findActiveGameId() {
        return jdbcGameRepository.findActiveGameId();
    }

    public GameSession continueGame(int gameId) {
        Game game = jdbcGameRepository.load(gameId);
        return new GameSession(game, gameId);
    }

    public GameSession createGame(BoardSetUp choBoardSetUp, BoardSetUp hanBoardSetUp) {
        Game game = Game.createGame(choBoardSetUp, hanBoardSetUp);
        int gameId = jdbcGameRepository.save(game);
        return new GameSession(game, gameId);
    }

    public GameResult move(GameSession session, Point from, Point to) {
        GameResult result = session.game().move(from, to);
        jdbcGameRepository.update(session.gameId(), session.game());

        if (result.isGameOver()) {
            jdbcGameRepository.finish(session.gameId(), result.getWinner());
        }

        return result;
    }

    public void finish(int gameId, Side winner) {
        jdbcGameRepository.finish(gameId, winner);
    }
}
