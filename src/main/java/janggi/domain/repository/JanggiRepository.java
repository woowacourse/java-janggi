package janggi.domain.repository;

import janggi.domain.board.Board;
import janggi.domain.game.Players;
import janggi.domain.game.Turn;
import java.util.Optional;

public interface JanggiRepository {
    // 게임 생성
    Long save(Players players);

    // 턴 끝날 때마다 게임 상태 저장
    void updateGameStatus(Long gameId, Board board, Turn turn);

    // 진행 중인 가장 최근 게임 조회
    Optional<Long> findInProgressGameId();

    // 게임 복구 (도메인 정보 조회)
    Board findBoardById(Long gameId);

    Players findPlayersById(Long gameId);

    Turn findTurnById(Long gameId);

    // 게임 종료
    void finishGame(Long gameId);
}
