package dto;

import domain.place.piece.Side;

public record GameStateDto(long roomId, Side currentSide) {
    public static GameStateDto of(long roomId, Side currentSide) {
        return new GameStateDto(roomId, currentSide);
    }
}
