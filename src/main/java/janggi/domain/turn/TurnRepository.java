package janggi.domain.turn;

import janggi.dto.TurnDto;

import java.util.Optional;

public class TurnRepository {

    private final TurnDao turnDao;

    public TurnRepository(TurnDao turnDao) {
        this.turnDao = turnDao;
    }

    public TurnDto findLastTurnByGameId(long gameId) {
        Optional<TurnDto> lastTurnDto = turnDao.findLastTurnByGameId(gameId);

        if (lastTurnDto.isEmpty()) {
            // 예외 발생하면 게임 ID 재입력받도록 수정해야함
            throw new IllegalArgumentException("해당 게임의 턴 데이터가 존재하지 않습니다.");
        }
        return lastTurnDto.get();
    }

    public void save(Turn turn, long gameId) {
        turnDao.save(TurnDto.from(turn, gameId));
    }
}
