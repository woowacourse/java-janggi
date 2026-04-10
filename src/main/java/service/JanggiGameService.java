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

import java.util.EnumMap;
import java.util.Map;

public class JanggiGameService {
    private static final int JANGGUN_COUNT = 5;

    private final JanggiGameRepository janggiGameRepository;
    private final BoardRepository boardRepository;
    private final Map<Side, Integer> jangGunCount = new EnumMap<>(Side.class);

    public JanggiGameService(JanggiGameRepository janggiGameRepository, BoardRepository boardRepository) {
        this.janggiGameRepository = janggiGameRepository;
        this.boardRepository = boardRepository;
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

    public void completePlacement(Board board, GameMetaData gameMetaData, Side side, JanggiGameStatus newStatus) {
        boardRepository.savePlacementByGameId(board, gameMetaData.id(), side);
        updateGameStatus(gameMetaData, newStatus);
    }

    public void updateGameStatus(GameMetaData gameMetaData, JanggiGameStatus newStatus) {
        janggiGameRepository.updateGameStatusById(gameMetaData.id(), newStatus);
    }

    public void restoreJangGunCount(GameMetaData gameMetaData) {
        jangGunCount.put(Side.CHO, gameMetaData.choJangGunCount());
        jangGunCount.put(Side.HAN, gameMetaData.hanJangGunCount());
    }

    public boolean isGameOver(Board board, Side currentTurnSide) {
        return isBigJang() || board.isEmptyGeneral(currentTurnSide);
    }

    public void move(Board board, Position from, Position to, Side currentTurnSide) {
        board.move(from, to, currentTurnSide);
    }

    public TurnResult processTurn(Position from, Position to, Long gameId, Board board, Side currentTurnSide) {
        pieceMoveProcess(board, from, to, gameId, currentTurnSide);
        currentTurnSide = changeSide(currentTurnSide);
        boolean isJangGun = updateAndStoreJangGunCount(board, gameId, currentTurnSide);
        updateTurn(gameId, currentTurnSide);
        return new TurnResult(currentTurnSide, isJangGun);
    }

    private void pieceMoveProcess(Board board, Position from, Position to, Long gameId, Side currentTurnSide) {
        boolean hasEnemyPieceAtTo = board.isBlocked(to) && !board.findBy(to).isSameSide(currentTurnSide);
        move(board, from, to, currentTurnSide);

        if (hasEnemyPieceAtTo) {
            boardRepository.deletePiecePositionByGameId(to, gameId);
        }
        boardRepository.updatePiecePositionByGameId(from, to, gameId);
    }

    private Side changeSide(Side currentTurnSide) {
        if (currentTurnSide == Side.HAN) return Side.CHO;
        return Side.HAN;
    }

    private void updateTurn(Long gameId, Side currentTurnSide) {
        janggiGameRepository.updateTurnById(currentTurnSide, gameId);
    }

    private boolean updateAndStoreJangGunCount(Board board, Long gameId, Side currentTurnSide) {
        boolean isJangGunCount = checkAndUpdateJangGunCount(board, currentTurnSide);
        janggiGameRepository.updateJangGunCountById(jangGunCount, gameId);
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

    private boolean isBigJang() {
        return jangGunCount.values().stream()
                .anyMatch(count -> count == JANGGUN_COUNT);
    }

    private GameMetaData createNewGame() {
        return janggiGameRepository.save(GameMetaData.newGame());
    }
}
