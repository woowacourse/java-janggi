package janggi.dto;

import janggi.domain.repository.GameInfo;

public record GameDto(
        Long id,
        String choName,
        String hanName,
        String currentTurn
) {

    public static GameDto from(GameInfo gameInfo) {
        return new GameDto(
                gameInfo.id(),
                gameInfo.choPlayerName(),
                gameInfo.hanPlayerName(),
                gameInfo.currentTurn()
        );
    }
}
