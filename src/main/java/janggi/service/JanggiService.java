package janggi.service;

import janggi.model.Janggi;
import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.gimul.AbstractGimul;
import janggi.model.position.Position;
import janggi.repository.GameRepository;
import janggi.repository.GimulRepository;
import java.util.List;
import java.util.Map;

public class JanggiService {
    private final GameRepository gameRepository;
    private final GimulRepository gimulRepository;
    private final TransactionManager transactionManager;

    public JanggiService(GameRepository gameRepository, GimulRepository gimulRepository,
                         TransactionManager transactionManager) {
        this.gameRepository = gameRepository;
        this.gimulRepository = gimulRepository;
        this.transactionManager = transactionManager;
    }

    public Long createGame(String name, Team currentTurn) {
        return gameRepository.save(currentTurn, name);
    }

    public boolean existsGame() {
        return !gameRepository.findAllNames().isEmpty();
    }

    public List<String> findAllGameNames() {
        return gameRepository.findAllNames();
    }

    public Janggi loadJanggiGameById(Long gameId) {
        Team currentTurn = gameRepository.findCurrentTurn(gameId)
                .orElseThrow(() -> new IllegalArgumentException("게임을 찾을 수 없습니다."));
        Map<Position, AbstractGimul> board = gimulRepository.findAll(gameId);
        return Janggi.of(new Board(board), currentTurn);
    }

    public void save(Long gameId, Board board, Team currentTurn) {
        transactionManager.execute(() -> {
            gameRepository.updateCurrentTurn(gameId, currentTurn);
            gimulRepository.deleteAll(gameId);
            gimulRepository.saveAll(gameId, board.snapshot());
        });
    }

    public Long findIdByName(String name) {
        return gameRepository.findIdByName(name)
                .orElseThrow(() -> new IllegalArgumentException("게임을 찾을 수 없습니다."));
    }

    public void deleteGame(Long gameId) {
        transactionManager.execute(() -> {
            gimulRepository.deleteAll(gameId);
            gameRepository.delete(gameId);
        });
    }

}
