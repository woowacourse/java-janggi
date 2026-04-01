package entity;

import domain.place.piece.Side;

public record GameStateEntity(long roomId, Side currentSide) {

    public static GameStateEntity of(long roomId, Side currentSide){
        return new GameStateEntity(roomId, currentSide);
    }
}
