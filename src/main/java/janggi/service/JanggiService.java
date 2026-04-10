package janggi.service;

import janggi.domain.JanggiGame;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.position.Position;
import janggi.dto.GameInformationDto;
import janggi.repository.JanggiRepository;
import janggi.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JanggiService {
    private final JanggiRepository janggiRepository;

    public JanggiService(JanggiRepository janggiRepository) {
        this.janggiRepository = janggiRepository;
    }

    public List<GameInformationDto> findAllGames() {
        try (Connection connection = DBConnectionManager.getConnection()) {
            return janggiRepository.findAll(connection);
        } catch (SQLException e) {
            throw new RuntimeException("게임 목록 조회 실패", e);
        }
    }

    public JanggiGame loadGame(int gameId) {
        try (Connection connection = DBConnectionManager.getConnection()) {
            return janggiRepository.findById(connection, gameId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 게임 방을 찾을 수 없습니다."));
        } catch (SQLException e) {
            throw new RuntimeException("게임 로드 실패", e);
        }
    }

    public JanggiGame createNewGame() {
        try (Connection connection = DBConnectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try {
                JanggiGame game = new JanggiGame(new Board(BoardFactory.generate()));
                JanggiGame savedGame = janggiRepository.save(connection, game);

                connection.commit();
                return savedGame;
            } catch (Exception e) {
                rollbackQuietly(connection);
                throw new RuntimeException("새 게임 생성 중 오류 발생", e);
            } finally {
                resetAutoCommit(connection);
            }
        } catch (SQLException e) {
            throw new RuntimeException("새 게임 생성 실패", e);
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
