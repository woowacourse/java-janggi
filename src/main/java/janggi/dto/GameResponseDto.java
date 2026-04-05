package janggi.dto;

import janggi.domain.GameInfo;
import janggi.domain.Side;

public record GameResponseDto(int id, String name, String createdAt, String updatedAt, String side, int turn) {
    public GameInfo toGameInfo() {
        return new GameInfo(id, name, createdAt, updatedAt, Side.from(side), turn);
    }
}
