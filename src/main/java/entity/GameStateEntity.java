package entity;

import domain.place.piece.Side;

public record GameStateEntity(long roomId, Side currentSide) {
}
