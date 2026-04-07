package janggi.service;

import janggi.domain.JanggiGame;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Team;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import janggi.entity.GameEntity;
import janggi.entity.PieceEntity;
import janggi.repository.GameRepository;
import janggi.repository.PieceRepository;
import janggi.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JanggiService {
    private final GameRepository gameRepository;
    private final PieceRepository pieceRepository;

    public JanggiService(GameRepository gameRepository, PieceRepository pieceRepository) {
        this.gameRepository = gameRepository;
        this.pieceRepository = pieceRepository;
    }

    public JanggiGame joinGame() {
        try (Connection connection = DBConnectionManager.getConnection()) {
            connection.setAutoCommit(false);

            try {
                Optional<GameEntity> lastGame = gameRepository.findInProgressGame(connection);

                if (lastGame.isPresent() && "PROGRESS".equals(lastGame.get().getState())) {
                    JanggiGame game = resumeGame(lastGame.get(), connection);
                    connection.commit();
                    return game;
                }

                int newGameId = lastGame.map(g -> g.getGameId() + 1).orElse(1);
                JanggiGame game = startNewGame(newGameId, connection);

                connection.commit();
                return game;
            } catch (Exception e) {
                rollbackQuietly(connection);
                throw new RuntimeException("DB 업데이트 중 오류 발생", e);
            } finally {
                resetAutoCommit(connection);
            }
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 연결 실패", e);
        }
    }

    public void move(JanggiGame game, int gameId, Position from, Position to) {
        try (Connection connection = DBConnectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try {
                boolean isCaptured = game.getBoard().hasPieceAt(to);

                if (isCaptured) {
                    pieceRepository.deleteByPosition(connection, gameId, to.getRow(), to.getColumn());
                }

                pieceRepository.updatePosition(connection, gameId,
                        from.getRow(), from.getColumn(),
                        to.getRow(), to.getColumn());

                game.move(from, to);

                gameRepository.updateTurn(connection, gameId, game.getCurrentTeam().name());

                connection.commit();
            } catch (Exception e) {
                rollbackQuietly(connection);
                throw new RuntimeException("DB 업데이트 중 오류 발생", e);
            } finally {
                resetAutoCommit(connection);
            }
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 연결 실패", e);
        }
    }

    private JanggiGame startNewGame(int gameId, Connection connection) {
        JanggiGame game = new JanggiGame(new Board(BoardFactory.generate()));

        GameEntity gameEntity = new GameEntity(gameId, "PROGRESS", Team.CHO.name(), new java.sql.Date(System.currentTimeMillis()));
        gameRepository.save(connection, gameEntity);

        List<PieceEntity> pieces = toPieceEntities(game.getBoard(), gameId);
        pieceRepository.saveAll(connection, pieces);

        return game;
    }

    private JanggiGame resumeGame(GameEntity gameEntity, Connection connection) {
        List<PieceEntity> pieceEntities = pieceRepository.findAllByGameId(connection, gameEntity.getGameId());

        Map<Position, Piece> pieces = new HashMap<>();
        for (PieceEntity entity : pieceEntities) {
            Position position = Position.of(Row.of(entity.getRow()), Column.of(entity.getColumn()));

            Team team = Team.from(entity.getTeam());
            PieceType type = PieceType.from(entity.getType());

            pieces.put(position, new Piece(team, type));
        }

        Board board = new Board(pieces);
        Team currentTurn = Team.from(gameEntity.getTurn());

        return new JanggiGame(board, currentTurn);
    }

    private List<PieceEntity> toPieceEntities(Board board, int gameId) {
        return board.getBoard().entrySet().stream()
                .map(entry -> new PieceEntity(
                        gameId,
                        entry.getValue().getPieceType().name(),
                        entry.getValue().getTeam().name(),
                        entry.getKey().getRow(),
                        entry.getKey().getColumn()
                ))
                .toList();
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
