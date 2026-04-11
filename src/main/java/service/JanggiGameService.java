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
import java.util.EnumMap;
import java.util.Map;

public class JanggiGameService {
    private static final int JANGGUN_COUNT = 5;

    private final JanggiGameRepository janggiGameRepository;
    private final BoardRepository boardRepository;
    private final DataSource dataSource;
    private final Map<Side, Integer> jangGunCount = new EnumMap<>(Side.class);

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
        return boardRepository.findByGameId(gameMetaData.id())
                .orElseGet(Board::new);
    }

    public void placePiece(Board board, Side side, int code) {
        board.placePieces(side, Placement.from(code));
    }

    // 트랜잭션
    public void completePlacement(Board board, GameMetaData gameMetaData, Side side, JanggiGameStatus newStatus) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                boardRepository.savePlacementByGameId(board, gameMetaData.id(), side, connection);
                janggiGameRepository.updateGameStatusById(gameMetaData.id(), newStatus, connection);
                connection.commit();
            } catch (IllegalStateException e) {
                connection.rollback();
                throw new IllegalStateException("상차림 도중 오류가 생겨 DB 반영에 실패하였습니다.", e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("트랜잭션을 시작하거나 종료하는 중 오류가 발생하였습니다.", e);
        }
    }

    public void restoreJangGunCount(GameMetaData gameMetaData) {
        jangGunCount.put(Side.CHO, gameMetaData.choJangGunCount());
        jangGunCount.put(Side.HAN, gameMetaData.hanJangGunCount());
    }

    public boolean isGameOver(Board board, Side currentTurnSide) {
        return isBigJang() || board.isEmptyGeneral(currentTurnSide);
    }

    public void updateGameStatus(GameMetaData gameMetaData, JanggiGameStatus newStatus) {
        janggiGameRepository.updateGameStatusById(gameMetaData.id(), newStatus);
    }

    // 트랜잭션 적용
    public TurnResult processTurn(Position from, Position to, Long gameId, Board board, Side currentTurnSide) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                pieceMoveProcess(board, from, to, gameId, currentTurnSide, connection);
                currentTurnSide = changeSide(currentTurnSide);
                boolean isJangGun = updateAndStoreJangGunCount(board, gameId, currentTurnSide, connection);
                updateTurn(gameId, currentTurnSide, connection);
                connection.commit();
                return new TurnResult(currentTurnSide, isJangGun);
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

    private void pieceMoveProcess(Board board, Position from, Position to, Long gameId, Side currentTurnSide, Connection connection) {
        boolean hasEnemyPieceAtTo = board.isBlocked(to) && !board.findBy(to).isSameSide(currentTurnSide);
        board.move(from, to, currentTurnSide);

        if (hasEnemyPieceAtTo) {
            boardRepository.deletePiecePositionByGameId(to, gameId, connection);
        }
        boardRepository.updatePiecePositionByGameId(from, to, gameId, connection);
    }

    private Side changeSide(Side currentTurnSide) {
        if (currentTurnSide == Side.HAN) return Side.CHO;
        return Side.HAN;
    }

    private boolean updateAndStoreJangGunCount(Board board, Long gameId, Side currentTurnSide, Connection connection) {
        boolean isJangGunCount = checkAndUpdateJangGunCount(board, currentTurnSide);
        janggiGameRepository.updateJangGunCountById(jangGunCount, gameId, connection);
        return isJangGunCount;
    }

    private boolean checkAndUpdateJangGunCount(Board board, Side currentTurnSide) {
        if (board.isJangGun(currentTurnSide)) {
            jangGunCount.put(currentTurnSide, jangGunCount.getOrDefault(currentTurnSide, 0) + 1);
            return true;
        }
        jangGunCount.put(currentTurnSide, 0);
        return false;
    }

    private void updateTurn(Long gameId, Side currentTurnSide, Connection connection) {
        janggiGameRepository.updateTurnById(currentTurnSide, gameId, connection);
    }

    private boolean isBigJang() {
        return jangGunCount.values().stream()
                .anyMatch(count -> count == JANGGUN_COUNT);
    }
}
