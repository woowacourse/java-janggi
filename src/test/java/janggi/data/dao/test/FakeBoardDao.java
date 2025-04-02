package janggi.data.dao.test;

import janggi.board.point.Point;
import janggi.data.dao.BoardDao;
import janggi.piece.Camp;
import janggi.piece.Piece;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class FakeBoardDao implements BoardDao {

    private final Map<Integer, Boolean> boards = new HashMap<>();
    private final Map<Integer, Camp> turnCamps = new HashMap<>();
    private final Map<Integer, Map<Point, Piece>> boardPieces = new HashMap<>();
    private final Map<Integer, LocalDateTime> boardCreatedAts = new HashMap<>();
    private final AtomicInteger boardId = new AtomicInteger(1);
    private int currentBoardId = 0;

    @Override
    public void create() {
        int newBoardId = boardId.getAndIncrement();
        boards.put(newBoardId, false);
        turnCamps.put(newBoardId, Camp.CHU);
        boardPieces.put(newBoardId, new HashMap<>());
        boardCreatedAts.put(newBoardId, LocalDateTime.now());
        currentBoardId = newBoardId;
    }

    @Override
    public void endGame() {
        if (boards.containsKey(currentBoardId)) {
            boards.put(currentBoardId, true);
            return;
        }
        throw new IllegalArgumentException("게임을 종료하는 도중 오류가 발생했습니다.");
    }

    @Override
    public boolean existsActiveGame() {
        return boards.containsKey(currentBoardId)
                && !boards.get(currentBoardId);
    }

    @Override
    public int findCurrentBoardId() {
        if (currentBoardId == 0) {
            throw new IllegalArgumentException("최신 보드판의 ID를 조회하는 도중 오류가 발생했습니다.");
        }
        return currentBoardId;
    }

    @Override
    public LocalDateTime findCurrentBoardCreatedAt() {
        if (!boardCreatedAts.containsKey(currentBoardId)) {
            throw new IllegalArgumentException("게임 시작 시간을 조회하는 도중 오류가 발생했습니다.");
        }
        return boardCreatedAts.get(currentBoardId);
    }

    @Override
    public Camp findCurrentTurnCamp() {
        if (!turnCamps.containsKey(currentBoardId)) {
            throw new IllegalArgumentException("현재 턴 진영을 조회하는 도중 오류가 발생했습니다.");
        }
        return turnCamps.get(currentBoardId);
    }

    @Override
    public void turnChange() {
        if (!turnCamps.containsKey(currentBoardId)) {
            throw new IllegalArgumentException("턴을 변경하는 도중 오류가 발생했습니다.");
        }
        Camp currentTurn = turnCamps.get(currentBoardId);
        if (currentTurn == Camp.CHU) {
            turnCamps.put(currentBoardId, Camp.HAN);
        }
        if (currentTurn == Camp.HAN) {
            turnCamps.put(currentBoardId, Camp.CHU);
        }
    }

    @Override
    public Map<Point, Piece> findCurrentBoardPieces() {
        if (!boardPieces.containsKey(currentBoardId)) {
            throw new IllegalArgumentException("현재 보드판의 기물 정보를 조회하는 도중 오류가 발생했습니다.");
        }
        return boardPieces.get(currentBoardId);
    }
}
