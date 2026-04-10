package repository;

import domain.piece.Camp;

public record GameData(long id, Camp currentTurn, boolean finished) {
}
