package janggi.service;

import janggi.config.TransactionManager;
import janggi.domain.Camp;
import janggi.domain.Janggi;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.FormationStrategyFactory;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.exception.DuplicateGameException;

import java.util.List;
import java.util.Map;

public class JanggiService {
    private final GameRepository gameRepository;
    private final TransactionManager transactionManager;

    public JanggiService(GameRepository gameRepository, TransactionManager transactionManager) {
        this.gameRepository = gameRepository;
        this.transactionManager = transactionManager;
    }

    public String createGame(String name, int choFormation, int hanFormation) {
        if (gameRepository.findByName(name).isPresent()) {
            throw new DuplicateGameException("이미 존재하는 게임입니다.");
        }

        Janggi janggi = Janggi.start(
                BoardFactory.create(
                        FormationStrategyFactory.from(choFormation),
                        FormationStrategyFactory.from(hanFormation))
        );

        return transactionManager.execute(conn ->
                gameRepository.save(conn, name, janggi));
    }

    public List<String> findAllNames() {
        return gameRepository.findAllNames();
    }

    public void surrender(String gameId) {
        Janggi janggi = findGameById(gameId);
        janggi.surrender();
        transactionManager.execute(conn -> {
            gameRepository.updateGameResult(conn, gameId, janggi);
            return null;
        });
    }

    public void draw(String gameId) {
        Janggi janggi = findGameById(gameId);
        janggi.draw();
        transactionManager.execute(conn -> {
            gameRepository.updateGameResult(conn, gameId, janggi);
            return null;
        });
    }

    public void validateTurn(String gameId, Position from) {
        Janggi janggi = findGameById(gameId);

        if (!janggi.isRunning()) {
            throw new IllegalStateException("이미 종료된 게임입니다.");
        }

        janggi.validateTurn(from);
    }

    public void play(String gameId, Position from, Position to) {
        Janggi janggi = findGameById(gameId);

        if (!janggi.isRunning()) {
            throw new IllegalStateException("이미 종료된 게임입니다.");
        }

        janggi.play(from, to);

        transactionManager.execute(conn -> {
            gameRepository.update(conn, gameId, janggi);
            return null;
        });
    }

    public Map<Position, Piece> getBoardStatus(String gameId) {
        return findGameById(gameId).getBoard();
    }

    public boolean isRunning(String gameId) {
        return findGameById(gameId).isRunning();
    }

    public String findGameByName(String gameName) {
        return gameRepository.findByName(gameName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));
    }

    public void deleteByName(String gameName) {
        String gameId = findGameByName(gameName);
        transactionManager.execute(conn -> {
            gameRepository.deleteById(conn, gameId);
            return null;
        });
    }

    public Camp getCurrentCamp(String gameId) {
        return findGameById(gameId).currentCamp();
    }

    public Camp getWinner(String gameId) {
        return findGameById(gameId).winner();
    }

    private Janggi findGameById(String gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));
    }
}
