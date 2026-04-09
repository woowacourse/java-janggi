package janggi.domain;

import janggi.dto.TurnDto;

import java.util.Optional;

public class TurnService {

    private final TurnDao turnDao;

    public TurnService(TurnDao turnDao) {
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
}
