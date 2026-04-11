package service;

import domain.board.Board;
import domain.board.Placement;
import domain.piece.Side;
import domain.position.Position;
import domain.janggigame.Game;
import domain.janggigame.GameStatus;
import domain.janggigame.result.LoadGameResult;
import domain.janggigame.result.TurnResult;
import repository.BoardRepository;
import repository.JanggiGameRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JanggiGameService {
    private final JanggiGameRepository janggiGameRepository;
    private final BoardRepository boardRepository;
    private final DataSource dataSource;

    public JanggiGameService(JanggiGameRepository janggiGameRepository, BoardRepository boardRepository, DataSource dataSource) {
        this.janggiGameRepository = janggiGameRepository;
        this.boardRepository = boardRepository;
        this.dataSource = dataSource;
    }

    public LoadGameResult loadOrCreateNewGame() {
        return janggiGameRepository.findLatestUnfinishedGame()
                .map(game -> new LoadGameResult(game, false))
                .orElseGet(() -> new LoadGameResult(createNewGame(), true));
    }

    public Board loadOrInitBoard(Game game) {
        return boardRepository.findByGameId(game.getId())
                .orElseGet(Board::new);
    }

    public void placePiece(Board board, Side side, int code) {
        board.placePieces(side, Placement.from(code));
    }

    public void updateGameStatus(Game game, GameStatus newStatus) {
        janggiGameRepository.updateGameStatusById(game.getId(), newStatus);
    }

    public void completePlacement(Board board, Game game, Side side, GameStatus newStatus) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                boardRepository.savePlacementByGameId(board, game.getId(), side, connection);
                janggiGameRepository.updateGameStatusById(game.getId(), newStatus, connection);
                connection.commit();
            } catch (IllegalStateException e) {
                connection.rollback();
                throw new IllegalStateException("상차림 도중 오류가 생겨 DB 반영에 실패하였습니다.", e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("트랜잭션을 시작하거나 종료하는 중 오류가 발생하였습니다.", e);
        }
    }

    public TurnResult processTurn(Position from, Position to, Board board, Game game) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                pieceMoveProcess(board, from, to, game, connection);
                boolean isIncreaseJangGunCount = updateAndStoreJangGunCount(board, game, connection);
                updateTurn(game, connection);
                connection.commit();

                return new TurnResult(isIncreaseJangGunCount);
            } catch (IllegalStateException e) {
                connection.rollback();
                throw new IllegalStateException("턴 진행 도중에 오류가 발생하여 DB 반영에 실패하였습니다.", e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("트랜잭션을 시작하거나 종료하는 중 오류가 발생하였습니다.", e);
        }
    }

    private Game createNewGame() {
        return janggiGameRepository.save(Game.newGame());
    }

    private void pieceMoveProcess(Board board, Position from, Position to, Game game, Connection connection) {
        boolean hasEnemyPieceAtTo = board.isBlocked(to) && !board.findBy(to).isSameSide(game.getCurrentTurnSide());
        board.move(from, to, game.getCurrentTurnSide());

        if (hasEnemyPieceAtTo) {
            boardRepository.deletePiecePositionByGameId(to, game.getId(), connection);
        }
        boardRepository.updatePiecePositionByGameId(from, to, game.getId(), connection);
    }

    private boolean updateAndStoreJangGunCount(Board board, Game game, Connection connection) {
        boolean isIncreaseJangGunCount = checkAndUpdateJangGunCount(board, game);
        janggiGameRepository.updateJangGunCountById(game.getId(), game.getJangGunCount(), connection);
        return isIncreaseJangGunCount;
    }

    private boolean checkAndUpdateJangGunCount(Board board, Game game) {
        Side currentTurnSide = game.getCurrentTurnSide();

        if (board.isJangGun(currentTurnSide)) {
            game.incrementJangGunCount(currentTurnSide);
            return true;
        }
        game.resetJangGunCount(currentTurnSide);
        return false;
    }

    private void updateTurn(Game game, Connection connection) {
        janggiGameRepository.updateTurnById(game.getId(), game.getCurrentTurnSide(), connection);
    }
}
