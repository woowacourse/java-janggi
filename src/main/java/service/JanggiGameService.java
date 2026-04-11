package service;

import domain.board.Board;
import domain.board.Placement;
import domain.piece.Side;
import domain.position.Position;
import janggigame.GameMetaData;
import janggigame.JanggiGameStatus;
import janggigame.result.LoadGameResult;
import janggigame.result.TurnResult;
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

    public Board loadOrInitBoard(GameMetaData gameMetaData) {
        return boardRepository.findByGameId(gameMetaData.getId())
                .orElseGet(Board::new);
    }

    public void placePiece(Board board, Side side, int code) {
        board.placePieces(side, Placement.from(code));
    }

    public void updateGameStatus(GameMetaData gameMetaData, JanggiGameStatus newStatus) {
        janggiGameRepository.updateGameStatusById(gameMetaData.getId(), newStatus);
    }

    public void completePlacement(Board board, GameMetaData gameMetaData, Side side, JanggiGameStatus newStatus) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                boardRepository.savePlacementByGameId(board, gameMetaData.getId(), side, connection);
                janggiGameRepository.updateGameStatusById(gameMetaData.getId(), newStatus, connection);
                connection.commit();
            } catch (IllegalStateException e) {
                connection.rollback();
                throw new IllegalStateException("상차림 도중 오류가 생겨 DB 반영에 실패하였습니다.", e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("트랜잭션을 시작하거나 종료하는 중 오류가 발생하였습니다.", e);
        }
    }

    public TurnResult processTurn(Position from, Position to, Board board, GameMetaData gameMetaData) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                pieceMoveProcess(board, from, to, gameMetaData, connection);
                boolean isIncreaseJangGunCount = updateAndStoreJangGunCount(board, gameMetaData, connection);
                updateTurn(gameMetaData, connection);
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

    private GameMetaData createNewGame() {
        return janggiGameRepository.save(GameMetaData.newGame());
    }

    private void pieceMoveProcess(Board board, Position from, Position to, GameMetaData gameMetaData, Connection connection) {
        boolean hasEnemyPieceAtTo = board.isBlocked(to) && !board.findBy(to).isSameSide(gameMetaData.getCurrentTurnSide());
        board.move(from, to, gameMetaData.getCurrentTurnSide());

        if (hasEnemyPieceAtTo) {
            boardRepository.deletePiecePositionByGameId(to, gameMetaData.getId(), connection);
        }
        boardRepository.updatePiecePositionByGameId(from, to, gameMetaData.getId(), connection);
    }

    private boolean updateAndStoreJangGunCount(Board board, GameMetaData gameMetaData, Connection connection) {
        boolean isIncreaseJangGunCount = checkAndUpdateJangGunCount(board, gameMetaData);
        janggiGameRepository.updateJangGunCountById(gameMetaData.getId(), gameMetaData.getJangGunCount(), connection);
        return isIncreaseJangGunCount;
    }

    private boolean checkAndUpdateJangGunCount(Board board, GameMetaData gameMetaData) {
        Side currentTurnSide = gameMetaData.getCurrentTurnSide();

        if (board.isJangGun(currentTurnSide)) {
            gameMetaData.incrementJangGunCount(currentTurnSide);
            return true;
        }
        gameMetaData.resetJangGunCount(currentTurnSide);
        return false;
    }

    private void updateTurn(GameMetaData gameMetaData, Connection connection) {
        janggiGameRepository.updateTurnById(gameMetaData.getId(), gameMetaData.getCurrentTurnSide(), connection);
    }
}
