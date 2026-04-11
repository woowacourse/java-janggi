package janggi.infrastructure;

import janggi.domain.board.Board;
import janggi.domain.game.Players;
import janggi.domain.game.Turn;
import janggi.domain.repository.JanggiRepository;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class FakeJanggiRepository implements JanggiRepository {
    private final Map<Long, Board> boards = new ConcurrentHashMap<>();
    private final Map<Long, Players> playersMap = new ConcurrentHashMap<>();
    private final Map<Long, Turn> turns = new ConcurrentHashMap<>();
    private final Map<Long, Boolean> finishedStatus = new ConcurrentHashMap<>();

    // ID 생성용
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public Long save(Players players) {
        long id = idGenerator.incrementAndGet();
        playersMap.put(id, players);
        turns.put(id, players.getTurn());
        finishedStatus.put(id, false); // 진행 중
        return id;
    }

    @Override
    public void updateGameStatus(Long gameId, Board board, Turn turn) {
        boards.put(gameId, board);
        turns.put(gameId, turn);
    }

    @Override
    public Optional<Long> findInProgressGameId() {
        return finishedStatus.entrySet().stream()
                .filter(entry -> !entry.getValue())
                .map(Map.Entry::getKey)
                .max(Long::compare);
    }

    @Override
    public List<Long> findAllInProgressGameIds() {
        return finishedStatus.entrySet().stream()
                .filter(entry -> !entry.getValue())
                .map(Map.Entry::getKey)
                .toList();
    }

    @Override
    public Board findBoardById(Long gameId) {
        return Optional.ofNullable(boards.get(gameId))
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 보드가 없습니다: " + gameId));
    }

    @Override
    public Players findPlayersById(Long gameId) {
        Players savedPlayers = Optional.ofNullable(playersMap.get(gameId))
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 플레이어 정보가 없습니다: " + gameId));

        Turn currentTurn = turns.getOrDefault(gameId, savedPlayers.getTurn());

        return Players.fromSavedStatus(
                savedPlayers.getChoPlayerName(),
                savedPlayers.getHanPlayerName(),
                currentTurn.getSide()
        );
    }

    @Override
    public Turn findTurnById(Long gameId) {
        return Optional.ofNullable(turns.get(gameId))
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 턴 정보가 없습니다: " + gameId));
    }

    @Override
    public void finishGame(Long gameId) {
        finishedStatus.put(gameId, true);
    }
}
