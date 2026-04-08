package janggi.service;

import janggi.domain.JanggiGame;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.position.Position;
import janggi.repository.JanggiRepository;
import janggi.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class JanggiService {
    private final JanggiRepository janggiRepository;

    public JanggiService(JanggiRepository janggiRepository) {
        this.janggiRepository = janggiRepository;
    }

    public JanggiGame joinGame() {
        try (Connection connection = DBConnectionManager.getConnection()) {
            Optional<JanggiGame> lastGame = janggiRepository.findInProgressGame(connection);

            if (lastGame.isPresent()) {
                return lastGame.get();
            }
            return startNewGame(connection);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 연결 실패", e);
        }
    }

    public void move(JanggiGame game, Position from, Position to) {
        try (Connection connection = DBConnectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try {
                game.move(from, to);
                janggiRepository.update(connection, game);
                connection.commit();
            } catch (Exception e) {
                rollbackQuietly(connection);
                throw new RuntimeException("게임 이동 중 오류 발생", e);
            } finally {
                resetAutoCommit(connection);
            }
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 연결 실패", e);
        }
    }

    private JanggiGame startNewGame(Connection connection) {
        JanggiGame game = new JanggiGame(new Board(BoardFactory.generate()));
        return janggiRepository.save(connection, game);
    }

    private void rollbackQuietly(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException ignored) {}
    }

    private void resetAutoCommit(Connection connection) {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException ignored) {}
    }
}
