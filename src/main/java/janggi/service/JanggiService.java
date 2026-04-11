package janggi.service;

import janggi.domain.Camp;
import janggi.domain.Janggi;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.FormationStrategyFactory;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.List;
import java.util.Map;

public class JanggiService {
    private final GameRepository gameRepository;

    public JanggiService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public String createGame(String name, int choFormation, int hanFormation) {
        Janggi janggi = Janggi.start(
                BoardFactory.create(
                        FormationStrategyFactory.from(choFormation),
                        FormationStrategyFactory.from(hanFormation))
        );
        return gameRepository.save(name, janggi);
    }

    public List<String> findAllNames() {
        return gameRepository.findAllNames();
    }

    public void surrender(String gameId) {
        Janggi janggi = findGameById(gameId);
        janggi.surrender();
        gameRepository.updateGameResult(gameId, janggi);
    }

    public void draw(String gameId) {
        Janggi janggi = findGameById(gameId);
        janggi.draw();
        gameRepository.updateGameResult(gameId, janggi);
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

        janggi.validateTurn(from);
        janggi.play(from, to);

        gameRepository.update(gameId, janggi);
    }

    public Map<Position, Piece> getBoardStatus(String gameId) {
        return findGameById(gameId).getBoard();
    }

    public boolean isRunning(String gameId) {
        return findGameById(gameId).isRunning();
    }

    private Janggi findGameById(String gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));
    }

    public String findGameByName(String gameName) {
        return gameRepository.findByName(gameName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));
    }

    public Camp getCurrentCamp(String gameId) {
        return findGameById(gameId).currentCamp();
    }

    public Camp getWinner(String gameId) {
        return findGameById(gameId).winner();
    }
}
