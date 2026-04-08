package janggi.service;

import janggi.database.DatabaseConnection;
import janggi.model.Board;
import janggi.model.Janggi;
import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import janggi.model.position.Position;
import janggi.repository.GameRepository;
import janggi.repository.GimulRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class JanggiService {
    private final GameRepository gameRepository;
    private final GimulRepository gimulRepository;

    public JanggiService(GameRepository gameRepository, GimulRepository gimulRepository) {
        this.gameRepository = gameRepository;
        this.gimulRepository = gimulRepository;
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
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);
            saveWithTransaction(connection, gameId, board, currentTurn);
        } catch (SQLException e) {
            throw new IllegalStateException("DB 오류가 발생했습니다.", e);
        }
    }

    private void saveWithTransaction(Connection connection, Long gameId, Board board, Team currentTurn)
            throws SQLException {
        try {
            gameRepository.updateCurrentTurn(connection, gameId, currentTurn);
            gimulRepository.deleteAll(connection, gameId);
            gimulRepository.saveAll(connection, gameId, board.snapshot());
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public Long findIdByName(String name) {
        return gameRepository.findIdByName(name)
                .orElseThrow(() -> new IllegalArgumentException("게임을 찾을 수 없습니다."));
    }

    public void deleteGame(Long gameId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);
            deleteWithTransaction(connection, gameId);
        } catch (SQLException e) {
            throw new IllegalStateException("DB 오류가 발생했습니다.", e);
        }
    }

    private void deleteWithTransaction(Connection connection, Long gameId) throws SQLException {
        try {
            gimulRepository.deleteAll(connection, gameId);
            gameRepository.delete(connection, gameId);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }
}
