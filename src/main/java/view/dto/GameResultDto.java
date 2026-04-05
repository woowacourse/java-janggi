package view.dto;

import domain.game.GameStatus;
import domain.pieces.Side;

public record GameResultDto(GameStatus status, Side winner) {
    public boolean isEnded() {
        return status == GameStatus.ENDED;
    }
}
